package io.lemonjuice.tvlgensokyo.client.events;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.ISpellInstrument;
import io.lemonjuice.tvlgensokyo.client.gui.overlay.PowerHUD;
import io.lemonjuice.tvlgensokyo.client.gui.overlay.SpellChantHUD;
import io.lemonjuice.tvlgensokyo.client.input.TGInputs;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.GensokyoRenderInfo;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.GensokyoWeatherRenderer;
import io.lemonjuice.tvlgensokyo.common.item.misc.ItemSpellBookPage;
import io.lemonjuice.tvlgensokyo.common.item.weapon.ItemSpellBook;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.ItemSpecialScrollPacket;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IWeatherRenderHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID,value = Dist.CLIENT)
public class TGClientEventsHandler {
    private static final Minecraft MC = TravelogueGensokyo.MC;

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onOverlayRender(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL || MC.player == null || MC.player.isSpectator() || MC.gameSettings.hideGUI) {
            return;
        }

        PlayerEntity player = MC.player;
        ItemStack item1 = null;

        if (player.getHeldItemMainhand().getItem() instanceof ISpellInstrument) {
            item1 = player.getHeldItemMainhand();
        } else if (player.getHeldItemOffhand().getItem() instanceof ISpellInstrument) {
            item1 = player.getHeldItemOffhand();
        }

        if(item1 != null && item1.getItem() instanceof ISpellInstrument) {
            float chantingProgress = TravelogueGensokyo.PROXY.getChantingProgress();
            if(!player.getActiveItemStack().equals(item1)) {
                chantingProgress = 0.0F;
            }
            if(item1.getItem() instanceof ItemSpellBook) {
                Spell spell = ItemSpellBookPage.getSpell(((ItemSpellBook) item1.getItem()).getInventory(item1).getCurrentPage());
                SpellChantHUD spellChantHUD = new SpellChantHUD(chantingProgress, player.isCreative(), new TranslationTextComponent(spell.getTranslationKey()));
                spellChantHUD.render(event.getMatrixStack());
            }
        }

        if(MC.player.isCreative()) {
            return;
        }

        ItemStack item2;

        if (player.getHeldItemMainhand().getItem() instanceof IRenderPowerHUD) {
            item2 = player.getHeldItemMainhand();
        } else if (player.getHeldItemOffhand().getItem() instanceof IRenderPowerHUD) {
            item2 = player.getHeldItemOffhand();
        } else {
            return;
        }

        assert item2.getItem() instanceof IRenderPowerHUD;
        int powerConsumption = TGMathUtils.calculatePowerCost(((IRenderPowerHUD) item2.getItem()).getPowerCost(item2), player, item2);
        int power = TravelogueGensokyo.PROXY.getPowerBarLength();
        boolean color = power >= powerConsumption;
        PowerHUD powerHUD = new PowerHUD(power, color);
        powerHUD.render(event.getMatrixStack());
    }

    @SubscribeEvent
    public static void onMouseScroll(InputEvent.MouseScrollEvent event) {
        PlayerEntity player = MC.player;
        if (TGInputs.SPECIAL_SCROLL_SWITCH.isKeyDown()) {
            if(player.getHeldItemMainhand().getItem() instanceof IScrollable) {
                TGNetworkHandler.CHANNEL.sendToServer(new ItemSpecialScrollPacket(-event.getScrollDelta()));
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        DimensionRenderInfo info = DimensionRenderInfo.field_239208_a_.get(new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"));
        if(!MC.isGamePaused() && MC.world != null && info instanceof GensokyoRenderInfo) {
            IWeatherRenderHandler weatherRenderHandler = info.getWeatherRenderHandler();
            if(weatherRenderHandler instanceof GensokyoWeatherRenderer) {
                ((GensokyoWeatherRenderer) weatherRenderHandler).tick();
            }
        }
    }
}
