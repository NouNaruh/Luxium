package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.client.Utils;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileLuxiumCrystal extends BlockEntity {
    double lux = 1000.3 + Utils.rand.nextDouble(2000.9);

    public TileLuxiumCrystal(BlockPos pos, BlockState state) {
        super(ModInit.crystal.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState pState, TileLuxiumCrystal tile) {

    }
    public double getLux(){
        return Math.round((lux) * 10.0) / 10.0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        lux = tag.getDouble(EnergyType.LUX.name().toLowerCase());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble(EnergyType.LUX.name().toLowerCase(), lux);
    }
}