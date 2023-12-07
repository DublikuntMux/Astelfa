package com.dublikunt.astelfa.item.common;

import com.dublikunt.astelfa.air_mana.ManaAmount;
import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;
import org.jetbrains.annotations.NotNull;

public class ManaDetectPaper extends TooltipItem {
    public ManaDetectPaper(@NotNull Settings settings) {
        super(Text.translatable("item.astelfa.mana_detect_paper.tooltip"), settings);
    }

    public static void registerTooltipAdding() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() == ModItems.USED_MANA_DETECT_PAPER) {
                NbtCompound nbtData = stack.getNbt();
                if (nbtData != null) {
                    lines.add(Text.translatable("item.astelfa.used_mana_detect_paper.tooltip",
                            nbtData.getInt("manAmount"),
                            nbtData.getInt("chunkPosX"),
                            nbtData.getInt("chunkPosZ")));
                }
            }
        });
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            WorldChunk chunk = world.getWorldChunk(user.getBlockPos());

            ItemStack new_paper = new ItemStack(ModItems.USED_MANA_DETECT_PAPER, 1);

            NbtCompound compound = new NbtCompound();
            compound.putInt("manAmount", ManaAmount.getOrCreateManaData(chunk).mana_amount);
            compound.putInt("chunkPosX", chunk.getPos().getCenterX());
            compound.putInt("chunkPosZ", chunk.getPos().getCenterZ());

            new_paper.setNbt(compound);

            user.getStackInHand(hand).setCount(user.getStackInHand(hand).getCount() - 1);
            user.getInventory().insertStack(new_paper);
        }
        return super.use(world, user, hand);
    }
}
