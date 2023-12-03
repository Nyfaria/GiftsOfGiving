package com.nyfaria.giftsofgiving.init;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.block.PresentBlock;
import com.nyfaria.giftsofgiving.block.entity.PresentBlockEntity;
import com.nyfaria.nyfsmultiloader.registration.RegistrationProvider;
import com.nyfaria.nyfsmultiloader.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MODID);
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MODID);

    public static final RegistryObject<PresentBlock> WHITE_PRESENT = registerBlock("white_present", () -> new PresentBlock(DyeColor.WHITE, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> ORANGE_PRESENT = registerBlock("orange_present", () -> new PresentBlock(DyeColor.ORANGE, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> MAGENTA_PRESENT = registerBlock("magenta_present", () -> new PresentBlock(DyeColor.MAGENTA, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> LIGHT_BLUE_PRESENT = registerBlock("light_blue_present", () -> new PresentBlock(DyeColor.LIGHT_BLUE, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> YELLOW_PRESENT = registerBlock("yellow_present", () -> new PresentBlock(DyeColor.YELLOW, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> LIME_PRESENT = registerBlock("lime_present", () -> new PresentBlock(DyeColor.LIME, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> PINK_PRESENT = registerBlock("pink_present", () -> new PresentBlock(DyeColor.PINK, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> GRAY_PRESENT = registerBlock("gray_present", () -> new PresentBlock(DyeColor.GRAY, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> LIGHT_GRAY_PRESENT = registerBlock("light_gray_present", () -> new PresentBlock(DyeColor.LIGHT_GRAY, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> CYAN_PRESENT = registerBlock("cyan_present", () -> new PresentBlock(DyeColor.CYAN, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> PURPLE_PRESENT = registerBlock("purple_present", () -> new PresentBlock(DyeColor.PURPLE, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> BLUE_PRESENT = registerBlock("blue_present", () -> new PresentBlock(DyeColor.BLUE, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> BROWN_PRESENT = registerBlock("brown_present", () -> new PresentBlock(DyeColor.BROWN, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> GREEN_PRESENT = registerBlock("green_present", () -> new PresentBlock(DyeColor.GREEN, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> RED_PRESENT = registerBlock("red_present", () -> new PresentBlock(DyeColor.RED, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));
    public static final RegistryObject<PresentBlock> BLACK_PRESENT = registerBlock("black_present", () -> new PresentBlock(DyeColor.BLACK, BlockBehaviour.Properties.of().strength(1.0f, 1.0f).sound(SoundType.WOOL).noOcclusion()));


    public static final RegistryObject<BlockEntityType<PresentBlockEntity>> PRESENT_BLOCK_ENTITY = BLOCK_ENTITIES.register("present_block_entity", () -> BlockEntityType.Builder.of(PresentBlockEntity::new,
            BlockInit.WHITE_PRESENT.get(),
            BlockInit.ORANGE_PRESENT.get(),
            BlockInit.MAGENTA_PRESENT.get(),
            BlockInit.LIGHT_BLUE_PRESENT.get(),
            BlockInit.YELLOW_PRESENT.get(),
            BlockInit.LIME_PRESENT.get(),
            BlockInit.PINK_PRESENT.get(),
            BlockInit.GRAY_PRESENT.get(),
            BlockInit.LIGHT_GRAY_PRESENT.get(),
            BlockInit.CYAN_PRESENT.get(),
            BlockInit.PURPLE_PRESENT.get(),
            BlockInit.BLUE_PRESENT.get(),
            BlockInit.BROWN_PRESENT.get(),
            BlockInit.GREEN_PRESENT.get(),
            BlockInit.RED_PRESENT.get(),
            BlockInit.BLACK_PRESENT.get()
    ).build(null));
    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        return registerBlock(name, block, b -> () -> new BlockItem(b.get(), ItemInit.getItemProperties()));
    }
    protected static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, Function<RegistryObject<T>, Supplier<? extends BlockItem>> item) {
        var reg = BLOCKS.register(name, block);
        ItemInit.ITEMS.register(name, () -> item.apply(reg).get());
        return reg;
    }
    public static <T extends Block> RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block) {
        var reg = BLOCKS.register(name, block);
        return reg;
    }
    public static void loadClass() {
    }
}
