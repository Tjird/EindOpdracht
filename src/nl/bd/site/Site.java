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

}