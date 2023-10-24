package com.dublikunt.astelfa.integration;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.integration.rei.InfuseCategory;
import com.dublikunt.astelfa.integration.rei.InfusingDisplay;
import com.dublikunt.astelfa.recipe.InfuseTableRecipe;
import com.dublikunt.astelfa.screen.screen.InfuseTableScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import org.jetbrains.annotations.NotNull;

public class ReiIntegration implements REIClientPlugin {
    @Override
    public void registerCategories(@NotNull CategoryRegistry registry) {
        registry.add(new InfuseCategory());

        registry.addWorkstations(InfuseCategory.INFUSING, EntryStacks.of(ModBlocks.INFUSING_TABLE_BLOCK));
    }

    @Override
    public void registerDisplays(@NotNull DisplayRegistry registry) {
        registry.registerRecipeFiller(InfuseTableRecipe.class, InfuseTableRecipe.Type.INSTANCE,
                InfusingDisplay::new);
    }

    @Override
    public void registerScreens(@NotNull ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), InfuseTableScreen.class,
                InfuseCategory.INFUSING);
    }
}
