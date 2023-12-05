package com.dublikunt.astelfa.item.common;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipItem extends Item {
    private final Text base_tooltip;

    public TooltipItem(Text tooltip, Settings settings) {
        super(settings);
        this.base_tooltip = tooltip;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, @NotNull List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.base_tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
