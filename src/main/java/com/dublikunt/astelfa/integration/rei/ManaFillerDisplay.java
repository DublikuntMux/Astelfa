package com.dublikunt.astelfa.integration.rei;

import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.recipe.ManaFillerRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeEntry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ManaFillerDisplay extends BasicDisplay {
    public ManaFillerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public ManaFillerDisplay(@NotNull RecipeEntry<ManaFillerRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM, recipe.value().getResult(null)))));
    }

    private static @NotNull List<EntryIngredient> getInputList(ManaFillerRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        list.add(EntryIngredients.of(ModFluids.STILL_MANA_FLUID, recipe.getManaAmount()));
        list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(0)));

        boolean hasCatalyst = recipe.getIngredients().size() == 2;
        if (hasCatalyst) {
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(1)));
        } else {
            list.add(EntryIngredients.ofIngredient(Ingredient.EMPTY));
        }
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ManaFillerCategory.MANA_FILLER;
    }
}
