package com.dublikunt.astelfa.recipe;

import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, Helpers.id(InfuseTableRecipe.Serializer.ID),
                InfuseTableRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Helpers.id(InfuseTableRecipe.Type.ID),
                InfuseTableRecipe.Type.INSTANCE);
    }
}
