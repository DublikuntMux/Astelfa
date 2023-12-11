package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ItemTagGenerator extends FabricTagProvider.ItemTagProvider {
    public ItemTagGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ModItems.AETHERIUM_INGOT);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "ingots")))
                .add(ModItems.AETHERIUM_INGOT)
                .add(ModItems.LUMINITE_INGOT);
        getOrCreateTagBuilder(TagKey.of(RegistryKeys.ITEM, new Identifier("c", "coal")))
                .add(ModItems.ESSENTIAL_FUEL);
    }
}
