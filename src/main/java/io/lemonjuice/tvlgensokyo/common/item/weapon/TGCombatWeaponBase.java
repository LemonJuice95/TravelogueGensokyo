package io.lemonjuice.tvlgensokyo.common.item.weapon;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TGCombatWeaponBase extends SwordItem {
    public TGCombatWeaponBase(int attackDamage, float attackSpeed) {
        super(new TGUniqueTier(), attackDamage, attackSpeed, new Item.Properties().group(TGItemGroups.COMBAT));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return 1.0F;
    }

    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }
}
