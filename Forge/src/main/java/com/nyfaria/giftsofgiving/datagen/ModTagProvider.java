package com.nyfaria.giftsofgiving.datagen;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.init.BlockInit;
import com.nyfaria.giftsofgiving.init.TagInit;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModTagProvider {

    public static class ItemsTags extends TagsProvider<Item> {

        public ItemsTags(PackOutput p_256596_, CompletableFuture<HolderLookup.Provider> p_256513_, @Nullable ExistingFileHelper existingFileHelper) {
            super(p_256596_, Registries.ITEM, p_256513_, Constants.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            populateTag(TagInit.ITEM_PRESENTS,
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
            );
            tag(TagInit.GLOBAL_CHEST)
                    .addOptionalTag(new ResourceLocation("forge", "chests"))
                    .addOptionalTag(new ResourceLocation("c", "chests"))
                    .add(ForgeRegistries.ITEMS.getResourceKey(Items.CHEST).get())
            ;
        }

        public void populateTag(TagKey<Item> tag, Supplier<? extends ItemLike>... items) {
            for (Supplier<? extends ItemLike> item : items) {
                tag(tag).add(ForgeRegistries.ITEMS.getResourceKey(item.get().asItem()).get());
            }
        }
    }

    public static class BlockTags extends TagsProvider<Block> {

        public BlockTags(PackOutput pGenerator, CompletableFuture<HolderLookup.Provider> p_256513_, @Nullable ExistingFileHelper existingFileHelper) {
            super(pGenerator, Registries.BLOCK, p_256513_, Constants.MODID, existingFileHelper);
        }

        @Override
        protected void addTags(HolderLookup.Provider pProvider) {
            populateTag(TagInit.BLOCK_PRESENTS,
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
            );
        }

        public <T extends Block> void populateTag(TagKey<Block> tag, Supplier<?>... items) {
            for (Supplier<?> item : items) {
                tag(tag).add(ForgeRegistries.BLOCKS.getResourceKey((Block) item.get()).get());
            }
        }
    }
}
