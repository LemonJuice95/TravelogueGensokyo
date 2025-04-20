package io.lemonjuice.tvlgensokyo.common.entity;

import io.lemonjuice.tvlgensokyo.common.entity.creature.EntityKappa;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TGLivingAttributesCreator {
    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(TGEntityRegister.KAPPA.get(), EntityKappa.createAttribute().create());
        event.put(TGEntityRegister.GENSOKYO_VILLAGER.get(), VillagerEntity.registerAttributes().create());
    }
}
