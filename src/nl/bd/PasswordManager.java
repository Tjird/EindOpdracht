package nl.bd;

import nl.bd.menus.account.CreateSite;
import nl.bd.menus.account.DeleteSite;
import nl.bd.menus.account.PasswordChange;
import nl.bd.menus.signin.MainMenu;
import nl.bd.menus.Menus;
import nl.bd.site.Site;

import java.util.List;
import java.util.Scanner;

/**
 * Deze klasse is de hoofdklasse van de applicatie.
 * Hierin wordt de applicatie gestart en het menu wordt weergegeven.
 * De variable currentUser is de huidige gebruiker, deze wordt gevuld met de gebruiker die ingelogd is.
 */
public class PasswordManager {

    private static boolean mainStop = false;
    private static User currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (!mainStop) {

            if (currentUser != null) {
                System.out.print(Menus.createMenu(Menus.OPTIONS));

                // Keuze variable voor dit menu
                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
                        // Laat een gebruiker uitloggen
                        System.out.println("Je bent succesvol uitgelogd...");
                        currentUser = null;

                        break;
                    case 1:
                        // Laat alle sites zien van een gebruiker
                        createTable();

                        break;
                    case 2:
                        // Open het menu om een site aan te maken
                        new CreateSite();

                        break;
                    case 3:
                        // Open het menu om een site te verwijderen
                        new DeleteSite();

                        break;
                    case 4:
                        // Open het menu om je wachtwoord te wijzigen
                        new PasswordChange();

                        break;
                }

                continue;
            }

            new MainMenu();
        }

        // Dit is de enige scanner object die wordt gesloten.
        // Andere scanner objecten worden niet gesloten vanwege dat anders alle scanner objecten gesloten worden.
        // System.in moet open blijven, anders kan de gebruiker niet meer data invoeren.
        scanner.close();
    }

    // Print een x aantal lege regels
    // Andere manieren werken niet in de console van intellij
    public static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }

    // Zet de mainStop variabele
    public static void setMainStop(boolean mainStop) {
        PasswordManager.mainStop = mainStop;
    }

    // Zet de currentUser variabele
    public static void setCurrentUser(User currentUser) {
        PasswordManager.currentUser = currentUser;
    }

    // Verkrijg de variable currentUser
    public static User getCurrentUser() {
        return currentUser;
    }

    // Toon alle sites van huidige gebruiker in tabel
    private static void createTable() {
        List<Site> sites = currentUser.getSites();

        // Als er geen sites zijn, geef een melding
        if (sites.size() == 0) {
            System.out.println("Je hebt nog geen sites aangemaakt.\n");
            return;
        }

        int countSizeSite = 4;
        int countSizeUsername = 8;
        int countSizePassword = 8;
        int maxSizeSite = 66;
        int maxSizeUsername = 30;
        int maxSizePassword = 32;

        // Loop door alle sites heen en bereken de grootte van de kolommen
        for (Site site : sites) {
            if (site.getName().length() > countSizeSite) {
                countSizeSite = Math.min(site.getName().length(), maxSizeSite);
            }

            if (site.getUsername().length() > countSizeUsername) {
                countSizeUsername = Math.min(site.getUsername().length(), maxSizeUsername);
            }

            if (site.getPassword().getDecodedString().length() > countSizePassword) {
                countSizePassword = Math.min(site.getPassword().getDecodedString().length(), maxSizePassword);
            }
        }

        String seperator = "+------+-" + "-".repeat(countSizeSite) + "-+-" + "-".repeat(countSizeUsername) + "-+-" + "-".repeat(countSizePassword) + "-+";

        System.out.println(seperator);
        System.out.println("| ID   | " +
                formatString("Site", countSizeSite, maxSizeSite) + " | " +
                formatString("Username", countSizeUsername, maxSizeUsername) + " | " +
                formatString("Password", countSizePassword, maxSizePassword) + " |"
        );
        System.out.println(seperator);

        // Loop door alle sites heen en print deze in de tabel
        for (int i = 0; i < sites.size(); i++) {
            Site site = sites.get(i);

            System.out.println("| " + formatString(i+1+"", 4, 5) + " | " +
                    formatString(site.getName(), countSizeSite, maxSizeSite) + " | " +
                    formatString(site.getUsername(), countSizeUsername, maxSizeUsername) + " | " +
                    formatString(site.getPassword().getDecodedString(), countSizePassword, maxSizePassword) + " |"
            );
        }

        System.out.println(seperator);
        System.out.println("\n");
    }

    // Formatteer string tot max grootte en extra spaties
    private static String formatString(String string, int size, int maxSize) {

        // Als de string langer is dan de maximum size, knip hem dan
        if (string.length() > maxSize) {
            string = string.substring(0, size - 3) + "...";
        }

        // Voeg extra spaties toe aan de string
        if (string.length() <= size) {
            string += " ".repeat(size - string.length());
        }

        return string;
    }

}
