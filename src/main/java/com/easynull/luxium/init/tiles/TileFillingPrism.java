package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.items.ItemScepter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileFillingPrism extends TileHideInventory  {
    double energy;
    protected ItemStack stack = getItemHandler().getItem(0);

    public TileFillingPrism(BlockPos pos, BlockState state) {
        super(ModInit.prism.get(), pos, state);
    }
    public static void tick(Level level, BlockPos pos, BlockState pState, TileFillingPrism tile) {
        if (tile.stack.getItem() instanceof ItemScepter scepter){
            scepter.setEnergy(scepter.getDefaultInstance().getOrCreateTag(), EnergyType.LUX, 50);
        }
    }
    public double getEnergy(){
        return Math.round((energy) * 10.0) / 10.0;
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        for(EnergyType type : EnergyType.values()) {
            energy = tag.getDouble(type.name().toLowerCase());
        }
    }
    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        for(EnergyType type : EnergyType.values()) {
            tag.putDouble(type.name().toLowerCase(), energy);
        }
    }
    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1){
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }
}