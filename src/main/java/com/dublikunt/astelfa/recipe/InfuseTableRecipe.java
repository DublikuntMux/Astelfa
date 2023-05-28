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

public class InfuseTableRecipe implements Recipe<SimpleInventory> {
    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;

    public InfuseTableRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public boolean matches(SimpleInventory inventory, @NotNull World world) {
        if (world.isClient()) {
            return false;
        }
        return recipeItems.get(0).test(inventory.getStack(0)) && recipeItems.get(1).test(inventory.getStack(1))
                && recipeItems.get(2).test(inventory.getStack(2)) && recipeItems.get(3).test(inventory.getStack(3))
                && recipeItems.get(4).test(inventory.getStack(4)) && recipeItems.get(5).test(inventory.getStack(5))
                && recipeItems.get(6).test(inventory.getStack(6)) && recipeItems.get(7).test(inventory.getStack(7))
                && recipeItems.get(8).test(inventory.getStack(8));
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

    public DefaultedList<Ingredient> getInputs() {
        return this.recipeItems;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<InfuseTableRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "infuse_table";

        private Type() {
        }
    }

    static class InfuseRecipeJsonFormat {
        JsonObject input1;
        JsonObject input2;
        JsonObject input3;
        JsonObject input4;
        JsonObject input5;
        JsonObject input6;
        JsonObject input7;
        JsonObject input8;
        JsonObject input9;

        JsonObject output;
    }

    public static class Serializer implements RecipeSerializer<InfuseTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "infuse_table";

        @Override
        public InfuseTableRecipe read(Identifier id, JsonObject json) {
            InfuseRecipeJsonFormat recipeJson = new Gson().fromJson(json, InfuseRecipeJsonFormat.class);
            ItemStack output = ShapedRecipe.outputFromJson(recipeJson.output);

            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(9, Ingredient.EMPTY);
            inputs.set(0, Ingredient.fromJson(recipeJson.input1));
            inputs.set(1, Ingredient.fromJson(recipeJson.input2));
            inputs.set(2, Ingredient.fromJson(recipeJson.input3));
            inputs.set(3, Ingredient.fromJson(recipeJson.input4));
            inputs.set(4, Ingredient.fromJson(recipeJson.input5));
            inputs.set(5, Ingredient.fromJson(recipeJson.input6));
            inputs.set(6, Ingredient.fromJson(recipeJson.input7));
            inputs.set(7, Ingredient.fromJson(recipeJson.input8));
            inputs.set(8, Ingredient.fromJson(recipeJson.input9));

            return new InfuseTableRecipe(id, output, inputs);
        }

        @Override
        public InfuseTableRecipe read(Identifier id, @NotNull PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }

            ItemStack output = buf.readItemStack();
            return new InfuseTableRecipe(id, output, inputs);
        }

        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull InfuseTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }
}
