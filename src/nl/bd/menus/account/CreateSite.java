package nl.bd.menus.account;

import nl.bd.PasswordManager;
import nl.bd.menus.Menus;
import nl.bd.site.Site;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;

import java.util.Scanner;

public class CreateSite {

    public CreateSite() {
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        int count = 0;

        while (!stop) {
            count++;

            if (count == 3) {
                System.out.println("Het lijkt wel alsof het je niet lukt... Wil je terug? (j/n)");
                String answer = scanner.nextLine();

                if (Menus.getYesList().contains(answer.toLowerCase())) {
                    stop = true;
                    continue;
                }

                count = 1;
            }

            System.out.println("Hoe heet de site die je wilt aanmaken? ");

            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen geldige invoer");
                continue;
            }

            String siteName = scanner.nextLine();

            if (siteName.length() < 3) {
                System.out.println("Geen geldige invoer, je moet meer dan 3 karakters invoeren.");
                continue;
            }

            System.out.println("Wat is de gebruikersnaam die je hierbij wilt opslaan? ");

            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen geldige invoer");
                continue;
            }

            String username = scanner.nextLine();

            if (username.length() < 3) {
                System.out.println("Geen geldige invoer, je moet meer dan 3 karakters invoeren.");
                continue;
            }

            System.out.println("Wat is het wachtwoord dat je hierbij wilt opslaan? ");

            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen geldige invoer");
                continue;
            }

            String passwordRaw = scanner.nextLine();

            if (!Password.checkPassword(passwordRaw)) {
                System.out.println("Geen geldige invoer, je moet minimaal 6 karakters bevatten.");
                continue;
            }

            Password password = new Password(passwordRaw, false);

            // Voeg de site toe aan de lijst met sites
            PasswordManager.getCurrentUser().getSites().add(new Site(siteName, username, password));
            // Update het bestand met de juiste waardes
            new FileHandler(PasswordManager.getCurrentUser().getUsername()).updateFile(PasswordManager.getCurrentUser());
            System.out.println("Site aangemaakt!\n");
            stop = true;
        }
    }

}
