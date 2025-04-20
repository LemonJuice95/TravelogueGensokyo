package io.lemonjuice.tvlgensokyo.api;

import io.lemonjuice.tvlgensokyo.api.TGAPI.ITGAPI;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.common.spell.TGSpellInit;
import net.minecraft.util.ResourceLocation;

public class TGAPIImpl implements ITGAPI {
    @Override
    public void registerSpell(ResourceLocation name, Spell spell) {
        TGSpellInit.register(name, spell);
    }
}
