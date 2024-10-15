package com.easynull.luxium.api.energies;

import net.minecraft.world.item.ItemStack;

public interface IMagic {
    default double getEnergy(ItemStack stack, EnergyType type){
        double energy = stack.getOrCreateTag().getDouble("Energy_" + type.name().toLowerCase());
        return Math.round(energy * 10.0) / 10.0;
    }
    double getMaxEnergy(ItemStack stack, EnergyType type);
}
