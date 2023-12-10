package com.dublikunt.astelfa;

import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;

public class ModCallbacks {
    public static void registerCallbacks() {
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
}
