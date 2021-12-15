package nl.bd.menus.signin;

import nl.bd.User;
import nl.bd.menus.Menus;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;

import java.util.Scanner;

public class Register {

    private String username = null;
    private Password password;

    public Register() {
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

                if (Menus.getYesList().contains(answer.toLowerCase())) {
                    stop = true;
                    continue;
                }

                count = 1;
            }

            // Verkrijg een gebruiksnaam waarmee je je account kan aanmaken
            System.out.print("Voer een gebruikersnaam in waarmee je wilt registeren: ");
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

            // Bekijk of gebruiker bestaat
            if (existsUser()) {
                System.out.println("Gebruiker bestaat al...");
                continue;
            }

            // Haal het wachtwoord op
            System.out.println("Wat wil je als wachtwoord? (minimaal 6 tekens)");
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

            // Haal het wachtwoord nogmaals op ter controle
            System.out.print("Vul je wachtwoord nogmaals in: ");
            // Valideer de input en zet deze in de variabele password
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen wachtwoord ingevuld...");
                continue;
            }

            passwordRaw = scanner.nextLine();

            // Valideer het wachtwoord doormiddel van een reguliere expressie
            if (!Password.checkPassword(passwordRaw)) {
                System.out.println("Wachtwoorden zijn niet gelijk aan elkaar...");
                continue;
            }

            // Valideer of de wachtwoorden gelijk zijn
            if (!comparePasswords(passwordRaw, password.getDecodedString())) {
                System.out.println("Wachtwoorden zijn niet gelijk aan elkaar...");
                continue;
            }

            // Maak een nieuw account aan
            FileHandler fileHandler = new FileHandler(this.username);
            fileHandler.createUser(this.password.getEncodedString());

            System.out.println("Account aangemaakt! Je kan nu inloggen.");
            stop = true;
        }
    }

    private boolean comparePasswords(String password, String password2) {
        return password.equals(password2);
    }

    // Bekijk of gebruiker bestaat
    private boolean existsUser() {
        FileHandler fileHandler = new FileHandler(this.username);

        return fileHandler.checkIfUserFileExists();
    }

    public String getUsername() {
        return this.username;
    }

    public Password getPassword() {
        return this.password;
    }
}
