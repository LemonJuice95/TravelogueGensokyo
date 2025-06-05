package io.lemonjuice.tvlgensokyo.common.misc;

import com.google.common.collect.ImmutableMap;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.CurrencyItem;

import java.util.Map;

public enum CurrencyType {
    COPPER_COIN,
    SILVER_PIECE,
    SILVER_INGOT;

    public static final Map<CurrencyType, CurrencyItem> CURRENCY_MAP = ImmutableMap.of(COPPER_COIN, TGItemRegister.COPPER_COIN.get(), SILVER_PIECE, TGItemRegister.SILVER_PIECE.get(), SILVER_INGOT, TGItemRegister.SILVER_INGOT.get());
}
