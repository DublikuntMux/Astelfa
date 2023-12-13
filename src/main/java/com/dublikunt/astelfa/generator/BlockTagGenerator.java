package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.item.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagGenerator extends FabricTagProvider.BlockTagProvider {
    public BlockTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ModTags.Blocks.SILVER_WOOD)
                .add(ModBlocks.SILVER_LOG)
                .add(ModBlocks.SILVER_WOOD)
                .add(ModBlocks.STRIPPED_SILVER_LOG)
                .add(ModBlocks.STRIPPED_SILVER_WOOD);

        getOrCreateTagBuilder(ModTags.Blocks.SILVER_PLANKS)
                .add(ModBlocks.SILVER_PLANKS)
                .add(ModBlocks.CARVED_SILVER_WOOD);

        getOrCreateTagBuilder(BlockTags.LEAVES)
                .add(ModBlocks.SILVER_LEAVES);

        getOrCreateTagBuilder(BlockTags.LOGS)
                .add(ModTags.Blocks.SILVER_WOOD.id());

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN)
                .add(ModTags.Blocks.SILVER_WOOD.id());

        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.SCULK_STATUE_BLOCK)
                .add(ModBlocks.LUMINITE_ORE)
                .add(ModBlocks.PEDESTAL_BLOCK)
                .add(ModBlocks.DEEPSLATE_LUMINITE_ORE)
                .add(ModBlocks.NETHERRACK_LUMINITE_ORE)
                .add(ModBlocks.ENDSTONE_LUMINITE_ORE);

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.MANA_FILLER_BLOCK)
                .add(ModBlocks.INFUSING_TABLE_BLOCK)
                .add(ModBlocks.SILVER_LOG)
                .add(ModBlocks.SILVER_WOOD)
                .add(ModBlocks.STRIPPED_SILVER_LOG)
                .add(ModBlocks.STRIPPED_SILVER_WOOD)
                .add(ModTags.Blocks.SILVER_PLANKS.id())
                .add(ModBlocks.SILVER_WOOD_FENCE)
                .add(ModBlocks.SILVER_WOOD_FENCE_GATE)
                .add(ModBlocks.SILVER_WOOD_PRESSURE_PLATE)
                .add(ModBlocks.SILVER_WOOD_BUTTON)
                .add(ModBlocks.SILVER_WOOD_SLAB)
                .add(ModBlocks.SILVER_WOOD_STAIRS)
                .add(ModBlocks.SILVER_WOOD_TRAPDOOR)
                .add(ModBlocks.SILVER_WOOD_DOOR);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.INFUSING_TABLE_BLOCK)
                .add(ModBlocks.PEDESTAL_BLOCK)
                .add(ModBlocks.LUMINITE_ORE)
                .add(ModBlocks.DEEPSLATE_LUMINITE_ORE)
                .add(ModBlocks.NETHERRACK_LUMINITE_ORE)
                .add(ModBlocks.ENDSTONE_LUMINITE_ORE);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SCULK_STATUE_BLOCK);

        getOrCreateTagBuilder(BlockTags.PLANKS)
                .add(ModTags.Blocks.SILVER_PLANKS.id());

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.SILVER_SAPLING);

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.SILVER_WOOD_BUTTON);

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.SILVER_WOOD_DOOR);

        getOrCreateTagBuilder(BlockTags.WOODEN_FENCES)
                .add(ModBlocks.SILVER_WOOD_FENCE);

        getOrCreateTagBuilder(BlockTags.FENCE_GATES)
                .add(ModBlocks.SILVER_WOOD_FENCE_GATE);

        getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.SILVER_WOOD_PRESSURE_PLATE);

        getOrCreateTagBuilder(BlockTags.WOODEN_SLABS)
                .add(ModBlocks.SILVER_WOOD_SLAB);

        getOrCreateTagBuilder(BlockTags.WOODEN_STAIRS)
                .add(ModBlocks.SILVER_WOOD_STAIRS);

        getOrCreateTagBuilder(BlockTags.WOODEN_TRAPDOORS)
                .add(ModBlocks.SILVER_WOOD_TRAPDOOR);
    }
}
