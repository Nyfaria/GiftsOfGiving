package com.nyfaria.giftsofgiving.datagen;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Stream.of()
        //         .map(Supplier::get)
        //         .forEach(this::simpleHandHeldModel);

        // Stream.of()
        //         .map(Supplier::get)
        //         .forEach(this::simpleGeneratedModel);

        Stream.of(
                        BlockInit.BLACK_PRESENT,
                        BlockInit.BLUE_PRESENT,
                        BlockInit.BROWN_PRESENT,
                        BlockInit.CYAN_PRESENT,
                        BlockInit.GRAY_PRESENT,
                        BlockInit.GREEN_PRESENT,
                        BlockInit.LIGHT_BLUE_PRESENT,
                        BlockInit.LIGHT_GRAY_PRESENT,
                        BlockInit.LIME_PRESENT,
                        BlockInit.MAGENTA_PRESENT,
                        BlockInit.ORANGE_PRESENT,
                        BlockInit.PINK_PRESENT,
                        BlockInit.PURPLE_PRESENT,
                        BlockInit.RED_PRESENT,
                        BlockInit.WHITE_PRESENT,
                        BlockInit.YELLOW_PRESENT
                )
                .map(Supplier::get)
                .forEach(this::simpleBlockItemModel);
    }

    protected ItemModelBuilder simpleBlockItemModel(Block block) {
        String name = getName(block);
        return withExistingParent(name, modLoc("block/" + name));
    }

    protected ItemModelBuilder simpleGeneratedModel(Item item) {
        return simpleModel(item, mcLoc("item/generated"));
    }

    protected ItemModelBuilder simpleHandHeldModel(Item item) {
        return simpleModel(item, mcLoc("item/handheld"));
    }

    protected ItemModelBuilder simpleModel(Item item, ResourceLocation parent) {
        String name = getName(item);
        return singleTexture(name, parent, "layer0", modLoc("item/" + name));
    }

    protected String getName(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    protected String getName(Block item) {
        return ForgeRegistries.BLOCKS.getKey(item).getPath();
    }
}
