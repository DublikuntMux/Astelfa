package com.dublikunt.astelfa.world.feature;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.BlobFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.StraightTrunkPlacer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> SILVER_WOOD_KEY = registerKey("silver_wood");

    public static final RegistryKey<ConfiguredFeature<?, ?>> LUMINITE_ORE_KEY = registerKey("luminite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> NETHER_LUMINITE_ORE_KEY = registerKey("nether_luminite_ore");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ENDSTONE_LUMINITE_ORE_KEY = registerKey("endstone_luminite_ore");

    public static void bootstrap(@NotNull Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, SILVER_WOOD_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.SILVER_LOG),
                new StraightTrunkPlacer(6, 7, 4),
                BlockStateProvider.of(ModBlocks.SILVER_LEAVES),
                new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 6),
                new TwoLayersFeatureSize(4, 0, 5)).build());

        RuleTest stoneReplaceables = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest netherReplaceables = new TagMatchRuleTest(BlockTags.BASE_STONE_NETHER);
        RuleTest endstoneReplaceables = new BlockMatchRuleTest(Blocks.END_STONE);

        List<OreFeatureConfig.Target> overworldLuminiteOres =
                List.of(OreFeatureConfig.createTarget(stoneReplaceables, ModBlocks.LUMINITE_ORE.getDefaultState()),
                        OreFeatureConfig.createTarget(deepslateReplaceables, ModBlocks.DEEPSLATE_LUMINITE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> netherLuminiteOres =
                List.of(OreFeatureConfig.createTarget(netherReplaceables, ModBlocks.NETHERRACK_LUMINITE_ORE.getDefaultState()));
        List<OreFeatureConfig.Target> endLuminiteOres =
                List.of(OreFeatureConfig.createTarget(endstoneReplaceables, ModBlocks.ENDSTONE_LUMINITE_ORE.getDefaultState()));

        register(context, LUMINITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(overworldLuminiteOres, 12));
        register(context, NETHER_LUMINITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(netherLuminiteOres, 12));
        register(context, ENDSTONE_LUMINITE_ORE_KEY, Feature.ORE, new OreFeatureConfig(endLuminiteOres, 12));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, Helpers.id(name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(@NotNull Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
