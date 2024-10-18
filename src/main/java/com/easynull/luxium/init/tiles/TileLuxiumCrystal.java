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

public class TileLuxiumCrystal extends BlockEntity implements IRelaySystem {
    double lux = 1000.3 + Utils.rand.nextDouble(2000.9);;
    int tick;
    public TileLuxiumCrystal(BlockPos pos, BlockState state) {
        super(ModInit.clepsydra.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState pState, TileLuxiumCrystal tile) {
        if(++tile.tick % 60 == 0){
            level.playSound(null, pos, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.75F, level.random.nextFloat() * 0.1F + 0.9F);
        }
        tile.transferEnergy(5.6, level);
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
    public void transferEnergy(double maxTransfer, Level level){
        for(int disX = -10; disX <= 10; disX++){
            for(int disY = -10; disY <= 10; disY++) {
                for (int disZ = -10; disZ <= 10; disZ++) {
                    BlockEntity entity = level.getBlockEntity(getBlockPos().offset(disX, disY, disZ));
                    if (entity instanceof TileEnergyRelay relay) {
                        double receive = Math.min(getLux(), maxTransfer);
                        lux = getLux() - receive;
                        rays(level, relay);
                    }
                }
            }
        }
    }
    public void rays(Level level, BlockEntity e) {
        if (getLux() > 0) {
            if (e == null) return;
            BlockPos posRelay = e.getBlockPos();
            BlockPos pos = getBlockPos();
            int steps = 10;
            double stepX = posRelay.getX() - pos.getX();
            double stepY = posRelay.getY() - pos.getY();
            double stepZ = posRelay.getZ() - pos.getZ();
            Vec3 directionVector = Vec3.atLowerCornerOf(posRelay).subtract(Vec3.atLowerCornerOf(pos)).normalize();
            for (int i = 0; i < steps; i++) {
                double x = stepX * i + (level.random.nextDouble() - 0.5) * 0.2;
                double y = stepY * i + (level.random.nextDouble() - 0.5) * 0.2;
                double z = stepZ * i + (level.random.nextDouble() - 0.5) * 0.2;
                level.addParticle(ParticleTypes.ELECTRIC_SPARK, x, y, z, directionVector.x, directionVector.y, directionVector.z);
            }
        }
    }
}