package io.lemonjuice.tvlgensokyo.common.misc;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GensokyoVillagerTrades {
    public static final Map<VillagerProfession, Int2ObjectMap<VillagerTrades.ITrade[]>> GENSOKYO_VILLAGER_DEFAULT_TRADES = new HashMap<>();

    public static final Map<Integer, VillagerTrades.ITrade[]> FARMER_TRADES = new HashMap<>();
    public static final Map<Integer, VillagerTrades.ITrade[]> LIBRARIAN_TRADES = new HashMap<>();

    public static final Map<Spell.TradeCategory, List<Pair<CurrencyType, Integer>>> SPELL_BASE_PRICES = new HashMap<>();

    static {
        SPELL_BASE_PRICES.put(Spell.TradeCategory.NONE, ImmutableList.of());
        SPELL_BASE_PRICES.put(Spell.TradeCategory.COMMON, ImmutableList.of(Pair.of(CurrencyType.COPPER_COIN, 25)));
        SPELL_BASE_PRICES.put(Spell.TradeCategory.UNCOMMON, ImmutableList.of(Pair.of(CurrencyType.COPPER_COIN, 55)));
        SPELL_BASE_PRICES.put(Spell.TradeCategory.RARE, ImmutableList.of(Pair.of(CurrencyType.SILVER_PIECE, 8), Pair.of(CurrencyType.COPPER_COIN, 5)));
        SPELL_BASE_PRICES.put(Spell.TradeCategory.EPIC, ImmutableList.of(Pair.of(CurrencyType.SILVER_PIECE, 16), Pair.of(CurrencyType.COPPER_COIN, 5)));
        SPELL_BASE_PRICES.put(Spell.TradeCategory.LEGENDARY, ImmutableList.of(Pair.of(CurrencyType.SILVER_PIECE, 40), Pair.of(CurrencyType.COPPER_COIN, 5)));

        FARMER_TRADES.put(1, new VillagerTrades.ITrade[] {
                new GensokyoVillagerTradeTypes.CurrencyForItemTrade(TGItemRegister.RICE.get(), 14, CurrencyType.COPPER_COIN, 1, 16, 2)
        });

        LIBRARIAN_TRADES.put(1, new VillagerTrades.ITrade[] {
                new GensokyoVillagerTradeTypes.CurrencyForItemTrade(Items.PAPER, 9, CurrencyType.COPPER_COIN, 1, 16, 2),
                new GensokyoVillagerTradeTypes.ItemForCurrencyTrade(CurrencyType.COPPER_COIN, 12, Items.BOOKSHELF, 1, 12, 1),
                new GensokyoVillagerTradeTypes.SpellBookPageTrade(Spell.TradeCategory.COMMON)
        });
        LIBRARIAN_TRADES.put(2, new VillagerTrades.ITrade[] {
                new GensokyoVillagerTradeTypes.CurrencyForItemTrade(Items.BOOK, 2, CurrencyType.COPPER_COIN, 1, 12, 5),
                new GensokyoVillagerTradeTypes.ItemForCurrencyTrade(CurrencyType.COPPER_COIN, 26, TGItemRegister.BASIC_SPELL_BOOK.get(), 1, 3, 10),
                new GensokyoVillagerTradeTypes.ItemForCurrencyTrade(CurrencyType.COPPER_COIN, 31, TGItemRegister.BASIC_THICK_SPELL_BOOK.get(), 1, 3, 10),
                new GensokyoVillagerTradeTypes.SpellBookPageTrade(Spell.TradeCategory.UNCOMMON)
        });

        GENSOKYO_VILLAGER_DEFAULT_TRADES.put(VillagerProfession.FARMER, getAsIntMap(FARMER_TRADES));
        GENSOKYO_VILLAGER_DEFAULT_TRADES.put(VillagerProfession.LIBRARIAN, getAsIntMap(LIBRARIAN_TRADES));
    }

    private static Int2ObjectMap<VillagerTrades.ITrade[]> getAsIntMap(Map<Integer, VillagerTrades.ITrade[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }
}
