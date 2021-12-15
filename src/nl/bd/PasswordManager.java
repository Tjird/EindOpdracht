package nl.bd;

import nl.bd.menus.account.CreateSite;
import nl.bd.menus.account.DeleteSite;
import nl.bd.menus.account.PasswordChange;
import nl.bd.menus.signin.Main;
import nl.bd.menus.Menus;
import nl.bd.site.Site;

import java.util.List;
import java.util.Scanner;


public class PasswordManager {

    private static boolean mainStop = false;
    private static User currentUser = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (!mainStop) {

            if (currentUser != null) {
                System.out.print(Menus.createMenu(Menus.OPTIONS));

                int choice = scanner.nextInt();

                switch (choice) {
                    case 0:
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

            new Main();
        }

        scanner.close();
    }

    // Print een x aantal lege regels
    public static void clearScreen() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }

    public static void setMainStop(boolean mainStop) {
        PasswordManager.mainStop = mainStop;
    }

    public static void setCurrentUser(User currentUser) {
        PasswordManager.currentUser = currentUser;
    }

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
