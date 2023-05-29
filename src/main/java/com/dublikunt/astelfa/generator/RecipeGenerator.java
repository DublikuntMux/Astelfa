package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerMatter(exporter, Items.REDSTONE, Items.DIAMOND, ModItems.MATTER_1);
        offerMatter(exporter, Items.DIAMOND, ModItems.MATTER_1, ModItems.MATTER_2);
        offerMatter(exporter, ModItems.MATTER_1, ModItems.MATTER_2, ModItems.MATTER_3);
        offerMatter(exporter, ModItems.MATTER_2, ModItems.MATTER_3, ModItems.MATTER_4);
        offerMatter(exporter, ModItems.MATTER_3, ModItems.MATTER_4, ModItems.MATTER_5);
        offerMatter(exporter, ModItems.MATTER_4, ModItems.MATTER_5, ModItems.MATTER_6);

        offerWood(exporter, ModBlocks.SILVER_WOOD, ModBlocks.SILVER_LOG, ModBlocks.STRIPPED_SILVER_WOOD,
                ModBlocks.STRIPPED_SILVER_LOG, ModBlocks.SILVER_PLANKS);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MANA_FILLER_BLOCK)
                .pattern("I I")
                .pattern("WGW")
                .pattern("WWW")
                .input('G', Blocks.GLASS)
                .input('W', ModBlocks.SILVER_LOG)
                .input('I', Items.AMETHYST_SHARD)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.SILVER_LOG),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.SILVER_LOG))
                .offerTo(exporter);
    }

    private void offerMatter(Consumer<RecipeJsonProvider> exporter, Item ringItem, Item inItem, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output)
                .pattern("OOO")
                .pattern("OIO")
                .pattern("OOO")
                .input('O', ringItem)
                .input('I', inItem)
                .criterion(FabricRecipeProvider.hasItem(inItem), FabricRecipeProvider.conditionsFromItem(inItem))
                .offerTo(exporter);
    }

    private void offerPlank(Consumer<RecipeJsonProvider> exporter, Block input, Block output, Integer id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input(input)
                .group("planks")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Helpers.id(output.getName().getString() + "_" + id));
    }

    private void offerWood(Consumer<RecipeJsonProvider> exporter, Block wood, Block log,
                           Block stripped_wood, Block stripped_log, Block planks) {
        offerBarkBlockRecipe(exporter, wood, log);
        offerBarkBlockRecipe(exporter, stripped_wood, stripped_log);

        offerPlank(exporter, log, planks, 1);
        offerPlank(exporter, wood, planks, 2);
        offerPlank(exporter, stripped_log, planks, 3);
        offerPlank(exporter, stripped_wood, planks, 4);
    }
}
