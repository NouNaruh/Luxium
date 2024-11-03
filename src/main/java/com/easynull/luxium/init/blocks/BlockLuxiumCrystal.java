package com.easynull.luxium.init.blocks;

import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.tiles.TileLuxiumCrystal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class BlockLuxiumCrystal extends BaseEntityBlock {
    public BlockLuxiumCrystal() {
        super(BlockBehaviour.Properties.of(Material.GLASS));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TileLuxiumCrystal(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> type) {
        return createTickerHelper(type, ModInit.crystal.get(), TileLuxiumCrystal::tick);
    }
}
