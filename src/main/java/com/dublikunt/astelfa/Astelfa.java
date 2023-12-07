package com.dublikunt.astelfa;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.comand.ModCommands;
import com.dublikunt.astelfa.effects.ModEffects;
import com.dublikunt.astelfa.enchantment.ModEnchantments;
import com.dublikunt.astelfa.entity.ModEntitys;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.generator.LootTableModifiers;
import com.dublikunt.astelfa.helper.chunkstorage.DataObjectRegistry;
import com.dublikunt.astelfa.helper.common.Logger;
import com.dublikunt.astelfa.item.ModItemGroup;
import com.dublikunt.astelfa.item.ModItems;
import com.dublikunt.astelfa.item.common.ManaDetectPaper;
import com.dublikunt.astelfa.networking.ModMessages;
import com.dublikunt.astelfa.particle.ModParticle;
import com.dublikunt.astelfa.recipe.ModRecipes;
import com.dublikunt.astelfa.screen.ModScreenHandlers;
import com.dublikunt.astelfa.world.gen.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib.GeckoLib;

import java.util.Random;

public class Astelfa implements ModInitializer {
    public static final String MOD_ID = "astelfa";
    public static final String MOD_NAME = "Astelfa Mod";

    public static final Random RANDOM = new Random();

    public static final AstelfaConfig config = AstelfaConfig.load();

    @Override
    public void onInitialize() {
        Logger.debug("Mod loading start...");

        Logger.debug("  Initialize Geckolib.");
        GeckoLib.initialize();

        Logger.debug("  Register C2S packets.");
        ModMessages.registerC2SPackets();
        Logger.debug("  Register particles on server.");
        ModParticle.registerServer();
        Logger.debug("  Register item groups.");
        ModItemGroup.registerGroup();
        Logger.debug("  Register items.");
        ModItems.registerModItems();
        Logger.debug("      Register tooltip adding.");
        ManaDetectPaper.registerTooltipAdding();
        Logger.debug("  Register flammable blocks.");
        ModBlocks.registerFlammableBlocks();
        Logger.debug("  Register stoppable blocks.");
        ModBlocks.registerStrippables();
        Logger.debug("  Register block entities.");
        ModBlockEntities.register();
        Logger.debug("  Register recipes.");
        ModRecipes.register();
        Logger.debug("  Register entity.");
        ModEntitys.register();
        Logger.debug("  Register enchantments.");
        ModEnchantments.register();
        Logger.debug("  Register screen handlers.");
        ModScreenHandlers.registry();
        Logger.debug("  Register world gen.");
        ModWorldGen.generateWorldGen();
        Logger.debug("  Register fluids.");
        ModFluids.register();
        Logger.debug("  Modify loot tables.");
        LootTableModifiers.modifyLootTables();
        Logger.debug("  Register chunk data objects.");
        DataObjectRegistry.initialize();
        Logger.debug("  Register mod commands.");
        ModCommands.RegisterCommands();
        Logger.debug("  Register mod effects.");
        ModEffects.registerEffects();

        Logger.debug("Mod loading complete!");
    }
}
