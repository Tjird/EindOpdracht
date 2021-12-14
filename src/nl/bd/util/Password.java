package nl.bd.util;

import java.util.Base64;

public class Password {

    private String encodedString;
    private String decodedString;

    public Password(String password, boolean encoded) {
        if (encoded) {
            this.encodedString = password;
            this.decodedString = decodePassword();
        } else {
            this.decodedString = password;
            this.encodedString = encodePassword();
        }
    }

    public String decodePassword() {
        byte[] decodedBytes = Base64.getDecoder().decode(this.encodedString);
        return new String(decodedBytes);
    }

    private String encodePassword() {
        return Base64.getEncoder().encodeToString(this.decodedString.getBytes());
    }

    public String encodePassword(String s) {
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public void setPassword(String password) {
        this.decodedString = password;
        this.encodedString = encodePassword();
    }

    public String getEncodedString() { return this.encodedString; }

    public String getDecodedString() { return this.decodedString; }

    // Valideer het wachtwoord doormiddel van een regex
    public static boolean checkPassword(String password) {
        return password.matches("^\\w{6,32}$");
    }

}