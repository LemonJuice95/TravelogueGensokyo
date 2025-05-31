package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public enum BiomesWithStructureLayer implements IAreaTransformer1 {
    INSTANCE;

    private Registry<Biome> biomeRegistry;

    public BiomesWithStructureLayer setup(Registry<Biome> registry) {
        this.biomeRegistry = registry;
        return this;
    }

    @Override
    public int apply(IExtendedNoiseRandom<?> context, IArea area, int x, int z) {
        return 0;
    }

    @Override
    public int getOffsetX(int x) {
        return x;
    }

    @Override
    public int getOffsetZ(int z) {
        return z;
    }
}
