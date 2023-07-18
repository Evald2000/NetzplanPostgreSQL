package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Methoden extends Main {
    public Scanner scan;
    public int nummer;
    private String row2 = "";
    private String row1="";

    public String vorgangsnummern;

    public int menu() {     //Hauptmenü

        System.out.println("Schreiben sie ein zahl rein, was sie mache wollen\n" +
                "1.Neues Netzplan für Projekt erstellen\n" +
                "2.Netzplan bearbeiten\n" +
                "3.Netzplan anzeigen \n");
        while(true){
            nummer=userEingabeInt("Bitte geben Sie eine Zahl.");
            if (nummer < 8 && nummer > 0) {
                return nummer;
            }
        }

    }

    public int menu2() {
        System.out.println("Was wills du machen \n" +
                "1. Ein Netzplan komplett löschen\n" +
                "2. Knoten löschen\n" +
                "3. Netzplan Name ändern\n" +
                "4. Knoten Name ändern\n"+
                "5. Netzplan Dauer ändern\n" +
                "6. Knoten vorgänger ändern\n"+
                "7. In Hauptmenu zurück gehen\n");
        while(true){
            nummer=userEingabeInt("Bitte geben Sie eine Zahl.");
            if (nummer < 8 && nummer > 0) {
                return nummer;
            }
        }
    }
    public int menu3(){
        System.out.println("Wählen sie Vorgänger aus \n" +
                "1. Vorgänger 1 \n" +
                "2. Vorgänger 2\n" +
                "3. Vorgänger 3\n");
        while(true){
            nummer=userEingabeInt("Bitte geben Sie eine Zahl.");
            if (nummer < 8 && nummer > 0) {
                return nummer;
            }
        }
    }


    public String userEingabeNP() {
        System.out.println("Bitte geben Sie Netzplan name ein");
        String nameNetzplan;
        scan = new Scanner(System.in);
        nameNetzplan = scan.nextLine();
        return nameNetzplan;
    }
    public String userEingabe(String text) {
        System.out.println(text);
        String nameNetzplan;
        scan = new Scanner(System.in);
        nameNetzplan = scan.nextLine();
        return nameNetzplan;
    }
    public String userEingabeKN() {
        System.out.println("Bitte geben Sie Knoten name ein");
        String nameNetzplan;
        scan = new Scanner(System.in);
        nameNetzplan = scan.nextLine();
        return nameNetzplan;
    }

    public int knotenDauer() {
        System.out.println("Bitte geben sie das Dauer an");
        scan=new Scanner(System.in);
        while (true)
            try {
                nummer = Integer.parseInt(scan.nextLine());
                break;
            }catch (NumberFormatException nfe) {
                System.out.println("Bitte geben Sie eine Zahl.");
            }
        return nummer;
    }
    public int userEingabeInt(String text) {
        System.out.println(text);
        scan=new Scanner(System.in);
        while (true)
            try {
                nummer = Integer.parseInt(scan.nextLine());
                break;
            }catch (NumberFormatException nfe) {
                System.out.println("Bitte geben Sie eine Zahl.");
            }
        return nummer;
    }

    public String checkZahl(int counter){//prüft ob eingabe von vorgänger mit kommas richtigen Format hat
        while (true) {
            System.out.println("Geben sie Vorgänger des Arbeitspaketes an,falls es mehrere Vorgänger gibt,trennen Sie die Angabe mit Komma, falls keine einfach 0 angeben: ");
            int[] numbers={};
            int vorgang=0;
            istZahl();
            int lengeListe=0;
            if(vorgangsnummern.contains(",")&&vorgangsnummern.contains("0")){continue;}
            else if(vorgangsnummern.contains(",")){
                String[] liste = vorgangsnummern.split(",");
                numbers=new int[liste.length];
                for (int i = 0; i < liste.length; i++) {
                    if(liste[i].equals("")){
                        numbers[i] =0;
                    }else{
                        numbers[i] = Integer.parseInt(liste[i]);}
                }lengeListe=liste.length;
            }else{
                vorgang = Integer.parseInt(vorgangsnummern);}
            int countComma=vorgangsnummern.length()-vorgangsnummern.replace(",","").length();
            if(countComma==2&& Arrays.toString(numbers).contains("0")){}
            else if ( vorgang < counter && !vorgangsnummern.contains(",") ) {break;}
            else if (countComma==1  && lengeListe==1 ) {}
            else if (countComma==1  && numbers[0]==  numbers[1]  ) {}
            else if (countComma==1 &&numbers[0] < counter && numbers[1] < counter  ) {break;}
            else if (countComma==2  && lengeListe==2 ) {}
            else if (  countComma==2 && (numbers[0] == numbers[1] ||numbers[2]==numbers[0] || numbers[2]==numbers[1] )) {}
            else if (  countComma==2 && numbers[0] < counter && numbers[1] < counter && numbers[2] < counter  ) {break;}
        }
        return vorgangsnummern;
    }

    public String istZahl(){//prüft ob die Eingabe von vorgänger ein Zahl ist
        while(true){
            vorgangsnummern = scan.nextLine();
            if(vorgangsnummern.length()>30){
                System.out.println("Zahl zu lang!!!");
            }else
            if(vorgangsnummern.equals(",")||vorgangsnummern.equals(",,")||vorgangsnummern.equals(",,,")||vorgangsnummern.equals(",,,,")||vorgangsnummern.equals(",,,,,")){
                System.out.println("Bitte geben Sie die Nummer des Vorgängers als Zahl an:");
            }else{
                break;
            }
        }
        while (!vorgangsnummern.matches("^[\\d|\\,]+")) {
            System.out.println("Bitte geben Sie die Nummer des Vorgängers als Zahl an:");
            vorgangsnummern = scan.nextLine();
        }

        return vorgangsnummern;
    }
    public void print () {//bei diese Methode kann Benutzer nach "stop" eingabe Knoten aufrufen
        boolean stop=true;
        while (true) {
            String frage = userEingabe("Um alle Pakete zu sehen, schreiben Sie: alle" + "\n" +
                    "Um einzelne Pakete zu sehen, schreiben Sie: einzelne");
            if (frage.equals("alle")) {
                for (int i = 0; i < knotens.size(); i++) {
                    nummer = i;
                    tabelePrint();
                }break;

            } else if (frage.equals("einzelne")) {
                while (stop) {
                    nummer = userEingabeInt("Welchen Arbeitspaket wollen Sie sehen?") - 1;
                    tabelePrint();

                    while (true) {
                        frage = userEingabe("weiter? ja/nein");
                        if (frage.equals("ja")) {
                            break;

                        } else if (frage.equals("nein")) {
                            stop = false;
                            break;
                        }
                    }
                }
                break; }
        }
    }
    public void tabelePrint(){  //Tabelle wird gedruck, die Zahlen in Tabelle können maximum 2 zeichen lang sein ohne dass die Tabelle sich vrhsciebt
        if (nummer < knotens.size()) {
            if (nummer < 10) {
                row1 = "│" + " " + (nummer+1) + "               │                                  │";
            } else if (nummer > 9) {
                row1 = "│" + " " + nummer + "              │                                  │";
            }

            if ((knotens.get(nummer).getDauer()) < 10 && (knotens.get(nummer).getGP()) < 10 && (knotens.get(nummer).getFP()) < 10) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "               │ " + (knotens.get(nummer).getGP()) + "              │ " + (knotens.get(nummer).getFP()) + "               │";
            } else if ((knotens.get(nummer).getDauer()) > 9 && (knotens.get(nummer).getGP()) > 9 && (knotens.get(nummer).getFP()) > 9) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "              │ " + (knotens.get(nummer).getGP()) + "             │ " + (knotens.get(nummer).getFP()) + "              │";
            } else if ((knotens.get(nummer).getDauer()) > 9 && (knotens.get(nummer).getGP()) < 10 && (knotens.get(nummer).getFP()) < 10) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "              │ " + (knotens.get(nummer).getGP()) + "              │ " + (knotens.get(nummer).getFP()) + "               │";
            } else if ((knotens.get(nummer).getDauer()) < 10 && (knotens.get(nummer).getGP()) > 9 && (knotens.get(nummer).getFP()) < 10) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "               │ " + (knotens.get(nummer).getGP()) + "             │ " + (knotens.get(nummer).getFP()) + "               │";
            } else if ((knotens.get(nummer).getDauer()) < 10 && (knotens.get(nummer).getGP()) < 10 && (knotens.get(nummer).getFP()) > 9) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "               │ " + (knotens.get(nummer).getGP()) + "              │ " + (knotens.get(nummer).getFP()) + "              │";
            } else if ((knotens.get(nummer).getDauer()) > 9 && (knotens.get(nummer).getGP()) > 9 && (knotens.get(nummer).getFP()) < 10) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "              │ " + (knotens.get(nummer).getGP()) + "             │ " + (knotens.get(nummer).getFP()) + "               │";
            } else if ((knotens.get(nummer).getDauer()) < 10 && (knotens.get(nummer).getGP()) > 9 && (knotens.get(nummer).getFP()) > 9) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "               │ " + (knotens.get(nummer).getGP()) + "             │ " + (knotens.get(nummer).getFP()) + "              │";
            } else if ((knotens.get(nummer).getDauer()) > 9 && (knotens.get(nummer).getGP()) < 10 && (knotens.get(nummer).getFP()) > 9) {
                row2 = "│" + " " + (knotens.get(nummer).getDauer()) + "              │ " + (knotens.get(nummer).getGP()) + "              │ " + (knotens.get(nummer).getFP()) + "              │";
            }
            int FAZ = knotens.get(nummer).getFAZ();
            int FEZ = knotens.get(nummer).getFEZ();
            int SAZ = knotens.get(nummer).getSAZ();
            int SEZ = knotens.get(nummer).getSEZ();
            System.out.println();
            System.out.println("Beschreibung:");
            System.out.println(knotens.get(nummer).getName());
            System.out.println();
            System.out.println(FAZ + "                                                     " + FEZ);
            System.out.println("┌─────────────────┬──────────────────────────────────┐");
            System.out.println(row1);
            System.out.println("├─────────────────┼────────────────┬─────────────────┤");
            System.out.println(row2);
            System.out.println("└─────────────────┴────────────────┴─────────────────┘");
            System.out.println(SAZ + "                                                     " + SEZ);
        }
    }

}