package io.lemonjuice.tvlgensokyo.common.item;

import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.script.DebuggerDialogueScripts;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class ItemDebugger extends Item {
    public ItemDebugger() {
        super(new Item.Properties().group(TGItemGroups.MISC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            DialogueScript script = DebuggerDialogueScripts.getScript(playerIn);
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> Minecraft.getInstance().displayGuiScreen(new DialogueScreen(script)));
        }
        return ActionResult.func_233538_a_(stack, worldIn.isRemote);
    }
}
