package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.items.ItemScepter;
import com.easynull.luxium.init.recipes.RecipePrism;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class TileFillingPrism extends TileHideInventory  {
    double energy = 4000;
    protected ItemStack stack = getItemHandler().getItem(0);

    public TileFillingPrism(BlockPos pos, BlockState state) {
        super(ModInit.prism.get(), pos, state);
    }
    public static void tick(Level level, BlockPos pos, BlockState pState, TileFillingPrism tile) {
        if (tile.stack.getItem() instanceof ItemScepter){
            ((ItemScepter) tile.stack.getItem()).setEnergy(tile.stack.getItem().getDefaultInstance().getOrCreateTag(), EnergyType.LUX, 50);
        }
        Optional<RecipePrism> recipe = tile.getRecipe();
        if(recipe.isPresent()) {
            if (!tile.getItemHandler().getItem(0).isEmpty()) {
                if (tile.getEnergy() >= recipe.get().getEnergyInTick()) {
                    tile.energy = tile.getEnergy() - recipe.get().getEnergyInTick();
                    ItemStack stack = recipe.get().getResultItem();
                    tile.getItemHandler().setItem(0, stack);
                }
            }
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
    private Optional<RecipePrism> getRecipe() {
        for (int i = 0; i < getItemHandler().getContainerSize(); i++) {
            getItemHandler().setItem(i, getItemHandler().getItem(i));
        }
        return this.level.getRecipeManager().getRecipeFor(RecipePrism.Type.INSTANCE, createItemHandler(), level);
    }
    public boolean extractEnergy(double maxExtract) {
        double extractedEnergy = 0;
        if (extractedEnergy < maxExtract) {
            double energyToExtract = Math.min(0.5, maxExtract - extractedEnergy);
            energy -= energyToExtract;
            extractedEnergy += energyToExtract;
            return extractedEnergy >= maxExtract;
        }
        return false;
    }
}