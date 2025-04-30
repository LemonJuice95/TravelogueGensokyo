package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.client.models.itemstack.BasicSpellBookModel;
import io.lemonjuice.tvlgensokyo.client.models.itemstack.SpellBookModelBase;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class TGClientUtils {
    public static final Map<Item, SpellBookModelBase> MODEL_MAP = new HashMap<>();

    static {
        MODEL_MAP.put(TGItemRegister.TEST_SPELL_BOOK.get(), new BasicSpellBookModel());
        MODEL_MAP.put(TGItemRegister.TEST_THICK_SPELL_BOOK.get(), new BasicSpellBookModel());
    }
}
