package io.lemonjuice.tvlgensokyo.datagen;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.datagen.loots.TGLootTableGenerator;
import io.lemonjuice.tvlgensokyo.datagen.model.TGItemModelGenerator;
import io.lemonjuice.tvlgensokyo.datagen.tag.TGBlockTagGenerator;
import io.lemonjuice.tvlgensokyo.datagen.tag.TGItemTagGenerator;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TGDataGenerators {
    @SubscribeEvent
    public static void onDataGather(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if(event.includeServer()) {
            BlockTagsProvider provider = new BlockTagsProvider(generator, TravelogueGensokyo.MODID, helper);
            event.getGenerator().addProvider(new TGBiomeDataGenerator(generator));
            event.getGenerator().addProvider(new TGLootTableGenerator(generator));
            event.getGenerator().addProvider(new TGItemTagGenerator(generator, provider, helper));
            event.getGenerator().addProvider(new TGBlockTagGenerator(generator, helper));
            event.getGenerator().addProvider(new TGItemModelGenerator(generator, helper));
        }
    }
}
