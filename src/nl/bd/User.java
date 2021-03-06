package nl.bd;

import nl.bd.site.Site;
import nl.bd.util.FileHandler;
import nl.bd.util.Password;

import java.util.List;

/**
 * Deze class is voor het User object dat gebruikt wordt om de gebruiker te
 * registreren en in te loggen.
 * Data van de user komt hier ook in te staan, dit gaat voornamelijk om de sites (List van sites).
 */

public class User {

    private final String username;
    private Password password;
    private List<Site> sites;

    public User(String username, Password password) {
        this.username = username;
        this.password = password;
        FileHandler fileHandler = new FileHandler(username);
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
        return !username.matches("^\\w{3,16}$");
    }

}