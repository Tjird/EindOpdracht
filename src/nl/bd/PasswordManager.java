package nl.bd;

import nl.bd.menus.PasswordChange;
import nl.bd.menus.Main;
import nl.bd.menus.Menus;

import java.util.Scanner;


public class PasswordManager {

    private static boolean mainStop = false;
    private static User currentUser = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (!mainStop) {

            if (currentUser != null) {
                System.out.print(Menus.createMenu(Menus.OPTIONS));

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        System.out.println("Je bent succesvol uitgelogd...");
                        currentUser = null;
                        break;
                    case 4:
                        new PasswordChange();
                        break;
                }

                continue;
            }

            new Main();
        }

        scanner.close();
    }

    public static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }

    public static void setMainStop(boolean mainStop) {
        PasswordManager.mainStop = mainStop;
    }

    public static void setCurrentUser(User currentUser) {
        PasswordManager.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
