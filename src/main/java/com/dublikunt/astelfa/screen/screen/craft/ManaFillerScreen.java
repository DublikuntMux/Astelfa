package com.dublikunt.astelfa.screen.screen.craft;

import com.dublikunt.astelfa.helper.FluidStack;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.renderer.fluid.FluidStackRenderer;
import com.dublikunt.astelfa.screen.handler.ManaFillerScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ManaFillerScreen extends HandledScreen<ManaFillerScreenHandler> {
    private static final Identifier TEXTURE = Helpers.id("textures/gui/mana_filler.png");
    private FluidStackRenderer fluidStackRenderer;

    public ManaFillerScreen(ManaFillerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
        assignFluidStackRenderer();
    }

    @Override
    protected void drawBackground(@NotNull DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        renderProgressArrow(context, x, y);
        this.fluidStackRenderer.drawFluid(context, this.handler.fluidStack, x + 37, y + 13, 16, 61,
                FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20);
    }

    private void assignFluidStackRenderer() {
        this.fluidStackRenderer = new FluidStackRenderer(FluidStack.convertDropletsToMb(FluidConstants.BUCKET) * 20,
                true, 16, 61);
    }

    private void renderFluidTooltip(DrawContext context, int mouseX, int mouseY, int x, int y,
                                    FluidStack fluidStack, int offsetX, int offsetY, FluidStackRenderer renderer) {
        if (isMouseAboveArea(mouseX, mouseY, x, y, offsetX, offsetY, renderer)) {
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, renderer.getTooltip(fluidStack, TooltipContext.Default.BASIC), mouseX - x, mouseY - y);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        renderFluidTooltip(context, mouseX, mouseY, x, y, this.handler.fluidStack, 37, 13, this.fluidStackRenderer);
    }

    private void renderProgressArrow(DrawContext context, int x, int y) {
        if (this.handler.isCrafting()) {
            context.drawTexture(TEXTURE, x + 76, y + 31, 176, 0, this.handler.getScaledProgress(), 25);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, @NotNull FluidStackRenderer renderer) {
        return Helpers.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }
}
