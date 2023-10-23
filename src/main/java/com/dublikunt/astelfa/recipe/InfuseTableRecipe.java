package com.dublikunt.astelfa.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.dublikunt.astelfa.helper.Helpers.validateAmount;

public class InfuseTableRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public InfuseTableRecipe(List<Ingredient> ingredients, ItemStack itemStack) {
        this.output = itemStack;
        this.recipeItems = ingredients;
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
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return output.copy();
    }

    public ItemStack getResult() {
        return output.copy();
    }

    public DefaultedList<Ingredient> getInputs() {
        return this.getIngredients();
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

    public static class Serializer implements RecipeSerializer<InfuseTableRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "infuse_table";

        public static final Codec<InfuseTableRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 9).fieldOf("ingredients").forGetter(InfuseTableRecipe::getIngredients),
                RecipeCodecs.CRAFTING_RESULT.fieldOf("output").forGetter(r -> r.output)
        ).apply(in, InfuseTableRecipe::new));

        @Override
        public Codec<InfuseTableRecipe> codec() {
            return CODEC;
        }

        @Override
        public InfuseTableRecipe read(@NotNull PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            return new InfuseTableRecipe(inputs, output);
        }

        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull InfuseTableRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getResult());
        }
    }
}
