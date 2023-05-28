package com.dublikunt.astelfa.fluid;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.block.FluidBlock;
import net.minecraft.item.BucketItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Blocks;

public class ModFluids {
    public static ManaFluid STILL_MANA_FLUID;
    public static ManaFluid FLOWING_MANA_FLUID;
    public static Block MANA_FLUID_BLOCK;
    public static Item MANA_BUCKET;

    public static void register() {
        STILL_MANA_FLUID = Registry.register(Registries.FLUID, Helpers.id("mana_fluid"), new ManaFluid.Still());
        FLOWING_MANA_FLUID = Registry.register(Registries.FLUID, Helpers.id("flowing_mana_fluid"), new ManaFluid.Flowing());

        MANA_FLUID_BLOCK = Registry.register(Registries.BLOCK, Helpers.id("mana_fluid_block"),
                new FluidBlock(ModFluids.STILL_MANA_FLUID, FabricBlockSettings.copyOf(Blocks.WATER)){ });
        MANA_BUCKET = Registry.register(Registries.ITEM, Helpers.id("mana_bucket"),
                new BucketItem(ModFluids.STILL_MANA_FLUID, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)));

        ItemGroupEvents.modifyEntriesEvent(ModItems.MOD_GROUP).register(entries -> entries.add(MANA_BUCKET));
    }
}
