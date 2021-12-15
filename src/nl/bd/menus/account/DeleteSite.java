package nl.bd.menus.account;

import nl.bd.PasswordManager;
import nl.bd.menus.Menus;
import nl.bd.util.FileHandler;

import java.util.Scanner;

public class DeleteSite {

    public DeleteSite() {
        if (PasswordManager.getCurrentUser().getSites().size() == 0) {
            System.out.println("Er zijn geen sites om te verwijderen.");
            return;
        }

        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        int count = 0; // Aantal pogingen

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

            int size = PasswordManager.getCurrentUser().getSites().size();
            System.out.println("Welke site wil je verwijderen? (1-" + size + ") ");

            if (!scanner.hasNextInt()) {
                System.out.println("Geen geldige invoer");
                scanner.next();
                continue;
            }

            int site = scanner.nextInt();
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
