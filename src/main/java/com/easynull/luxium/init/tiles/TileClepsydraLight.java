package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.tile.ITickable;
import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileClepsydraLight extends BlockEntity implements ITickable {

    public TileClepsydraLight(BlockPos pos, BlockState state) {
        super(ModInit.clepsydra.get(), pos, state);
    }

    @Override
    public void tick() {
    }
}