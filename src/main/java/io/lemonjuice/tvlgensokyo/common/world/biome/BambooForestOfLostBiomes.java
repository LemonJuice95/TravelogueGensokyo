package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.common.world.feature.TGFeatures;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class BambooForestOfLostBiomes {
    public static Biome getMain() {
        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();
        mobSpawnInfo.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.RABBIT, 8, 3, 5));

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.PODZOL.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState())));
        generationSettings.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Features.FOREST_ROCK);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, TGFeatures.BAMBOO);
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_TAIGA);

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.6F))
                .setWaterColor(0x3f76e4)
                .setWaterFogColor(0x050533)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.FOREST)
                .temperature(0.6F)
                .downfall(0.5F)
                .scale(0.1F)
                .depth(0.2F)
                .precipitation(Biome.RainType.RAIN)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .setEffects(effects)
                .build();
    }
}
