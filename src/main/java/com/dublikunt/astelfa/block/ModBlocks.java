package com.dublikunt.astelfa.block;

import com.dublikunt.astelfa.block.common.SparkleLeavesBlock;
import com.dublikunt.astelfa.block.common.SparklePillarBlock;
import com.dublikunt.astelfa.block.common.SparkleSaplingBlock;
import com.dublikunt.astelfa.block.custom.InfuseTableBlock;
import com.dublikunt.astelfa.block.custom.ManaFillerBlock;
import com.dublikunt.astelfa.block.custom.SculkStatueBlock;
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
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModBlocks {
    public static final InfuseTableBlock INFUSING_TABLE_BLOCK = new InfuseTableBlock(
            FabricBlockSettings.of(Material.METAL).strength(2f).requiresTool().nonOpaque());
    public static final ManaFillerBlock MANA_FILLER_BLOCK = new ManaFillerBlock(
            FabricBlockSettings.of(Material.STONE).nonOpaque());
    public static final SculkStatueBlock SCULK_STATUE_BLOCK = new SculkStatueBlock(
            FabricBlockSettings.of(Material.SCULK).strength(3f).requiresTool().nonOpaque());

    public static final SparklePillarBlock SILVER_LOG = new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.OAK_LOG).mapColor(DyeColor.LIGHT_GRAY));
    public static final SparklePillarBlock SILVER_WOOD = new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.OAK_WOOD).mapColor(DyeColor.LIGHT_GRAY));
    public static final SparklePillarBlock STRIPPED_SILVER_LOG = new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.STRIPPED_OAK_LOG).mapColor(DyeColor.LIGHT_GRAY));
    public static final SparklePillarBlock STRIPPED_SILVER_WOOD = new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.STRIPPED_OAK_WOOD).mapColor(DyeColor.LIGHT_GRAY));
    public static final SparkleLeavesBlock SILVER_LEAVES = new SparkleLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final SparkleSaplingBlock SILVER_SAPLING = new SparkleSaplingBlock(new SilverWoodSaplingGenerator(),
            FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)
                    .mapColor(DyeColor.LIGHT_GRAY));

    public static final Block SILVER_PLANKS = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final Block CARVED_SILVER_WOOD = new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .mapColor(DyeColor.LIGHT_GRAY));
    public static final ButtonBlock SILVER_WOOD_BUTTON = new ButtonBlock(FabricBlockSettings.of(Material.WOOD)
            .noCollision().strength(0.5F), BlockSetType.OAK, 30, true);
    public static final DoorBlock SILVER_WOOD_DOOR = new DoorBlock(FabricBlockSettings.of(Material.WOOD,
            SILVER_PLANKS.getDefaultMapColor()).strength(3.0F).nonOpaque(), BlockSetType.OAK);
    public static final TrapdoorBlock SILVER_WOOD_TRAPDOOR = new TrapdoorBlock(FabricBlockSettings.of(Material.WOOD,
            SILVER_PLANKS.getDefaultMapColor()).strength(3.0F).nonOpaque(), BlockSetType.OAK);
    public static final PressurePlateBlock SILVER_WOOD_PRESSURE_PLATE = new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING,
            FabricBlockSettings.of(Material.WOOD, SILVER_PLANKS.getDefaultMapColor()).noCollision().strength(0.5F),
            BlockSetType.OAK
    );
    public static final FenceBlock SILVER_WOOD_FENCE = new FenceBlock(FabricBlockSettings.of(Material.WOOD,
            SILVER_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));
    public static final FenceGateBlock SILVER_WOOD_FENCE_GATE = new FenceGateBlock(FabricBlockSettings.of(Material.WOOD,
            SILVER_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F), WoodType.OAK);
    public static final StairsBlock SILVER_WOOD_STAIRS = new StairsBlock(SILVER_PLANKS.getDefaultState(),
            FabricBlockSettings.copyOf(SILVER_PLANKS));
    public static final SlabBlock SILVER_WOOD_SLAB = new SlabBlock(FabricBlockSettings.of(Material.WOOD,
            SILVER_PLANKS.getDefaultMapColor()).requiresTool().strength(1.5F, 6.0F));

    public static Map<String, Block> BLOCKS = new LinkedHashMap<>();

    static {
        BLOCKS.put("infuse_table", INFUSING_TABLE_BLOCK);
        BLOCKS.put("mana_filler", MANA_FILLER_BLOCK);
        BLOCKS.put("sculk_statue", SCULK_STATUE_BLOCK);

        BLOCKS.put("silver_log", SILVER_LOG);
        BLOCKS.put("silver_wood", SILVER_WOOD);
        BLOCKS.put("stripped_silver_log", STRIPPED_SILVER_LOG);
        BLOCKS.put("stripped_silver_wood", STRIPPED_SILVER_WOOD);
        BLOCKS.put("silver_leaves", SILVER_LEAVES);
        BLOCKS.put("silver_sapling", SILVER_SAPLING);

        BLOCKS.put("silver_planks", SILVER_PLANKS);
        BLOCKS.put("carved_silver_wood", CARVED_SILVER_WOOD);
        BLOCKS.put("silver_wood_button", SILVER_WOOD_BUTTON);
        BLOCKS.put("silver_wood_door", SILVER_WOOD_DOOR);
        BLOCKS.put("silver_wood_trapdoor", SILVER_WOOD_TRAPDOOR);
        BLOCKS.put("silver_wood_pressure_plate", SILVER_WOOD_PRESSURE_PLATE);
        BLOCKS.put("silver_wood_fence", SILVER_WOOD_FENCE);
        BLOCKS.put("silver_wood_fence_gate", SILVER_WOOD_FENCE_GATE);
        BLOCKS.put("silver_wood_stairs", SILVER_WOOD_STAIRS);
        BLOCKS.put("silver_wood_slab", SILVER_WOOD_SLAB);
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
        registry.add(SILVER_LEAVES, 30, 60);
        registry.add(SILVER_SAPLING, 5, 20);

        registry.add(SILVER_PLANKS, 5, 20);
        registry.add(CARVED_SILVER_WOOD, 5, 20);
        registry.add(SILVER_WOOD_BUTTON, 5, 20);
        registry.add(SILVER_WOOD_DOOR, 5, 20);
        registry.add(SILVER_WOOD_TRAPDOOR, 5, 20);
        registry.add(SILVER_WOOD_PRESSURE_PLATE, 5, 20);
        registry.add(SILVER_WOOD_FENCE, 5, 20);
        registry.add(SILVER_WOOD_FENCE_GATE, 5, 20);
        registry.add(SILVER_WOOD_STAIRS, 5, 20);
        registry.add(SILVER_WOOD_SLAB, 5, 20);
    }

    public static void registerStrippables() {
        StrippableBlockRegistry.register(ModBlocks.SILVER_LOG, ModBlocks.STRIPPED_SILVER_LOG);
        StrippableBlockRegistry.register(ModBlocks.SILVER_WOOD, ModBlocks.STRIPPED_SILVER_WOOD);
    }
}
