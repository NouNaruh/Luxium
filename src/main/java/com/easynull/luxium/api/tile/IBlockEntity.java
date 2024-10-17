package com.easynull.luxium.api.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface IBlockEntity {
    BlockEntity getBlockEntity(BlockState state, BlockPos pos);
}
