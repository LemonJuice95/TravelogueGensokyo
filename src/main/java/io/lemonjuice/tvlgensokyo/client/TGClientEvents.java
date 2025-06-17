package io.lemonjuice.tvlgensokyo.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import io.lemonjuice.tvlgensokyo.client.input.TGInputs;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CItemSpecialScrollPacket;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
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

    @SubscribeEvent
    public static void onFogDensity(EntityViewRenderEvent.FogDensity event) {
        float fogAmount = TravelogueGensokyo.PROXY.getFogAmount();
        boolean flag = event.getInfo().getRenderViewEntity() instanceof LivingEntity && ((LivingEntity) event.getInfo().getRenderViewEntity()).isPotionActive(Effects.BLINDNESS);
        if(fogAmount > 0.0F && event.getInfo().getFluidState().isEmpty() && !flag) {
            event.setDensity(0.0F);
            float f2;
            float f3;
            float farPlaneDistance = MC.gameRenderer.getFarPlaneDistance();
            float f4 = Math.max(farPlaneDistance - 16.0F, 32.0F);
            if (event.getType() == FogRenderer.FogType.FOG_SKY) {
                f2 = 0.0F;
                f3 = farPlaneDistance;
            } else {
                f2 = f4 * (0.75F - fogAmount * 0.725F);
                f3 = f4 * (1.0F - fogAmount * 0.25F);
            }
            RenderSystem.fogStart(f2);
            RenderSystem.fogEnd(f3);
            RenderSystem.fogMode(GlStateManager.FogMode.LINEAR);
            RenderSystem.setupNvFogDistance();
            event.setCanceled(true);
        }
    }
}
