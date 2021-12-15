package nl.bd;

import nl.bd.site.Site;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;

import java.util.ArrayList;
import java.util.List;

public class User {

    private final String username;
    private Password password;
    private List<Site> sites = new ArrayList<>();
    private FileHandler fileHandler;

    public User(String username, String password) {
        this.username = username;
        this.password = new Password(password, false);
    }

    public User(String username, Password password) {
        this.username = username;
        this.password = password;
        this.fileHandler = new FileHandler(username);
        this.sites = fileHandler.readSites();
    }

    public List<Site> getSites() {
        return this.sites;
    }

    public String getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }

    // Validatie van de gebruikersnaam met een reguliere expressie
    public static boolean checkUsername(String username) {
        return username.matches("^\\w{3,16}$");
    }

}