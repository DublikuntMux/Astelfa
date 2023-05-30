package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
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
                ModBlocks.STRIPPED_SILVER_LOG, ModBlocks.SILVER_PLANKS, ModBlocks.SILVER_WOOD_BUTTON,
                ModBlocks.SILVER_WOOD_DOOR, ModBlocks.SILVER_WOOD_TRAPDOOR, ModBlocks.SILVER_WOOD_PRESSURE_PLATE,
                ModBlocks.SILVER_WOOD_FENCE, ModBlocks.SILVER_WOOD_FENCE_GATE);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.CARVED_SILVER_WOOD, 8)
                .pattern("WWW")
                .pattern("W W")
                .pattern("WWW")
                .input('W', ModBlocks.SILVER_PLANKS)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.SILVER_LOG),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.SILVER_LOG))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MANA_FILLER_BLOCK)
                .pattern("I I")
                .pattern("WGW")
                .pattern("WWW")
                .input('G', Blocks.GLASS)
                .input('W', ModBlocks.STRIPPED_SILVER_LOG)
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

    private void offerWood(Consumer<RecipeJsonProvider> exporter, Block wood, Block log,
                           Block stripped_wood, Block stripped_log, Block planks, Block button, Block door,
                           Block trapdoor, Block pressurePlate, Block fence, Block fenceGate) {
        offerBarkBlockRecipe(exporter, wood, log);
        offerBarkBlockRecipe(exporter, stripped_wood, stripped_log);

        offerPlank(exporter, log, planks, 1);
        offerPlank(exporter, wood, planks, 2);
        offerPlank(exporter, stripped_log, planks, 3);
        offerPlank(exporter, stripped_wood, planks, 4);

        offerButton(exporter, button, planks.asItem());
        offerDoor(exporter, door, planks.asItem());
        offerTrapdoor(exporter, trapdoor, planks.asItem());
        offerPressurePlate(exporter, pressurePlate, planks.asItem());
        offerFence(exporter, fence, planks.asItem());
        offerFenceGate(exporter, fenceGate, planks.asItem());
    }

    private void offerPlank(Consumer<RecipeJsonProvider> exporter, Block input, Block output, Integer id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input(input)
                .group("planks")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, Helpers.id(output.getName().getString() + "_" + id));
    }

    private void offerButton(Consumer<RecipeJsonProvider> exporter, Block output, Item input) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void offerDoor(Consumer<RecipeJsonProvider> exporter, Block output, Item input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void offerTrapdoor(Consumer<RecipeJsonProvider> exporter, Block output, Item input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 2)
                .pattern("###")
                .pattern("###")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void offerPressurePlate(Consumer<RecipeJsonProvider> exporter, Block output, Item input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
                .pattern("##")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    private void offerFence(Consumer<RecipeJsonProvider> exporter, Block output, Item input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 3)
                .input('#', Items.STICK)
                .input('W', input)
                .pattern("W#W")
                .pattern("W#W")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);;
    }

    private void offerFenceGate(Consumer<RecipeJsonProvider> exporter, Block output, Item input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
                .input('#', Items.STICK)
                .input('W', input)
                .pattern("#W#")
                .pattern("#W#")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);;
    }
}
