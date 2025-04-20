package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.ItemDanmaku;
import io.lemonjuice.tvlgensokyo.common.item.weapon.ItemLaevatein;
import io.lemonjuice.tvlgensokyo.utils.TGDanmakuUtils;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;

public class TGItemModelsProperties {
    public static void registerProperties() {
        ItemModelsProperties.registerProperty(TGItemRegister.LAEVATEIN.get(), new ResourceLocation(TravelogueGensokyo.MODID, "activated"), (stack, world, entity) -> {
            return ItemLaevatein.isActivated(stack) ? 1.0F : 0.0F;
        });
        TGDanmakuUtils.DANMAKU_ITEM_MAP.values().forEach((danmakuItem) -> {
            ItemModelsProperties.registerProperty(danmakuItem, new ResourceLocation(TravelogueGensokyo.MODID, "color"), (stack, world, entity) -> {
                return ItemDanmaku.getColor(stack).ordinal();
            });
        });
    }
}
