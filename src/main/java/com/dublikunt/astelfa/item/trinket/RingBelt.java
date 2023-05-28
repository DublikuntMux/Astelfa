package com.dublikunt.astelfa.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotAttributes;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class RingBelt extends TrinketItem {
    public RingBelt(Settings settings) {
        super(settings);
    }

    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        SlotAttributes.addSlotModifier(modifiers, "hand/ring", uuid, 5, EntityAttributeModifier.Operation.ADDITION);
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astelfa.ring_belt.tooltip"));
    }
}
