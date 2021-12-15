package nl.bd.menus.signin;

import nl.bd.PasswordManager;
import nl.bd.User;
import nl.bd.menus.Menus;

import java.util.Scanner;

public class Main {

    public Main() {
        Scanner input = new Scanner(System.in);

        // Print het hoofd menu
        System.out.print(Menus.createMenu(Menus.MAIN));

        // Lees de gebruikers input en kijk of dit een getal is
        if (!input.hasNextInt()) {
            input.close();
            System.out.println("Foutive invoer, probeer opnieuw...\n");
            return;
        }

        int choice = input.nextInt();

        // Valideer dat het getal tussen 0 en de Menus.MAIN lengte is
        if (choice > Menus.MAIN.list.size() || choice < 0) {
            System.out.println("Keuze is niet geldig, probeer opnieuw...\n");
            return;
        }

        // Kijk welke keuze is gekozen
        switch (choice) {
            case 0:
                System.out.println("\nDoei!");
                PasswordManager.setMainStop(true);
                input.close();
                break;
            case 1:
                PasswordManager.clearScreen();
                Register registerMenu = new Register();

                if (registerMenu.getPassword() != null && registerMenu.getUsername() != null) {
                    PasswordManager.setCurrentUser(new User(registerMenu.getUsername(), registerMenu.getPassword()));
                }

                break;
            case 2:
                Login loginMenu = new Login();

                if (loginMenu.getUsername() != null && loginMenu.getPassword() != null) {
                    PasswordManager.setCurrentUser(new User(loginMenu.getUsername(), loginMenu.getPassword()));
                }

                break;
        }

    }

}
