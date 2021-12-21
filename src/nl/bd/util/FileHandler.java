package nl.bd.util;

import nl.bd.User;
import nl.bd.site.Site;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Deze class is voor het lezen en schrijven van bestanden.
 * De meest gebruikte methode is `updateFile(User user)`, dit wordt gebruikt input data in een bestand te schrijven.
 */

public class FileHandler {

    private final String userName;

    public FileHandler(String fileName) {
        this.userName = fileName;
    }

    // Maak een nieuwe file aan als deze nog niet bestaat
    private void createFile() {
        try {
            File file = new File(getPath());

            if (file.createNewFile()) {
//                System.out.println("File created: " + file.getName());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // Bekijk of het specifieke user bestand bestaat.
    public boolean checkIfUserFileExists() { return (new File(getPath())).exists(); }

    // Verkijg het wachtwoord van een user bestand
    public Password getPassword() {
        BufferedReader reader = null;
        String rawData = "";

        try {
            reader = new BufferedReader(new FileReader(getPath()));
            rawData = reader.readLine();

            if (rawData == null) {
                return null;
            }

            rawData = rawData.substring(8);

            if (!validateBase64(rawData)) {
                return null;
            }
        } catch (IOException e) {
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return new Password(rawData, true);
    }

    // Lees het bestand en maak een lijst van alle sites
    public List<Site> readSites() {
        List<Site> list = new ArrayList<>();
        BufferedReader reader = null;

        try {
            // BufferedReader om het bestand in te lezen
            reader = new BufferedReader(new FileReader(getPath()));
            String line;

            // Wanneer de eerst volgende regel bestaan word dit bekeken en eventueel toegevoegd aan de lijst.
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("$pass / ")) continue;

                String[] data = line.split(" \\/\\/ ");

                if (data.length < 3) continue;
                if (!validateBase64(data[2])) continue;

                list.add(new Site(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            System.out.println("Er ging iets mis bij het lezen van je account.");
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.out.println("Er ging iets mis bij het lezen van je account.");
                }
            }
        }

        return list;
    }

    private String getPath() {
        return "." + File.separator + this.userName + ".txt";
    }

    public String getPath(String userName) {
        return "." + File.separator + userName + ".txt";
    }

    // Creër een nieuwe user bestand met alleen het wachtwoord
    public void createUser(String encodedPassword) {
        createFile();

        FileWriter writer = null;

        try {
            writer = new FileWriter(getPath());
            writer.write("$pass / " + encodedPassword);
        } catch (IOException e) {
            System.out.println("Er ging iets mis bij het aanmaken van je account.");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Er ging iets mis bij het aanmaken van je account.");
                }
            }
        }
    }

    // Validate base64 with regular expression
    private boolean validateBase64(String base64) {
        return base64.matches("^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})$");
    }

    // Update het bestand met alle waardes die een user bevat
    public boolean updateFile(User user) {
        createFile();

        FileWriter writer = null;

        try {
            new PrintWriter(getPath(user.getUsername())).close();
        } catch (FileNotFoundException e) {
            System.out.println("Er ging iets mis bij het updaten van je account.");
            return false;
        }

        try {
            writer = new FileWriter(getPath());
            writer.write("$pass / " + user.getPassword().getEncodedString() + "\n");
            writer.write("\n");
            writer.write("<site> // <gebruikersnaam> // <wachtwoord>\n");
            for (Site site : user.getSites()) {
                writer.write(site.getName() + " // " + site.getUsername() + " // " + site.getPassword().getEncodedString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Er ging iets mis bij het updaten van je account.");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.out.println("Er ging iets mis bij het updaten van je account.");
                }
            }
        }

        return true;
    }
}
