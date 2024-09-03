package io.github.mosadie.exponentialpower.datagen;

import io.github.mosadie.exponentialpower.ExponentialPower;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        ExponentialPower.LOGGER.info("Registering Data Providers!");

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        if (event.includeServer()) {
            ExponentialPower.LOGGER.info("Registering Server Providers!");
            generator.addProvider(true, new DataRecipes(output));
            generator.addProvider(true, new DataLootTables(output));
        }
        if (event.includeClient()) {
            ExponentialPower.LOGGER.info("Registering Client Providers!");
            generator.addProvider(true, new DataBlockStates(output, existingFileHelper));
            generator.addProvider(true, new DataItems(output, existingFileHelper));
            generator.addProvider(true, new DataLang(output, "en_us"));
        }
    }
}
