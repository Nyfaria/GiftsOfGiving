package com.nyfaria.giftsofgiving.datagen;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.init.BlockInit;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput generator, ExistingFileHelper existingFileHelper) {
        super(generator, Constants.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        // Stream.of(
        //
        //         )
        //         .map(Supplier::get)
        //         .forEach(this::simpleCubeBottomTopBlockState);
        //
        // Stream.of(
        //
        // ).map(Supplier::get)
        //         .forEach(this::simpleBlock);
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
                ).map(Supplier::get)
                .forEach(this::giftBlock);

    }

    protected void simpleCubeBottomTopBlockState(Block block) {
        simpleBlock(block, blockCubeTopModel(block));
    }

    protected BlockModelBuilder blockCubeTopModel(Block block) {
        String name = getName(block);
        return models().cubeBottomTop(name, modLoc("block/" + name + "_side"), modLoc("block/" + name + "_bottom"), modLoc("block/" + name + "_top"));
    }

    protected void giftBlock(Block block) {
        simpleBlock(block, models().withExistingParent(getName(block), modLoc("block/present"))
                .texture("6", modLoc("block/" + getName(block)))
                .texture("particle", modLoc("block/" + getName(block)))
                .renderType("cutout")
        );
    }


    protected String getName(Block item) {
        return ForgeRegistries.BLOCKS.getKey(item).getPath();
    }
}
