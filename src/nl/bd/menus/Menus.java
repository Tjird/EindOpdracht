package nl.bd.menus;

import java.util.*;

public enum Menus {

    MAIN(new ArrayList<>(Arrays.asList("Creeër account", "Inloggen"))),
    OPTIONS(new ArrayList<>(Arrays.asList("Laat al je sites zien", "Voeg een site toe", "Verwijder een site", "Wijzig je wachtwoord")));

    public final List<String> list;

    private Menus(List<String> list) {
        this.list = list;
    }

    public static String createMenu(Menus menu) {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < menu.list.size(); i++) {
            res.append("[").append(i + 1).append("] ").append(menu.list.get(i)).append("\n");
        }

        if (menu.equals(Menus.MAIN)) res.append("[0] Sluit af\n");
        else res.append("[0] Uitloggen\n");

        res.append("-=-=-=-=-=-=-=-=-=-=-=-=-=-").append("\n").append("Keuze: ");

        return res.toString();
    }

    public static String[] createAccount() {
//        Scanner input = "";

        return new String[]{"Username", "Wachtwoord"};
    }
}
