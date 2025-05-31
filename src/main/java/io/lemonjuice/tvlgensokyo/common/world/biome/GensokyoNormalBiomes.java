package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class GensokyoNormalBiomes {
   public static Biome getGensokyoRiver() {
       MobSpawnInfo.Builder mobSpawnInfo = new MobSpawnInfo.Builder();

       BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
       generationSettings.withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
       DefaultBiomeFeatures.withOverworldOres(generationSettings);
       generationSettings.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);

       BiomeAmbience effects = new BiomeAmbience.Builder()
               .withSkyColor(TGBiomeUtils.getSkyColor(0.5F))
               .setWaterColor(0x3f76e4)
               .setWaterFogColor(0x050533)
               .setFogColor(0xc0d8ff)
               .build();

       return new Biome.Builder()
               .category(Biome.Category.RIVER)
               .temperature(0.5F)
               .downfall(0.5F)
               .scale(0.0F)
               .depth(-0.5F)
               .precipitation(Biome.RainType.RAIN)
               .withMobSpawnSettings(mobSpawnInfo.build())
               .withGenerationSettings(generationSettings.build())
               .setEffects(effects)
               .build();
   }
}
