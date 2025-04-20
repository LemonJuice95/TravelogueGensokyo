package io.lemonjuice.tvlgensokyo.common.world.biome;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.Map;

public class TGBiomeMapper {
    public static final Map<RegistryKey<Biome>, Biome> BIOME_MAP = mapBiomes();

    //I HATE my trash codes
    private static ImmutableMap<RegistryKey<Biome>, Biome> mapBiomes() {
        ImmutableMap.Builder<RegistryKey<Biome>, Biome> builder = new ImmutableMap.Builder<>();

        builder.put(TGBiomeRegister.DREAM_WORLD, DreamWorldBiomes.getMain());
        builder.put(TGBiomeRegister.SUN_FIELD, SunFieldBiomes.getMain());
        builder.put(TGBiomeRegister.YOUKAI_MOUNTAIN, YoukaiMountainBiomes.getMain());

        return builder.build();
    }
}
