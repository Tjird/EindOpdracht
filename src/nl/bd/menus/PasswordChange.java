package nl.bd.menus;

import nl.bd.PasswordManager;
import nl.bd.util.Password;

import java.util.Scanner;

public class PasswordChange {

    public PasswordChange() {
        Scanner scanner = new Scanner(System.in);

        int count = 0; // Aantal pogingen
        boolean stop = false;

        while (!stop) {
            count++;

            if (count == 3) {
                System.out.println("Het lijkt wel alsof het je niet lukt... Wil je terug? (j/n)");
                String answer = scanner.nextLine();

                if (answer.equalsIgnoreCase("j") || answer.equalsIgnoreCase("ja") || answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    stop = true;
                    continue;
                }

                count = 1;
            }

            // Vraag aan de gebruiker om zijn huidige wachtwoord
            System.out.print("Wat is je huidige wachtwoord?");
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen wachtwoord ingevuld...");
                continue;
            }
            String oldPassword = scanner.nextLine();

            // Bekijk of het wachtwoord correct is
            if (!oldPassword.equals(PasswordManager.getCurrentUser().getPassword().getDecodedString())) {
                System.out.println("Dit is niet je huidige wachtwoord...");
                continue;
            }

            // Vraag aan de gebruiker om zijn nieuwe wachtwoord
            System.out.println("Wat wil dat je nieuwe wachtwoord zal zijn? (minimaal 6 tekens)");
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen wachtwoord ingevuld...");
                continue;
            }

            String newPassword = scanner.nextLine();

            // Valideer het wachtwoord doormiddel van een reguliere expressie
            if (!Password.checkPassword(newPassword)) {
                System.out.println("Wachtwoord is niet geldig...");
                continue;
            }

            // Laat de gebruiker zijn nieuwe wachtwoord nog een keer invoeren
            System.out.print("Typ nogmaals je nieuwe wachtwoord: ");
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen wachtwoord ingevuld...");
                continue;
            }
            String newPassword2 = scanner.nextLine();

            // Controleer of de twee wachtwoorden gelijk zijn
            if (!newPassword.equals(newPassword2)) {
                System.out.println("De twee wachtwoorden komen niet overeen...");
                continue;
            }

            // Pas het wachtwoord aan
            PasswordManager.getCurrentUser().setPassword(new Password(newPassword, false));
            System.out.println("Wachtwoord is aangepast...");
        }


    }

}
