package com.dublikunt.astelfa.client;

import com.dublikunt.astelfa.Astelfa;
import com.dublikunt.astelfa.air_mana.IPlayerDataSaver;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;

public class ManaInfoHudOverlay implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            if (!client.player.isSpectator()) {
                if (((IPlayerDataSaver) client.player).astelfa$getShowManaInfo()) {
                    NbtCompound compound = ((IPlayerDataSaver) client.player).astelfa$getPersistentData();

                    Text text1 = Text.translatable("hud.astelfa.mana_info.1", compound.getInt("mama_around"));
                    Text text2 = Text.translatable("hud.astelfa.mana_info.2", compound.getInt("maximum_amount"));

                    int width = client.getWindow().getScaledWidth();
                    int height = client.getWindow().getScaledHeight();

                    drawContext.drawText(client.textRenderer, text1,
                            width / 2 + 120 + Astelfa.config.manaOverlayPosX,
                            height - 25 + Astelfa.config.manaOverlayPosY,
                            Colors.WHITE, true
                    );
                    drawContext.drawText(client.textRenderer, text2,
                            width / 2 + 120 + Astelfa.config.manaOverlayPosX,
                            height - 12 + Astelfa.config.manaOverlayPosY,
                            Colors.WHITE, true
                    );
                }
            }
        }
    }
}
