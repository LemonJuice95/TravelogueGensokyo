package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.input.TGInputs;
import io.lemonjuice.tvlgensokyo.client.models.baked.SpellBookBakedModel;
import io.lemonjuice.tvlgensokyo.client.renderer.block.TGBlockRenderHandler;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.TGEntityRendererRegister;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.DreamWorldRenderInfo;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.GensokyoRenderInfo;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.container.TGContainerRegister;
import io.lemonjuice.tvlgensokyo.utils.TGItemUtils;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TGClientRegister {
    public static void registerInputs() {
        ClientRegistry.registerKeyBinding(TGInputs.SPECIAL_SCROLL_SWITCH);
    }



    @SubscribeEvent
    public static void onModelBaked(ModelBakeEvent event) {
        Map<ResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
        ResourceLocation location;
        for(RegistryObject<Item> book : TGItemUtils.SPELL_BOOKS_OBJECT) {
            location = new ModelResourceLocation(book.get().getRegistryName(), "inventory");
            IBakedModel existingModel = modelRegistry.get(location);
            if(existingModel == null) {
                throw new RuntimeException("Did not find original model in registry");
            } else if (existingModel instanceof SpellBookBakedModel) {
                throw new RuntimeException("Tried to replace model twice");
            } else {
                SpellBookBakedModel bakedModel = new SpellBookBakedModel(existingModel);
                event.getModelRegistry().put(location, bakedModel);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockColorRegister(ColorHandlerEvent.Block event) {
        BlockColors blockColors = event.getBlockColors();
        blockColors.register((state, reader, pos, color) -> {
            if(color != 0) {
                return reader != null && pos != null ? BiomeColors.getGrassColor(reader, pos) : GrassColors.get(0.5, 1.0);
            }

            return -1;
        }, TGBlockRegister.PINK_PETALS.get());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        TGContainerRegister.registerContainerScreens();
        TGItemModelsProperties.registerProperties();
        TGEntityRendererRegister.entityRenderersRegistry();
        TGBlockRenderHandler.registerBlockRenderType();
        TGBlockRenderHandler.registerTileEntityRenderer();
        TGClientRegister.registerInputs();

        DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(TravelogueGensokyo.MODID, "dream_world"), new DreamWorldRenderInfo());
        DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"), new GensokyoRenderInfo());
    }
}
