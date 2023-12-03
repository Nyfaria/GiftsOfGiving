package com.nyfaria.giftsofgiving.init;

import com.nyfaria.giftsofgiving.Constants;
import com.nyfaria.giftsofgiving.block.entity.menu.PresentBoxMenu;
import com.nyfaria.nyfsmultiloader.registration.RegistrationProvider;
import com.nyfaria.nyfsmultiloader.registration.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class MenuInit {
    public static RegistrationProvider<MenuType<?>> MENU_TYPES = RegistrationProvider.get(Registries.MENU, Constants.MODID);

    public static RegistryObject<MenuType<PresentBoxMenu>> PRESENT_BOX = MENU_TYPES.register("present_box", () -> new MenuType<>(PresentBoxMenu::new, FeatureFlagSet.of()));

    public static void loadClass(){}

}
