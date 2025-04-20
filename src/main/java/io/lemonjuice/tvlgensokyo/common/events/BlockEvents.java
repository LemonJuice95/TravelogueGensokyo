package io.lemonjuice.tvlgensokyo.common.events;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.world.dimension.TGDimensionRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockEvents {
    @SubscribeEvent
    public static void onEntityPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        if(entity.world.getDimensionKey() == TGDimensionRegister.DREAM_WORLD) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        PlayerEntity player = event.getPlayer();
        if(player.world.getDimensionKey() == TGDimensionRegister.DREAM_WORLD) {
            event.setCanceled(true);
        }
    }
}
