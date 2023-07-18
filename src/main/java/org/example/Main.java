package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<Knoten> knotens;

    public static void main(String[] args) throws SQLException {

        DatenbankMethoden datenbank = new DatenbankMethoden();
        Methoden methoden = new Methoden();
        //Connection zu den Datenbank
        datenbank.sqlConection();
        while (true) {

            //Ruft Methode wo man ein punkt aus menü auswählen kann
            int userEingabe = methoden.menu();
            if (userEingabe == 1) {//Menü/Netzplan erstellen
                String nameNetzplan = methoden.userEingabeNP();
                datenbank.createNetzplan(nameNetzplan);
                int counter = 0;
                while (true) {
                    String nameKnoten = methoden.userEingabe("Bitte geben Sie Name von den " + (counter + 1) + ". Knoten ein oder stop");
                    if (nameKnoten.contains("stop")) {
                        break;
                    } //Wenn user stop schreibt kommt er in Hauptmenü
                    int dauerKnoten = methoden.knotenDauer();
                    counter = counter + 1;
                    String vorgänger = methoden.checkZahl(counter);
                    if (vorgänger.equals("0")) {     // Erstellt Knoten ohne Vorgänger
                        datenbank.createKnoten(nameKnoten, dauerKnoten, counter, datenbank.idNetzplan(nameNetzplan));
                    } else if (vorgänger.contains(",")) {
                        String[] liste = vorgänger.split(",");
                        int[] numbers = getNumbers(liste);
                        int countComma = vorgänger.length() - vorgänger.replace(",", "").length();       //Zählt wieviele kommas String hat
                        if (countComma == 1) {         //Erstellt Knoten mit 2 Vorgämger(1 Komma)
                            datenbank.createKnotenMitVorgänger(nameKnoten, dauerKnoten, numbers[0], numbers[1], counter, datenbank.idNetzplan(nameNetzplan));
                        } else if (countComma == 2) {    //Erstellt KNoten mit 3 Vorgänger (2 Kommas)
                            datenbank.createKnotenMitVorgänger(nameKnoten, dauerKnoten, numbers[0], numbers[1], numbers[2], counter, datenbank.idNetzplan(nameNetzplan));
                        }
                    } else {
                        int numbers = Integer.parseInt(vorgänger);    // Erstellt Knoten mit einem Vorgänger
                        datenbank.createKnotenMitVorgänger(nameKnoten, dauerKnoten, numbers, counter, datenbank.idNetzplan(nameNetzplan));
                    }
                }

            } else if (userEingabe == 2) {              //Menü/Netzplan bearbaeiten
                int userEingabe2 = methoden.menu2();
                if (userEingabe2 == 1) {               //Menü/Netzplan bearbeiten/ Netzplan löschen
                    datenbank.deleteNetzplan(datenbank.idNetzplan(methoden.userEingabeNP()));
                } else if (userEingabe2 == 2) {       //Menü/Netzplan bearbeiten/ Knoten löschen
                    int idnp = datenbank.idNetzplan(methoden.userEingabeNP());
                    datenbank.deleteKnoten(idnp, datenbank.idKnoten(methoden.userEingabeKN(), idnp));
                } else if (userEingabe2 == 3) {       //Menü/Netzplan bearbeiten/ Netzplan name ändern
                    datenbank.changeNetzplanName(datenbank.idNetzplan(methoden.userEingabeNP()), methoden.userEingabe("Bitte geben Sie neue Netzplan name ein"));
                } else if (userEingabe2 == 4) {       //Menü/Netzplan bearbeiten/ Knoten Name ändern
                    datenbank.changeKnotenName(datenbank.idNetzplan(methoden.userEingabeNP()), methoden.userEingabeKN(), methoden.userEingabe("Bitte geben Sie neue Knoten name ein"));
                } else if (userEingabe2 == 5) {       //Knoten Dauer ändern
                    datenbank.changeKnotenDauer(datenbank.idNetzplan(methoden.userEingabeNP()), methoden.userEingabeKN(), methoden.knotenDauer());
                } else if (userEingabe2 == 6) {        //Knoten Vorgänger ändern
                    int userEingabe3 = methoden.menu3(); //Eingabe in Menü 3
                    int idnp = datenbank.idNetzplan(methoden.userEingabeNP());
                    String nameKnoten = methoden.userEingabeKN();
                    int kId = datenbank.idKnoten(nameKnoten, idnp);
                    while (true) {
                        int vorgaengerEingabe = methoden.userEingabeInt("Geben Sie neue wert für vorgänger");
                        if (vorgaengerEingabe == kId || vorgaengerEingabe > datenbank.idKnotenMax(idnp)) {
                        } else {
                            if (userEingabe3 == 1) {      //Ändert Vorgänger 1
                                datenbank.changeVorgänger(idnp, nameKnoten, vorgaengerEingabe, 1);
                                break;
                            } else if (userEingabe3 == 2) {     //Ändert Vorgänger 2
                                datenbank.changeVorgänger(idnp, nameKnoten, vorgaengerEingabe, 2);
                                break;
                            } else if (userEingabe3 == 3) {     //Ändert Vorgänger 3
                                datenbank.changeVorgänger(idnp, nameKnoten, vorgaengerEingabe, 3);
                                break;
                            }
                        }
                    }
                } else if (userEingabe2 == 7) {
                }       //In Hauptmenü rausgehen
            } else if (userEingabe == 3) {              //Menü/Netzplan anzeigen(Berechnen)
                int idNetzplan = datenbank.idNetzplan(methoden.userEingabe("Tipen Sie ein, name von Netzplan welche wollen Sie einzaigen lassen. "));
                int anzahlKnoten = datenbank.idKnotenMax(idNetzplan);
                knotens = new ArrayList<>();
                Knoten knoten;
                for (int i = 1; i <= anzahlKnoten; i++) {
                    knoten = new Knoten(datenbank.getNameKnoten(idNetzplan, i), datenbank.getDauerKnoten(idNetzplan, i));
                    for (int j = 1; j < 4; j++) {
                        int vorgänger = datenbank.getVorgänger(idNetzplan, i, j);
                        if (vorgänger != 0) {
                            knoten.vorgenger.add(knotens.get(vorgänger - 1));
                        }
                    }
                    knotens.add(knoten);
                    for (Knoten vorgengaknoten : knotens.get(knotens.size() - 1).getVorgenger()) {
                        vorgengaknoten.nachvolger.add(knotens.get(knotens.size() - 1));
                    }

                }
                methoden.print();

            }
        }
    }

    private static int[] getNumbers(String[] liste) {
        int[] numbers = new int[liste.length];
        for (int i = 0; i < liste.length; i++) {
            if (liste[i].equals("")) {
                numbers[i] = 0;//wird auf 0 gesetzt damit wenn zwischen kommas nichts ist der Benutzer nochmal abgefragt wird
            } else {
                numbers[i] = Integer.parseInt(liste[i]);        //Konventiert mit Komma getrente String in Int und fügt es zu int array(0-2)
            }
        }
        return numbers;
    }
}