package com.easynull.luxium.init.blocks;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.tile.IBlockEntity;
import com.easynull.luxium.init.tiles.TileClepsydraLight;
import com.easynull.luxium.init.tiles.TileMembraniumTotem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockMembraniumTotem extends Block implements IBlockEntity {
    private final EnergyType type;
    public BlockMembraniumTotem(EnergyType type) {
        super(Properties.of(Material.STONE));
        this.type = type;
    }
    @Override
    public BlockEntity getBlockEntity(BlockState state, BlockPos pos) {
        return new TileMembraniumTotem(pos, state);
    }
    public EnergyType getType() {
        return type;
    }
}
