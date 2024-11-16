package com.easynull.luxium.init.recipes;

import com.easynull.luxium.LuxiumMod;
import com.easynull.luxium.api.energies.EnergyType;
import com.easynull.luxium.init.ModInit;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class RecipePrism implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;
    private final Map<EnergyType, Double> energyMap;


    public RecipePrism(ResourceLocation id, ItemStack output, Map<EnergyType, Double> energyMap, Ingredient... inputItems) {
        this.id = id;
        this.output = output;
        this.energyMap = energyMap;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems);
    }

    @Override
    public boolean matches(SimpleContainer cont, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }
        return inputs.get(0).test(cont.getItem(0));
    }

    public Map<EnergyType, Double> getEnergyMap() {
        return Collections.unmodifiableMap(energyMap);
    }

    @Override
    public @NotNull ItemStack assemble(SimpleContainer container) {
        return output;
    }
    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<RecipePrism> {
        public static Type INSTANCE = new Type();
        public static final String ID = "filling_prism";
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<RecipePrism> {
        public static Serializer INSTANCE = new Serializer();

        @Override
        public RecipePrism fromJson(ResourceLocation id, JsonObject jsObj) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsObj, "output"));
            JsonArray pIngredients = GsonHelper.getAsJsonArray(jsObj, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : pIngredients) {
                inputs.add(Ingredient.fromJson(e));
            }
            JsonArray energyArray = jsObj.getAsJsonArray("energy");

            Map<EnergyType, Double> energyMap = new HashMap<>();
            for (JsonElement energyElement : energyArray) {
                JsonObject energyObject = energyElement.getAsJsonObject();
                if (energyObject.has("type")) {
                    double energyAmount = 1;
                    String energyTypeName = energyObject.get("type").getAsString();
                    EnergyType energyType = EnergyType.valueOf(energyTypeName);
                    if (energyObject.has("amount")) {
                        energyAmount = energyObject.get("amount").getAsDouble();
                        energyMap.put(energyType, energyAmount);
                    }
                }
            }
            return new RecipePrism(id, output, energyMap, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public RecipePrism fromNetwork(ResourceLocation id, FriendlyByteBuf byteBuf) {
            Ingredient[] inputs = new Ingredient[byteBuf.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(byteBuf);
            }
            Map<EnergyType, Double> energyMap = new HashMap<>();
            ItemStack output = byteBuf.readItem();

            EnergyType energyType = byteBuf.readEnum(EnergyType.class);
            double energyAmount = byteBuf.readDouble();
            energyMap.put(energyType, energyAmount);
            return new RecipePrism(id, output, energyMap, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf byteBuf, RecipePrism recipe) {
            byteBuf.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(byteBuf);
            }
            byteBuf.writeItemStack(recipe.getResultItem(), false);
            Map<EnergyType, Double> energyMap = recipe.getEnergyMap();
            byteBuf.writeInt(energyMap.size());
            for (Map.Entry<EnergyType, Double> entry : energyMap.entrySet()) {
                byteBuf.writeEnum(entry.getKey());
                byteBuf.writeDouble(entry.getValue());
            }
        }
    }
}
