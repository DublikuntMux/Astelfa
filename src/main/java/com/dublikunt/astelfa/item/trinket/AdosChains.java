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
    private double currentFlyTime = 0;

    public AdosChains(Settings settings) {
        super(settings);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            playerEntity.getAbilities().allowFlying = true;
            playerEntity.sendAbilitiesUpdate();
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            if (!playerEntity.isCreative() && !playerEntity.isSpectator()) {
                playerEntity.getAbilities().allowFlying = true;
                playerEntity.sendAbilitiesUpdate();

                int MAX_FLY_TIME = 60 * 20;
                if (playerEntity.getAbilities().flying && !(this.currentFlyTime >= MAX_FLY_TIME)) {
                    this.currentFlyTime++;

                    if (Math.random() < (double) 10 / 100) {
                        if (playerEntity.isSprinting()) {
                            playerEntity.addExhaustion(0.15F * 2);
                        } else {
                            playerEntity.addExhaustion(0.15F);
                        }
                        if (stack.getDamage() == stack.getMaxDamage() - 1) {
                            playerEntity.getAbilities().allowFlying = false;
                            playerEntity.getAbilities().flying = false;
                            playerEntity.playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                            playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 200, 0));
                        }
                        stack.damage(1, playerEntity, e -> e.sendEquipmentBreakStatus(EquipmentSlot.LEGS));
                    }
                }
                if ((this.currentFlyTime >= MAX_FLY_TIME)) {
                    playerEntity.getAbilities().allowFlying = false;
                    playerEntity.sendAbilitiesUpdate();
                }
                if (!(playerEntity.getAbilities().flying) && (this.currentFlyTime > 0)) {
                    this.currentFlyTime = this.currentFlyTime - 0.3;
                }
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            if (!playerEntity.getAbilities().creativeMode && !playerEntity.isSpectator()) {
                playerEntity.getAbilities().allowFlying = false;
                playerEntity.getAbilities().flying = false;
                playerEntity.sendAbilitiesUpdate();
            }
            this.currentFlyTime = 0;
        }
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, @NotNull List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(Text.translatable("item.astelfa.ados_chains.tooltip"));
    }
}
