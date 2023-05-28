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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfuseTableEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final EmiStack output;
    private final List<EmiIngredient> inputs;

    public InfuseTableEmiRecipe(InfuseTableRecipe recipe) {
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
        return 139;
    }

    @Override
    public int getDisplayHeight() {
        return 80;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 33, 34);
        widgets.addSlot(inputs.get(1), 33, 7);
        widgets.addSlot(inputs.get(2), 51, 16);
        widgets.addSlot(inputs.get(3), 60, 34);
        widgets.addSlot(inputs.get(4), 51, 52);
        widgets.addSlot(inputs.get(5), 33, 61);
        widgets.addSlot(inputs.get(6), 15, 52);
        widgets.addSlot(inputs.get(7), 6, 34);
        widgets.addSlot(inputs.get(8), 15, 16);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 81, 34);

        widgets.addSlot(output, 114, 34).recipeContext(this);
    }
}
