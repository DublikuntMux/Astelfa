package com.dublikunt.astelfa.block;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.ModItems;
import com.dublikunt.astelfa.world.feature.tree.SilverWoodSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlocks {
    public static final InfuseTableBlock INFUSING_TABLE_BLOCK = new InfuseTableBlock(
            FabricBlockSettings.of(Material.METAL).strength(2f).requiresTool().nonOpaque());
    public static final SculkStatueBlock SCULK_STATUE_BLOCK = new SculkStatueBlock(
            FabricBlockSettings.of(Material.SCULK).strength(3f).requiresTool().nonOpaque());
    public static final Block SILVER_LOG = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block SILVER_WOOD = new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block STRIPPED_SILVER_LOG = new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block STRIPPED_SILVER_WOOD = new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block SILVER_PLANKS = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block SILVER_LEAVES = new LeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block SILVER_SAPLING = new SaplingBlock(new SilverWoodSaplingGenerator(),
            FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static Map<String, Block> BLOCKS = new LinkedHashMap<>();

    static {
        BLOCKS.put("infuse_table", INFUSING_TABLE_BLOCK);
        BLOCKS.put("sculk_statue", SCULK_STATUE_BLOCK);

        BLOCKS.put("silver_log", SILVER_LOG);
        BLOCKS.put("silver_wood", SILVER_WOOD);
        BLOCKS.put("stripped_silver_log", STRIPPED_SILVER_LOG);
        BLOCKS.put("stripped_silver_wood", STRIPPED_SILVER_WOOD);
        BLOCKS.put("silver_planks", SILVER_PLANKS);
        BLOCKS.put("silver_leaves", SILVER_LEAVES);
        BLOCKS.put("silver_sapling", SILVER_SAPLING);
    }

    public static void register() {
        for (Map.Entry<String, Block> block : BLOCKS.entrySet()) {
            Registry.register(Registries.BLOCK, Helpers.id(block.getKey()), block.getValue());
            Item item = Registry.register(Registries.ITEM, Helpers.id(block.getKey()),
                    new BlockItem(block.getValue(), new FabricItemSettings()));
            ItemGroupEvents.modifyEntriesEvent(ModItems.MOD_GROUP).register(entries -> entries.add(item));
        }
    }

    public static void registerFlammableBlocks() {
        FlammableBlockRegistry registry = FlammableBlockRegistry.getDefaultInstance();

        registry.add(SILVER_LOG, 5, 5);
        registry.add(SILVER_WOOD, 5, 5);
        registry.add(STRIPPED_SILVER_LOG, 5, 5);
        registry.add(STRIPPED_SILVER_WOOD, 5, 5);

        registry.add(SILVER_PLANKS, 5, 20);
        registry.add(SILVER_LEAVES, 30, 60);
        registry.add(SILVER_SAPLING, 5, 20);
    }

    public static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.SILVER_LOG, ModBlocks.STRIPPED_SILVER_LOG);
        StrippableBlockRegistry.register(ModBlocks.SILVER_WOOD, ModBlocks.STRIPPED_SILVER_WOOD);
    }
}
