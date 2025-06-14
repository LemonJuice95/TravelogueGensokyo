package io.lemonjuice.tvlgensokyo.common.spell;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public class Spell {
    private final int powerCost;
    private final int chantTime;
    private final TextFormatting[] textFormattings;
    private String translationKey;
    private TradeCategory tradeCategory;

    public Spell(int powerCost, int chantTime) {
        this(powerCost, chantTime, TextFormatting.LIGHT_PURPLE);
    }

    public Spell(int powerCost, int chantTime, TextFormatting... textFormattings) {
        this.powerCost = powerCost;
        this.chantTime = chantTime;
        this.textFormattings = textFormattings;
        this.tradeCategory = TradeCategory.NONE;
    }

    public Spell withTradeCategory(TradeCategory category) {
        this.tradeCategory = category;
        return this;
    }

    //决定魔导书页在村民交易时的基础价格
    public TradeCategory getTradeCategory() {
        return this.tradeCategory;
    }

    public void addInformation(@Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("spell.info.none").mergeStyle(TextFormatting.DARK_GRAY));
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

    public void onActivate(LivingEntity entity, ItemStack book){
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
        return Optional.ofNullable(TGSpellInit.SPELL_MAP.inverse().get(this)).orElseGet(() -> {
            TravelogueGensokyo.LOGGER.warn("Found a spell not registered: " + this);
            TravelogueGensokyo.LOGGER.warn("Please check your code at " + new Throwable().getStackTrace()[4]);
            return new ResourceLocation("tvlgensokyo", "empty");
        });
    }

    public enum TradeCategory {
        NONE,
        COMMON,
        UNCOMMON,
        RARE,
        EPIC,
        LEGENDARY
    }


}
