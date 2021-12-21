package nl.bd.site;

import nl.bd.util.Password;

/**
 * Deze class is voor het Site object dat gebruikt wordt om de
 * data van de site op te slaan.
 */

public class Site {

    public String name;
    public String username;
    public Password password;

    // Deze constructor is voor sites die al in de database zitten.
    public Site(String site, String username,String password) {
        this.name = site;
        this.username = username;
        this.password = new Password(password, true);
    }

    // Deze constructor is voor nieuwe sites.
    public Site(String site, String username,Password password) {
        this.name = site;
        this.username = username;
        this.password = password;
    }

    // Verkrijg de naam van de site.
    public String getName() {
        return name;
    }

    // Verkrijg de gebruikersnaam van de site.
    public String getUsername() {
        return username;
    }

    // Verkrijg het wachtwoord van de site.
    public Password getPassword() {
        return password;
    }
}