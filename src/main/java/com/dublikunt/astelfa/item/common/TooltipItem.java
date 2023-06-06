package com.dublikunt.astelfa.item.common;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TooltipItem extends Item {
    private final Text tooltip;

    public TooltipItem(Text tooltip, Settings settings) {
        super(settings);
        this.tooltip = tooltip;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(this.tooltip);
    }
}
