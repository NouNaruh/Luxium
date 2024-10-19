package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IRelaySystem;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TileEnergyRelay extends BlockEntity implements IRelaySystem {
    public double energy;
    public int fromX,fromY,fromZ;
    public int toX,toY,toZ;
    public boolean work = false;
    public TileEnergyRelay(BlockPos pos, BlockState state) {
        super(ModInit.relay.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TileEnergyRelay tile) {
        if (!level.isClientSide) {
            for (int x = -5; x <= 5; x++) {
                BlockPos pos1 = pos.offset(x, x, x);
                BlockEntity entity = level.getBlockEntity(pos1);
                if(entity instanceof IRelaySystem system){
                    tile.addRayEnergy(pos, pos1);
                    system.setEnergy((BlockEntity) system, 0.0002);
                    tile.work = true;
                }
            }
        }
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        energy = tag.getDouble("Energy_" + EnergyType.values().toString().toLowerCase());
        fromX = tag.getInt("fromX");
        fromY = tag.getInt("fromY");
        fromZ = tag.getInt("fromZ");
        toX = tag.getInt("toX");
        toY = tag.getInt("toY");
        toZ = tag.getInt("toZ");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putDouble("Energy_" + EnergyType.values().toString().toLowerCase(), energy);
        tag.putInt("fromX", fromX);
        tag.putInt("fromY", fromY);
        tag.putInt("fromZ", fromZ);
        tag.putInt("toX", toX);
        tag.putInt("toY", toY);
        tag.putInt("toZ", toZ);
    }

    @Override
    public double getEnergy(BlockEntity e) {
        return energy;
    }

    @Override
    public double getMaxEnergy() {
        return 1;
    }

    @Override
    public boolean canConnectTransferEnergy() {
        return false;
    }

    @Override
    public boolean canConnectReceiveEnergy() {
        return false;
    }

    @Override
    public void setEnergy(int energy) {

    }
    public void addRayEnergy(BlockPos pFrom, BlockPos pTo){
        CompoundTag tag = new CompoundTag();
        tag.putInt("fromX", pFrom.getX());
        tag.putInt("fromY", pFrom.getY());
        tag.putInt("fromZ", pFrom.getZ());

        tag.putInt("toX", pTo.getX());
        tag.putInt("toY", pTo.getY());
        tag.putInt("toZ", pTo.getZ());
    }
}