package com.dublikunt.astelfa.integration.emi;

import com.dublikunt.astelfa.integration.AstelfaEmiPlugin;
import com.dublikunt.astelfa.recipe.InfuseTableRecipe;
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

public class InfuseTableEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final EmiStack output;
    private final List<EmiIngredient> inputs;

    public InfuseTableEmiRecipe(@NotNull InfuseTableRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = List.of(EmiIngredient.of(recipe.getInputs().get(0)), EmiIngredient.of(recipe.getInputs().get(1)),
                EmiIngredient.of(recipe.getInputs().get(2)), EmiIngredient.of(recipe.getInputs().get(3)),
                EmiIngredient.of(recipe.getInputs().get(4)), EmiIngredient.of(recipe.getInputs().get(5)),
                EmiIngredient.of(recipe.getInputs().get(6)), EmiIngredient.of(recipe.getInputs().get(7)),
                EmiIngredient.of(recipe.getInputs().get(8)));
        this.output = EmiStack.of(recipe.getOutput());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return AstelfaEmiPlugin.INFUSE_CATEGORY;
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
        return 118;
    }

    @Override
    public int getDisplayHeight() {
        return 72;
    }

    @Override
    public void addWidgets(@NotNull WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 27, 27);
        widgets.addSlot(inputs.get(1), 27, 0);
        widgets.addSlot(inputs.get(2), 45, 9);
        widgets.addSlot(inputs.get(3), 54, 27);
        widgets.addSlot(inputs.get(4), 45, 45);
        widgets.addSlot(inputs.get(5), 27, 54);
        widgets.addSlot(inputs.get(6), 9, 45);
        widgets.addSlot(inputs.get(7), 0, 27);
        widgets.addSlot(inputs.get(8), 9, 9);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 74, 27);

        widgets.addSlot(output, 100, 27).recipeContext(this);
    }
}
