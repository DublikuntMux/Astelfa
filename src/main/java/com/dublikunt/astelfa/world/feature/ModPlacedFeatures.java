package com.dublikunt.astelfa.world.feature;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModPlacedFeatures {
    public static final RegistryKey<PlacedFeature> SILVER_WOOD_CHECKED_KEY =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Helpers.id("silver_tree_checked"));
    public static final RegistryKey<PlacedFeature> SILVER_WOOD_PLACED_KEY =
            RegistryKey.of(RegistryKeys.PLACED_FEATURE, Helpers.id("silver_tree_placed"));

    public static void register(@NotNull Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        registerFeatures(context, SILVER_WOOD_CHECKED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILVER_WOOD_KEY),
                List.of(PlacedFeatures.wouldSurvive(ModBlocks.SILVER_SAPLING)));
        registerFeatures(context, SILVER_WOOD_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SILVER_WOOD_KEY),
                VegetationPlacedFeatures.modifiers(100));

    }

    private static void registerFeatures(@NotNull Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                         List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
