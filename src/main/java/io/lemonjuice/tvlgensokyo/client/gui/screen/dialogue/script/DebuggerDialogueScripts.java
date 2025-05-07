package io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.script;

import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueButtonForScript;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialoguePage;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CChangePlayerDimPacket;
import io.lemonjuice.tvlgensokyo.common.world.dimension.TGDimensionRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.HashMap;

public class DebuggerDialogueScripts {
    public static DialogueScript getScript(PlayerEntity player) {
        HashMap<String, DialoguePage> page_map = new HashMap<>();
        page_map.put("index", makeIndexPage());
        page_map.put("dimensions", makeDimensionPage());

        return new DialogueScript(page_map, "index");
    }

    private static DialoguePage makeIndexPage() {
        DialoguePage index = new DialoguePage(new TranslationTextComponent("dialogue.tvlgensokyo.debugger"));
        index.addButton(new DialogueButtonForScript(new TranslationTextComponent("dialogue.tvlgensokyo.debugger.dimensions"), "dimensions"));

        return index;
    }

    private static DialoguePage makeDimensionPage() {
        DialoguePage dimensionsPage = new DialoguePage(new TranslationTextComponent("dialogue.tvlgensokyo.debugger.dimensions"));

        dimensionsPage.addButton(new DialogueButtonForScript(new TranslationTextComponent("dialogue.button.tvlgensokyo.to_the_overworld"), (button) -> {
            TGNetworkHandler.CHANNEL.sendToServer(new CChangePlayerDimPacket(World.OVERWORLD, 0, 255, 0));
        }));

        dimensionsPage.addButton(new DialogueButtonForScript(new TranslationTextComponent("dialogue.button.tvlgensokyo.to_the_gensokyo"), (button) -> {
            TGNetworkHandler.CHANNEL.sendToServer(new CChangePlayerDimPacket(TGDimensionRegister.GENSOKYO, 0, 255, 0));
        }));

        dimensionsPage.addButton(new DialogueButtonForScript(new TranslationTextComponent("dialogue.button.tvlgensokyo.to_the_dream_world"), (button) -> {
            TGNetworkHandler.CHANNEL.sendToServer(new CChangePlayerDimPacket(TGDimensionRegister.DREAM_WORLD, 0, 255, 0));
        }));

        return dimensionsPage;
    }
}
