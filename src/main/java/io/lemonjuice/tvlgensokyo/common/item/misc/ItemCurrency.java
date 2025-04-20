package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCurrency extends Item {
    public final int value;

    public ItemCurrency(int value) {
        super(new Item.Properties().group(TGItemGroups.MISC));
        this.value = value;
    }

    @Override
    public void addInformation(ItemStack itemStack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.currency_value", new StringTextComponent(Integer.toString(this.value)).mergeStyle(TextFormatting.GREEN)));
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.currency").mergeStyle(TextFormatting.DARK_PURPLE).mergeStyle(TextFormatting.ITALIC));
    }
}