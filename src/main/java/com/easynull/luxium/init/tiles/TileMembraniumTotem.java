package com.easynull.luxium.init.tiles;

import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileMembraniumTotem extends BlockEntity {

    public TileMembraniumTotem(BlockPos pos, BlockState state) {
        super(ModInit.membraniumTotem.get(), pos, state);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, TileMembraniumTotem tile) {
        pLevel.playSound(pLevel.getNearestPlayer(tile.getBlockPos().getX(), tile.getBlockPos().getY(), tile.getBlockPos().getZ(), 10, true), tile.getBlockPos(), SoundEvents.BOOK_PUT, SoundSource.AMBIENT, 1.0F, 1.0F);
    }
}
