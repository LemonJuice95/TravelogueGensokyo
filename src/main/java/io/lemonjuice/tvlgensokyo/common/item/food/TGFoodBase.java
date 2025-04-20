package io.lemonjuice.tvlgensokyo.common.item.food;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class TGFoodBase extends Item {
    public TGFoodBase(int hunger, float saturation) {
        super(new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE).food(new Food.Builder().hunger(hunger).saturation(saturation).build()));
    }
}
