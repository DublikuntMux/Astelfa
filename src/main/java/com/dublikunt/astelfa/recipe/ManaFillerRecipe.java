package com.dublikunt.astelfa.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.dublikunt.astelfa.helper.Helpers.validateAmount;

public class ManaFillerRecipe implements Recipe<SimpleInventory> {
    private final ItemStack output;
    private final List<Ingredient> recipeItems;
    private final int manaAmount;

    public ManaFillerRecipe(List<Ingredient> ingredients, ItemStack itemStack, int manaAmount) {
        this.output = itemStack;
        this.recipeItems = ingredients;
        this.manaAmount = manaAmount;
    }

    @Override
    public boolean matches(SimpleInventory inventory, @NotNull World world) {
        if (world.isClient()) {
            return false;
        }
        boolean hasCatalyst = this.recipeItems.size() == 2;
        return this.recipeItems.get(0).test(inventory.getStack(0)) && (hasCatalyst ? this.recipeItems.get(1).test(inventory.getStack(1)) : Ingredient.empty().test(inventory.getStack(1)));
    }

    @Override
    public ItemStack craft(SimpleInventory inventory, DynamicRegistryManager registryManager) {
        return this.output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.output.copy();
    }

    public ItemStack getResult() {
        return this.output.copy();
    }

    public int getManaAmount() {
        return this.manaAmount;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(this.recipeItems);
        return list;
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
    }

    public static class Serializer implements RecipeSerializer<ManaFillerRecipe> {
        public static final ManaFillerRecipe.Serializer INSTANCE = new ManaFillerRecipe.Serializer();
        public static final String ID = "mana_filler";

        public static final Codec<ManaFillerRecipe> CODEC = RecordCodecBuilder.create(in -> in.group(
                validateAmount(Ingredient.DISALLOW_EMPTY_CODEC, 2).fieldOf("ingredients").forGetter(ManaFillerRecipe::getIngredients),
                RecipeCodecs.CRAFTING_RESULT.fieldOf("output").forGetter(r -> r.output),
                Codecs.POSITIVE_INT.fieldOf("mana_amount").forGetter(ManaFillerRecipe::getManaAmount)
        ).apply(in, ManaFillerRecipe::new));

        @Override
        public Codec<ManaFillerRecipe> codec() {
            return CODEC;
        }

        @Override
        public ManaFillerRecipe read(@NotNull PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));
            ItemStack output = buf.readItemStack();
            return new ManaFillerRecipe(inputs, output, buf.readInt());
        }

        @Override
        public void write(@NotNull PacketByteBuf buf, @NotNull ManaFillerRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getResult());
        }
    }
}
