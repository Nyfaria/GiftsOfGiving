package com.nyfaria.giftsofgiving.init;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.recipe.PresentBoxColoring;
import com.nyfaria.nyfsmultiloader.registration.RegistrationProvider;
import com.nyfaria.nyfsmultiloader.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class RecipeInit {
    public static RegistrationProvider<RecipeSerializer<?>> RECIPES = RegistrationProvider.get(Registries.RECIPE_SERIALIZER, Constants.MODID);
    public static RegistryObject<RecipeSerializer<PresentBoxColoring>> PRESNET_BOX_COLORING = RECIPES.register("present_box_coloring", ()->new SimpleCraftingRecipeSerializer<>(PresentBoxColoring::new));

    public static void loadClass(){}
}
