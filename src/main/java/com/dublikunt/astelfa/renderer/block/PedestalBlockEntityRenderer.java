package com.dublikunt.astelfa.renderer.block;

import com.dublikunt.astelfa.block.custom.ManaFillerBlock;
import com.dublikunt.astelfa.block.entity.PedestalBlockEntity;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

import java.util.List;

public class PedestalBlockEntityRenderer implements BlockEntityRenderer<PedestalBlockEntity> {
    public PedestalBlockEntityRenderer(BlockEntityRendererFactory.Context context) {
    }

    @Override
    public void render(@NotNull PedestalBlockEntity entity, float tickDelta, @NotNull MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        List<ItemStack> itemStacks = entity.getRenderStacks();
        ItemStack firstElement = ItemStack.EMPTY;
        if (!itemStacks.isEmpty()) {
            firstElement = itemStacks.get(0);
            itemStacks.remove(0);
        }

        matrices.push();

        float[] angles = new float[itemStacks.size()];

        float anglePer = (float) 360 / itemStacks.size();
        float totalAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            angles[i] = totalAngle += anglePer;
        }

        float time = MinecraftClient.getInstance().world.getTime() + tickDelta;

        for (int i = 0; i < itemStacks.size(); i++) {
            matrices.push();
            matrices.translate(0.5, 1.25, 0.5);
            matrices.multiply(new Quaternionf().rotateY(Helpers.toRadians(angles[i] + time)));
            matrices.translate(MathHelper.clamp((double) itemStacks.size() / 14, 1.2, 2.3), 0, 0.25);
            matrices.multiply(new Quaternionf().rotateY(Helpers.toRadians(90)));
            matrices.translate(0, 0.08 * Math.sin((time + i * 20) / 5), 0);
            ItemStack stack = itemStacks.get(i);
            if (!stack.isEmpty()) {
                itemRenderer.renderItem(stack, ModelTransformationMode.GROUND,
                        Helpers.getLightLevel(entity.getWorld(), entity.getPos()),
                        OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
            }
            matrices.pop();
        }

        matrices.push();
        matrices.translate(0.49, 1.1, 0.49);
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(1.5708f));

        switch (entity.getCachedState().get(ManaFillerBlock.FACING)) {
            case NORTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(Helpers.toRadians(170)));
            case EAST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(Helpers.toRadians(-229)));
            case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(0));
            case WEST -> matrices.multiply(RotationAxis.POSITIVE_Z.rotation(Helpers.toRadians(57)));
        }

        itemRenderer.renderItem(firstElement, ModelTransformationMode.GUI,
                Helpers.getLightLevel(entity.getWorld(), entity.getPos()),
                OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), 1);
        matrices.pop();

        matrices.pop();
    }
}
