package com.dublikunt.astelfa.block;

import com.dublikunt.astelfa.block.common.SparkleLeavesBlock;
import com.dublikunt.astelfa.block.common.SparklePillarBlock;
import com.dublikunt.astelfa.block.common.SparkleSaplingBlock;
import com.dublikunt.astelfa.block.custom.*;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.world.feature.tree.SilverWoodSaplingGenerator;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;

public class ModBlocks {
    public static final InfuseTableBlock INFUSING_TABLE_BLOCK = registerBlock(new InfuseTableBlock(
            FabricBlockSettings.copyOf(Blocks.STONE).strength(2f).requiresTool().nonOpaque()), "infuse_table");
    public static final ManaFillerBlock MANA_FILLER_BLOCK = registerBlock(new ManaFillerBlock(
            FabricBlockSettings.copyOf(Blocks.STONE).nonOpaque()), "mana_filler");
    public static final SculkStatueBlock SCULK_STATUE_BLOCK = registerBlock(new SculkStatueBlock(
            FabricBlockSettings.copyOf(Blocks.SCULK).strength(3f).requiresTool().nonOpaque()), "sculk_statue");

    public static final SparklePillarBlock SILVER_LOG = registerBlock(new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.OAK_LOG).mapColor(DyeColor.LIGHT_GRAY)), "silver_log");
    public static final SparklePillarBlock SILVER_WOOD = registerBlock(new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.OAK_WOOD).mapColor(DyeColor.LIGHT_GRAY)), "silver_wood");
    public static final SparklePillarBlock STRIPPED_SILVER_LOG = registerBlock(new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.STRIPPED_OAK_LOG).mapColor(DyeColor.LIGHT_GRAY)), "stripped_silver_log");
    public static final SparklePillarBlock STRIPPED_SILVER_WOOD = registerBlock(new SparklePillarBlock(FabricBlockSettings.copyOf(
            Blocks.STRIPPED_OAK_WOOD).mapColor(DyeColor.LIGHT_GRAY)), "stripped_silver_wood");
    public static final SparkleLeavesBlock SILVER_LEAVES = registerBlock(new SparkleLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES)
            .mapColor(DyeColor.LIGHT_GRAY)), "silver_leaves");
    public static final SparkleSaplingBlock SILVER_SAPLING = registerBlock(new SparkleSaplingBlock(new SilverWoodSaplingGenerator(),
            FabricBlockSettings.copyOf(Blocks.OAK_SAPLING)
                    .mapColor(DyeColor.LIGHT_GRAY)), "silver_sapling");

    public static final Block SILVER_PLANKS = registerBlock(new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .mapColor(DyeColor.LIGHT_GRAY)), "silver_planks");
    public static final Block CARVED_SILVER_WOOD = registerBlock(new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)
            .mapColor(DyeColor.LIGHT_GRAY)), "carved_silver_wood");
    public static final ButtonBlock SILVER_WOOD_BUTTON = registerBlock(new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD)
            .noCollision().strength(0.5F), BlockSetType.OAK, 30, true), "silver_wood_button");
    public static final DoorBlock SILVER_WOOD_DOOR = registerBlock(new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).
            mapColor(SILVER_PLANKS.getDefaultMapColor()).strength(3.0F).nonOpaque(), BlockSetType.OAK), "silver_wood_door");
    public static final TrapdoorBlock SILVER_WOOD_TRAPDOOR = registerBlock(new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).
            mapColor(SILVER_PLANKS.getDefaultMapColor()).strength(3.0F).nonOpaque(), BlockSetType.OAK), "silver_wood_trapdoor");
    public static final PressurePlateBlock SILVER_WOOD_PRESSURE_PLATE = registerBlock(new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING,
            FabricBlockSettings.copyOf(Blocks.OAK_WOOD).mapColor(SILVER_PLANKS.getDefaultMapColor()).noCollision().strength(0.5F),
            BlockSetType.OAK
    ), "silver_wood_pressure_pate");
    public static final FenceBlock SILVER_WOOD_FENCE = registerBlock(new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).
            mapColor(SILVER_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD)), "silver_wood_fence");
    public static final FenceGateBlock SILVER_WOOD_FENCE_GATE = registerBlock(new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).
            mapColor(SILVER_PLANKS.getDefaultMapColor()).strength(2.0F, 3.0F), WoodType.OAK), "silver_wood_fence_gate");
    public static final StairsBlock SILVER_WOOD_STAIRS = registerBlock(new StairsBlock(SILVER_PLANKS.getDefaultState(),
            FabricBlockSettings.copyOf(SILVER_PLANKS)), "silver_wood_stairs");
    public static final SlabBlock SILVER_WOOD_SLAB = registerBlock(new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).
            mapColor(SILVER_PLANKS.getDefaultMapColor()).requiresTool().strength(1.5F, 6.0F)), "silver_wood_slab");
    public static final AquaticTorchBlock AQUATIC_TORCH = registerBlockWithoutItem(new AquaticTorchBlock(FabricBlockSettings.copyOf(Blocks.TORCH)
            .noCollision().breakInstantly().luminance(15).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME), "aquatic_torch");
    public static final AquaticWallTorchBlock AQUATIC_WALL_TORCH = registerBlockWithoutItem(new AquaticWallTorchBlock(FabricBlockSettings.copyOf(Blocks.TORCH)
            .noCollision().breakInstantly().luminance(15).sounds(BlockSoundGroup.WOOD), ParticleTypes.FLAME), "aquatic_wall_torch");

    private static <T extends Block> T registerBlock(T block, String name) {
        registerBlockItem(block, name);
        return Registry.register(Registries.BLOCK, Helpers.id(name), block);
    }

    private static <T extends Block> T registerBlockWithoutItem(T block, String name) {
        return Registry.register(Registries.BLOCK, Helpers.id(name), block);
    }

    private static <T extends Block> Item registerBlockItem(T block, String name) {
        return Registry.register(Registries.ITEM, Helpers.id(name),
                new BlockItem(block, new FabricItemSettings()));
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
