package com.nyfaria.giftsofgiving.init;

import com.nyfaria.giftsofgiving.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagInit {

    public static TagKey<Item> ITEM_PRESENTS = TagKey.create(Registries.ITEM, new ResourceLocation(Constants.MODID, "presents"));
    public static TagKey<Block> BLOCK_PRESENTS = TagKey.create(Registries.BLOCK, new ResourceLocation(Constants.MODID, "presents"));

}
