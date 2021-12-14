package nl.bd.util;

import nl.bd.User;
import nl.bd.site.Site;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

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

    public void saveListToFile(List<User> listSites) {

    }

    //Read the file and return a list of sites
    private List<Site> readListFromFile() {
        List<Site> list = new ArrayList<>();
        Scanner reader = new Scanner(getPath());

        while(reader.hasNextLine()) {
            String rawData = reader.nextLine();
            String[] data = rawData.split("/\\|\\\\");

            if (rawData.startsWith("$pass / ")) continue;

            list.add(new Site(data[0], data[1]));
        }

        reader.close();

        return list;
    }

    private String getPath() {
        return "." + File.separator + this.userName + ".txt";
    }

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
}