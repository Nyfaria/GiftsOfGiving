package com.nyfaria.giftsofgiving.event;

import com.nyfaria.giftsofgiving.client.CommonClientClass;
import com.nyfaria.giftsofgiving.client.renderer.PresentBlockEntityRenderer;
import com.nyfaria.giftsofgiving.init.BlockInit;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerScreen(FMLClientSetupEvent event) {
        CommonClientClass.loadClass();
    }

    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockInit.PRESENT_BLOCK_ENTITY.get(), PresentBlockEntityRenderer::new);
    }
}
