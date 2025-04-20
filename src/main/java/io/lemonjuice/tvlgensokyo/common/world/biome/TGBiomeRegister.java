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
    public static final RegistryKey<Biome> SUN_FIELD = makeKey("sun_field", SunFieldBiomes::getMain);
    public static final RegistryKey<Biome> YOUKAI_MOUNTAIN = makeKey("youkai_mountain", YoukaiMountainBiomes::getMain);

    private static RegistryKey<Biome> makeKey(String name, Supplier<Biome> biome) {
        BIOMES.register(name, biome);
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, new ResourceLocation(TravelogueGensokyo.MODID, name));
    }


}
