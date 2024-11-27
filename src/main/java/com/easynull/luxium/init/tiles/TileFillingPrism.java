package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IEnergyBlock;
import com.easynull.luxium.client.utils.ClientUtil;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.recipes.RecipePrism;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class TileFillingPrism extends TileHideInventory implements IEnergyBlock {
    double lux, tenebris = 1000, cons;
    final Map<EnergyType, Double> craftedMap;
    public boolean canCraft;

    public TileFillingPrism(BlockPos pos, BlockState state) {
        super(ModInit.prism.get(), pos, state);
        this.lux = 10;
        this.craftedMap = new LinkedHashMap<>();
    }
    @Override
    public double getEnergy(EnergyType type){
        return switch (type) {
            case lux -> Math.round((lux) * 10.0) / 10.0;
            case tenebris -> Math.round((tenebris) * 10.0) / 10.0;
        };
    }
    public double getEnergyMax() {
        return 6000;
    }
    @Override
    public void addEnergy(EnergyType type, double amount) {
        double currentEnergy = getEnergy(type);
        double newEnergy = Math.min(currentEnergy + amount, getEnergyMax());
        switch (type) {
            case lux -> lux = newEnergy;
            case tenebris -> tenebris = newEnergy;
        }
    }
    @Override
    public void removeEnergy(EnergyType type, double amount) {
        double currentEnergy = getEnergy(type);
        double newEnergy = Math.max(currentEnergy - amount, 0);
        switch (type) {
            case lux -> lux = newEnergy;
            case tenebris -> tenebris = newEnergy;
        }
    }
    @Override
    protected SimpleContainer createInv() {
        return new SimpleContainer(1){
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }
    public static void tick(Level level, BlockPos pos, BlockState state, TileFillingPrism tile) {
        Optional<RecipePrism> recipe = tile.getRecipe();
        if (!level.isClientSide() && recipe.isPresent() && tile.canCraft) {
            if (tile.allConsumeEnergy(recipe.get())) {
                tile.craftItem(pos, recipe.get());
                tile.canCraft = false;
            } else {
                level.playSound(null, pos, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, SoundSource.BLOCKS, 4.0f, 1.0f);
            }
        }
    }
    private boolean allConsumeEnergy(RecipePrism recipe) {
        for (Map.Entry<EnergyType, Double> entry : recipe.getEnergyMap().entrySet()) {
            double craftedEnergy = entry.getValue();
            double energyToConsume = Math.min(0.5, getEnergy(entry.getKey()));
            cons = craftedMap.getOrDefault(entry.getKey(), craftedMap.getOrDefault(entry.getKey(), 0.0) + cons);

            if (cons <= craftedEnergy) {
                cons += energyToConsume;
                craftedMap.put(entry.getKey(), cons);
                removeEnergy(entry.getKey(), energyToConsume);

                System.out.print(" Процесс наполнения " + entry.getKey().name().toUpperCase() + ": " + cons + " из " + craftedEnergy);
                return false;
            } else {
                cons = 0;
            }
        }
        craftedMap.clear();
        return true;
    }
    private void craftItem(BlockPos pos, RecipePrism recipe) {
        ItemStack stack = recipe.getResultItem();
        getInv().setItem(0, stack);
        level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 1.0f, 1.0f);
    }
    private Optional<RecipePrism> getRecipe() {
        return getLevel().getRecipeManager().getRecipeFor(RecipePrism.Type.INSTANCE, (SimpleContainer)getInv(), level);
    }
    @Override
    public void readNBT(CompoundTag tag) {
        super.readNBT(tag);
        cons = tag.getDouble("consumed");
        canCraft = tag.getBoolean("canCraft");
        lux = tag.getDouble(EnergyType.lux.name());
        tenebris = tag.getDouble(EnergyType.tenebris.name());
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        super.writeNBT(tag);
        tag.putDouble("consumed", cons);
        tag.putBoolean("canCraft", canCraft);
        tag.putDouble(EnergyType.lux.name(), lux);
        tag.putDouble(EnergyType.tenebris.name(), tenebris);
    }
}