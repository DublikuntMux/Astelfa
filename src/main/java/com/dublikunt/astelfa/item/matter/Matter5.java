package com.dublikunt.astelfa.item.matter;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Matter5 extends Item {
    public Matter5(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astelfa.matter.tooltip").formatted(Formatting.DARK_PURPLE));
    }

}
