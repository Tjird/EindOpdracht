package nl.bd.menus.signin;

import nl.bd.PasswordManager;
import nl.bd.User;
import nl.bd.menus.Menus;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;
import java.util.Scanner;

/**
 * Deze class is voor het inloggen van een gebruiker.
 * Deze class is een menu class.
 * De input wordt gevalideerd en word gebruikt om te kijken of dit een bestaande gebruiker is en wachtwoord gelijk is.
 * De gebruiker wordt dan ingelogd.
 */

public class LoginMenu {

    private String username = "";
    private Password password;

    public LoginMenu() {
        int count = 0; // Aantal pogingen
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);

        while (!stop) {
            count++;
            this.username = null;
            this.password = null;

            // Optie om dit menu te beëindigen.
            if (count == 3) {
                System.out.println("Het lijkt wel alsof het je niet lukt... Wil je terug? (j/n)");
                String answer = scanner.nextLine();

                // Bekijken of de input een item is in de yesList.
                if (Menus.getYesList().contains(answer.toLowerCase())) {
                    stop = true;
                    continue;
                }

                count = 1;
            }

            // Haal iemand zijn gebruikersnaam op
            System.out.print("Vul je gebruikersnaam in: ");
            // Valideer de input en zet deze in de variabele username
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen gebruikersnaam ingevuld...");
                continue;
            }

            this.username = scanner.nextLine();

            // Valideer de gebruikersnaam doormiddel van een reguliere expressie
            if (User.checkUsername(username)) {
                System.out.println("Gebruikersnaam is niet geldig...");
                continue;
            }

            // Haal het wachtwoord op
            System.out.print("Vul je wachtwoord in: ");
            // Valideer de input en zet deze in de variabele password
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen wachtwoord ingevuld...");
                continue;
            }

            String passwordRaw = scanner.nextLine();

            // Valideer het wachtwoord doormiddel van een reguliere expressie
            if (!Password.checkPassword(passwordRaw)) {
                System.out.println("Wachtwoord is niet geldig...");
                continue;
            }

            this.password = new Password(passwordRaw, false);

            // Bekijk of gebruiker bestaat en wachtwoord gelijk is
            if (this.existsUser()) {
                PasswordManager.clearScreen();
                System.out.println("Welkom terug " + this.username + "!");
                stop = true;
            } else {
                System.out.println("Gebruikersnaam of wachtwoord is niet correct...");
                this.username = null;
                this.password = null;
            }


        }

    }

    // Bekijk of gebruiker bestaat en encoded wachtwoorden gelijk zijn
    private boolean existsUser() {
        FileHandler fileHandler = new FileHandler(this.username);

        if (!fileHandler.checkIfUserFileExists()) return false;

        return fileHandler.getPassword() != null && fileHandler.getPassword().getEncodedString().equals(this.password.getEncodedString());
    }

    // Haal de gebruikersnaam op
    public String getUsername() {
        return this.username;
    }

    // Haal het wachtwoord op (Password class)
    public Password getPassword() {
        return this.password;
    }

}