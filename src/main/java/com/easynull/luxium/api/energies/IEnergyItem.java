package com.easynull.luxium.api.energies;

import net.minecraft.nbt.CompoundTag;

public interface IEnergyItem {
    default double getEnergy(CompoundTag tag, EnergyType type){
        double energy = tag.getDouble(type.name());
        return Math.round(energy * 10.0) / 10.0;
    }
    double getMaxEnergy(EnergyType type);

    default void addEnergy(CompoundTag tag, EnergyType type, double amount) {
        double currentEnergy = getEnergy(tag, type);
        double newEnergy = Math.min(currentEnergy + amount, getMaxEnergy(type));
        tag.putDouble(type.name(), newEnergy);
    }

    default void removeEnergy(CompoundTag tag, EnergyType type, double amount) {
        double currentEnergy = getEnergy(tag, type);
        double newEnergy = Math.min(currentEnergy - amount, 0);
        tag.putDouble(type.name(), newEnergy);
    }
}
