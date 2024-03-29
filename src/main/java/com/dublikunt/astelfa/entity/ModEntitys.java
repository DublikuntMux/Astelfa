package com.dublikunt.astelfa.entity;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.renderer.entity.IrritantRenderer;
import com.dublikunt.astelfa.renderer.entity.SlimeBossRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntitys {
    public static final EntityType<IrritantEntity> IRRITANT = Registry.register(
            Registries.ENTITY_TYPE, Helpers.id("irritant"),
            FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, IrritantEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5F, 0.5F)).build());

    public static final EntityType<SlimeBossEntity> SLIME_BOSS = Registry.register(
            Registries.ENTITY_TYPE, Helpers.id("slime_boss"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SlimeBossEntity::new)
                    .dimensions(EntityDimensions.fixed(4.0F, 4.0F)).build());

    public static void registerRenderer() {
        EntityRendererRegistry.register(IRRITANT, IrritantRenderer::new);
        EntityRendererRegistry.register(SLIME_BOSS, SlimeBossRenderer::new);
    }

    public static void register() {
        FabricDefaultAttributeRegistry.register(IRRITANT, IrritantEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SLIME_BOSS, SlimeBossEntity.setAttributes());
    }
}
