package io.lemonjuice.tvlgensokyo.common.item.weapon;

import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.GungnirEntity;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class GungnirItem extends TGCombatWeaponBase implements IRenderPowerHUD {
    public GungnirItem() {
        super(9, -2.8F);
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return 400;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(player.isCreative() || TGCapabilityUtils.getPower(player) >= TGMathUtils.calculatePowerCost(this.getPowerCost(stack), player, stack)) {
            if(!world.isRemote) {
                player.getCooldownTracker().setCooldown(this, 60);
                GungnirEntity gungnir = new GungnirEntity(world, player, IHasGroup.TGGroups.FRIENDLY, 400);
                gungnir.shoot(player.getPosX(), player.getPosY() + 1.5F, player.getPosZ(), player.rotationPitch, player.rotationYaw, 2.5);
                gungnir.setDamage(30.0F);
                world.addEntity(gungnir);
                if(!player.isCreative()) {
                    TGCapabilityUtils.addPower(player, -TGMathUtils.calculatePowerCost(this.getPowerCost(stack), player, stack));
                }
            }
            return ActionResult.resultSuccess(stack);
        }
        return ActionResult.resultFail(stack);
    }
}
