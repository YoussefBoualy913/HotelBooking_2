package service;

import db.DatabaseConnection;
import exception.*;
import model.*;
import model.enums.PaymentMethod;
import model.enums.ReservationStatus;
import policy.Refund.RefundPolicy;
import policy.pricing.PricingContext;
import repository.*;
import util.InputUtils;
import util.ValidationUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class ReservationService {

    private InputUtils inputUtils;
    private ReservationRepository reservationRepository;
    private AuthService authService;
    private PaymentRepository paymentRepository;
    private InvoiceRepository invoiceRepository;
    private UserRepository userRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              AuthService authService, InputUtils inputUtils,
                              PaymentRepository paymentRepository,
                               InvoiceRepository invoiceRepository,
                              UserRepository userRepository) {
       this.inputUtils =  inputUtils;
        this.reservationRepository = reservationRepository;
        this.authService = authService;
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
    }





    public void createReservation(User user, RoomRepository roomRepository) {

        String roomnumber = inputUtils.readString("Room nomber:");
        if (!ValidationUtils.isNotEmpty(roomnumber)) {
            throw new IllegalArgumentException("Room nomber cannot be empty");
        }

        System.out.println(roomnumber);
        Room room = roomRepository.findByRoomNumber(roomnumber)
                .orElseThrow(() -> new RoomNotFoundException(
                        "Room number does not exist"
                ));

        LocalDate checkIn;
        LocalDate checkOut;

        while (true) {

            checkIn = inputUtils.readDate("Check-in (YYYY-MM-DD): ");
            checkOut = inputUtils.readDate("Check-out (YYYY-MM-DD): ");

            if (!ValidationUtils.isValidDateRange(checkIn, checkOut)) {
                System.out.println("Check-in must be before check-out.");
                continue;
            }
            boolean isRoomAvailable = roomRepository.isRoomAvailable(room.getId(), checkIn, checkOut);

            if (!isRoomAvailable) {
                System.out.println(
                        "The room is not available for the selected period."
                );
                continue;
            }

            break;
        }

        int numberOfGuests;

        while (true) {
            numberOfGuests = inputUtils.readInt("Number of guests: ");

            if (numberOfGuests < 1) {
                System.out.println("Number of guests cannot be less than 1");
                continue;
            }

            if (numberOfGuests > room.getCapacity()) {
                System.out.println(
                        "Number of guests cannot be greater than room capacity"
                );
                continue;
            }

            break;
        }

        int numberOfNights =
                Math.toIntExact(ChronoUnit.DAYS.between(checkIn, checkOut));

        PricingContext pricingContext = new PricingContext(LocalDate.now(),checkIn,checkOut,room.getPricePerNight());
        BigDecimal totalPrice = new PricingService().calculateTotalPrice(pricingContext);

        Reservation reservation = new Reservation(UUID.randomUUID(),user.getId(), room.getId(), checkIn, checkOut, numberOfGuests,numberOfNights,ReservationStatus.CONFIRMED);
        Invoice invoice = new InvoiceService().createInvoice(totalPrice);
        Payment payment = new PaymentService().createPayment(reservation.getId(),invoice.getTotalTTC(), PaymentMethod.CARD);
        invoice.setPaymentId(payment.getId());
        if(user.getBalance().compareTo(invoice.getTotalTTC()) < 0){
            throw  new UserNotBalanceException("Insufficient balance:"+user.getBalance() +"DH Please contact the administrator or try another reservation.");
        }
        BigDecimal newBalance = user.getBalance().subtract(invoice.getTotalTTC());
        saveReservationTransaction(reservation,payment,invoice,newBalance);
    }

    public List<Reservation> getMyReservations(){
      return  reservationRepository.findByUserId(authService.getCurrentUser().getId());
    }
    public List<Reservation> getAllReservations(){

        if(!authService.getCurrentUser().isAdmin()) {
                throw new UnauthorizedException(
                        "Unauthorized access."
                );
        }
        return reservationRepository.findAll();
    }


    public Reservation reservationDetailes(String reservationCode) {

        User user =  authService.getCurrentUser();

        Reservation reservation = reservationRepository
                .findByCode(reservationCode)
                .orElseThrow(() ->
                        new ReservationNotFoundException(
                                "Reservation not found"
                        ));
        if(!user.isAdmin()) {
            if (!reservation.getUserId().equals(user.getId()) || reservation.getStatus() == ReservationStatus.CANCELLED) {
                throw new IllegalArgumentException(
                        "You cannot show this reservation."
                );
            }
        }
        return reservation;
    }

    public void cancelReservation() {
        String reservationCode = inputUtils.readString("Reservation code: ");
        User user =  authService.getCurrentUser();
        Reservation reservation = reservationRepository
                .findByCode(reservationCode)
                .orElseThrow(() ->
                        new ReservationNotFoundException("Reservation not found"));
        if(!user.isAdmin()) {
            if (!reservation.getUserId().equals(user.getId())) {
                throw new IllegalArgumentException(
                        "You cannot update this reservation."
                );
            }
        }
        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
            throw new IllegalArgumentException(
                    "Only confirmed reservations can be cancelled."
            );
        }

        Payment payment = paymentRepository
                .findByReservationId(reservation.getId())
                .orElseThrow(() ->
                        new RuntimeException("Payment not found")
                );
        BigDecimal amount = payment.getAmount();
       BigDecimal newBalance = RefundPolicy.calculateRefund(amount,reservation.getCheckIn().atStartOfDay());
       user.setBalance(user.getBalance().add(newBalance));
       cancelReservationTransaction(reservation,user);

    }

    public void cancelReservationTransaction(
            Reservation reservation,
            User user) {

        Connection connection =
                DatabaseConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            reservationRepository.cancel(reservation.getId());
            userRepository.updateBalance(user,user.getBalance());

            connection.commit();

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                e.addSuppressed(rollbackException);
            }

            throw new RuntimeException(
                    "Erreur lors de la modification de la réservation",
                    e
            );

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Impossible de réinitialiser la transaction",
                        e
                );
            }
        }
    }


    public void saveReservationTransaction(
            Reservation reservation,
            Payment payment,
            Invoice invoice,
            BigDecimal newBalance) {

        Connection connection =
                DatabaseConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            reservationRepository.save(reservation);
            paymentRepository.save(payment);
            invoiceRepository.save(invoice);
            userRepository.updateBalance(authService.getCurrentUser(),newBalance);
            connection.commit();

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                e.addSuppressed(rollbackException);
            }

            throw new RuntimeException(
                    "Erreur lors de la création de la réservation",
                    e
            );

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Impossible de réinitialiser la transaction",
                        e
                );
            }
        }
    }
}