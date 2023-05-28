package com.dublikunt.astelfa.world.feature;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_WOOD_KEY =
            RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Helpers.id("silver_wood"));
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_WOOD_SPAWN_KEY =
            RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Helpers.id("silver_wood_spawn"));

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(@NotNull Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    public static void register(@NotNull Registerable<ConfiguredFeature<?, ?>> context) {
        var placedFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        register(context, SILVER_WOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.SILVER_LOG),
                new StraightTrunkPlacer(6, 7, 4),
                BlockStateProvider.of(ModBlocks.SILVER_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 6),
                new TwoLayersFeatureSize(4, 0, 5)).build());

        register(context, SILVER_WOOD_SPAWN_KEY, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfig(List.of(new RandomFeatureEntry(placedFeatureRegistryEntryLookup.getOrThrow(ModPlacedFeatures.SILVER_WOOD_PLACED_KEY),
                        0.5f)), placedFeatureRegistryEntryLookup.getOrThrow(ModPlacedFeatures.SILVER_WOOD_PLACED_KEY)));
    }

}
