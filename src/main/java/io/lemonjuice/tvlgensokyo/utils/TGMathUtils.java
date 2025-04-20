package io.lemonjuice.tvlgensokyo.utils;

import io.lemonjuice.tvlgensokyo.common.enchantments.TGEnchantmentRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class TGMathUtils {
    public static int calculatePowerCost(int baseCost, PlayerEntity player, ItemStack stack) {
        int actualCost = baseCost;
        actualCost = (int)Math.floor(actualCost * (1 - EnchantmentHelper.getEnchantmentLevel(TGEnchantmentRegister.POWER_SAVING.get(), stack) * 0.1));
        return Math.max(actualCost, 0);
    }

    //TODO Complete it when the equipment system is done
    public static int calculatePowerRecovery(PlayerEntity player) {
        int recovery = TGCapabilityUtils.BASE_POWER_RECOVERY;
        return recovery;
    }

}
