package nl.bd.menus.account;

import nl.bd.PasswordManager;
import nl.bd.menus.Menus;
import nl.bd.site.Site;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;

import java.util.Scanner;

/**
 * Deze class is voor het aanmaken van een nieuwe site.
 * Deze class is een menu class.
 * De input wordt gevalideerd en word gebruikt om de nieuwe site aan te maken.
 * De site wordt dan opgeslagen naar de list en de file.
 */
public class CreateSite {

    public CreateSite() {
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        // Aantal keren dat de gebruiker de input moet invoeren.
        int count = 0;

        // Wanneer de gebruiker zelf aangeeft dat hij/zij wilt stoppen of als
        // het correct is uitgevoerd wordt deze while loop gestopt, anders gaat deze door.
        while (!stop) {
            count++;

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

            System.out.println("Hoe heet de site die je wilt aanmaken? ");

            // Valideer of er een volgende input is.
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen geldige invoer");
                continue;
            }

            String siteName = scanner.nextLine();

            // Input moet langer zijn dan 3 karakters.
            if (siteName.length() < 3) {
                System.out.println("Geen geldige invoer, je moet meer dan 3 karakters invoeren.");
                continue;
            }

            // Input mag geen " // " bevatten, dit is een separator in de file.
            if (siteName.contains(" // ")) {
                System.out.println("Geen geldige invoer, je mag geen ' // ' in je site naam hebben.");
                continue;
            }

            System.out.println("Wat is de gebruikersnaam die je hierbij wilt opslaan? ");

            // Valideer of er een volgende input is.
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen geldige invoer");
                continue;
            }

            String username = scanner.nextLine();

            // Input moet langer zijn dan 3 karakters.
            if (username.length() < 3) {
                System.out.println("Geen geldige invoer, je moet meer dan 3 karakters invoeren.");
                continue;
            }

            // Input mag geen " // " bevatten, dit is een separator in de file.
            if (username.contains(" // ")) {
                System.out.println("Geen geldige invoer, je mag geen ' // ' in je username hebben.");
                continue;
            }

            System.out.println("Wat is het wachtwoord dat je hierbij wilt opslaan? ");

            // Valideer of er een volgende input is.
            if (!scanner.hasNextLine()) {
                scanner.next();
                System.out.println("Geen geldige invoer");
                continue;
            }

            String passwordRaw = scanner.nextLine();

            // Valideer of het wachtwoord correct is d.m.v. de checkPassword methode in Password class.
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
