package com.dublikunt.astelfa.integration;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.integration.rei.InfuseCategory;
import com.dublikunt.astelfa.integration.rei.InfusingDisplay;
import com.dublikunt.astelfa.integration.rei.ManaFillerCategory;
import com.dublikunt.astelfa.integration.rei.ManaFillerDisplay;
import com.dublikunt.astelfa.recipe.InfuseTableRecipe;
import com.dublikunt.astelfa.recipe.ManaFillerRecipe;
import com.dublikunt.astelfa.screen.screen.InfuseTableScreen;
import com.dublikunt.astelfa.screen.screen.ManaFillerScreen;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import org.jetbrains.annotations.NotNull;

public class ReiIntegration implements REIClientPlugin {
    @Override
    public void registerCategories(@NotNull CategoryRegistry registry) {
        registry.add(new InfuseCategory());
        registry.add(new ManaFillerCategory());

        registry.addWorkstations(InfuseCategory.INFUSING, EntryStack.of(VanillaEntryTypes.ITEM, ModBlocks.INFUSING_TABLE_BLOCK.asItem().getDefaultStack()));
        registry.addWorkstations(ManaFillerCategory.MANA_FILLER, EntryStack.of(VanillaEntryTypes.ITEM, ModBlocks.MANA_FILLER_BLOCK.asItem().getDefaultStack()));
    }

    @Override
    public void registerDisplays(@NotNull DisplayRegistry registry) {
        registry.registerRecipeFiller(InfuseTableRecipe.class, InfuseTableRecipe.Type.INSTANCE,
                InfusingDisplay::new);
        registry.registerRecipeFiller(ManaFillerRecipe.class, ManaFillerRecipe.Type.INSTANCE,
                ManaFillerDisplay::new);
    }

    @Override
    public void registerScreens(@NotNull ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(89, 38, 28, 10), InfuseTableScreen.class,
                InfuseCategory.INFUSING);
        registry.registerClickArea(screen -> new Rectangle(76, 38, 54, 10), ManaFillerScreen.class,
                ManaFillerCategory.MANA_FILLER);
    }
}
