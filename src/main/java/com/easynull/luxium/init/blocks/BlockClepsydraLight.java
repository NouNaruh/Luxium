package com.easynull.luxium.init.blocks;

import com.easynull.luxium.api.tile.IBlockEntity;
import com.easynull.luxium.init.tiles.TileClepsydraLight;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockClepsydraLight extends Block implements IBlockEntity {
    public BlockClepsydraLight() {
        super(BlockBehaviour.Properties.of(Material.GLASS));
    }
    @Override
    public BlockEntity getBlockEntity(BlockState state, BlockPos pos) {
        return new TileClepsydraLight(pos, state);
    }
}
