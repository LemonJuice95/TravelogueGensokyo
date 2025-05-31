package io.lemonjuice.tvlgensokyo.common.world.biome;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class TGBiomeRegister {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, TravelogueGensokyo.MODID);

    public static final RegistryKey<Biome> DREAM_WORLD = makeKey("dream_world", DreamWorldBiomes::getMain);

    //Normal Biomes
    public static final RegistryKey<Biome> GENSOKYO_RIVER = makeKey("gensokyo_river", GensokyoNormalBiomes::getGensokyoRiver);

    //Sun Field
    public static final RegistryKey<Biome> SUN_FIELD = makeKey("sun_field", SunFieldBiomes::getMain);

    //Youkai Mountain
    public static final RegistryKey<Biome> YOUKAI_MOUNTAIN = makeKey("youkai_mountain", YoukaiMountainBiomes::getMain);
    public static final RegistryKey<Biome> GENBU_RAVINE = makeKey("genbu_ravine", YoukaiMountainBiomes::getGenbuRavine);
    public static final RegistryKey<Biome> GENBU_RAVINE_EDGE = makeKey("genbu_ravine_edge", YoukaiMountainBiomes::getGenbuRavineEdge);

    //Youkai Trail
    public static final RegistryKey<Biome> YOUKAI_TRAIL = makeKey("youkai_trail", YoukaiTrailBiomes::getYoukaiTrail);
    public static final RegistryKey<Biome> YOUKAI_TRAIL_RIVER = makeKey("youkai_trail_river", YoukaiTrailBiomes::getYoukaiTrailRiver);

    //Bamboo Forest of Lost
    public static final RegistryKey<Biome> BAMBOO_FOREST_OF_LOST = makeKey("bamboo_forest_of_lost", BambooForestOfLostBiomes::getMain);

    //Misty Lake
    public static final RegistryKey<Biome> MISTY_LAKE = makeKey("misty_lake", MistyLakeBiomes::getMain);

    private static RegistryKey<Biome> makeKey(String name, Supplier<Biome> biome) {
        BIOMES.register(name, biome);
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(TravelogueGensokyo.MODID, name));
    }


}
