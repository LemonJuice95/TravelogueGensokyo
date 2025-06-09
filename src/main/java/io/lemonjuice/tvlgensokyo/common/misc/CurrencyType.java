package io.lemonjuice.tvlgensokyo.common.misc;

import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.CurrencyItem;

public enum CurrencyType {
    COPPER_COIN(TGItemRegister.COPPER_COIN.get()),
    SILVER_PIECE(TGItemRegister.SILVER_PIECE.get()),
    SILVER_INGOT(TGItemRegister.SILVER_INGOT.get());

    public final CurrencyItem currencyItem;

    private CurrencyType(CurrencyItem currencyItem) {
        this.currencyItem = currencyItem;
    }

}
