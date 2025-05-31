package io.lemonjuice.tvlgensokyo.utils;

import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TGBiomeUtils {
    public static final List<ResourceLocation> BIOMES_WITH_GRASS_PATH = new ArrayList<>();
    public static final List<ResourceLocation> BIOMES_WITH_MOSSY_PATH = new ArrayList<>();
    public static final List<ResourceLocation> BIOMES_WITH_COBBLE_PATH = new ArrayList<>();

    static {
        BIOMES_WITH_GRASS_PATH.add(TGBiomeRegister.SUN_FIELD.getLocation());
        BIOMES_WITH_GRASS_PATH.add(TGBiomeRegister.YOUKAI_TRAIL.getLocation());

        BIOMES_WITH_MOSSY_PATH.add(TGBiomeRegister.BAMBOO_FOREST_OF_LOST.getLocation());
    }

    public static int getSkyColor(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public static int getBiomeId(RegistryKey<Biome> key, Registry<Biome> registry) {
        return registry.getId(registry.getValueForKey(key));
    }

    public static boolean isBiomeWithGrassPath(Biome biome) {
        return BIOMES_WITH_GRASS_PATH.contains(biome.getRegistryName());
    }

    public static boolean isBiomeWithCobblePath(Biome biome) {
        return BIOMES_WITH_COBBLE_PATH.contains(biome.getRegistryName());
    }

    public static boolean isBiomeWithMossyPath(Biome biome) {
        return BIOMES_WITH_MOSSY_PATH.contains(biome.getRegistryName());
    }

    public static boolean isSameBiome(Biome biome, RegistryKey<Biome> biomeKey) {
        return Objects.equals(biome.getRegistryName(), biomeKey.getLocation());
    }
}
