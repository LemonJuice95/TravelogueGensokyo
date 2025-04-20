package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Deprecated
public class ItemPowerPoint extends Item implements IRenderPowerHUD {
    public ItemPowerPoint() {
        super(new Item.Properties().group(TGItemGroups.MISC));
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return 0;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if(!world.isRemote) {
            player.getCooldownTracker().setCooldown(this, 20);
            TGCapabilityUtils.addPower(player, 50);
            if(!player.isCreative()) {
                stack.shrink(1);
            }
            return ActionResult.resultConsume(stack);
        }

        return ActionResult.resultConsume(stack);
    }
}
