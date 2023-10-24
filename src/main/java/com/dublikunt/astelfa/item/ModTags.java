package com.dublikunt.astelfa.item;

import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> SILVER_WOOD =
                createTag("silver_wood");
        public static final TagKey<Block> SILVER_PLANKS =
                createTag("silver_planks");

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Helpers.id(name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Helpers.id(name));
        }
    }
}
