package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum RoadStartLayer implements IAreaTransformer0 {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int x, int z) {
        return context.random(39999) + 2;
    }
}
