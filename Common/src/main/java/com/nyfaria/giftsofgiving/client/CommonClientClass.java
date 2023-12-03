package com.nyfaria.giftsofgiving.client;

import com.nyfaria.giftsofgiving.client.renderer.PresentBlockEntityRenderer;
import com.nyfaria.giftsofgiving.client.screen.PresentBoxScreen;
import com.nyfaria.giftsofgiving.init.BlockInit;
import com.nyfaria.giftsofgiving.init.EntityInit;
import com.nyfaria.giftsofgiving.init.MenuInit;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.List;
import java.util.function.Supplier;

public class CommonClientClass {

    public static void loadClass() {
        MenuScreens.register(MenuInit.PRESENT_BOX.get(), PresentBoxScreen::new);

    }
}
