package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.tile.ITickable;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileMembraniumTotem extends BlockEntity implements ITickable {

    public TileMembraniumTotem(BlockPos pos, BlockState state) {
        super(ModInit.membraniumTotem.get(), pos, state);
    }

    @Override
    public void tick() {
        getLevel().playSound(level.getNearestPlayer(getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 10, true), getBlockPos(), SoundEvents.BOOK_PUT, SoundSource.AMBIENT, 1.0F, 1.0F);
    }
}
