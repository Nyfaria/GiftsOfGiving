package com.nyfaria.giftsofgiving;

import com.nyfaria.giftsofgiving.client.CommonClientClass;
import com.nyfaria.giftsofgiving.client.renderer.PresentBlockEntityRenderer;
import com.nyfaria.giftsofgiving.init.BlockInit;
import com.nyfaria.nyfsmultiloader.registration.RegistryObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.world.level.block.Block;

public class GiftsOfGivingClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        CommonClientClass.loadClass();
        BlockEntityRenderers.register(BlockInit.PRESENT_BLOCK_ENTITY.get(), PresentBlockEntityRenderer::new);

        for (RegistryObject<Block> present : BlockInit.BLOCKS.getEntries()) {
            BlockRenderLayerMap.INSTANCE.putBlock(present.get(), RenderType.cutout());
        }
    }
}
