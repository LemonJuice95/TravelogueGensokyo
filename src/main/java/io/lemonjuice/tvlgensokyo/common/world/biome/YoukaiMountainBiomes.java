package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.common.world.feature.TGFeatures;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class YoukaiMountainBiomes {
    public static Biome getMain() {
        MobSpawnInfo.Builder mobSpawn = new MobSpawnInfo.Builder();


        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.MOUNTAIN.func_242929_a(new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.STONE.getDefaultState())));
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withDefaultFlowers(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, TGFeatures.MULTI_MAPLE_TREE);

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.2F))
                .setWaterColor(0x3f76e4)
                .setWaterFogColor(0x050533)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.EXTREME_HILLS)
                .scale(0.1F)
                .depth(2.5F)
                .downfall(0.4F)
                .temperature(0.2F)
                .precipitation(Biome.RainType.SNOW)
                .setEffects(effects)
                .withMobSpawnSettings(mobSpawn.build())
                .withGenerationSettings(generationSettings.build())
                .build();
    }

    public static Biome getGenbuRavine() {
        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.BASALT.getDefaultState(), Blocks.BASALT.getDefaultState(), Blocks.BASALT.getDefaultState())));
        DefaultBiomeFeatures.withOverworldOres(generationSettings);


        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.2F))
                .setWaterColor(0x76c0c9)
                .setWaterFogColor(0x679aa2)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.RIVER)
                .temperature(0.2F)
                .downfall(0.4F)
                .scale(0.0F)
                .depth(-0.5F)
                .precipitation(Biome.RainType.RAIN)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .setEffects(effects)
                .build();
    }

    public static Biome getGenbuRavineEdge() {
        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.BASALT.getDefaultState(), Blocks.BASALT.getDefaultState(), Blocks.BASALT.getDefaultState())));
        DefaultBiomeFeatures.withOverworldOres(generationSettings);


        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.2F))
                .setWaterColor(0x76c0c9)
                .setWaterFogColor(0x679aa2)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.EXTREME_HILLS)
                .temperature(0.2F)
                .downfall(0.4F)
                .scale(0.0F)
                .depth(2.5F)
                .precipitation(Biome.RainType.RAIN)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .setEffects(effects)
                .build();
    }
}
