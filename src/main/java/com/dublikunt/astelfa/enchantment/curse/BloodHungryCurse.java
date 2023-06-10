package com.dublikunt.astelfa.enchantment.curse;

import com.dublikunt.astelfa.Astelfa;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class BloodHungryCurse extends Enchantment {
    public BloodHungryCurse() {
        super(Rarity.COMMON, EnchantmentTarget.ARMOR,
                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (attacker != null
                && Astelfa.RANDOM.nextInt(Astelfa.config.hungryChange) == 0
                && level > 0
                && user instanceof PlayerEntity player
                && player.getHealth() > 1) {
            float consume = Math.min(player.getHealth() - 1, 2);
            player.setHealth(player.getHealth() - consume);
            int slot = Astelfa.RANDOM.nextInt(4);
            player.getInventory().armor.get(slot).setDamage((int) (player.getInventory().armor.get(slot).getDamage() + consume));
        }
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isCursed() {
        return true;
    }
}
