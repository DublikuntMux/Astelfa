package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.world.feature.ModConfiguredFeatures;
import com.dublikunt.astelfa.world.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.NotNull;

public class AstelfaDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(@NotNull FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(RecipeGenerator::new);
        pack.addProvider(ModelGenerator::new);
        pack.addProvider(WorldGenerator::new);
        pack.addProvider(ItemTagGenerator::new);
        pack.addProvider(BlockTagGenerator::new);
        pack.addProvider(FluidTagGenerator::new);
        pack.addProvider(AdvancementGenerator::new);
    }

    @Override
    public void buildRegistry(@NotNull RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}
