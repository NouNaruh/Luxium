package com.easynull.luxium.init.tiles;

import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.ModInit;
import com.easynull.luxium.init.items.ItemScepter;
import com.easynull.luxium.init.recipes.RecipePrism;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TileFillingPrism extends TileHideInventory  {
    double lux = 600, tenebris = 4000;
    protected ItemStack stack = getItemHandler().getItem(0);

    public TileFillingPrism(BlockPos pos, BlockState state) {
        super(ModInit.prism.get(), pos, state);
    }
    public static void tick(Level level, BlockPos pos, BlockState state, TileFillingPrism tile) {
        Optional<RecipePrism> recipe = tile.getRecipe();
        if (recipe.isPresent()) {
            for (Map.Entry<EnergyType, Double> entry : recipe.get().getEnergyMap().entrySet()) {
                ItemStack stack = recipe.get().getResultItem();
                tile.craft(stack);
                tile.removeEnergy(entry.getKey(), entry.getValue());
            }
        }
    }
    public void craft(ItemStack stack){
        clearContent();
        getItemHandler().setItem(0, stack);
    }
    public double getEnergy(EnergyType type){
        return switch (type) {
            case lux -> Math.round((lux) * 10.0) / 10.0;
            case tenebris -> Math.round((tenebris) * 10.0) / 10.0;
        };
    }

    private Optional<RecipePrism> getRecipe() {
        return getLevel().getRecipeManager().getRecipeFor(RecipePrism.Type.INSTANCE, (SimpleContainer)getItemHandler(), level);
    }

    public void addEnergy(EnergyType type, double amount){
        for (Map.Entry<EnergyType, Double> entry : getRecipe().get().getEnergyMap().entrySet()) {
            if (getEnergy(type) >= entry.getValue()) {
                switch (type) {
                    case lux -> lux += amount;
                    case tenebris -> tenebris += amount;
                }
            }
        }
    }
    public void removeEnergy(EnergyType type, double amount) {
        switch (type) {
            case lux -> lux -= amount;
            case tenebris -> tenebris -= amount;
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
    @Override
    public void readNBT(CompoundTag tag) {
        super.readNBT(tag);
        lux = tag.getDouble(EnergyType.lux.name());
        tenebris = tag.getDouble(EnergyType.tenebris.name());
    }
    @Override
    public void writeNBT(CompoundTag tag) {
        super.writeNBT(tag);
        tag.putDouble(EnergyType.lux.name(), lux);
        tag.putDouble(EnergyType.tenebris.name(), tenebris);
    }
}