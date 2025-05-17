package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import io.lemonjuice.tvlgensokyo.common.spell.TGSpellInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class SpellBookPageItem extends Item {

    public SpellBookPageItem() {
        super(new Item.Properties().group(TGItemGroups.BOOK_PAGE));
    }

    public static ItemStack setSpell(ItemStack stack, Spell spell) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putString("Spell", spell.getName().toString());
        stack.setTag(nbt);
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        Spell spell = getSpell(stack);
        TranslationTextComponent spellTooltip = new TranslationTextComponent(spell.getTranslationKey());
        Arrays.stream(spell.getTextFormattings()).forEach(spellTooltip::mergeStyle);
        tooltip.add(spellTooltip);
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.spell_power_cost", spell.getPowerCost()));
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.spell_chant_time", spell.getChantTime()));
        tooltip.add(new StringTextComponent(""));
        spell.addInformation(world, tooltip, flag);
        tooltip.add(new StringTextComponent(""));
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return getSpell(stack) != TGSpellInit.EMPTY || super.hasEffect(stack);
    }

    public static Spell getSpell(ItemStack stack) {
        if(stack.getItem() instanceof SpellBookPageItem) {
            CompoundNBT tag = stack.getTag();
            if(tag.contains("Spell")) {
                return Spell.getSpellFromName(ResourceLocation.tryCreate(tag.getString("Spell")));
            }
        }
        return TGSpellInit.EMPTY;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (group == ItemGroup.SEARCH || group == TGItemGroups.BOOK_PAGE) {
            for (Spell spell : TGSpellInit.SPELL_MAP.values()) {
                if (spell != TGSpellInit.EMPTY)
                    items.add(setSpell(new ItemStack(this), spell));
            }
        }
    }
}
