package view;

import service.AuthService;
import util.InputUtils;

public class GuestView {
    private final AuthService authService;
    private final InputUtils inputUtils;
    public GuestView(AuthService authService, InputUtils inputUtils) {
        this.authService = authService;
        this.inputUtils = inputUtils;
    }
    public  void showGuestMenu() {
        System.out.println("========================");
        System.out.println("     HOTEL BOOKING");
        System.out.println("========================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.print("Choice: ");
    }

    public boolean show() {
        while (true) {

            showGuestMenu();

            int choice = inputUtils.readInt("");

            switch (choice) {

                case 1:
                    try {
                        authService.register();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    return true;

                case 2:
                    try {
                        authService.login();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                    return true;

                case 0:
                    return false;

                default:
                    System.out.println("Wrong choice");
            }
        }
    }
}
