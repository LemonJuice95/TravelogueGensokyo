package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.AmuletEntity;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AmuletItem extends Item {
    public AmuletItem() {
        super(new Item.Properties().group(TGItemGroups.MISC));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote) {
            AmuletEntity amulet = new AmuletEntity(world, true, player, IHasGroup.TGGroups.FRIENDLY, 60);
            amulet.shoot(player.getPosX(), player.getPosY() + 1.5, player.getPosZ(), player.rotationPitch, player.rotationYaw, 1.2);
            world.addEntity(amulet);

            if(!player.isCreative())
                stack.shrink(1);
        }
        return ActionResult.resultSuccess(stack);
    }
}
