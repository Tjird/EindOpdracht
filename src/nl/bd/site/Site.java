package nl.bd.site;

import nl.bd.util.Password;

public class Site {

    public String name;
    public String username;
    public Password password;

    public Site(String site, String username,String password) {
        this.name = site;
        this.username = username;
        this.password = new Password(password, true);
    }

    public Site(String site, String username,Password password) {
        this.name = site;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}