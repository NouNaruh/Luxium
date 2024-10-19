package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IRelaySystem;
import com.easynull.luxium.client.Utils;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TileLuxiumCrystal extends BlockEntity {
    double lux = 1000.3 + Utils.rand.nextDouble(2000.9);;
    int tick;
    public TileLuxiumCrystal(BlockPos pos, BlockState state) {
        super(ModInit.clepsydra.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState pState, TileLuxiumCrystal tile) {
        if(++tile.tick % 60 == 0){
            level.playSound(null, pos, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.75F, level.random.nextFloat() * 0.1F + 0.9F);
        }
    }
    public double getLux(){
        return lux;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        lux = tag.getDouble("Energy_" + EnergyType.LUX.name().toLowerCase());
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("Energy_" + EnergyType.LUX.name().toLowerCase(), lux);
    }
}