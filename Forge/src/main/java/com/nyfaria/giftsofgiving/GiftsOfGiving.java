package com.nyfaria.giftsofgiving;

import com.nyfaria.giftsofgiving.datagen.ModBlockStateProvider;
import com.nyfaria.giftsofgiving.datagen.ModItemModelProvider;
import com.nyfaria.giftsofgiving.datagen.ModLangProvider;
import com.nyfaria.giftsofgiving.datagen.ModLootTableProvider;
import com.nyfaria.giftsofgiving.datagen.ModRecipeProvider;
import com.nyfaria.giftsofgiving.datagen.ModSoundProvider;
import com.nyfaria.giftsofgiving.datagen.ModTagProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Constants.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GiftsOfGiving {
    
    public GiftsOfGiving() {
        Constants.LOG.info("Hello Forge world!");
        CommonClass.init();
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        boolean includeServer = event.includeServer();
        boolean includeClient = event.includeClient();
        generator.addProvider(includeServer, new ModRecipeProvider(packOutput));
        generator.addProvider(includeServer, new ModLootTableProvider(packOutput));
        generator.addProvider(includeServer, new ModSoundProvider(packOutput, existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.BlockTags(packOutput,event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeServer, new ModTagProvider.ItemsTags(packOutput,event.getLookupProvider(), existingFileHelper));
        generator.addProvider(includeClient, new ModBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(includeClient, new ModLangProvider(packOutput));
    }
}