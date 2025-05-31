package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IC0Transformer;

public enum GensokyoRiverStartLayer implements IC0Transformer {
    INSTANCE;

    //TODO 雾之湖完成后添加筛选
    @Override
    public int apply(INoiseRandom context, int value) {
        return context.random(29999) + 2;
    }
}
