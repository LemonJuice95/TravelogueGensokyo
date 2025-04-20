package io.lemonjuice.tvlgensokyo.common.world.feature;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.registries.ForgeRegistries;

public class TGFeatureRegister {
    public static void registerFeatures() {
        register(TGFeatures.CROP_CIRCLE, "crop_circle");
    }

    public static void register(Feature<?> feature, String key) {
        feature.setRegistryName(new ResourceLocation(TravelogueGensokyo.MODID, key));
        ForgeRegistries.FEATURES.register(feature);
    }
}
