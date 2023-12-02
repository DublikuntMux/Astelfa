package com.dublikunt.astelfa.renderer.entity;

import com.dublikunt.astelfa.entity.SlimeBossEntity;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SlimeBossRenderer extends GeoEntityRenderer<SlimeBossEntity> {
    public SlimeBossRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(Helpers.id("slime_boss")));
        scaleWidth = 4f;
        scaleHeight = 4f;
    }
}
