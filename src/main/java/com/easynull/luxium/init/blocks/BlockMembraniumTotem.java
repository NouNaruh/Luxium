package com.easynull.luxium.init.blocks;

import com.easynull.luxium.api.energies.EnergyType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class BlockMembraniumTotem extends Block {
    private final EnergyType type;
    public BlockMembraniumTotem(EnergyType type) {
        super(Properties.of(Material.STONE));
        this.type = type;
    }
    public EnergyType getType(){
        return type;
    }
}
