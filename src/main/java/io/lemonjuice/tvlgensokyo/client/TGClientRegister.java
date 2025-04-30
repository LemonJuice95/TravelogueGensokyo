package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.input.TGInputs;
import io.lemonjuice.tvlgensokyo.client.models.baked.SpellBookBakedModel;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.SpellBookModelBase;
import io.lemonjuice.tvlgensokyo.utils.TGItemUtils;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

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
}
