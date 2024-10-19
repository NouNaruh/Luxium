package com.easynull.luxium.api.energies;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IRelaySystem {
    double getMaxEnergy();

    boolean canConnectTransferEnergy();
    boolean canConnectReceiveEnergy();

    void setEnergy(int energy);
    default CompoundTag getOrCreateEnergyTag(BlockEntity tile) {
        return tile.getTileData();
    }

    default double getSpace(BlockEntity tile) {
        return this.getMaxEnergy() - this.getEnergy(tile);
    }

    default double getEnergy(BlockEntity tile) {
        CompoundTag tag = this.getOrCreateEnergyTag(tile);
        return tag.getDouble("Energy_" + EnergyType.values().toString().toLowerCase());
    }

    default void setEnergy(BlockEntity tile, double energy) {
        CompoundTag tag = this.getOrCreateEnergyTag(tile);
        double stored = Math.min(this.getEnergy(tile), getMaxEnergy());
        stored += energy;
        tag.putDouble("Energy_" + EnergyType.values().toString().toLowerCase(), stored);
    }
}
