package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.IRelaySystem;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TileEnergyRelay extends BlockEntity implements IRelaySystem {
    public TileEnergyRelay(BlockPos pos, BlockState state) {
        super(ModInit.relay.get(), pos, state);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TileEnergyRelay tile) {
        if(tile.isConnect()){
            tile.rays(pLevel);
            pLevel.playSound(pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 10, true), pPos, SoundEvents.ZOGLIN_ANGRY, SoundSource.AMBIENT, 1.0F, 1.0F);
        }
    }
    public boolean isConnect(){
        for(int disX = -10; disX <= 10; disX++){
            for(int disY = -10; disY <= 10; disY++) {
                for (int disZ = -10; disZ <= 10; disZ++) {
                    BlockEntity entity = level.getBlockEntity(getBlockPos().offset(disX, disY, disZ));
                    if (entity instanceof TileLuxiumCrystal con) {
                       if(con.getLux() > 0){
                           return true;
                       }
                    }
                }
            }
        }
        return false;
    }
    public void rays(Level level) {
        for (int disX = -10; disX <= 10; disX++) {
            for (int disY = -10; disY <= 10; disY++) {
                for (int disZ = -10; disZ <= 10; disZ++) {
                    BlockEntity entity = level.getBlockEntity(getBlockPos().offset(disX, disY, disZ));
                    if (entity instanceof TileEnergyRelay relay) {
                        BlockPos posRelay = relay.getBlockPos();
                        BlockPos pos = getBlockPos();

                        Vec3 directionVector = Vec3.atLowerCornerOf(posRelay).subtract(Vec3.atLowerCornerOf(pos)).normalize();
                        Vec3 startPos = Vec3.atLowerCornerOf(pos).add(new Vec3(0.5, 0.5, 0.5));
                        double randomX = (level.random.nextDouble() - 0.5) * 0.2;
                        double randomY = (level.random.nextDouble() - 0.5) * 0.2;
                        double randomZ = (level.random.nextDouble() - 0.5) * 0.2;
                        level.addParticle(ParticleTypes.ELECTRIC_SPARK, startPos.x + randomX, startPos.y + randomY, startPos.z + randomZ, directionVector.x, directionVector.y, directionVector.z);
                    }else if(entity instanceof TileMembraniumTotem relay) {
                        BlockPos posRelay = relay.getBlockPos();
                        BlockPos pos = getBlockPos();

                        Vec3 directionVector = Vec3.atLowerCornerOf(posRelay).subtract(Vec3.atLowerCornerOf(pos)).normalize();
                        Vec3 startPos = Vec3.atLowerCornerOf(pos).add(new Vec3(0.5, 0.5, 0.5));
                        double randomX = (level.random.nextDouble() - 0.5) * 0.2;
                        double randomY = (level.random.nextDouble() - 0.5) * 0.2;
                        double randomZ = (level.random.nextDouble() - 0.5) * 0.2;
                        level.addParticle(ParticleTypes.ELECTRIC_SPARK, startPos.x + randomX, startPos.y + randomY, startPos.z + randomZ, directionVector.x, directionVector.y, directionVector.z);
                    }
                }
            }
        }
    }
}