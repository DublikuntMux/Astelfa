package com.dublikunt.astelfa.generator;

import com.dublikunt.astelfa.item.ModItems;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class LootTableModifiers {
    private static final Identifier BURIED_TREASURE_ID
            = new Identifier("minecraft", "chests/buried_treasure");
    private static final Identifier DESERT_PYRAMID_ID
            = new Identifier("minecraft", "chests/desert_pyramid");
    private static final Identifier END_CITY_TREASURE_ID
            = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier IGLOO_CHEST_ID
            = new Identifier("minecraft", "chests/igloo_chest");
    private static final Identifier JUNGLE_TEMPLE_ID
            = new Identifier("minecraft", "chests/jungle_temple");
    private static final Identifier WOODLAND_MANSION_ID
            = new Identifier("minecraft", "chests/woodland_mansion");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BURIED_TREASURE_ID.equals(id) || DESERT_PYRAMID_ID.equals(id) || END_CITY_TREASURE_ID.equals(id)
                    || IGLOO_CHEST_ID.equals(id) || JUNGLE_TEMPLE_ID.equals(id) || WOODLAND_MANSION_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.20f))
                        .with(ItemEntry.builder(ModItems.MANA_INGOT))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)).build());

                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
