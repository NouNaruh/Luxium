package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IEnergyBlock;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.recipes.RecipePrism;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;

public class TileFillingPrism extends TileHideInventory implements IEnergyBlock {
    double lux = 6000, tenebris = 6000, craftedEnergy;
    public boolean canCraft;

    public TileFillingPrism(BlockPos pos, BlockState state) {
        super(ModInit.prism.get(), pos, state);
        this.canCraft = false;
    }
    @Override
    public double getEnergy(EnergyType type){
        return switch (type) {
            case lux -> Math.round((lux) * 10.0) / 10.0;
            case tenebris -> Math.round((tenebris) * 10.0) / 10.0;
        };
    }
    public double getEnergyMax() {
        if (selectIdol() != null && selectIdol().size() <= 8){
            return 6000 * (1 + 0.2 * selectIdol().size());
        }
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
    protected SimpleContainer createItemHandler() {
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
                level.playSound(null, pos, SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, SoundSource.BLOCKS, 1.0f, 1.0f);
            }
        }
    }
    private boolean allConsumeEnergy(RecipePrism recipe) {
        Map<EnergyType, Double> craftedMap = new HashMap<>();
        for (Map.Entry<EnergyType, Double> entry : recipe.getEnergyMap().entrySet()) {
            EnergyType energyType = entry.getKey();
            double requiredEnergy = entry.getValue();
            double totalConsumed = 0.0;

            if (totalConsumed < requiredEnergy) {
                double energyToConsume = Math.min(1.5, getEnergy(energyType));
                if (energyToConsume <= 0) {
                    break;
                }
                totalConsumed += energyToConsume;
                craftedMap.put(energyType, craftedMap.getOrDefault(energyType, 0.0) + energyToConsume);
                removeEnergy(energyType, energyToConsume);

                System.out.print("Недостаточно энергии для " + energyType + ": " + totalConsumed + " из " + requiredEnergy);
                return false;
            }
        }
        return true;
    }
    private void craftItem(BlockPos pos, RecipePrism recipe) {
        ItemStack stack = recipe.getResultItem();
        getItemHandler().setItem(0, stack);
        level.playSound(null, pos, SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.BLOCKS, 1.0f, 1.0f);
    }
    public List<TileLuxiumCrystal> selectIdol(){
        List<TileLuxiumCrystal> list = new ArrayList<>();
        for(int x = 0; x <= 4; x++){
            BlockPos pos = getBlockPos().offset(x, x, x);
            list.add((TileLuxiumCrystal) level.getBlockEntity(pos));
        }
        return list;
    }
    private Optional<RecipePrism> getRecipe() {
        return getLevel().getRecipeManager().getRecipeFor(RecipePrism.Type.INSTANCE, (SimpleContainer)getItemHandler(), level);
    }
    @Override
    public void readNBT(CompoundTag tag) {
        super.readNBT(tag);
        canCraft = tag.getBoolean("canCraft");
        craftedEnergy = tag.getDouble("craftedEnergy");
        lux = tag.getDouble(EnergyType.lux.name());
        tenebris = tag.getDouble(EnergyType.tenebris.name());
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        super.writeNBT(tag);
        tag.putBoolean("canCraft", canCraft);
        tag.putDouble("craftedEnergy", craftedEnergy);
        tag.putDouble(EnergyType.lux.name(), lux);
        tag.putDouble(EnergyType.tenebris.name(), tenebris);
    }
}