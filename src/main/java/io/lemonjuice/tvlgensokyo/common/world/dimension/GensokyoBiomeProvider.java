package io.lemonjuice.tvlgensokyo.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.common.world.dimension.layers.GensokyoBaseBiomeLayer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Optional;
import java.util.function.LongFunction;

public class GensokyoBiomeProvider extends BiomeProvider {

    public static final Codec<GensokyoBiomeProvider> CODEC = RecordCodecBuilder.create((builder) ->
            builder.group(Codec.LONG.fieldOf("seed").stable().orElseGet(() -> TGDimensionRegister.seed).forGetter((provider) -> provider.seed),
            RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((provider) -> provider.biomeRegistry))
        .apply(builder, builder.stable(GensokyoBiomeProvider::new)));

    private final long seed;
    private final Registry<Biome> biomeRegistry;
    private final Layer genLayer;

    private static final List<RegistryKey<Biome>> BIOME = ImmutableList.of(
            TGBiomeRegister.SUN_FIELD,
            TGBiomeRegister.YOUKAI_MOUNTAIN
    );


    public GensokyoBiomeProvider(long seed, Registry<Biome> biomeRegistry) {
        super(BIOME.stream()
                .map(RegistryKey::getLocation)
                .map(biomeRegistry::getOptional)
                .filter(Optional::isPresent)
                .map(opt -> opt::get));

        this.seed = seed;
        this.biomeRegistry = biomeRegistry;
        this.genLayer = genLayers(seed, biomeRegistry);
    }

    private static Layer genLayers(long seed, Registry<Biome> registry) {
        IAreaFactory<LazyArea> iAreaFactory = genLayers((context) -> new LazyAreaLayerContext(25, seed, context), registry);

        return new Layer(iAreaFactory);
    }

    //TODO Complete it later
    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genLayers(LongFunction<C> seed, Registry<Biome> registry) {
        IAreaFactory<T> biomes = GensokyoBaseBiomeLayer.INSTANCE.setup(registry).apply(seed.apply(1L));

        biomes = ZoomLayer.FUZZY.apply(seed.apply(495L), biomes);
        biomes = ZoomLayer.FUZZY.apply(seed.apply(500L), biomes);
        biomes = ZoomLayer.NORMAL.apply(seed.apply(514L), biomes);

        return biomes;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.genLayer.func_242936_a(this.biomeRegistry, x, z);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public BiomeProvider getBiomeProvider(long seed) {
        return new GensokyoBiomeProvider(seed, this.biomeRegistry);
    }


}
