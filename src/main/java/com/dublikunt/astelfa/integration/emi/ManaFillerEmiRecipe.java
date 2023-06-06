package com.dublikunt.astelfa.integration.emi;

import com.dublikunt.astelfa.integration.AstelfaEmiPlugin;
import com.dublikunt.astelfa.recipe.ManaFillerRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ManaFillerEmiRecipe implements EmiRecipe {
    private final Identifier id;
    private final EmiStack output;
    private final List<EmiIngredient> inputs;
    private final int manaAmount;

    public ManaFillerEmiRecipe(@NotNull ManaFillerRecipe recipe) {
        this.id = recipe.getId();
        this.inputs = List.of(EmiIngredient.of(recipe.getInputs().get(0)), EmiIngredient.of(recipe.getInputs().get(1)));
        this.output = EmiStack.of(recipe.getOutput());
        this.manaAmount = recipe.getManaAmount();
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
        return 105;
    }

    @Override
    public int getDisplayHeight() {
        return 55;
    }

    @Override
    public void addWidgets(@NotNull WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 6, 4);
        widgets.addSlot(inputs.get(1), 6, 31);

        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 18);

        widgets.addSlot(output, 53, 18).recipeContext(this);
        widgets.addText(Text.translatable("emi.astelfa.mana_amount", manaAmount), 26, 45, 212121, false);
    }
}
