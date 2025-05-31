package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum RoadLayer implements ICastleTransformer {
    INSTANCE;

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        return center == north && center == east && center == west && center == south ? 0 : 1;
    }
}
