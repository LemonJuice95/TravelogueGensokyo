package io.lemonjuice.tvlgensokyo.api.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface IRenderPowerHUD {
    int getPowerCost(ItemStack stack);
}
