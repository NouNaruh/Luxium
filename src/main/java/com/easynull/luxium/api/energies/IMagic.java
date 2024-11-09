package com.easynull.luxium.api.energies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IMagic {
    default double getEnergy(CompoundTag tag, EnergyType type){
        double energy = tag.getDouble(type.name());
        return Math.round(energy * 10.0) / 10.0;
    }
    double getMaxEnergy(EnergyType type);

    default void setEnergy(CompoundTag tag, EnergyType type, double amount) {
        tag.putDouble(type.name(), getEnergy(tag, type) + amount);
        if(getEnergy(tag, type) >= getMaxEnergy(type)){
            tag.putDouble(type.name(), getMaxEnergy(type));
        }
    }
}
