package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.fluid.ModFluids;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.FluidTags;

import java.util.concurrent.CompletableFuture;

public class FluidTagGenerator extends FabricTagProvider.FluidTagProvider {
    public FluidTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(FluidTags.WATER)
                .add(ModFluids.FLOWING_MANA_FLUID);
        getOrCreateTagBuilder(FluidTags.WATER)
                .add(ModFluids.STILL_MANA_FLUID);
    }
}
