package com.dublikunt.astelfa.world.feature.tree;

import com.dublikunt.astelfa.world.feature.ModConfiguredFeatures;
import net.minecraft.block.sapling.SaplingGenerator;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public class SilverWoodSaplingGenerator extends SaplingGenerator {
    @Override
    protected RegistryKey<ConfiguredFeature<?, ?>> getTreeFeature(Random random, boolean bees) {
        return ModConfiguredFeatures.SILVER_WOOD_KEY;
    }
}
