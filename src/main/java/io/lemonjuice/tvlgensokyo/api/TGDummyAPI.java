package io.lemonjuice.tvlgensokyo.api;

import io.lemonjuice.tvlgensokyo.api.TGAPI.ITGAPI;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import net.minecraft.util.ResourceLocation;

public class TGDummyAPI implements ITGAPI{
    public static final ITGAPI INSTANCE = new TGDummyAPI();

    @Override
    public void registerSpell(ResourceLocation name, Spell spell) {
    }
}
