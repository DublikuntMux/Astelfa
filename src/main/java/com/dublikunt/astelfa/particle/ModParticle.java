package com.dublikunt.astelfa.particle;

import com.dublikunt.astelfa.helper.Helpers;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticle {
    public static DefaultParticleType END_FIRE_FLAME = Registry.register(Registries.PARTICLE_TYPE, Helpers.id("end_fire_flame"), FabricParticleTypes.simple());

    public static void register() {
        ParticleFactoryRegistry.getInstance().register(END_FIRE_FLAME, FlameParticle.Factory::new);
    }
}
