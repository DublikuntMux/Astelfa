package com.dublikunt.astelfa;

import com.dublikunt.astelfa.block.ModBlockEntities;
import com.dublikunt.astelfa.block.ModBlocks;
import com.dublikunt.astelfa.client.ManaInfoHudOverlay;
import com.dublikunt.astelfa.entity.ModEntitys;
import com.dublikunt.astelfa.fluid.ModFluids;
import com.dublikunt.astelfa.helper.common.Logger;
import com.dublikunt.astelfa.networking.ModMessages;
import com.dublikunt.astelfa.particle.ModParticle;
import com.dublikunt.astelfa.renderer.block.InfuseTableBlockEntityRenderer;
import com.dublikunt.astelfa.renderer.block.ManaFillerBlockEntityRenderer;
import com.dublikunt.astelfa.screen.ModScreenHandlers;
import com.dublikunt.astelfa.screen.screen.craft.InfuseTableScreen;
import com.dublikunt.astelfa.screen.screen.craft.ManaFillerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.network.GeckoLibNetwork;

public class AstelfaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Logger.debug("Mod client loading...");

        Logger.debug("  Register Geckolib networking.");
        GeckoLibNetwork.registerClientReceiverPackets();

        Logger.debug("  Register block renderers.");
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_WOOD_DOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SILVER_WOOD_TRAPDOOR, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MANA_FILLER_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.PEDESTAL_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AQUATIC_TORCH, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AQUATIC_WALL_TORCH, RenderLayer.getCutout());

        Logger.debug("  Register S2C packets.");
        ModMessages.registerS2CPackets();
        Logger.debug("  Register entity renderers.");
        ModEntitys.registerRenderer();
        Logger.debug("  Register particles on client.");
        ModParticle.registerClient();

        Logger.debug("  Register screens.");
        HandledScreens.register(ModScreenHandlers.INFUSE_TABLE_SCREEN_HANDLER, InfuseTableScreen::new);
        HandledScreens.register(ModScreenHandlers.MANA_FILLER_SCREEN_HANDLER, ManaFillerScreen::new);

        Logger.debug("  Register block entity renderers.");
        BlockEntityRendererFactories.register(ModBlockEntities.INFUSE_TABLE_BLOCK_ENTITY_TYPE, InfuseTableBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.MANA_FILLER_BLOCK_ENTITY_TYPE, ManaFillerBlockEntityRenderer::new);

        Logger.debug("  Register fluid renderers.");
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_MANA_FLUID, ModFluids.FLOWING_MANA_FLUID,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0xA138C1E0
                ));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_MANA_FLUID, ModFluids.FLOWING_MANA_FLUID);

        Logger.debug("  Register hud overlays.");
        HudRenderCallback.EVENT.register(new ManaInfoHudOverlay());

        Logger.debug("Mod client loading complete!");
    }
}
