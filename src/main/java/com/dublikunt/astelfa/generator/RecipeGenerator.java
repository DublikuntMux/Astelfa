package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        offerMatter(exporter, Items.REDSTONE, Items.DIAMOND, ModItems.MATTER_1);
        offerMatter(exporter, Items.DIAMOND, ModItems.MATTER_1, ModItems.MATTER_2);
        offerMatter(exporter, ModItems.MATTER_1, ModItems.MATTER_2, ModItems.MATTER_3);
        offerMatter(exporter, ModItems.MATTER_2, ModItems.MATTER_3, ModItems.MATTER_4);
        offerMatter(exporter, ModItems.MATTER_3, ModItems.MATTER_4, ModItems.MATTER_5);
        offerMatter(exporter, ModItems.MATTER_4, ModItems.MATTER_5, ModItems.MATTER_6);
        offerTorch(exporter, ModItems.ESSENTIAL_FUEL, ModBlocks.AQUATIC_TORCH);

        offerWood(exporter, ModBlocks.SILVER_WOOD, ModBlocks.SILVER_LOG, ModBlocks.STRIPPED_SILVER_WOOD,
                ModBlocks.STRIPPED_SILVER_LOG, ModBlocks.SILVER_PLANKS, ModBlocks.SILVER_WOOD_BUTTON,
                ModBlocks.SILVER_WOOD_DOOR, ModBlocks.SILVER_WOOD_TRAPDOOR, ModBlocks.SILVER_WOOD_PRESSURE_PLATE,
                ModBlocks.SILVER_WOOD_FENCE, ModBlocks.SILVER_WOOD_FENCE_GATE, ModBlocks.SILVER_WOOD_SLAB,
                ModBlocks.SILVER_WOOD_STAIRS);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.CARVED_SILVER_WOOD, 8)
                .pattern("WWW")
                .pattern("W W")
                .pattern("WWW")
                .input('W', ModBlocks.SILVER_PLANKS)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.SILVER_LOG),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.SILVER_LOG))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.CARVED_SILVER_WOOD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MANA_FILLER_BLOCK)
                .pattern("ICI")
                .pattern("WGW")
                .pattern("WWW")
                .input('G', Blocks.GLASS)
                .input('W', ModBlocks.STRIPPED_SILVER_LOG)
                .input('I', Items.AMETHYST_SHARD)
                .input('C', Blocks.RED_CARPET)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.SILVER_LOG),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.SILVER_LOG))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.MANA_FILLER_BLOCK)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.INFUSING_TABLE_BLOCK)
                .pattern("AAA")
                .pattern("PPP")
                .pattern("W W")
                .input('A', Blocks.AMETHYST_BLOCK)
                .input('P', ModBlocks.SILVER_PLANKS)
                .input('W', ModBlocks.STRIPPED_SILVER_LOG)
                .criterion(FabricRecipeProvider.hasItem(ModBlocks.SILVER_LOG),
                        FabricRecipeProvider.conditionsFromItem(ModBlocks.SILVER_LOG))
                .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.INFUSING_TABLE_BLOCK)));
    }

    private void offerMatter(RecipeExporter exporter, Item ringItem, Item inItem, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output)
                .pattern("OOO")
                .pattern("OIO")
                .pattern("OOO")
                .input('O', ringItem)
                .input('I', inItem)
                .criterion(FabricRecipeProvider.hasItem(inItem), FabricRecipeProvider.conditionsFromItem(inItem))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerWood(RecipeExporter exporter, Block wood, Block log,
                           Block stripped_wood, Block stripped_log, Block planks, Block button, Block door,
                           Block trapdoor, Block pressurePlate, Block fence, Block fenceGate, Block slab, Block stairs) {
        offerBarkBlockRecipe(exporter, wood, log);
        offerBarkBlockRecipe(exporter, stripped_wood, stripped_log);

        offerPlank(exporter, log, planks, 1);
        offerPlank(exporter, wood, planks, 2);
        offerPlank(exporter, stripped_log, planks, 3);
        offerPlank(exporter, stripped_wood, planks, 4);

        offerButton(exporter, button, planks);
        offerDoor(exporter, door, planks);
        offerTrapdoor(exporter, trapdoor, planks);
        offerPressurePlate(exporter, pressurePlate, planks);
        offerFence(exporter, fence, planks);
        offerFenceGate(exporter, fenceGate, planks);
        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, slab, planks);
        offerStairs(exporter, stairs, planks);
    }

    private void offerPlank(RecipeExporter exporter, ItemConvertible input, ItemConvertible output, Integer id) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .input(input)
                .group("planks")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output) + id));
    }

    private void offerButton(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
                .input(input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerDoor(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 3)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerTrapdoor(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output, 2)
                .pattern("###")
                .pattern("###")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerPressurePlate(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
                .pattern("##")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerFence(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 3)
                .input('#', Items.STICK)
                .input('W', input)
                .pattern("W#W")
                .pattern("W#W")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerFenceGate(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.REDSTONE, output)
                .input('#', Items.STICK)
                .input('W', input)
                .pattern("#W#")
                .pattern("#W#")
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerStairs(RecipeExporter exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .input('#', input)
                .criterion(FabricRecipeProvider.hasItem(input), FabricRecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }

    private void offerTorch(RecipeExporter exporter, ItemConvertible ingredient, ItemConvertible output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, output, 4)
                .pattern("R")
                .pattern("S")
                .input('R', ingredient)
                .input('S', Items.STICK)
                .criterion(FabricRecipeProvider.hasItem(ingredient), FabricRecipeProvider.conditionsFromItem(ingredient))
                .offerTo(exporter, new Identifier(getRecipeName(output)));
    }
}
