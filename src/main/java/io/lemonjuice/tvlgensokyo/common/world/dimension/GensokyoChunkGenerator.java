package io.lemonjuice.tvlgensokyo.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;

import java.util.function.Supplier;

//TODO Complete it later
public class GensokyoChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<GensokyoChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BiomeProvider.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeProvider),
            Codec.LONG.fieldOf("seed").stable().orElseGet(() -> TGDimensionRegister.seed).forGetter((generator) -> generator.seed),
            DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((generator) -> generator.settings)
    ).apply(instance, instance.stable(GensokyoChunkGenerator::new)));

    private final long seed;
    private final Supplier<DimensionSettings> settings;

    public GensokyoChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> settings) {
        super(biomeProvider, seed, settings);
        this.seed = seed;
        this.settings = settings;
    }

    @Override
    public int getSeaLevel() {
        return -255;
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }
}