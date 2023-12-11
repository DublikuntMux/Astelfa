package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.criterion.ManaFillerCriterion;
import com.dublikunt.astelfa.criterion.ModCriterion;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class AdvancementGenerator extends FabricAdvancementProvider {
    protected AdvancementGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateAdvancement(Consumer<AdvancementEntry> consumer) {
        AdvancementEntry rootAdvancement = Advancement.Builder.create()
                .display(
                        ModBlocks.SILVER_LOG,
                        Text.translatable("advancement.astelfa.root.title"),
                        Text.translatable("advancement.astelfa.root.description"),
                        Helpers.id("textures/block/sliver_log.png"),
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_sliver_log", InventoryChangedCriterion.Conditions.items(ModBlocks.SILVER_LOG))
                .build(consumer, Astelfa.MOD_ID + "/root");

        AdvancementEntry manaFillerAdvancement = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModBlocks.MANA_FILLER_BLOCK,
                        Text.translatable("advancement.astelfa.mana_filler.title"),
                        Text.translatable("advancement.astelfa.mana_filler.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_mana_filler", InventoryChangedCriterion.Conditions.items(ModBlocks.MANA_FILLER_BLOCK))
                .build(consumer, Astelfa.MOD_ID + "/mana_filler_ad");

        AdvancementEntry addManaAdvancement = Advancement.Builder.create().parent(manaFillerAdvancement)
                .display(
                        ModItems.AETHERIUM_INGOT,
                        Text.translatable("advancement.astelfa.add_mana.title"),
                        Text.translatable("advancement.astelfa.add_mana.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_mana_ingot", ModCriterion.MANA_FILLER.create(new ManaFillerCriterion.Conditions(ModItems.AETHERIUM_INGOT)))
                .criterion("got_essential_fuel", ModCriterion.MANA_FILLER.create(new ManaFillerCriterion.Conditions(ModItems.ESSENTIAL_FUEL)))
                .build(consumer, Astelfa.MOD_ID + "/add_mana");

        AdvancementEntry aquaticTorchAdvancement = Advancement.Builder.create().parent(addManaAdvancement)
                .display(
                        ModBlocks.AQUATIC_TORCH,
                        Text.translatable("advancement.astelfa.aquatic_torch.title"),
                        Text.translatable("advancement.astelfa.aquatic_torch.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                )
                .criterion("got_aquatic_torch", InventoryChangedCriterion.Conditions.items(ModBlocks.AQUATIC_TORCH))
                .build(consumer, Astelfa.MOD_ID + "/aquatic_torch");

        AdvancementEntry StarManaAdvancement = Advancement.Builder.create().parent(addManaAdvancement)
                .display(
                        Items.NETHER_STAR,
                        Text.translatable("advancement.astelfa.star_mana.title"),
                        Text.translatable("advancement.astelfa.star_mana.description"),
                        null,
                        AdvancementFrame.CHALLENGE,
                        true,
                        true,
                        false
                )
                .criterion("got_star_from_mana", ModCriterion.MANA_FILLER.create(new ManaFillerCriterion.Conditions(Items.NETHER_STAR)))
                .build(consumer, Astelfa.MOD_ID + "/mana_star");

        AdvancementEntry infuserAdvancement = Advancement.Builder.create().parent(rootAdvancement)
                .display(
                        ModBlocks.INFUSING_TABLE_BLOCK,
                        Text.translatable("advancement.astelfa.infuser.title"),
                        Text.translatable("advancement.astelfa.infuser.description"),
                        null,
                        AdvancementFrame.TASK,
                        true,
                        true,
                        false
                )
                .criterion("got_infuser", InventoryChangedCriterion.Conditions.items(ModBlocks.INFUSING_TABLE_BLOCK))
                .build(consumer, Astelfa.MOD_ID + "/infuser");
    }
}
