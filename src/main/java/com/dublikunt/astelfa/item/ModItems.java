package com.dublikunt.astelfa.item;

import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.entity.ModEntitys;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.animated.PhilosophersStone;
import com.dublikunt.astelfa.item.common.ManaDetectPaper;
import com.dublikunt.astelfa.item.common.TooltipItem;
import com.dublikunt.astelfa.item.trinket.AdosChains;
import com.dublikunt.astelfa.item.trinket.HartRing;
import com.dublikunt.astelfa.item.trinket.ManaGlasses;
import com.dublikunt.astelfa.item.trinket.RingBelt;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

public class ModItems {
    public static final TooltipItem MATTER_1 = registerItem(new TooltipItem(Text.translatable("item.astelfa.matter.tooltip")
            .formatted(Formatting.RED), new FabricItemSettings()), "matter1");
    public static final TooltipItem MATTER_2 = registerItem(new TooltipItem(Text.translatable("item.astelfa.matter.tooltip")
            .formatted(Formatting.YELLOW), new FabricItemSettings()), "matter2");
    public static final TooltipItem MATTER_3 = registerItem(new TooltipItem(Text.translatable("item.astelfa.matter.tooltip")
            .formatted(Formatting.GREEN), new FabricItemSettings().rarity(Rarity.RARE)), "matter3");
    public static final TooltipItem MATTER_4 = registerItem(new TooltipItem(Text.translatable("item.astelfa.matter.tooltip")
            .formatted(Formatting.AQUA), new FabricItemSettings().rarity(Rarity.RARE)), "matter4");
    public static final TooltipItem MATTER_5 = registerItem(new TooltipItem(Text.translatable("item.astelfa.matter.tooltip")
            .formatted(Formatting.DARK_PURPLE), new FabricItemSettings().rarity(Rarity.EPIC)), "matter5");
    public static final TooltipItem MATTER_6 = registerItem(new TooltipItem(Text.translatable("item.astelfa.matter.tooltip")
            .formatted(Formatting.WHITE), new FabricItemSettings().rarity(Rarity.EPIC)), "matter6");
    public static final TooltipItem REVELATION_MIRROR = registerItem(new TooltipItem(Text.translatable("item.astelfa.revelation_mirror.tooltip"),
            new FabricItemSettings()), "revelation_mirror");
    public static final TooltipItem AETHERIUM_INGOT = registerItem(new TooltipItem(Text.translatable("item.astelfa.aetherium_ingot.tooltip"),
            new FabricItemSettings()), "aetherium_ingot");

    public static final Item RAW_LUMINITE = registerItem(new Item(new FabricItemSettings()), "raw_luminite");
    public static final TooltipItem LUMINITE_INGOT = registerItem(new TooltipItem(Text.translatable("item.astelfa.luminite_ingot.tooltip"),
            new FabricItemSettings()), "luminite_ingot");
    public static final TooltipItem ESSENTIAL_FUEL = registerItem(new TooltipItem(Text.translatable("item.astelfa.essential_fuel.tooltip"),
            new FabricItemSettings()), "essential_fuel");
    public static final ManaDetectPaper MANA_DETECT_PAPER = registerItem(new ManaDetectPaper(new FabricItemSettings()), "mana_detect_paper");
    public static final TooltipItem USED_MANA_DETECT_PAPER = registerItem(new TooltipItem(Text.translatable("item.astelfa.mana_detect_paper.tooltip"),
            new FabricItemSettings().maxCount(1)), "used_mana_detect_paper");

    public static final HartRing HART_RING = registerItem(new HartRing(new FabricItemSettings().rarity(Rarity.RARE)), "hart_ring");
    public static final AdosChains ADOS_CHAINS = registerItem(new AdosChains(new FabricItemSettings().rarity(Rarity.RARE)), "ados_chains");
    public static final RingBelt RING_BELT = registerItem(new RingBelt(new FabricItemSettings().rarity(Rarity.UNCOMMON)), "ring_belt");
    public static final ManaGlasses MANA_GLASSES = registerItem(new ManaGlasses(new FabricItemSettings().rarity(Rarity.UNCOMMON)), "mana_glasses");

    public static final PhilosophersStone PHILOSOPHERS_STONE = registerItem(new PhilosophersStone(new FabricItemSettings().rarity(Rarity.RARE).maxCount(1)),
            "philosophers_stone");

    public static final BucketItem MANA_BUCKET = registerItem(new BucketItem(ModFluids.STILL_MANA_FLUID, new FabricItemSettings().recipeRemainder(Items.BUCKET).maxCount(1)), "mana_bucket");

    public static final SpawnEggItem IRRITANT_EGG = registerItem(new SpawnEggItem(ModEntitys.IRRITANT, 0x101B21,
            0x009195, new FabricItemSettings()), "irritant_egg");
    public static final SpawnEggItem SLIME_BOSS_EGG = registerItem(new SpawnEggItem(ModEntitys.SLIME_BOSS, 0x163614,
            0x2CDE1F, new FabricItemSettings()), "slime_boss_egg");
    public static final VerticallyAttachableBlockItem AQUATIC_TORCH_ITEM = registerItem(new VerticallyAttachableBlockItem(ModBlocks.AQUATIC_TORCH, ModBlocks.AQUATIC_WALL_TORCH,
            new Item.Settings(), Direction.DOWN), "aquatic_torch");

    private static <T extends Item> T registerItem(T item, String name) {
        return Registry.register(Registries.ITEM, Helpers.id(name), item);
    }

    private static void addItemsToIngredientItemGroup(@NotNull FabricItemGroupEntries entries) {
        entries.add(AETHERIUM_INGOT);
        entries.add(ESSENTIAL_FUEL);
    }

    public static void registerModItems() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}
