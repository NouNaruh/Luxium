package com.easynull.luxium.init.tiles;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;

public interface ITickable {
    void tick();
    static <T extends BlockEntity> BlockEntityTicker<T> getTickerHelper() {
        return (level0, pos0, state0, blockEntity) -> ((ITickable) blockEntity).tick();
    }
}
