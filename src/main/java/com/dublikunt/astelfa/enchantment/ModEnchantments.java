package com.dublikunt.astelfa.enchantment;

import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModEnchantments {
    public static final SonicDeflectEnchantment SONIC_DEFLECT_ENCHANTMENT = new SonicDeflectEnchantment();
    public static Map<String, Enchantment> ENCHANTMENTS = new LinkedHashMap<>();

    static {
        ENCHANTMENTS.put("sonic_deflect", SONIC_DEFLECT_ENCHANTMENT);
    }

    public static void register() {
        for (Map.Entry<String, Enchantment> item : ENCHANTMENTS.entrySet()) {
            Registry.register(Registries.ENCHANTMENT, Helpers.id(item.getKey()), item.getValue());
        }
    }
}
