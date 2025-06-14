package io.lemonjuice.tvlgensokyo.common.spell;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.HashMap;
import java.util.Map;

public class TGSpellInit {
    public static final BiMap<ResourceLocation, Spell> SPELL_MAP = HashBiMap.create();

    public static final Spell EMPTY = register("empty", new Spell(0, 0, TextFormatting.GRAY));

    public static final Spell TEST_SPELL = register("test_spell", new TestSpell().withTradeCategory(Spell.TradeCategory.COMMON));
    public static final Spell TEST_SPELL_2 = register("test_spell_2", new TestSpell2().withTradeCategory(Spell.TradeCategory.COMMON));

    private static Spell register(String name, Spell spell) {
        return register(new ResourceLocation(TravelogueGensokyo.MODID, name), spell);
    }

    public static Spell register(ResourceLocation name, Spell spell) {
        SPELL_MAP.put(name, spell);
        return spell;
    }
}
