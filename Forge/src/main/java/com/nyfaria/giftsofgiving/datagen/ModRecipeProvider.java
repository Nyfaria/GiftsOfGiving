package com.nyfaria.giftsofgiving.datagen;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.init.BlockInit;
import com.nyfaria.giftsofgiving.init.RecipeInit;
import com.nyfaria.giftsofgiving.init.TagInit;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeSaver) {
        SpecialRecipeBuilder.special(RecipeInit.PRESNET_BOX_COLORING.get())
                .save(recipeSaver, Constants.MODID+":present_box_coloring");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockInit.WHITE_PRESENT.get())
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .define('X', TagInit.GLOBAL_CHEST)
                .define('#', Items.PAPER)
                .unlockedBy("has_chest", has(TagInit.GLOBAL_CHEST))
                .save(recipeSaver);

    }


}
