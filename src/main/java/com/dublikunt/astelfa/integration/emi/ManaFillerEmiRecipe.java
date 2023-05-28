package com.dublikunt.astelfa.integration.emi;

import com.dublikunt.astelfa.integration.AstelfaEmiPlugin;
import com.dublikunt.astelfa.recipe.ManaFillerRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaFillerEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final EmiStack output;
    private final List<EmiIngredient> inputs;

    public ManaFillerEmiRecipe(@NotNull ManaFillerRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = List.of(EmiIngredient.of(recipe.getInputs().get(0)), EmiIngredient.of(recipe.getInputs().get(1)));
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return AstelfaEmiPlugin.MANA_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 139;
    }

    @Override
    public int getDisplayHeight() {
        return 80;
    }

    @Override
    public void addWidgets(@NotNull WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 43, 17);
        widgets.addSlot(inputs.get(1), 43, 53);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 81, 34);

        widgets.addSlot(output, 114, 34).recipeContext(this);
    }
}
