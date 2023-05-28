package com.dublikunt.astelfa;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.enchantment.ModEnchantments;
import com.dublikunt.astelfa.entity.ModEntitys;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.helper.Logger;
import com.dublikunt.astelfa.item.ModItems;
import com.dublikunt.astelfa.networking.ModMessages;
import com.dublikunt.astelfa.particle.ModParticle;
import com.dublikunt.astelfa.recipe.ModRecipes;
import com.dublikunt.astelfa.screen.ModScreenHandlers;
import com.dublikunt.astelfa.world.gen.ModWorldGen;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib.GeckoLib;

public class Astelfa implements ModInitializer {
    public static final String MOD_ID = "astelfa";
    public static final String MOD_NAME = "Astelfa Mod";

    public static AstelfaConfig config = null;

    @Override
    public void onInitialize() {
        config = AstelfaConfig.load();
        Logger.debug("Mod loading start...");

        GeckoLib.initialize();

        ModMessages.registerC2SPackets();
        ModParticle.register();
        ModItems.register();
        ModBlocks.register();
        ModBlocks.registerFlammableBlocks();
        ModBlocks.registerStrippables();
        ModBlockEntities.register();
        ModRecipes.register();
        ModEntitys.register();
        ModEnchantments.register();
        ModScreenHandlers.registry();
        ModWorldGen.generateWorldGen();
        ModFluids.register();

        Logger.debug("Mod loading complete!");
    }
}
