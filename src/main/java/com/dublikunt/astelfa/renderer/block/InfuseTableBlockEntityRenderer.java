package com.dublikunt.astelfa.renderer.block;

import com.dublikunt.astelfa.block.custom.InfuseTableBlock;
import com.dublikunt.astelfa.block.entity.InfuseTableBlockEntity;
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

public class InfuseTableBlockEntityRenderer implements BlockEntityRenderer<InfuseTableBlockEntity> {
    public InfuseTableBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(@NotNull InfuseTableBlockEntity entity, float tickDelta, @NotNull MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack itemStack = entity.getRenderStacks().get(0);
        matrices.push();
        matrices.translate(0.49, 0.82, 0.49);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.5708f));

        switch (entity.getCachedState().get(InfuseTableBlock.FACING)) {
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
