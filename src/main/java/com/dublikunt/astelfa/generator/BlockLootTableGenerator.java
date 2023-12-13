package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class BlockLootTableGenerator extends FabricBlockLootTableProvider {
    protected BlockLootTableGenerator(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.LUMINITE_ORE, block -> oreDrops(block, ModItems.RAW_LUMINITE));
        addDrop(ModBlocks.DEEPSLATE_LUMINITE_ORE, block -> oreDrops(block, ModItems.RAW_LUMINITE));
        addDrop(ModBlocks.NETHERRACK_LUMINITE_ORE, block -> oreDrops(block, ModItems.RAW_LUMINITE));
        addDrop(ModBlocks.ENDSTONE_LUMINITE_ORE, block -> oreDrops(block, ModItems.RAW_LUMINITE));

        addDrop(ModBlocks.INFUSING_TABLE_BLOCK);
        addDrop(ModBlocks.MANA_FILLER_BLOCK);
        addDrop(ModBlocks.PEDESTAL_BLOCK);
        addDrop(ModBlocks.SCULK_STATUE_BLOCK);
        addDrop(ModBlocks.SILVER_LOG);
        addDrop(ModBlocks.SILVER_WOOD);
        addDrop(ModBlocks.STRIPPED_SILVER_LOG);
        addDrop(ModBlocks.STRIPPED_SILVER_WOOD);
        addDrop(ModBlocks.SILVER_SAPLING);
        addDrop(ModBlocks.SILVER_PLANKS);
        addDrop(ModBlocks.CARVED_SILVER_WOOD);
        addDrop(ModBlocks.SILVER_WOOD_BUTTON);
        addDrop(ModBlocks.SILVER_WOOD_DOOR);
        addDrop(ModBlocks.SILVER_WOOD_TRAPDOOR);
        addDrop(ModBlocks.SILVER_WOOD_PRESSURE_PLATE);
        addDrop(ModBlocks.SILVER_WOOD_FENCE);
        addDrop(ModBlocks.SILVER_WOOD_FENCE_GATE);
        addDrop(ModBlocks.SILVER_WOOD_STAIRS);
        addDrop(ModBlocks.SILVER_WOOD_SLAB);
        addDrop(ModBlocks.AQUATIC_TORCH);
        addDrop(ModBlocks.AQUATIC_WALL_TORCH, ModBlocks.AQUATIC_TORCH);
    }
}
