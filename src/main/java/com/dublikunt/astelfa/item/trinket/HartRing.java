package com.dublikunt.astelfa.item.trinket;

import com.google.common.collect.Multimap;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class HartRing extends TrinketItem {

    public HartRing(Settings settings) {
        super(settings);
    }

    @Override
    public void tick(@NotNull ItemStack stack, SlotReference slot, @NotNull LivingEntity entity) {
        entity.setStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10 * 20, 3, true, false, false), entity);
        stack.damage(1, entity, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, UUID uuid) {
        var modifiers = super.getModifiers(stack, slot, entity, uuid);
        modifiers.put(EntityAttributes.GENERIC_MAX_HEALTH, new EntityAttributeModifier(uuid, "generic.max_health", 20, EntityAttributeModifier.Operation.ADDITION));
        return modifiers;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astelfa.hart_ring.tooltip"));
    }
}
