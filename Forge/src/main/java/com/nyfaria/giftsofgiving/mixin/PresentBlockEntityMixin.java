package com.nyfaria.giftsofgiving.mixin;

import com.nyfaria.giftsofgiving.block.entity.PresentBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PresentBlockEntity.class)
public abstract class PresentBlockEntityMixin extends RandomizableContainerBlockEntity implements WorldlyContainer {
    protected PresentBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    @Override
    protected IItemHandler createUnSidedHandler() {
        return new EmptyHandler();
    }

}
