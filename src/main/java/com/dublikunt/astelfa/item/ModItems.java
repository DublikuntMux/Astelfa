package com.dublikunt.astelfa.item;

import com.dublikunt.astelfa.entity.ModEntitys;
import com.dublikunt.astelfa.helper.Helpers;
import com.dublikunt.astelfa.item.animated.PhilosophersStone;
import com.dublikunt.astelfa.item.matter.*;
import com.dublikunt.astelfa.item.trinket.AdosChains;
import com.dublikunt.astelfa.item.trinket.HartRing;
import com.dublikunt.astelfa.item.trinket.RingBelt;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    public static final Matter1 MATTER_1 = new Matter1(new FabricItemSettings());
    public static final Matter2 MATTER_2 = new Matter2(new FabricItemSettings());
    public static final Matter3 MATTER_3 = new Matter3(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Matter4 MATTER_4 = new Matter4(new FabricItemSettings().rarity(Rarity.RARE));
    public static final Matter5 MATTER_5 = new Matter5(new FabricItemSettings().rarity(Rarity.EPIC));
    public static final Matter6 MATTER_6 = new Matter6(new FabricItemSettings().rarity(Rarity.EPIC));

    public static final HartRing HART_RING = new HartRing(new FabricItemSettings().rarity(Rarity.RARE));
    public static final AdosChains ADOS_CHAINS = new AdosChains(new FabricItemSettings().rarity(Rarity.RARE));

    public static final RingBelt RING_BELT = new RingBelt(new FabricItemSettings());

    public static final PhilosophersStone PHILOSOPHERS_STONE = new PhilosophersStone(new FabricItemSettings().rarity(Rarity.RARE).maxCount(1));

    public static final Item IRRITANT_EGG = new SpawnEggItem(ModEntitys.IRRITANT, 0x101B21, 0x009195, new FabricItemSettings());
    public static final ItemGroup MOD_GROUP = FabricItemGroup.builder(Helpers.id("item_group"))
            .icon(() -> new ItemStack(ModItems.MATTER_1))
            .build();
    public static Map<String, Item> ITEMS = new LinkedHashMap<>();

    static {
        ITEMS.put("matter1", MATTER_1);
        ITEMS.put("matter2", MATTER_2);
        ITEMS.put("matter3", MATTER_3);
        ITEMS.put("matter4", MATTER_4);
        ITEMS.put("matter5", MATTER_5);
        ITEMS.put("matter6", MATTER_6);
        ITEMS.put("hart_ring", HART_RING);
        ITEMS.put("ados_chains", ADOS_CHAINS);
        ITEMS.put("philosophers_stone", PHILOSOPHERS_STONE);
        ITEMS.put("ring_belt", RING_BELT);
        ITEMS.put("irritant_egg", IRRITANT_EGG);
    }

    public static void register() {
        for (Map.Entry<String, Item> item : ITEMS.entrySet()) {
            Registry.register(Registries.ITEM, Helpers.id(item.getKey()), item.getValue());
            ItemGroupEvents.modifyEntriesEvent(MOD_GROUP).register(content -> content.add(item.getValue()));
        }
    }
}
