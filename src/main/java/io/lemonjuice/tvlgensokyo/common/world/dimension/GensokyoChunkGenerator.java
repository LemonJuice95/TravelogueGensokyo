package io.lemonjuice.tvlgensokyo.common.world.dimension;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.world.WorldGenInfoHolder;
import io.lemonjuice.tvlgensokyo.common.world.dimension.layers.RoadLayer;
import io.lemonjuice.tvlgensokyo.common.world.dimension.layers.RoadStartLayer;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.ZoomLayer;

import java.util.function.LongFunction;
import java.util.function.Supplier;

//TODO Complete it later
public class GensokyoChunkGenerator extends NoiseChunkGenerator {
    public static final Codec<GensokyoChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BiomeProvider.CODEC.fieldOf("biome_source").forGetter(ChunkGenerator::getBiomeProvider),
            Codec.LONG.fieldOf("seed").stable().orElseGet(() -> WorldGenInfoHolder.getInstance().seed).forGetter((generator) -> generator.seed),
            DimensionSettings.DIMENSION_SETTINGS_CODEC.fieldOf("settings").forGetter((generator) -> generator.settings)
    ).apply(instance, instance.stable(GensokyoChunkGenerator::new)));

    private final long seed;
    private final Supplier<DimensionSettings> settings;
    private final LazyArea roadFactory;

    public GensokyoChunkGenerator(BiomeProvider biomeProvider, long seed, Supplier<DimensionSettings> settings) {
        super(biomeProvider, seed, settings);
        this.seed = seed;
        this.settings = settings;
        this.roadFactory = genRoadLayer((context) -> new LazyAreaLayerContext(25, seed, context)).make();
    }

    private static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> genRoadLayer(LongFunction<C> seed) {
        IAreaFactory<T> layer = RoadStartLayer.INSTANCE.apply(seed.apply(514L));
        for(int i = 1; i <= 7; i++) {
            layer = ZoomLayer.NORMAL.apply(seed.apply(514L), layer);
        }
        layer = RoadLayer.INSTANCE.apply(seed.apply(514L), layer);
        layer = SmoothLayer.INSTANCE.apply(seed.apply(514L), layer);
        layer = SmoothLayer.INSTANCE.apply(seed.apply(500L), layer);
        layer = SmoothLayer.INSTANCE.apply(seed.apply(495L), layer);

        return layer;
    }

    @Override
    public void generateSurface(WorldGenRegion world, IChunk chunk) {
        super.generateSurface(world, chunk);

        int x0 = chunk.getPos().getXStart();
        int z0 = chunk.getPos().getZStart();

        for(int xi = 0; xi < 16; xi++) {
            for(int zi = 0; zi < 16; zi++) {
                int x = x0 + xi;
                int y = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE_WG, xi, zi) + 1;
                int z = z0 + zi;

                if(this.roadFactory.getValue(x, z) == 1) {
                    Biome biome = world.getBiome(new BlockPos(x, y, z));


                    if (TGBiomeUtils.isBiomeWithGrassPath(biome) && y > this.getSeaLevel()) {
                        chunk.setBlockState(new BlockPos(xi, y - 1, zi), Blocks.GRASS_PATH.getDefaultState(), false);
                    }

                    if(TGBiomeUtils.isBiomeWithCobblePath(biome) && y > this.getSeaLevel()) {
                        chunk.setBlockState(new BlockPos(xi, y - 1, zi), Blocks.COBBLESTONE.getDefaultState(), false);
                    }

                    if(TGBiomeUtils.isBiomeWithMossyPath(biome) && y > this.getSeaLevel()) {
                        if(this.randomSeed.nextInt(5) == 0)
                            chunk.setBlockState(new BlockPos(xi, y - 1, zi), Blocks.MOSSY_COBBLESTONE.getDefaultState(), false);
                        else
                            chunk.setBlockState(new BlockPos(xi, y - 1, zi), Blocks.COBBLESTONE.getDefaultState(), false);
                    }
                }
            }
        }
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }


}