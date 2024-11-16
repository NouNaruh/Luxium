package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.api.energies.IEnergyBlock;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.items.ItemScepter;
import com.easynull.luxium.init.recipes.RecipePrism;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TileFillingPrism extends TileHideInventory implements IEnergyBlock {
    double lux = 6000, tenebris = 6000, craftedEnergy, maxEnergy = 6000;
    boolean canCraft = false;

    public TileFillingPrism(BlockPos pos, BlockState state) {
        super(ModInit.prism.get(), pos, state);
    }

    @Override
    public double getEnergy(EnergyType type){
        return switch (type) {
            case lux -> Math.round((lux) * 10.0) / 10.0;
            case tenebris -> Math.round((tenebris) * 10.0) / 10.0;
        };
    }

    public double getEnergyMax(){
       return maxEnergy;
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
        double newEnergy = Math.min(currentEnergy - amount, 0);
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
        if(!level.isClientSide()) {
            Map<EnergyType, Double> craftedMap = new HashMap<>();
            Optional<RecipePrism> recipe = tile.getRecipe();
            if (recipe.isPresent()) {
                for (Map.Entry<EnergyType, Double> entry : recipe.get().getEnergyMap().entrySet()) {
                    double energyToConsume = Math.min(0.5, tile.getEnergy(entry.getKey()));
                    double craftedEnergy = craftedMap.getOrDefault(entry.getKey(), entry.getValue());
                    if(craftedEnergy > 0) {
                        tile.removeEnergy(entry.getKey(), energyToConsume);
                        craftedEnergy -= energyToConsume;
                    } else if (craftedEnergy <= 0) {
                        tile.canCraft = true;
                        craftedEnergy = 0;
                    }
                    craftedMap.put(entry.getKey(), craftedEnergy);
                }
            }
        }
    }

    private Optional<RecipePrism> getRecipe() {
        return getLevel().getRecipeManager().getRecipeFor(RecipePrism.Type.INSTANCE, (SimpleContainer)getItemHandler(), level);
    }

    public void activateCraft(Player player) {
        Optional<RecipePrism> recipe = getRecipe();
        ItemStack stack = recipe.get().getResultItem();
        if (canCraft) {
            canCraft = false;
            getItemHandler().setItem(0, stack);
            getItemHandler().setChanged();
            this.level.playSound(player, this.getBlockPos(), SoundEvents.END_PORTAL_FRAME_FILL, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public void readNBT(CompoundTag tag) {
        super.readNBT(tag);
        craftedEnergy = tag.getDouble("crafted");
        lux = tag.getDouble(EnergyType.lux.name());
        tenebris = tag.getDouble(EnergyType.tenebris.name());
    }

    @Override
    public void writeNBT(CompoundTag tag) {
        super.writeNBT(tag);
        tag.putDouble("crafted", craftedEnergy);
        tag.putDouble(EnergyType.lux.name(), lux);
        tag.putDouble(EnergyType.tenebris.name(), tenebris);
    }
}