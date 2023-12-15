package com.dublikunt.astelfa.world.feature;

import net.minecraft.block.SaplingGenerator;

import java.util.Optional;

public class ModSaplings {
    public static final SaplingGenerator SILVER_WOOD = new SaplingGenerator(
            "silver_wood",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.SILVER_WOOD_KEY),
            Optional.empty()
    );
}
