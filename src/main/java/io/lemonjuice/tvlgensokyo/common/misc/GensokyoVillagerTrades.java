package io.lemonjuice.tvlgensokyo.common.misc;

import com.google.common.collect.ImmutableMap;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.CurrencyItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import java.util.Map;
import java.util.Random;

public class GensokyoVillagerTrades {
    public static final Map<CurrencyType, CurrencyItem> CURRENCY_MAP = ImmutableMap.of(CurrencyType.COPPER_COIN, TGItemRegister.COPPER_COIN.get(), CurrencyType.SILVER_PIECE, TGItemRegister.SILVER_PIECE.get(), CurrencyType.SILVER_INGOT, TGItemRegister.SILVER_INGOT.get());

    static class CurrencyForItemTrade implements VillagerTrades.ITrade {
        private final Item tradeItem;
        private final int count;
        private final CurrencyType currencyType;
        private final int currencyCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public CurrencyForItemTrade(IItemProvider tradeItem, int count, CurrencyType currencyType, int currencyCount, int maxUses, int xpValue) {
            this.tradeItem = tradeItem.asItem();
            this.count = count;
            this.currencyType = currencyType;
            this.currencyCount = currencyCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(this.tradeItem, this.count), new ItemStack(CURRENCY_MAP.get(this.currencyType), this.currencyCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    static class ItemForCurrencyTrade implements VillagerTrades.ITrade {
        private final CurrencyType currencyType;
        private final int currencyCount;
        private final Item item;
        private final int itemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemForCurrencyTrade(CurrencyType currencyType, int currencyCount, IItemProvider item, int itemCount, int maxUses, int xpValue) {
            this.currencyType = currencyType;
            this.currencyCount = currencyCount;
            this.item = item.asItem();
            this.itemCount = itemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(CURRENCY_MAP.get(this.currencyType), this.currencyCount), new ItemStack(this.item, this.itemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public enum CurrencyType {
        COPPER_COIN,
        SILVER_PIECE,
        SILVER_INGOT
    }
}
