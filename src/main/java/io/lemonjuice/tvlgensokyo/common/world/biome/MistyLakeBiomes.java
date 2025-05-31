package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class MistyLakeBiomes {
    public static Biome getMain() {
        MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .withSkyColor(TGBiomeUtils.getSkyColor(0.2F))
                .setWaterColor(0x3f76e4)
                .setWaterFogColor(0x050533)
                .setFogColor(0xc0d8ff)
                .build();

        return new Biome.Builder()
                .category(Biome.Category.OCEAN)
                .temperature(0.2F)
                .downfall(0.4F)
                .scale(0.0F)
                .depth(-1.5F)
                .precipitation(Biome.RainType.SNOW)
                .withMobSpawnSettings(mobSpawnInfo.build())
                .withGenerationSettings(generationSettings.build())
                .setEffects(effects)
                .build();
    }
}
