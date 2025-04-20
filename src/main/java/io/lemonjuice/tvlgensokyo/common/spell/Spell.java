package io.lemonjuice.tvlgensokyo.common.spell;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;

public class Spell{
    private final int powerCost;
    private final int chantTime;
    private final TextFormatting[] textFormattings;
    private String translationKey;

    public Spell(int powerCost, int chantTime) {
        this(powerCost, chantTime, TextFormatting.LIGHT_PURPLE);
    }

    public Spell(int powerCost, int chantTime, TextFormatting... textFormattings) {
        this.powerCost = powerCost;
        this.chantTime = chantTime;
        this.textFormattings = textFormattings;
    }

    public static Spell getSpellFromName(ResourceLocation name) {
        return TGSpellInit.SPELL_MAP.get(name);
    }

    public int getPowerCost() {
        return this.powerCost;
    }

    public int getChantTime() {
        return this.chantTime;
    }

    public void onActivate(PlayerEntity player, ItemStack book){
    }

    public TextFormatting[] getTextFormattings() {
        return this.textFormattings;
    }

    public String getTranslationKey() {
        if(this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("spell", this.getName());
        }
        return this.translationKey;
    }

    public ResourceLocation getName() {
        try {
            return TGSpellInit.SPELL_MAP.inverse().get(this);
        } catch (NullPointerException e) {
            TravelogueGensokyo.LOGGER.warn("Found a spell not registered: " + this.toString());
            return new ResourceLocation("", "");
        }
    }


}
