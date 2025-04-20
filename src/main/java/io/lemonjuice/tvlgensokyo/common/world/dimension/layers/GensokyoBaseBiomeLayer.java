package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import com.google.common.collect.ImmutableList;
import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import java.util.List;

public enum GensokyoBaseBiomeLayer implements IAreaTransformer0 {
    INSTANCE;

    private Registry<Biome> biomeRegistry;

    public GensokyoBaseBiomeLayer setup(Registry<Biome> registry) {
        this.biomeRegistry = registry;
        return this;
    }

    private static final List<RegistryKey<Biome>> BASE_BIOMES = ImmutableList.of(
            TGBiomeRegister.SUN_FIELD,
            TGBiomeRegister.YOUKAI_MOUNTAIN
    );

    @Override
    public int apply(INoiseRandom random, int x, int z) {
        return TGBiomeUtils.getBiomeId(BASE_BIOMES.get(random.random(BASE_BIOMES.size())), this.biomeRegistry);
    }
}
