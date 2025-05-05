package io.lemonjuice.tvlgensokyo.common.enchantments.misc;

import io.lemonjuice.tvlgensokyo.common.enchantments.TGEnchantmentTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;

public class PowerSavingEnchantment extends Enchantment {
    public PowerSavingEnchantment() {
        super(Rarity.COMMON, TGEnchantmentTypes.POWER, new EquipmentSlotType[] { EquipmentSlotType.OFFHAND, EquipmentSlotType.MAINHAND });
    }

    @Override
    public int getMinEnchantability(int level) {
        return 2 * level + 5;
    }

    @Override
    public int getMaxEnchantability(int level) {
        return 4 * level + 10;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
