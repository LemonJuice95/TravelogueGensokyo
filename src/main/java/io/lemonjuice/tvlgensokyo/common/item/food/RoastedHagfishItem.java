package io.lemonjuice.tvlgensokyo.common.item.food;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class RoastedHagfishItem extends Item {
    private static final Food ROASTED_HAGFISH = new Food.Builder().setAlwaysEdible().hunger(5).saturation(1.6F).effect(() -> {
        return new EffectInstance(Effects.NIGHT_VISION, 1800, 0);
    }, 1.0F).build();

    public RoastedHagfishItem() {
        super(new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE).food(ROASTED_HAGFISH));
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, LivingEntity entity) {
        if(!world.isRemote) {
            entity.removePotionEffect(Effects.BLINDNESS);
        }
        return super.onItemUseFinish(stack, world, entity);
    }
}
