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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RecipePrism implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputs;
    private final ItemStack output;
    private final ResourceLocation id;
    private final EnergyType type;
    private final double energyInTick;


    public RecipePrism(ResourceLocation id, ItemStack output, EnergyType type, double energyInTick, Ingredient... inputItems) {
        this.id = id;
        this.output = output;
        this.type = type;
        this.energyInTick = energyInTick;
        this.inputs = NonNullList.of(Ingredient.EMPTY, inputItems);
    }

    @Override
    public boolean matches(SimpleContainer cont, Level pLevel) {
        boolean craft = true;
        for (int i = 0; i < 2; i += 1) {
            if (!inputs.get(i).test(cont.getItem(i))) {
                craft = false;
            }
        }
        return craft;
    }

    public EnergyType getEnergyType(){
        return type;
    }

    public double getEnergyInTick(){
        return energyInTick;
    }

    @Override
    public ItemStack assemble(SimpleContainer container) {
        return output;
    }
    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
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
        public static final ResourceLocation ID = new ResourceLocation(LuxiumMod.ID,"filling_prism");

        @Override
        public RecipePrism fromJson(ResourceLocation id, JsonObject jsObj) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsObj, "output"));
            JsonArray pIngredients = GsonHelper.getAsJsonArray(jsObj, "ingredients");
            List<Ingredient> inputs = new ArrayList<>();
            for (JsonElement e : pIngredients) {
                inputs.add(Ingredient.fromJson(e));
            }
            EnergyType type = EnergyType.valueOf(GsonHelper.getAsString(jsObj, "energy").toLowerCase());
            double energyAmount = GsonHelper.getAsDouble(jsObj, "amount");
            return new RecipePrism(id, output, type, energyAmount, inputs.toArray(new Ingredient[0]));
        }

        @Nullable
        @Override
        public RecipePrism fromNetwork(ResourceLocation id, FriendlyByteBuf byteBuf) {
            Ingredient[] inputs = new Ingredient[byteBuf.readInt()];
            for (int i = 0; i < inputs.length; i++) {
                inputs[i] = Ingredient.fromNetwork(byteBuf);
            }
            ItemStack output = byteBuf.readItem();
            EnergyType type = byteBuf.readEnum(EnergyType.class);
            double energyAmount = byteBuf.readDouble();

            return new RecipePrism(id, output, type, energyAmount, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf byteBuf, RecipePrism recipe) {
            byteBuf.writeInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.toNetwork(byteBuf);
            }

            byteBuf.writeItemStack(recipe.getResultItem(), false);
            byteBuf.writeEnum(recipe.getEnergyType());
            byteBuf.writeDouble(recipe.getEnergyInTick());
        }
    }
}
