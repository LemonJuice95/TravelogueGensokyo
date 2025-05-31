package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

import java.util.function.Supplier;

public enum GensokyoRiverLayer implements ICastleTransformer {
    INSTANCE;

    private Registry<Biome> biomeRegistry;

    public GensokyoRiverLayer setup(Registry<Biome> registry) {
        this.biomeRegistry = registry;
        return this;
    }

    @Override
    public int apply(INoiseRandom context, int north, int west, int south, int east, int center) {
        int i = riverFilter(center);
        return i == riverFilter(east) && i == riverFilter(north) && i == riverFilter(west) && i == riverFilter(south) ? -1 :
                TGBiomeUtils.getBiomeId(TGBiomeRegister.GENSOKYO_RIVER, this.biomeRegistry);
    }

    private static int riverFilter(int value) {
        return value >= 2 ? 2 + (value & 1) : value;
    }
}
