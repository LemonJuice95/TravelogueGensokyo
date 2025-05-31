package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenbuRavineEdgeLayer implements ICastleTransformer {
    INSTANCE;

    private Registry<Biome> biomeRegistry;

    public GenbuRavineEdgeLayer setup(Registry<Biome> registry) {
        this.biomeRegistry = registry;
        return this;
    }

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        boolean flag = north == south;
        boolean flag2 = east == west;
        if(flag && flag2)
            return center;
        else if(!(center == TGBiomeUtils.getBiomeId(TGBiomeRegister.GENBU_RAVINE, this.biomeRegistry))){
            if(!flag) {
                if(edgeFilter(context, north, south) || edgeFilter(context, south, north)) {
                    return TGBiomeUtils.getBiomeId(TGBiomeRegister.GENBU_RAVINE_EDGE, this.biomeRegistry);
                }
            } else {
                if(edgeFilter(context, east, west) || edgeFilter(context, west, east)) {
                    return TGBiomeUtils.getBiomeId(TGBiomeRegister.GENBU_RAVINE_EDGE, this.biomeRegistry);
                }
            }
        }
        return center;
    }

    private boolean edgeFilter(INoiseRandom context, int s1, int s2) {
        boolean flag = s1 == TGBiomeUtils.getBiomeId(TGBiomeRegister.YOUKAI_MOUNTAIN, this.biomeRegistry);
        boolean flag2 = s2 == TGBiomeUtils.getBiomeId(TGBiomeRegister.GENBU_RAVINE, this.biomeRegistry) ||
                        s2 == TGBiomeUtils.getBiomeId(TGBiomeRegister.GENBU_RAVINE_EDGE, this.biomeRegistry) &&
                        context.random(8) == 1;
        return flag && flag2;
    }
}
