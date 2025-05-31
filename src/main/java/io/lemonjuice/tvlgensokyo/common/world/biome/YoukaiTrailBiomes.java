package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.common.world.feature.TGFeatures;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class YoukaiTrailBiomes {
    public static Biome getYoukaiTrail() {
        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState())));
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
//        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, TGFeatures.MULTI_SAKURA_TREE);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.6F))
                .setWaterColor(0x649686)
                .setWaterFogColor(0x49634d)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.FOREST)
                .temperature(0.6F)
                .downfall(0.4F)
                .scale(0.1F)
                .depth(0.2F)
                .precipitation(Biome.RainType.RAIN)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .setEffects(effects)
                .build();
    }

    public static Biome getYoukaiTrailRiver() {
        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.6F))
                .setWaterColor(0x649686)
                .setWaterFogColor(0x49634d)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.RIVER)
                .temperature(0.6F)
                .downfall(0.4F)
                .scale(0.0F)
                .depth(-0.5F)
                .precipitation(Biome.RainType.RAIN)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .setEffects(effects)
                .build();
    }
}
