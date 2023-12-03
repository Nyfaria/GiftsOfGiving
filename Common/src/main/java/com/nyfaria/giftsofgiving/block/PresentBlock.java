package com.nyfaria.giftsofgiving.block;

import com.nyfaria.giftsofgiving.block.entity.PresentBlockEntity;
import com.nyfaria.giftsofgiving.init.BlockInit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class PresentBlock extends BaseEntityBlock {
    private static final VoxelShape UP_OPEN_AABB = Shapes.join(Shapes.empty(), Shapes.box(0.21875, 0, 0.21875, 0.78125, 0.5625, 0.78125), BooleanOp.OR);

    public static final EnumProperty<Direction> FACING = DirectionalBlock.FACING;
    public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
    @Nullable
    private final DyeColor color;

    public PresentBlock(@Nullable DyeColor pColor, BlockBehaviour.Properties pProperties) {
        super(pProperties);
        this.color = pColor;
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.UP));
    }

    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PresentBlockEntity(this.color, pPos, pState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockInit.PRESENT_BLOCK_ENTITY.get(), PresentBlockEntity::tick);
    }

    /**
     * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     *
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#getRenderShape}
     * whenever possible. Implementing/overriding is fine.
     */
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if (pPlayer.isSpectator()) {
            return InteractionResult.CONSUME;
        } else {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof PresentBlockEntity) {
                PresentBlockEntity presentboxblockentity = (PresentBlockEntity) blockentity;
                if (canOpen(pPlayer, presentboxblockentity)) {
                    pPlayer.openMenu(presentboxblockentity);
                }

                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    private static boolean canOpen(Player player, PresentBlockEntity pBlockEntity) {
        boolean isGiftGiver = pBlockEntity.isGiftGiver(player);
        String name = player.getDisplayName().getString();
        String giveeName = pBlockEntity.getDisplayName().getString();
        boolean isGiftee = Objects.equals(name, giveeName);
        boolean closed = pBlockEntity.isClosed();
        return (isGiftGiver || isGiftee) && closed;
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getClickedFace());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually collect
     * this block
     */
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof PresentBlockEntity presentboxblockentity) {
            if (!pLevel.isClientSide && pPlayer.isCreative() && !presentboxblockentity.isEmpty()) {
                ItemStack itemstack = getColoredItemStack(this.getColor());
                blockentity.saveToItem(itemstack);
                if (presentboxblockentity.hasCustomName()) {
                    itemstack.setHoverName(presentboxblockentity.getCustomName());
                }

                ItemEntity itementity = new ItemEntity(pLevel, (double) pPos.getX() + 0.5D, (double) pPos.getY() + 0.5D, (double) pPos.getZ() + 0.5D, itemstack);
                itementity.setDefaultPickUpDelay();
                pLevel.addFreshEntity(itementity);
            } else {
                presentboxblockentity.unpackLootTable(pPlayer);
            }
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public List<ItemStack> getDrops(BlockState pState, LootParams.Builder pParams) {
        BlockEntity blockentity = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (blockentity instanceof PresentBlockEntity presentboxblockentity) {
            pParams = pParams.withDynamicDrop(CONTENTS, (p_56219_) -> {
                for (int i = 0; i < presentboxblockentity.getContainerSize(); ++i) {
                    p_56219_.accept(presentboxblockentity.getItem(i));
                }

            });
        }

        return super.getDrops(pState, pParams);
    }

    /**
     * Called by BlockItem after this block has been placed.
     */
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {

        BlockEntity blockentity = pLevel.getBlockEntity(pPos);
        if (blockentity instanceof PresentBlockEntity) {
            if (pStack.hasCustomHoverName()) {
                ((PresentBlockEntity) blockentity).setCustomName(pStack.getHoverName());
            }
            ((PresentBlockEntity) blockentity).setGiftGiver((Player) pPlacer);
        }

    }

    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockEntity blockentity = pLevel.getBlockEntity(pPos);
            if (blockentity instanceof PresentBlockEntity) {
                pLevel.updateNeighbourForOutputSignal(pPos, pState.getBlock());
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        CompoundTag compoundtag = BlockItem.getBlockEntityData(pStack);
        if (compoundtag != null) {
            if (compoundtag.contains("LootTable", 8)) {
                pTooltip.add(Component.literal("???????"));
            }

            if (compoundtag.contains("Items", 9)) {
                NonNullList<ItemStack> nonnulllist = NonNullList.withSize(27, ItemStack.EMPTY);
                ContainerHelper.loadAllItems(compoundtag, nonnulllist);
                int i = 0;
                int j = 0;

                for (ItemStack itemstack : nonnulllist) {
                    if (!itemstack.isEmpty()) {
                        ++j;
                        if (i <= 4) {
                            ++i;
                            MutableComponent mutablecomponent = itemstack.getHoverName().copy();
                            mutablecomponent.append(" x").append(String.valueOf(itemstack.getCount()));
                            pTooltip.add(mutablecomponent);
                        }
                    }
                }

                if (j - i > 0) {
                    pTooltip.add(Component.translatable("container.presentBox.more", j - i).withStyle(ChatFormatting.ITALIC));
                }
            }
        }

    }

    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return UP_OPEN_AABB;
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return UP_OPEN_AABB;
    }

    /**
     * @deprecated call via {@link
     * net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#hasAnalogOutputSignal} whenever possible.
     * Implementing/overriding is fine.
     */


    public ItemStack getCloneItemStack(BlockGetter pLevel, BlockPos pPos, BlockState pState) {
        ItemStack itemstack = super.getCloneItemStack(pLevel, pPos, pState);
        pLevel.getBlockEntity(pPos).saveToItem(itemstack);
        return itemstack;
    }

    @Nullable
    public static DyeColor getColorFromItem(Item pItem) {
        return getColorFromBlock(Block.byItem(pItem));
    }

    @Nullable
    public static DyeColor getColorFromBlock(Block pBlock) {
        return pBlock instanceof PresentBlock ? ((PresentBlock) pBlock).getColor() : null;
    }

    public static Block getBlockByColor(@Nullable DyeColor pColor) {
        return switch (pColor) {
            case WHITE -> BlockInit.WHITE_PRESENT.get();
            case ORANGE -> BlockInit.ORANGE_PRESENT.get();
            case MAGENTA -> BlockInit.MAGENTA_PRESENT.get();
            case LIGHT_BLUE -> BlockInit.LIGHT_BLUE_PRESENT.get();
            case YELLOW -> BlockInit.YELLOW_PRESENT.get();
            case LIME -> BlockInit.LIME_PRESENT.get();
            case PINK -> BlockInit.PINK_PRESENT.get();
            case GRAY -> BlockInit.GRAY_PRESENT.get();
            case LIGHT_GRAY -> BlockInit.LIGHT_GRAY_PRESENT.get();
            case CYAN -> BlockInit.CYAN_PRESENT.get();
            case BLUE -> BlockInit.BLUE_PRESENT.get();
            case BROWN -> BlockInit.BROWN_PRESENT.get();
            case GREEN -> BlockInit.GREEN_PRESENT.get();
            case RED -> BlockInit.RED_PRESENT.get();
            case BLACK -> BlockInit.BLACK_PRESENT.get();
            default -> BlockInit.PURPLE_PRESENT.get();
        };
    }

    @Nullable
    public DyeColor getColor() {
        return this.color;
    }

    public static ItemStack getColoredItemStack(@Nullable DyeColor pColor) {
        return new ItemStack(getBlockByColor(pColor));
    }

    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     *
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#rotate} whenever
     * possible. Implementing/overriding is fine.
     */
    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     *
     * @deprecated call via {@link net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase#mirror} whenever
     * possible. Implementing/overriding is fine.
     */
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
}