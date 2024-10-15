package com.easynull.luxium.init.tiles;

import com.easynull.luxium.init.ModInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TileMembraniumTotem extends BlockEntity implements ITickable{
    public TileMembraniumTotem(BlockPos pos, BlockState state) {
        super(ModInit.membraniumTotem.get(), pos, state);
    }

    @Override
    public void tick() {

    }
}
