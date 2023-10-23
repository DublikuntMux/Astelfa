package com.dublikunt.astelfa.fluid;

import com.dublikunt.astelfa.helper.Helpers;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModFluids {
    public static ManaFluid STILL_MANA_FLUID;
    public static ManaFluid FLOWING_MANA_FLUID;
    public static Block MANA_FLUID_BLOCK;

    public static void register() {
        STILL_MANA_FLUID = Registry.register(Registries.FLUID, Helpers.id("mana_fluid"), new ManaFluid.Still());
        FLOWING_MANA_FLUID = Registry.register(Registries.FLUID, Helpers.id("flowing_mana_fluid"), new ManaFluid.Flowing());

        MANA_FLUID_BLOCK = Registry.register(Registries.BLOCK, Helpers.id("mana_fluid_block"),
                new FluidBlock(ModFluids.STILL_MANA_FLUID, FabricBlockSettings.copyOf(Blocks.WATER)) {
                });
    }
}
