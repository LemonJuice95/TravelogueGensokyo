package io.lemonjuice.tvlgensokyo.common.enchantments;

import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import net.minecraft.enchantment.EnchantmentType;

public class TGEnchantmentTypes {
    public static final EnchantmentType POWER = EnchantmentType.create("power", (item) -> {
        return item instanceof IRenderPowerHUD;
    });

    public static EnchantmentType[] getGCEnchantmentTypes() {
        return new EnchantmentType[]{POWER};
    }
}
