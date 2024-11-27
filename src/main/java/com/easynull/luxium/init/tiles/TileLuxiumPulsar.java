package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IEnergyBlock;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileLuxiumPulsar extends TileMod {
    public final List<BlockPos> receivers;
    double lux;

    public TileLuxiumPulsar(BlockPos pos, BlockState state) {
        super(ModInit.pulsar.get(), pos, state);
        this.receivers = new ArrayList<>();
        this.lux = 923.354 + new Random().nextDouble(1854.9343);
    }
    public static void tick(Level level, BlockPos pos, BlockState pState, TileLuxiumPulsar tile) {
        tile.ambientTick();
        tile.receiversTick();
    }
    private void ambientTick() {
        for (int xx = -10; xx <= 10; ++xx) {
            for (int zz = -10; zz <= 10; ++zz) {
                for (int yy = -6; yy <= 6; ++yy) {
                    if (xx != 0 || zz != 0) {
                        int x = pX() + xx;
                        int y = pY() - yy;
                        int z = pZ() + zz;
                        BlockPos p = new BlockPos(x, y, z);
                        BlockEntity t = level.getBlockEntity(p);
                        if (t instanceof IEnergyBlock) {
                            receivers.add(p);
                        }
                    }
                }
            }
        }
        for (BlockPos p : receivers) {
            if (getLux() > 0) {
                level.playSound(null, p, SoundEvents.BASALT_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
                BlockPos thisPos = getBlockPos();
                float x = thisPos.getX();
                float y = thisPos.getY();
                float z = thisPos.getZ();

                if (x <= p.getX() && y <= p.getY() && z <= p.getZ()) {
                    x += 0.05F;
                    y += 0.05F;
                    z += 0.05F;
                }
                BlockPos pos = new BlockPos(x, y, z);
                Vec3 directionVector = Vec3.atLowerCornerOf(pos).add(new Vec3(0.5, 0.5, 0.5)).normalize();
                Vec3 startPos = Vec3.atLowerCornerOf(thisPos).add(new Vec3(0.5, 0.5, 0.5));
                level.addParticle(ParticleTypes.ELECTRIC_SPARK, startPos.x + directionVector.x, startPos.y  + directionVector.y, startPos.z + directionVector.z, directionVector.x, directionVector.y, directionVector.z);
            }
        }
    }
    private void receiversTick() {
        for(BlockPos p : receivers) {
            if (getLux() > 0) {
                BlockEntity t = level.getBlockEntity(p);
                if ((t instanceof IEnergyBlock rec)) {
                    rec.addEnergy(EnergyType.lux, Math.min(3.5, getLux()));
                    removeEnergy(Math.min(3.5, getLux()));
                }
            }
        }
        receivers.clear();
    }
    public void removeEnergy(double amount) {
        double currentEnergy = getLux();
        lux = Math.max(currentEnergy - amount, 0);
    }
    public double getLux() {
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