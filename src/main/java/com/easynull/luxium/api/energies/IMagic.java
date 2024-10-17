package com.easynull.luxium.api.energies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IMagic {
    default double getEnergy(CompoundTag tag, EnergyType type){
        double energy = tag.getDouble("Energy_" + type.name().toLowerCase());
        return Math.round(energy * 10.0) / 10.0;
    }
    double getMaxEnergy(EnergyType type);
}
