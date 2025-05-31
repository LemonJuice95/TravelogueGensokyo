package io.lemonjuice.tvlgensokyo.common.world.dimension;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.world.WorldGenInfoHolder;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

public class TGDimensionRegister {
    public static final RegistryKey<DimensionType> DREAM_WORLD_TYPE = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(TravelogueGensokyo.MODID, "dream_world"));
    public static final RegistryKey<Dimension> DREAM_WORLD_DIM = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, new ResourceLocation(TravelogueGensokyo.MODID, "dream_world"));
    public static final RegistryKey<World> DREAM_WORLD = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(TravelogueGensokyo.MODID, "dream_world"));

    public static final RegistryKey<DimensionType> GENSOKYO_TYPE = RegistryKey.getOrCreateKey(Registry.DIMENSION_TYPE_KEY, new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"));
    public static final RegistryKey<Dimension> GENSOKYO_DIM = RegistryKey.getOrCreateKey(Registry.DIMENSION_KEY, new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"));
    public static final RegistryKey<World> GENSOKYO = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo"));



    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo_biome_provider"), GensokyoBiomeProvider.CODEC);
            Registry.register(Registry.CHUNK_GENERATOR_CODEC, new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo_chunk_generator"), GensokyoChunkGenerator.CODEC);
            Registry.register(WorldGenRegistries.NOISE_SETTINGS, new ResourceLocation(TravelogueGensokyo.MODID, "gensokyo_noise_settings"), TGDimensionSettings.GENOKYO_NOISE_SETTINGS);
        });
    }
}
