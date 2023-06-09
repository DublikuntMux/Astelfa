package com.dublikunt.astelfa.enchantment;

import com.dublikunt.astelfa.enchantment.curse.*;
import com.dublikunt.astelfa.enchantment.encant.EnderEyesEnchantment;
import com.dublikunt.astelfa.enchantment.encant.HungryEnchantment;
import com.dublikunt.astelfa.enchantment.encant.SonicDeflectEnchantment;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModEnchantments {
    public static final SonicDeflectEnchantment SONIC_DEFLECT_ENCHANTMENT = new SonicDeflectEnchantment();
    public static final EnderEyesEnchantment ENDER_EYES_ENCHANTMENT = new EnderEyesEnchantment();
    public static final HungryEnchantment HUNGRY_ENCHANTMENT = new HungryEnchantment();

    public static final IllusionCurse ILLUSION_CURSE = new IllusionCurse();
    public static final LooseCurse LOOSE_CURSE = new LooseCurse();
    public static final BloodHungryCurse BLOOD_HUNGRY_CURSE = new BloodHungryCurse();
    public static final ItemSwitchCurse ITEM_SWITCH_CURSE = new ItemSwitchCurse();
    public static final ClimbingCurse CLIMBING_CURSE = new ClimbingCurse();

    public static final Map<String, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();

    static {
        ENCHANTMENTS.put("sonic_deflect", SONIC_DEFLECT_ENCHANTMENT);
        ENCHANTMENTS.put("ender_eyes", ENDER_EYES_ENCHANTMENT);
        ENCHANTMENTS.put("hungry", HUNGRY_ENCHANTMENT);

        ENCHANTMENTS.put("illusion_curse", ILLUSION_CURSE);
        ENCHANTMENTS.put("loose_curse", LOOSE_CURSE);
        ENCHANTMENTS.put("blood_hungry", BLOOD_HUNGRY_CURSE);
        ENCHANTMENTS.put("item_switch", ITEM_SWITCH_CURSE);
        ENCHANTMENTS.put("climbing_curse", CLIMBING_CURSE);
    }

    public static void register() {
        for (Map.Entry<String, Enchantment> item : ENCHANTMENTS.entrySet()) {
            Registry.register(Registries.ENCHANTMENT, Helpers.id(item.getKey()), item.getValue());
        }
    }
}
