package nl.bd.site;

import nl.bd.util.Password;

public class Site {

    public String name;
    public Password password;

    public Site(String site, String password) {
        this.name = site;
        this.password = new Password(password, true);
    }

}