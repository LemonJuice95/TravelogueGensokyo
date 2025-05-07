package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.client.gui.overlay.PowerHUD;
import io.lemonjuice.tvlgensokyo.client.gui.overlay.SpellChantHUD;
import io.lemonjuice.tvlgensokyo.client.input.TGInputs;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.GensokyoRenderInfo;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.GensokyoWeatherRenderer;
import io.lemonjuice.tvlgensokyo.common.item.interfaces.ISpellInstrument;
import io.lemonjuice.tvlgensokyo.common.item.misc.SpellBookPageItem;
import io.lemonjuice.tvlgensokyo.common.item.weapon.ItemSpellBook;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CSetBookOpenStatePacket;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.IWeatherRenderHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class TGClientEventsHandler {
    private static Minecraft MC = Minecraft.getInstance();

    public static void handleDimensionRenderInfo() {
        DimensionRenderInfo info = DimensionRenderInfo.field_239208_a_.get(new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"));
        if(!MC.isGamePaused() && MC.world != null && info instanceof GensokyoRenderInfo) {
            IWeatherRenderHandler weatherRenderHandler = info.getWeatherRenderHandler();
            if(weatherRenderHandler instanceof GensokyoWeatherRenderer) {
                ((GensokyoWeatherRenderer) weatherRenderHandler).tick();
            }
        }
    }

    public static void handleSpellBookOpenState() {
        if(MC.player != null) {
            ItemStack stack = MC.player.getHeldItemMainhand();
            if(!(stack.getItem() instanceof ItemSpellBook)) {
                stack = MC.player.getHeldItemOffhand();
            }
            if(stack.getItem() instanceof ItemSpellBook) {
                if(TGInputs.SPECIAL_SCROLL_SWITCH.isKeyDown()) {
                    if(!ItemSpellBook.isOpened(stack))
                        TGNetworkHandler.CHANNEL.sendToServer(new CSetBookOpenStatePacket(true));
                } else if(ItemSpellBook.isOpened(stack) && TravelogueGensokyo.PROXY.getChantingProgress() == 0) {
                    TGNetworkHandler.CHANNEL.sendToServer(new CSetBookOpenStatePacket(false));
                }
            }
        }
    }

    public static void renderSpellChantingHud(RenderGameOverlayEvent event) {
        PlayerEntity player = MC.player;
        ItemStack item1 = player.getActiveItemStack();

        if(item1.isEmpty()) {
            if (player.getHeldItemMainhand().getItem() instanceof ISpellInstrument) {
                item1 = player.getHeldItemMainhand();
            } else if (player.getHeldItemOffhand().getItem() instanceof ISpellInstrument) {
                item1 = player.getHeldItemOffhand();
            }
        }

        if(item1 != null && item1.getItem() instanceof ISpellInstrument) {
            float chantingProgress = TravelogueGensokyo.PROXY.getChantingProgress();
            if(!player.getActiveItemStack().getItem().equals(item1.getItem()) && chantingProgress > 0.0F) {
                TravelogueGensokyo.PROXY.setChantingProgress(0.0F);
                chantingProgress = 0.0F;
            }
            if(item1.getItem() instanceof ItemSpellBook) {
                Spell spell = SpellBookPageItem.getSpell(((ItemSpellBook) item1.getItem()).getInventory(item1).getCurrentPage());
                SpellChantHUD spellChantHUD = new SpellChantHUD(chantingProgress, player.isCreative(), new TranslationTextComponent(spell.getTranslationKey()));
                spellChantHUD.render(event.getMatrixStack());
            }
        }
    }

    public static void renderPowerHud(RenderGameOverlayEvent event) {
        PlayerEntity player = MC.player;

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

}
