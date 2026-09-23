import config.DatabaseInitializer;
import model.User;
import repository.ReservationRepository;
import repository.RoomRepository;
import repository.jdbc.ReservationRepositoryJdbc;
import repository.jdbc.RoomRepositoryJdbc;
import repository.jdbc.UserRepositoryJdbc;
import service.AuthService;
import service.RoomService;
import service.UserService;
import util.InputUtils;
import view.AdminView;
//import view.ClientView;
import view.GuestView;

public class Main {
    public static void main(String[] args) {
        InputUtils inputUtils = new InputUtils();
        UserRepositoryJdbc userRepository = new UserRepositoryJdbc();
        RoomRepository roomRepository = new RoomRepositoryJdbc();
       ReservationRepository reservationRepository = new ReservationRepositoryJdbc();
//        InputUtils inputUtils = new InputUtils();
          AuthService authService = new AuthService(userRepository, inputUtils);
//        AuthServiceReservationService authServiceReservationService = new AuthServiceReservationService(reservationRepository, authService);
       UserService userService = new UserService(userRepository,authService,inputUtils);
        RoomService roomService = new RoomService(roomRepository, reservationRepository, inputUtils);
//
//        authServiceReservationService.updateExpiredReservations(reservationRepository);
        GuestView guestView = new GuestView(authService, inputUtils);
//        ClientView clientView = new ClientView(authService,
//                roomService,authServiceReservationService,roomRepository,inputUtils,userService);
       AdminView adminView = new AdminView(authService,roomService,inputUtils,roomRepository,userService);
        authService.autoLogin();

        boolean running = true;

        while (running) {

            User user = authService.getCurrentUser();

            if (user == null) {
                running = guestView.show();
            } else if (user.isAdmin()) {
                running = adminView.show();
            } else {
                //running = clientView.show(user);
            }
        }
    }
}