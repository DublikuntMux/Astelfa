package com.dublikunt.astelfa.renderer.item;

import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.animated.PhilosophersStone;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class PhilosophersStoneRenderer extends GeoItemRenderer<PhilosophersStone> {
    public PhilosophersStoneRenderer() {
        super(new DefaultedItemGeoModel<>(Helpers.id("philosophers_stone")));
    }
}
