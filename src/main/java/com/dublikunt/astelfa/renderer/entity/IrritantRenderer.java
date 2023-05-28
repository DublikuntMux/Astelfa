package com.dublikunt.astelfa.renderer.entity;

import com.dublikunt.astelfa.entity.IrritantEntity;
import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IrritantRenderer extends GeoEntityRenderer<IrritantEntity> {
    public IrritantRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(Helpers.id("irritant")));
    }
}
