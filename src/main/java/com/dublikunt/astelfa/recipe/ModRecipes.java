package com.dublikunt.astelfa.recipe;

import com.dublikunt.astelfa.helper.Helpers;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModRecipes {
    public static void register() {
        Registry.register(Registries.RECIPE_SERIALIZER, Helpers.id(InfuseTableRecipe.Serializer.ID),
                InfuseTableRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Helpers.id(ManaFillerRecipe.Serializer.ID),
                ManaFillerRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_SERIALIZER, Helpers.id(PedestalRecipe.Serializer.ID),
                PedestalRecipe.Serializer.INSTANCE);

        Registry.register(Registries.RECIPE_TYPE, Helpers.id(InfuseTableRecipe.Type.ID),
                InfuseTableRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Helpers.id(ManaFillerRecipe.Type.ID),
                ManaFillerRecipe.Type.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE, Helpers.id(PedestalRecipe.Type.ID),
                PedestalRecipe.Type.INSTANCE);
    }
}
