package de.marcey.marceyapi.objects;

import java.util.Random;

public class Chance {

    private double chance;

    public Chance(double chance){
        this.chance = chance;
    }

    public boolean isChance(){
        Random rnd = new Random();
        double value = rnd.nextDouble(101);
        return value <= this.chance;
    }

    public double getPercent() {
        return chance;
    }

}
