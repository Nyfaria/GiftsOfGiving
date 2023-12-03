package com.nyfaria.giftsofgiving.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AnvilMenu.class)
public class AnvilMenuMixin {

    @Shadow @Final private DataSlot cost;

    @Inject(method = "createResult", at = @At(value = "INVOKE",target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void checkIfPreventRenameGiveForTheft(CallbackInfo ci, ItemStack stack) {
        CompoundTag beTag = BlockItem.getBlockEntityData(stack);
        if(beTag!=null && beTag.contains("PreventRenameGiftForTheft") && beTag.getBoolean("PreventRenameGiftForTheft")) {
            this.cost.set(Integer.MAX_VALUE);
        }
    }
}
