package io.lemonjuice.tvlgensokyo.common.misc;

import com.mojang.datafixers.util.Pair;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.SpellBookPageItem;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.common.spell.TGSpellInit;
import net.minecraft.entity.Entity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.util.IItemProvider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GensokyoVillagerTradeTypes {

    public static class CurrencyForItemTrade implements VillagerTrades.ITrade {
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
            return new MerchantOffer(new ItemStack(this.tradeItem, this.count), new ItemStack(this.currencyType.currencyItem, this.currencyCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static class ItemForCurrencyTrade implements VillagerTrades.ITrade {
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
            return new MerchantOffer(new ItemStack(this.currencyType.currencyItem, this.currencyCount), new ItemStack(this.item, this.itemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static class ItemForDoubleCurrencyTrade implements VillagerTrades.ITrade {
        private final CurrencyType currencyType1;
        private final int currencyCount1;
        private final CurrencyType currencyType2;
        private final int currencyCount2;
        private final Item item;
        private final int itemCount;
        private final int maxUses;
        private final int xpValue;
        private final float priceMultiplier;

        public ItemForDoubleCurrencyTrade(CurrencyType currencyType1, int currencyCount1, CurrencyType currencyType2, int currencyCount2, IItemProvider item, int itemCount, int maxUses, int xpValue) {
            this.currencyType1 = currencyType1;
            this.currencyCount1 = currencyCount1;
            this.currencyType2 = currencyType2;
            this.currencyCount2 = currencyCount2;
            this.item = item.asItem();
            this.itemCount = itemCount;
            this.maxUses = maxUses;
            this.xpValue = xpValue;
            this.priceMultiplier = 0.05F;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            return new MerchantOffer(new ItemStack(this.currencyType1.currencyItem, this.currencyCount1), new ItemStack(this.currencyType2.currencyItem, this.currencyCount2), new ItemStack(this.item, this.itemCount), this.maxUses, this.xpValue, this.priceMultiplier);
        }
    }

    public static class SpellBookPageTrade implements VillagerTrades.ITrade {
        private final Spell.TradeCategory tradeCategory;

        public SpellBookPageTrade(Spell.TradeCategory tradeCategory) {
            this.tradeCategory = tradeCategory;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity trader, Random rand) {
            int xp = this.tradeCategory.ordinal();
            List<Spell> spellList = TGSpellInit.SPELL_MAP.values().stream().filter((s) -> s.getTradeCategory() == this.tradeCategory).collect(Collectors.toList());
            Spell spell = TGSpellInit.EMPTY;
            List<Pair<CurrencyType, Integer>> currencyList = GensokyoVillagerTrades.SPELL_BASE_PRICES.get(this.tradeCategory);
            int priceModifier = rand.nextInt(this.tradeCategory.ordinal() * 3);
            ItemStack buyStack1 = new ItemStack(TGItemRegister.COPPER_COIN.get());

            if(!spellList.isEmpty())
                spell = spellList.get(rand.nextInt(spellList.size()));

            if(!currencyList.isEmpty())
                buyStack1 = new ItemStack(currencyList.get(0).getFirst().currencyItem, currencyList.get(0).getSecond());

            if(currencyList.size() >= 2) {
                return new MerchantOffer(buyStack1,
                        new ItemStack(currencyList.get(1).getFirst().currencyItem,  Math.min(currencyList.get(1).getSecond() + priceModifier, 64)),
                        SpellBookPageItem.setSpell(new ItemStack(TGItemRegister.SPELL_BOOK_PAGE.get()), spell),
                        12,
                        xp,
                        0.2F);
            }

            buyStack1.setCount(Math.min(buyStack1.getCount() + priceModifier, 64));

            return new MerchantOffer(buyStack1,
                    SpellBookPageItem.setSpell(new ItemStack(TGItemRegister.SPELL_BOOK_PAGE.get()), spell),
                    12,
                    xp,
                    0.2F);
        }
    }

}
