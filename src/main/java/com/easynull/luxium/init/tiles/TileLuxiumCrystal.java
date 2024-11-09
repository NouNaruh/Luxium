package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class TileLuxiumCrystal extends TileMod {
    double lux = 1000.3 + new Random().nextDouble(2000.9);

    public TileLuxiumCrystal(BlockPos pos, BlockState state) {
        super(ModInit.crystal.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState pState, TileLuxiumCrystal tile) {

    }
    public double getLux(){
        return Math.round((lux) * 10.0) / 10.0;
    }

    @Override
    public void readNBT(CompoundTag tag) {
        super.readNBT(tag);
        lux = tag.getDouble(EnergyType.lux.name());
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        super.writeNBT(tag);
        tag.putDouble(EnergyType.lux.name(), lux);
    }
}