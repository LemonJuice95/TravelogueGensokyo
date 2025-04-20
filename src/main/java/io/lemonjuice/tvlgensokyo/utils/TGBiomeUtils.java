package io.lemonjuice.tvlgensokyo.utils;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class TGBiomeUtils {

    public static int getSkyColor(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public static int getBiomeId(RegistryKey<Biome> key, Registry<Biome> registry) {
        return registry.getId(registry.getValueForKey(key));
    }
}
