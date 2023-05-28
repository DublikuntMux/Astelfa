package com.dublikunt.astelfa.integration;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.integration.emi.InfuseTableEmiRecipe;
import com.dublikunt.astelfa.recipe.InfuseTableRecipe;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.util.Identifier;

public class AstelfaEmiPlugin implements EmiPlugin {
    public static final Identifier SPRITE_SHEET = Helpers.id("textures/gui/emi_simplified.png");
    public static final EmiStack INFUSE_TABLE = EmiStack.of(ModBlocks.INFUSING_TABLE_BLOCK);
    public static final EmiRecipeCategory INFUSE_CATEGORY
            = new EmiRecipeCategory(Helpers.id("infuse_table"), INFUSE_TABLE,
            new EmiTexture(SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(INFUSE_CATEGORY);
        registry.addWorkstation(INFUSE_CATEGORY, INFUSE_TABLE);

        RecipeManager manager = registry.getRecipeManager();
        for (InfuseTableRecipe recipe : manager.listAllOfType(InfuseTableRecipe.Type.INSTANCE)) {
            registry.addRecipe(new InfuseTableEmiRecipe(recipe));
        }
    }
}
