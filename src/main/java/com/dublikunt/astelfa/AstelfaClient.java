package com.dublikunt.astelfa;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.entity.ModEntitys;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.helper.Logger;
import com.dublikunt.astelfa.networking.ModMessages;
import com.dublikunt.astelfa.renderer.block.InfuseTableBlockEntityRenderer;
import com.dublikunt.astelfa.screen.InfuseTableScreen;
import com.dublikunt.astelfa.screen.ManaFillerScreen;
import com.dublikunt.astelfa.screen.ModScreenHandlers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.network.GeckoLibNetwork;

public class AstelfaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Logger.debug("Mod client loading...");

        GeckoLibNetwork.registerClientReceiverPackets();

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_SAPLING, RenderLayer.getCutout());

        ModMessages.registerS2CPackets();
        ModEntitys.registerRenderer();
        HandledScreens.register(ModScreenHandlers.INFUSE_TABLE_SCREEN_HANDLER, InfuseTableScreen::new);
        HandledScreens.register(ModScreenHandlers.MANA_FILLER_SCREEN_HANDLER, ManaFillerScreen::new);
        BlockEntityRendererFactories.register(ModBlockEntities.INFUSE_TABLE_BLOCK_ENTITY_TYPE, InfuseTableBlockEntityRenderer::new);

        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_MANA_FLUID, ModFluids.FLOWING_MANA_FLUID,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0xA138C1E0
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_MANA_FLUID, ModFluids.FLOWING_MANA_FLUID);

        Logger.debug("Mod client loading complete!");
    }
}
