package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import org.jetbrains.annotations.NotNull;

public class ModelGenerator extends FabricModelProvider {
    public ModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(@NotNull BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerLog(ModBlocks.SILVER_LOG).log(ModBlocks.SILVER_LOG).wood(ModBlocks.SILVER_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_SILVER_LOG).log(ModBlocks.STRIPPED_SILVER_LOG)
                .wood(ModBlocks.STRIPPED_SILVER_WOOD);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SILVER_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SILVER_PLANKS);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.CARVED_SILVER_WOOD);

        blockStateModelGenerator.registerTintableCross(ModBlocks.SILVER_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTorch(ModBlocks.AQUATIC_TORCH, ModBlocks.AQUATIC_WALL_TORCH);
    }

    @Override
    public void generateItemModels(@NotNull ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.MATTER_1, Models.GENERATED);
        itemModelGenerator.register(ModItems.MATTER_2, Models.GENERATED);
        itemModelGenerator.register(ModItems.MATTER_3, Models.GENERATED);
        itemModelGenerator.register(ModItems.MATTER_4, Models.GENERATED);
        itemModelGenerator.register(ModItems.MATTER_5, Models.GENERATED);
        itemModelGenerator.register(ModItems.MATTER_6, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANA_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.ESSENTIAL_FUEL, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANA_DETECT_PAPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.USED_MANA_DETECT_PAPER, Models.GENERATED);

        itemModelGenerator.register(ModItems.ADOS_CHAINS, Models.GENERATED);
        itemModelGenerator.register(ModItems.HART_RING, Models.GENERATED);
        itemModelGenerator.register(ModItems.RING_BELT, Models.GENERATED);
        itemModelGenerator.register(ModItems.MANA_BUCKET, Models.GENERATED);
    }
}
