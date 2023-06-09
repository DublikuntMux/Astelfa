package com.dublikunt.astelfa.enchantment.encant;

import com.dublikunt.astelfa.Astelfa;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class HungryEnchantment extends Enchantment {
    public HungryEnchantment() {
        super(Rarity.COMMON, EnchantmentTarget.ARMOR,
                new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    @Override
    public void onUserDamaged(LivingEntity user, Entity attacker, int level) {
        if (attacker != null
                && Astelfa.RANDOM.nextInt(5) == 0
                && level > 0
                && user instanceof PlayerEntity player
                && player.getHungerManager().getFoodLevel() > 1) {
            int consume = Math.min(player.getHungerManager().getFoodLevel() - 1, 2);
            player.getHungerManager().setFoodLevel(player.getHungerManager().getFoodLevel() - consume);
            int slot = Astelfa.RANDOM.nextInt(4);
            player.getInventory().armor.get(slot).setDamage(player.getInventory().armor.get(slot).getDamage() + consume);
        }
    }

    @Override
    public boolean isTreasure() {
        return true;
    }
}
