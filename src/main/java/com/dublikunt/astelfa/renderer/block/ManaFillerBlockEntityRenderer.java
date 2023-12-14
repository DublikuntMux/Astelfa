package com.dublikunt.astelfa.renderer.block;

import com.dublikunt.astelfa.block.custom.ManaFillerBlock;
import com.dublikunt.astelfa.block.entity.ManaFillerBlockEntity;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;

public class ManaFillerBlockEntityRenderer implements BlockEntityRenderer<ManaFillerBlockEntity> {
    public ManaFillerBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(@NotNull ManaFillerBlockEntity entity, float tickDelta, @NotNull MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack itemStack = entity.getRenderStacks().get(0);
        matrices.push();
        matrices.translate(0.49, 0.75, 0.49);
        matrices.scale(0.35f, 0.35f, 0.35f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.5708f));

        switch (entity.getCachedState().get(ManaFillerBlock.FACING)) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(Helpers.toRadians(170)));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(Helpers.toRadians(-229)));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(0));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(Helpers.toRadians(57)));
        }

        itemRenderer.renderItem(itemStack, ModelTransformationMode.GUI, Helpers.getLightLevel(entity.getWorld(),
                        entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();
    }
}
