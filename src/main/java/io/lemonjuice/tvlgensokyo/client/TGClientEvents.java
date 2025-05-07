package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import io.lemonjuice.tvlgensokyo.client.input.TGInputs;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CItemSpecialScrollPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID,value = Dist.CLIENT)
public class TGClientEvents {
    private static final Minecraft MC = Minecraft.getInstance();

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onOverlayRender(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || MC.player == null || MC.player.isSpectator() || MC.gameSettings.hideGUI) {
            return;
        }
        TGClientEventsHandler.renderSpellChantingHud(event);
        if(!MC.player.isCreative()) {
            TGClientEventsHandler.renderPowerHud(event);
        }
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollEvent event) {
        PlayerEntity player = MC.player;
        if (TGInputs.SPECIAL_SCROLL_SWITCH.isKeyDown() && player != null) {
            if(player.getHeldItemMainhand().getItem() instanceof IScrollable || player.getHeldItemOffhand().getItem() instanceof IScrollable) {
                TGNetworkHandler.CHANNEL.sendToServer(new CItemSpecialScrollPacket(-event.getScrollDelta()));
                event.setCanceled(true);
            }
        }
    }


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        TGClientEventsHandler.handleDimensionRenderInfo();
        TGClientEventsHandler.handleSpellBookOpenState();
    }
}
