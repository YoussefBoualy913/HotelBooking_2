package view;

import model.Reservation;
import model.User;
import repository.RoomRepository;
import service.AuthService;
import service.ReservationService;
import service.RoomService;
import service.UserService;
import util.InputUtils;

import java.util.List;

public class ClientView {
   private final AuthService authService;
   private final RoomService roomService;
   private final ReservationService reservationService;
   private final RoomRepository roomRepository;
   private final InputUtils inputUtils;
   private final UserService userService;
   public ClientView(AuthService authService,
                     RoomService roomService,
                     ReservationService reservationService,
                     RoomRepository roomRepository,
                     InputUtils inputUtils,
                     UserService userService
                    ) {
       this.authService = authService;
       this.roomService = roomService;
       this.reservationService = reservationService;
       this.inputUtils = inputUtils;
       this.roomRepository = roomRepository;
       this.userService = userService;

   }
    public void showClientMenu(User user) {
        System.out.println("================================");
        System.out.println("Logged in as: " + user.getFullName());
        System.out.println("================================");
        System.out.println("1. Search available rooms");
        System.out.println("2. View all rooms");
        System.out.println("3. Create reservation");
        System.out.println("4. My reservations");
        System.out.println("5. Reservation details");
        System.out.println("6. Update reservation");
        System.out.println("7. Cancel reservation");
        System.out.println("8. Update profile");
        System.out.println("9. Change password");
        System.out.println("10. Logout");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    public boolean show(User user) {
        while (true) {

            showClientMenu(user);

            int choice = inputUtils.readInt("");

            switch (choice) {

                case 1:
                    roomService.searchAvailableRooms();
                    break;

                case 2:
                    roomService.showRooms();
                    return true;

                case 3:
                    try {
                        reservationService.createReservation(authService.getCurrentUser(),roomRepository);
                        System.out.println("Reservation Created");
                    }catch (Exception e){
                        System.out.println("errur:"+e.getMessage());
                    }
                    return true;
                case 4:
                    try {
                        List<Reservation> reservations =
                                reservationService.getMyReservations();

                        if (reservations.isEmpty()) {
                            System.out.println("You have no reservations.");
                        } else {
                            System.out.println("You have " + reservations.size() + " reservations.");
                            reservations.forEach(System.out::println);
                        }
                    }catch (Exception e){
                        System.out.println("errur:"+e.getMessage());
                    }
                    return true;
                case 5:
                    try {
                        String reservationCode =  inputUtils.readString("Reservation code: ");
                        Reservation reservation  = reservationService.reservationDetailes(reservationCode);
                        System.out.println();
                        System.out.println("⫷ RESERVATION DETAILS ⫸\n" +
                                "* Code:" +reservation.getReservationCode()+"\n"+
                                "* User:" +authService.getCurrentUser().getFullName()+"\n"+
                                "* Check-in:" +reservation.getCheckIn()+"\n"+
                                "* Check-out:" +reservation.getCheckOut()+"\n"+
                                "* Guests:" +reservation.getNumberOfGuests()+"\n"+
                                "* Nights:" +reservation.getNumberOfNights()+"\n"+
                                "* Status:" +reservation.getStatus()+"\n"+
                                "* Reserver at:"+reservation.getCreatedAt()+"\n"+
                                "");
                    }catch (Exception e){
                        System.out.println("errur:"+e.getMessage());
                    }
                    return true;
//                case 6:
//                    try {
//                        authServiceReservationService.updateReservation(authService,roomRepository);
//                        System.out.println("Reservation Updated");
//                    }catch (Exception e){
//                        System.out.println("errur:"+e.getMessage());
//                    }
//                    return true;
                case 7:
                    try {
                        reservationService.cancelReservation();
                        System.out.println("Reservation Cancelled");
                    }catch (Exception e){
                        System.out.println("errur:"+e.getMessage());
                    }
                    return true;
                case 8:
                    try {
                        userService.updateProfile();
                        System.out.println("Profile Updated");
                    }catch (Exception e){
                        System.out.println("errur:"+e.getMessage());
                    }
                    return true;
                case 9:
                    try {
                        userService.changePassword();
                        System.out.println("Password Changed");
                    }catch (Exception e){
                        System.out.println("errur:"+e.getMessage());
                    }
                    return true;
                case 10:
                    authService.logout();
                    return true;
                case 0:
                    return false;
                default:
                    System.out.println("Wrong choice");
            }
        }
    }

}
