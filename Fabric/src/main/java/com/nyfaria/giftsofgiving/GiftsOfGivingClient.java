package com.nyfaria.giftsofgiving;

import com.nyfaria.giftsofgiving.client.CommonClientClass;
import com.nyfaria.giftsofgiving.client.renderer.PresentBlockEntityRenderer;
import com.nyfaria.giftsofgiving.init.BlockInit;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class GiftsOfGivingClient implements ClientModInitializer {
    
    @Override
    public void onInitializeClient() {
        CommonClientClass.loadClass();
        BlockEntityRenderers.register(BlockInit.PRESENT_BLOCK_ENTITY.get(), PresentBlockEntityRenderer::new);
    }
}
