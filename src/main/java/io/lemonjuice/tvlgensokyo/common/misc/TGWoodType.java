package io.lemonjuice.tvlgensokyo.common.misc;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.block.WoodType;
import net.minecraft.util.ResourceLocation;

public class TGWoodType {
    public static final WoodType MAPLE = WoodType.register(WoodType.create(new ResourceLocation(TravelogueGensokyo.MODID, "maple").toString()));
    public static final WoodType SAKURA = WoodType.register(WoodType.create(new ResourceLocation(TravelogueGensokyo.MODID, "sakura").toString()));
}
