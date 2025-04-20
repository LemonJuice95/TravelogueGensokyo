package io.lemonjuice.tvlgensokyo.common.item.weapon;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class TGUniqueTier implements IItemTier {
    @Override
    public int getMaxUses() {
        return 9999;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public float getEfficiency() {
        return 8.0F;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return null;
    }

    @Override
    public int getEnchantability() {
        return 16;
    }

    @Override
    public int getHarvestLevel() {
        return 4;
    }
}
