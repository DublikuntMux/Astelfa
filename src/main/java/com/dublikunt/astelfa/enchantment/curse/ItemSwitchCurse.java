package com.dublikunt.astelfa.enchantment.curse;

import com.dublikunt.astelfa.Astelfa;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public class ItemSwitchCurse extends Enchantment {
    public ItemSwitchCurse() {
        super(Rarity.COMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public void onTargetDamaged(LivingEntity user, Entity target, int level) {
        if (Astelfa.RANDOM.nextInt(20) == 0 && target != null && level > 0 && user instanceof PlayerEntity playerEntity) {
            ItemStack mainHand = playerEntity.getMainHandStack();
            playerEntity.setStackInHand(Hand.MAIN_HAND, playerEntity.getOffHandStack());
            playerEntity.setStackInHand(Hand.OFF_HAND, mainHand);
        }
    }

    @Override
    public boolean isAcceptableItem(@NotNull ItemStack stack) {
        return stack.getItem() instanceof SwordItem;
    }

    @Override
    public boolean isCursed() {
        return true;
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
