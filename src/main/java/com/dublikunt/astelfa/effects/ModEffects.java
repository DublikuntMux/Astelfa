package com.dublikunt.astelfa.effects;

import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEffects {
    public static final AscensionEffect ASCENSION_EFFECT = new AscensionEffect();
    public static final MagicalSuffocationEffect MAGICAL_SUFFOCATION_EFFECT = new MagicalSuffocationEffect();

    public static void registerEffects() {
        Registry.register(Registries.STATUS_EFFECT, Helpers.id("ascension"), ASCENSION_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, Helpers.id("magical_suffocation"), MAGICAL_SUFFOCATION_EFFECT);
    }
}
