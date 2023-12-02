package com.dublikunt.astelfa.item;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ModItemGroup {
    public static final RegistryKey<ItemGroup> MOD_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, Helpers.id("item_group"));

    public static void registerGroup() {
        Registry.register(Registries.ITEM_GROUP, MOD_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.MATTER_1))
                .displayName(Text.translatable("itemGroup.astelfa.item_group"))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.MATTER_1);
                    entries.add(ModItems.MATTER_2);
                    entries.add(ModItems.MATTER_3);
                    entries.add(ModItems.MATTER_4);
                    entries.add(ModItems.MATTER_5);
                    entries.add(ModItems.MATTER_6);
                    entries.add(ModItems.MANA_INGOT);
                    entries.add(ModItems.ESSENTIAL_FUEL);

                    entries.add(ModItems.HART_RING);
                    entries.add(ModItems.ADOS_CHAINS);
                    entries.add(ModItems.PHILOSOPHERS_STONE);
                    entries.add(ModItems.RING_BELT);

                    entries.add(ModItems.MANA_BUCKET);

                    entries.add(ModItems.IRRITANT_EGG);
                    entries.add(ModItems.SLIME_BOSS_EGG);

                    entries.add(ModItems.AQUATIC_TORCH_ITEM);

                    entries.add(ModBlocks.INFUSING_TABLE_BLOCK);
                    entries.add(ModBlocks.MANA_FILLER_BLOCK);
                    entries.add(ModBlocks.SCULK_STATUE_BLOCK);

                    entries.add(ModBlocks.SILVER_LOG);
                    entries.add(ModBlocks.SILVER_WOOD);
                    entries.add(ModBlocks.STRIPPED_SILVER_LOG);
                    entries.add(ModBlocks.STRIPPED_SILVER_WOOD);
                    entries.add(ModBlocks.SILVER_LEAVES);
                    entries.add(ModBlocks.SILVER_SAPLING);

                    entries.add(ModBlocks.SILVER_PLANKS);
                    entries.add(ModBlocks.CARVED_SILVER_WOOD);
                    entries.add(ModBlocks.SILVER_WOOD_BUTTON);
                    entries.add(ModBlocks.SILVER_WOOD_DOOR);
                    entries.add(ModBlocks.SILVER_WOOD_TRAPDOOR);
                    entries.add(ModBlocks.SILVER_WOOD_PRESSURE_PLATE);
                    entries.add(ModBlocks.SILVER_WOOD_FENCE);
                    entries.add(ModBlocks.SILVER_WOOD_FENCE_GATE);
                    entries.add(ModBlocks.SILVER_WOOD_STAIRS);
                    entries.add(ModBlocks.SILVER_WOOD_SLAB);
                })
                .build());
    }
}
