package nl.bd.menus;

import nl.bd.PasswordManager;
import nl.bd.User;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;
import java.util.Scanner;

public class Login {

    private String username = "";
    private Password password;

    public Login() {
        int count = 0; // Aantal pogingen
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);

        while (!stop) {
            count++;
            this.username = null;
            this.password = null;

            if (count == 3) {
                System.out.println("Het lijkt wel alsof het je niet lukt... Wil je terug? (j/n)");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja") || answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
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
            if (!User.checkUsername(username)) {
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

    public String getUsername() {
        return this.username;
    }

    public Password getPassword() {
        return this.password;
    }

}