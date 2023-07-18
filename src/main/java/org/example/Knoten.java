package org.example;

import java.util.ArrayList;
import java.util.List;
public class Knoten {
    private String name;
    private int dauer;
    public List<Knoten> vorgenger;
    public List<Knoten> nachvolger;
    public Knoten(String name, int dauer) {
        this.name = name;
        this.dauer = dauer;
        this.vorgenger = new ArrayList<>();
        this.nachvolger = new ArrayList<>();
    }
    public int getDauer() {
        return dauer;
    }
    public String getName() {
        return name;
    }
    public List<Knoten> getVorgenger() {
        return vorgenger;
    }
    public int getFAZ() {
        if (vorgenger.size() == 0) {
            return 0;
        } else {
            int maxFEZ = 0;
            for (Knoten k : getVorgenger()) {
                if (maxFEZ < k.getFEZ()) {
                    maxFEZ = k.getFEZ();
                }
            }
            return maxFEZ;
        }
    }
    public int getFEZ() {
        return getFAZ() + dauer;
    }
    public int getSEZ() {
        int min = 0;
        if (nachvolger.size() != 0) {
            for (Knoten n : nachvolger) {
                min=nachvolger.get(0).getSAZ();
                if (min > n.getSAZ()) {
                    min = n.getSAZ();
                }
            }return min;
        }return getFEZ();
    }
    public int getGP() {
        return getSAZ() - getFAZ();
    }
    public int getFP() {
        int FP = 0;
        if(nachvolger.size()!=0){
            int min = nachvolger.get(0).getFAZ();
            for (Knoten n : nachvolger) {
                if (min > n.getFAZ()) {
                    min = n.getFAZ();
                }
            }
            FP = min - getFEZ();
            return FP;
        }
        return FP;
    }
    public int getSAZ() {
        return getSEZ() - dauer;
    }
}