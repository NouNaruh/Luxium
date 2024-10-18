package com.easynull.luxium.init.tiles;

import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileClepsydraLight extends BlockEntity {
    public TileClepsydraLight(BlockPos pos, BlockState state) {
        super(ModInit.membraniumTotem.get(), pos, state);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TileClepsydraLight tile) {
        pLevel.playSound(pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 10, true), pPos, SoundEvents.ZOGLIN_ANGRY, SoundSource.AMBIENT, 1.0F, 1.0F);
    }
}