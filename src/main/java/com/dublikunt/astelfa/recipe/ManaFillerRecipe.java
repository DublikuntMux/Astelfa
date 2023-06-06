package com.dublikunt.astelfa.recipe;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class ManaFillerRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final int manaAmount;

    public ManaFillerRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, int manaAmount) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.manaAmount = manaAmount;
    }

    @Override
    public boolean matches(SimpleInventory inventory, @NotNull World world) {
        if (world.isClient()) {
            return false;
        }
        return recipeItems.get(0).test(inventory.getStack(0)) && recipeItems.get(1).test(inventory.getStack(1));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output.copy();
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public int getManaAmount(){
        return manaAmount;
    }

    public DefaultedList<Ingredient> getInputs() {
        return this.recipeItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ManaFillerRecipe.Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ManaFillerRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<ManaFillerRecipe> {
        public static final ManaFillerRecipe.Type INSTANCE = new ManaFillerRecipe.Type();
        public static final String ID = "mana_filler";

        private Type() {
        }
    }

    static class ManaFillerRecipeJsonFormat {
        JsonObject input;
        JsonObject catalyst;
        Integer mana_amount;
        JsonObject output;
    }

    public static class Serializer implements RecipeSerializer<ManaFillerRecipe> {
        public static final ManaFillerRecipe.Serializer INSTANCE = new ManaFillerRecipe.Serializer();
        public static final String ID = "mana_filler";

        @Override
        public ManaFillerRecipe read(Identifier id, JsonObject json) {
            ManaFillerRecipe.ManaFillerRecipeJsonFormat recipeJson = new Gson().fromJson(json, ManaFillerRecipe.ManaFillerRecipeJsonFormat.class);
            ItemStack output = ShapedRecipe.outputFromJson(recipeJson.output);

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(9, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(recipeJson.input));
            inputs.set(1, Ingredient.fromJson(recipeJson.catalyst));

            return new ManaFillerRecipe(id, output, inputs, recipeJson.mana_amount);
        }

        @Override
        public ManaFillerRecipe read(Identifier id, @NotNull PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            return new ManaFillerRecipe(id, output, inputs, buf.readInt());
        }

        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull ManaFillerRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
