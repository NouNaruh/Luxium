package com.easynull.luxium.api.energies;

public interface IEnergyBlock {
    double getEnergy(EnergyType type);
    void addEnergy(EnergyType type, double amount);
    void removeEnergy(EnergyType type, double amount);
}
