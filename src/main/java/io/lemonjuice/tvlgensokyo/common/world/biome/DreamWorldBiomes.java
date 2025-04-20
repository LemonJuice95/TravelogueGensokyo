package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class DreamWorldBiomes {

    public static Biome getMain() {
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder();
        generationSettings.withSurfaceBuilder(SurfaceBuilder.DEFAULT.func_242929_a(new SurfaceBuilderConfig(TGBlockRegister.DREAM_BASE.get().getDefaultState(), TGBlockRegister.DREAM_BASE.get().getDefaultState(), TGBlockRegister.DREAM_BASE.get().getDefaultState())));

        BiomeAmbience effects = new BiomeAmbience.Builder()
                .setWaterColor(0x0044ff)
                .setWaterFogColor(0x0097d1)
                .setFogColor(0x0f2157)
                .withSkyColor(0x0f2157)
                .build();

        return new Biome.Builder().category(Biome.Category.NONE)
                .depth(0.0F)
                .downfall(1.0F)
                .precipitation(Biome.RainType.NONE)
                .scale(0.0F)
                .temperature(0.6F)
                .setEffects(effects)
                .withGenerationSettings(generationSettings.build())
                .withMobSpawnSettings(MobSpawnInfo.EMPTY)
                .build();
    }

}
