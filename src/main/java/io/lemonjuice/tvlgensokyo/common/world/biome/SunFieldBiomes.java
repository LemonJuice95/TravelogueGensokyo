package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.common.world.feature.TGFeatures;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class SunFieldBiomes {
    public static Biome getMain() {
        MobSpawnInfo.Builder mobSpawn = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState())));
        generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, TGFeatures.MASSIVE_SUNFLOWER.chance(1));
        generationSettings.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, TGFeatures.CONFIGURED_CROP_CIRCLE.chance(80));
        DefaultBiomeFeatures.withOverworldOres(generationSettings);

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .setWaterColor(0x3f76e4)
                .setWaterFogColor(0x050533)
                .setFogColor(0xc0d8ff)
                .withSkyColor(TGBiomeUtils.getSkyColor(0.8F))
                .build();

        return new Biome.Builder().category(Biome.Category.PLAINS)
                .scale(0.0F)
                .depth(0.125F)
                .downfall(0.4F)
                .temperature(0.8F)
                .precipitation(Biome.RainType.RAIN)
                .withGenerationSettings(generationSettings.build())
                .withMobSpawnSettings(mobSpawn.build())
                .setEffects(effects)
                .build();

    }
}
