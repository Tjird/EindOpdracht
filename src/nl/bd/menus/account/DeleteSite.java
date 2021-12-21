package nl.bd.menus.account;

import nl.bd.PasswordManager;
import nl.bd.menus.Menus;
import nl.bd.util.FileHandler;

import java.util.Scanner;

/**
 * Deze class is voor het verwijderen van een bestaande site.
 * Deze class is een menu class.
 * De input wordt gevalideerd en hierna wordt de site verwijderd.
 * De site wordt eerst verwijderd uit de list daarna uit de file.
 */

public class DeleteSite {

    public DeleteSite() {

        // Check of er een site bestaat in de list.
        if (PasswordManager.getCurrentUser().getSites().size() == 0) {
            System.out.println("Er zijn geen sites om te verwijderen.");
            return;
        }

        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        int count = 0; // Aantal pogingen

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

            int size = PasswordManager.getCurrentUser().getSites().size();
            System.out.println("Welke site wil je verwijderen? (1-" + size + ") ");

            // Valideer of er een volgende input is.
            if (!scanner.hasNextInt()) {
                System.out.println("Geen geldige invoer");
                scanner.next();
                continue;
            }

            int site = scanner.nextInt();

            // Valideer of de input een getal is tussen 1 en het aantal sites.
            if (site < 1 || site > size) {
                System.out.println("Geen geldige invoer, kies tussen 1 en " + size + ".\n");
                continue;
            }

            PasswordManager.getCurrentUser().getSites().remove(site - 1);
            // Update het bestand met de juiste waardes
            new FileHandler(PasswordManager.getCurrentUser().getUsername()).updateFile(PasswordManager.getCurrentUser());
            System.out.println("Site verwijderd\n");
            stop = true;
        }

    }

}
