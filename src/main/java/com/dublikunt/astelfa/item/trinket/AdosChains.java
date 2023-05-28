package com.dublikunt.astelfa.item.trinket;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdosChains extends TrinketItem {
    private final int MAX_FLY_TIME = 60 * 20;
    private double currentFlyTime = 0;

    public AdosChains(Settings settings) {
        super(settings);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        player.getAbilities().allowFlying = true;
        player.sendAbilitiesUpdate();
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().allowFlying = true;
            player.sendAbilitiesUpdate();

            if (player.getAbilities().flying && !(currentFlyTime >= MAX_FLY_TIME)) {
                currentFlyTime++;

                if (Math.random() < (double) 10 / 100) {
                    if (player.isSprinting()) {
                        player.addExhaustion(0.15F * 2);
                    } else {
                        player.addExhaustion(0.15F);
                    }
                    if (stack.getDamage() == stack.getMaxDamage() - 1) {
                        player.getAbilities().allowFlying = false;
                        player.getAbilities().flying = false;
                        player.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0));
                    }
                    stack.damage(1, player, e -> e.sendEquipmentBreakStatus(EquipmentSlot.LEGS));
                }
            }
            if ((currentFlyTime >= MAX_FLY_TIME)) {
                player.getAbilities().allowFlying = false;
                player.sendAbilitiesUpdate();
            }
            if (!(player.getAbilities().flying) && (currentFlyTime > 0)) {
                currentFlyTime = currentFlyTime - 0.3;
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        PlayerEntity player = (PlayerEntity) entity;
        if (!player.getAbilities().creativeMode && !player.isSpectator()) {
            player.getAbilities().allowFlying = false;
            player.getAbilities().flying = false;
            player.sendAbilitiesUpdate();
        }
        currentFlyTime = 0;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astelfa.ados_chains.tooltip"));
    }
}
