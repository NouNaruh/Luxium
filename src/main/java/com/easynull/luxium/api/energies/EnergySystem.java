package com.easynull.luxium.api.energies;

public class EnergySystem {
    public static double addEnergy(double energy, double add, double max) {
        if (energy >= 0) {
            energy = Math.round((energy + add) * 10.0) / 10.0;
        } else {
            energy = max;
        }
        return energy;
    }
    public static double removeEnergy(double energy, double remove){
        if (energy <= 0) {
            energy = Math.round((energy - remove) * 10.0) / 10.0;
        } else {
            energy = 0;
        }
        return energy;
    }
}
