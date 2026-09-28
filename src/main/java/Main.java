import config.DatabaseInitializer;
import model.User;
import repository.InvoiceRepository;
import repository.PaymentRepository;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.jdbc.*;
import service.AuthService;
import service.ReservationService;
import service.RoomService;
import service.UserService;
import util.InputUtils;
import view.AdminView;
import view.ClientView;
import view.GuestView;

public class Main {
    public static void main(String[] args) {
        InputUtils inputUtils = new InputUtils();
        UserRepositoryJdbc userRepository = new UserRepositoryJdbc();
        RoomRepository roomRepository = new RoomRepositoryJdbc();
       ReservationRepository reservationRepository = new ReservationRepositoryJdbc();
       PaymentRepository paymentRepository = new PaymentRepositoryJdbc();
       InvoiceRepository invoiceRepository = new InvoiceRepositoryJdbc();

          AuthService authService = new AuthService(userRepository, inputUtils);
        ReservationService reservationService = new ReservationService(reservationRepository, authService,inputUtils,
                paymentRepository, invoiceRepository,userRepository);
       UserService userService = new UserService(userRepository,authService,inputUtils);
        RoomService roomService = new RoomService(roomRepository, reservationRepository, inputUtils);

        GuestView guestView = new GuestView(authService, inputUtils);
        ClientView clientView = new ClientView(authService,
                roomService,reservationService,roomRepository,inputUtils,userService);
       AdminView adminView = new AdminView(authService,roomService,inputUtils,
               roomRepository,userService,reservationService
               );
        //authService.autoLogin();

        boolean running = true;

        while (running) {

            User user = authService.getCurrentUser();

            if (user == null) {
                running = guestView.show();
            } else if (user.isAdmin()) {
                running = adminView.show();
            } else {
                running = clientView.show(user);
            }
        }
    }
}