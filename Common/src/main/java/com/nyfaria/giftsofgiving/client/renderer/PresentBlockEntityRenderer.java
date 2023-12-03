package com.nyfaria.giftsofgiving.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.block.entity.PresentBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;

public class PresentBlockEntityRenderer implements BlockEntityRenderer<PresentBlockEntity> {
    public PresentBlockEntityRenderer(BlockEntityRendererProvider.Context $$0) {

    }
    @Override
    public void render(PresentBlockEntity presentBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        int lightAbove = LevelRenderer.getLightColor(Minecraft.getInstance().level, presentBlockEntity.getBlockPos());

        renderNameTag(presentBlockEntity, Component.translatable(Constants.MODID + ".container.present3", presentBlockEntity.getDisplayName(),presentBlockEntity.getGiftGiverName()).withStyle(ChatFormatting.WHITE), poseStack, multiBufferSource, lightAbove);
    }

    protected void renderNameTag(PresentBlockEntity entity, Component displayName, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        double d = Minecraft.getInstance().getEntityRenderDispatcher().distanceToSqr(entity.getBlockPos().getX(), entity.getBlockPos().getY(), entity.getBlockPos().getZ());
        if (!(d > 4096.0)) {
            float f = 1.0F;
            int i = 0;
            matrixStack.pushPose();
            matrixStack.translate(0.5F, f, 0.5F);
            matrixStack.mulPose(Minecraft.getInstance().getEntityRenderDispatcher().cameraOrientation());
            matrixStack.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStack.last().pose();
            float g = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
            int j = (int) (g * 255.0F) << 24;
            Font font = Minecraft.getInstance().font;
            float h = (float) (-font.width(displayName) / 2);

            font.drawInBatch(displayName, h, (float) i, 0xFF0000, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
//            font.drawInBatch(displayName, h, (float) i, 0xFF0000, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
            matrixStack.popPose();
        }
    }
}
