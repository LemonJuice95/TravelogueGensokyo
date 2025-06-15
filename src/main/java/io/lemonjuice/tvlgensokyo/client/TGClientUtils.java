package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.client.models.itemstack.BasicSpellBookModel;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.SpellBookModelBase;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class TGClientUtils {
    public static final Map<Item, SpellBookModelBase> SPELL_BOOK_MODEL_MAP = new HashMap<>();

    static {
        SPELL_BOOK_MODEL_MAP.put(TGItemRegister.BASIC_SPELL_BOOK.get(), new BasicSpellBookModel());
        SPELL_BOOK_MODEL_MAP.put(TGItemRegister.BASIC_THICK_SPELL_BOOK.get(), new BasicSpellBookModel());
    }
}
