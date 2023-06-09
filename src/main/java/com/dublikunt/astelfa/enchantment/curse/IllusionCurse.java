package com.dublikunt.astelfa.enchantment.curse;

import com.dublikunt.astelfa.Astelfa;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IllusionCurse extends Enchantment {
    private static final List<SoundEvent> EVENTS = new ArrayList<>();

    public IllusionCurse() {
        super(Rarity.UNCOMMON, EnchantmentTarget.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
        EVENTS.add(SoundEvents.ENTITY_TNT_PRIMED);
        EVENTS.add(SoundEvents.ENTITY_CREEPER_PRIMED);
        EVENTS.add(SoundEvents.ENTITY_HUSK_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_ZOMBIE_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_ZOMBIFIED_PIGLIN_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_ENDERMAN_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_SKELETON_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_WITCH_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_GHAST_SCREAM);
        EVENTS.add(SoundEvents.ENTITY_PHANTOM_AMBIENT);
        EVENTS.add(SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE);
        EVENTS.add(SoundEvents.ENTITY_ENDER_DRAGON_HURT);
        EVENTS.add(SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON);
        EVENTS.add(SoundEvents.BLOCK_LAVA_AMBIENT);
        EVENTS.add(SoundEvents.BLOCK_PORTAL_AMBIENT);
        EVENTS.add(SoundEvents.ITEM_SHIELD_BREAK);
    }

    public static SoundEvent getRandomSound() {
        return EVENTS.get(Astelfa.RANDOM.nextInt(EVENTS.size()));
    }

    @Override
    public boolean isAcceptableItem(@NotNull ItemStack stack) {
        return stack.getItem() instanceof ArmorItem &&
                ((ArmorItem) stack.getItem()).getType().getEquipmentSlot() == EquipmentSlot.HEAD;
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
