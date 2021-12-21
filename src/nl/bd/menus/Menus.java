package nl.bd.menus;

import java.util.*;

/**
 * Dit is een enum met de mogelijke keuzes in meerdere menus.
 * Alleen de twee "hoofd" menus zijn hier beschikbaar.
 */

public enum Menus {

    // Enum item voor het hoofdmenu
    MAIN(new ArrayList<>(Arrays.asList("Creeër account", "Inloggen"))),
    // Enum item voor het menu wanneer je ingelogd bent
    OPTIONS(new ArrayList<>(Arrays.asList("Laat al je sites zien", "Voeg een site toe", "Verwijder een site", "Wijzig je wachtwoord")));

    public final List<String> list;
    // De yesList variable is nodig om te kunnen checken wat de keuze is om door te gaan of te stoppen.
    private static final List<String> yesList = new ArrayList<>(List.of("ja", "j", "y", "yes"));

    Menus(List<String> list) {
        this.list = list;
    }

    // Dit is een methode om dynamisch de keuzes in de menu's te kunnen tonen.
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

    // Verkrijg de yesList variable om te kunnen checken wat de keuze is om door te gaan of te stoppen.
    // Deze methode wordt door meerdere menu classes gebruikt.
    public static List<String> getYesList() {
        return yesList;
    }

}
