package io.lemonjuice.tvlgensokyo.common.item;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.script.DebuggerDialogueScripts;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class DebuggerItem extends Item {
    public DebuggerItem() {
        super(new Item.Properties().group(TGItemGroups.MISC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if(worldIn.isRemote) {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                DialogueScript script = DebuggerDialogueScripts.getScript(playerIn);
                TravelogueGensokyo.PROXY.openDialogueGui(script);
            });
        }
        return ActionResult.func_233538_a_(stack, worldIn.isRemote);
    }
}
