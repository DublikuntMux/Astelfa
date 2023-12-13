package com.dublikunt.astelfa.integration.rei;

import com.dublikunt.astelfa.recipe.PedestalRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.RecipeEntry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PedestalDisplay extends BasicDisplay {
    public PedestalDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public PedestalDisplay(@NotNull RecipeEntry<PedestalRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStack.of(VanillaEntryTypes.ITEM, recipe.value().getResult(null)))));
    }

    private static @NotNull List<EntryIngredient> getInputList(PedestalRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        List<EntryIngredient> list = new ArrayList<>();
        for (int i = 0; i < recipe.getIngredients().size(); i++) {
            list.add(EntryIngredients.ofIngredient(recipe.getIngredients().get(i)));
        }
        return list;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PedestalCategory.PEDESTAL;
    }
}
