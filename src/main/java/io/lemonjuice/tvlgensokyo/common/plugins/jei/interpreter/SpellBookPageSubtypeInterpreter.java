package io.lemonjuice.tvlgensokyo.common.plugins.jei.interpreter;

import io.lemonjuice.tvlgensokyo.common.item.misc.SpellBookPageItem;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.item.ItemStack;

public class SpellBookPageSubtypeInterpreter implements IIngredientSubtypeInterpreter<ItemStack> {
    public SpellBookPageSubtypeInterpreter() {
    }

    @Override
    public String apply(ItemStack stack, UidContext uidContext) {
        if(!stack.hasTag()) {
            return "";
        } else {
            Spell spell = SpellBookPageItem.getSpell(stack);
            return spell.getName().toString();
        }
    }
}
