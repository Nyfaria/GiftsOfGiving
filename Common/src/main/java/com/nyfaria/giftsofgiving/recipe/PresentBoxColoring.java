//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.nyfaria.giftsofgiving.recipe;

import com.nyfaria.giftsofgiving.block.PresentBlock;
import com.nyfaria.giftsofgiving.init.RecipeInit;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class PresentBoxColoring extends CustomRecipe {
   public PresentBoxColoring(ResourceLocation resourceLocation, CraftingBookCategory craftingBookCategory) {
      super(resourceLocation, craftingBookCategory);
   }

   public boolean matches(CraftingContainer inv, Level level) {
      int i = 0;
      int j = 0;

      for(int k = 0; k < inv.getContainerSize(); ++k) {
         ItemStack itemStack = inv.getItem(k);
         if (!itemStack.isEmpty()) {
            if (Block.byItem(itemStack.getItem()) instanceof PresentBlock) {
               ++i;
            } else {
               if (!(itemStack.getItem() instanceof DyeItem)) {
                  return false;
               }

               ++j;
            }

            if (j > 1 || i > 1) {
               return false;
            }
         }
      }

      return i == 1 && j == 1;
   }

   public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
      ItemStack itemStack = ItemStack.EMPTY;
      DyeItem dyeItem = (DyeItem)Items.WHITE_DYE;

      for(int i = 0; i < container.getContainerSize(); ++i) {
         ItemStack itemStack2 = container.getItem(i);
         if (!itemStack2.isEmpty()) {
            Item item = itemStack2.getItem();
            if (Block.byItem(item) instanceof PresentBlock) {
               itemStack = itemStack2;
            } else if (item instanceof DyeItem) {
               dyeItem = (DyeItem)item;
            }
         }
      }

      ItemStack itemStack3 = PresentBlock.getColoredItemStack(dyeItem.getDyeColor());
      if (itemStack.hasTag()) {
         itemStack3.setTag(itemStack.getTag().copy());
      }

      return itemStack3;
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> getSerializer() {
      return RecipeInit.PRESNET_BOX_COLORING.get();
   }
}
