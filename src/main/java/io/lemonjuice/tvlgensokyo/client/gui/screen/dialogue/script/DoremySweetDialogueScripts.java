package io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.script;

import com.google.common.collect.ImmutableMap;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueButtonForScript;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialoguePage;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import io.lemonjuice.tvlgensokyo.common.world.dimension.TGDimensionRegister;
import io.lemonjuice.tvlgensokyo.utils.TGPlayerUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class DoremySweetDialogueScripts {
    public static DialogueScript getTempScript(PlayerEntity player, BlockPos destinyPosGensokyo) {
        DialoguePage page = new DialoguePage(new StringTextComponent("Not yet."));
        page.addButton(new DialogueButtonForScript(new TranslationTextComponent("dialogue.button.tvlgensokyo.to_the_gensokyo"), (button) -> {
            TGPlayerUtils.changePlayerDimension(player, TGDimensionRegister.DREAM_WORLD, destinyPosGensokyo.getX(), destinyPosGensokyo.getY(), destinyPosGensokyo.getZ());
        }));
        DialogueScript script = new DialogueScript(ImmutableMap.of("tempPage", page), "tempPage");
        return script;
    }
}
