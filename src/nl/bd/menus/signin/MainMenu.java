package nl.bd.menus.signin;

import nl.bd.PasswordManager;
import nl.bd.User;
import nl.bd.menus.Menus;

import java.util.Scanner;

/**
 * Deze class is hoofdmenu waar iedere gebruiker komt.
 * Deze class is een menu class.
 * De input wordt gevalideerd en word gebruikt om de keuze te bepalen daar deze heen wilt.
 * De gebruiker wordt dan eventueel doorgestuurd naar een ander menu.
 */

public class MainMenu {

    public MainMenu() {
        Scanner input = new Scanner(System.in);

        // Print het hoofd menu
        System.out.print(Menus.createMenu(Menus.MAIN));

        // Lees de gebruikers input en kijk of dit een getal is
        if (!input.hasNextInt()) {
            input.next();
            System.out.println("Foutieve invoer, probeer opnieuw...\n");
            return;
        }

        // Keuze variable voor dit menu
        int choice = input.nextInt();

        // Valideer dat het getal tussen 0 en de Menus.MAIN lengte is
        if (choice > Menus.MAIN.list.size() || choice < 0) {
            System.out.println("Keuze is niet geldig, probeer opnieuw...\n");
            return;
        }

        // Kijk welke keuze is gekozen
        switch (choice) {
            // Keuze om het programma te sluiten
            case 0:
                System.out.println("\nDoei!");
                PasswordManager.setMainStop(true);
                input.close();
                break;
            // Keuze om een nieuwe gebruiker aan te maken
            case 1:
                // Clear het scherm voor de netheid
                PasswordManager.clearScreen();
                // Registratie menu wordt hier gestart en de uitkomst hiervan word hier ook opgeslagen.
                RegisterMenu registerMenu = new RegisterMenu();

                // Bekijk of de gebruiker is geregistreerd met deze naam en wachtwoord
                if (registerMenu.getPassword() != null && registerMenu.getUsername() != null) {
                    // Zet de currentUser variable om het menu te laten werken
                    PasswordManager.setCurrentUser(new User(registerMenu.getUsername(), registerMenu.getPassword()));
                }

                break;
            // Keuze om in te loggen
            case 2:
                // Het login menu wordt hier gestart en de uitkomst hiervan word hier ook opgeslagen.
                LoginMenu loginMenu = new LoginMenu();

                // Bekijk of de gebruiker is geregistreerd met deze naam en wachtwoord
                if (loginMenu.getUsername() != null && loginMenu.getPassword() != null) {
                    // Zet de currentUser variable om het menu te laten werken
                    PasswordManager.setCurrentUser(new User(loginMenu.getUsername(), loginMenu.getPassword()));
                }

                break;
        }

    }

}
