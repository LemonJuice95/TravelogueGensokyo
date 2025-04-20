package io.lemonjuice.tvlgensokyo.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import net.minecraft.data.BiomeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class TGBiomeDataGenerator extends BiomeProvider {
    private final DataGenerator generator;
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    public TGBiomeDataGenerator(DataGenerator generator) {
        super(generator);
        this.generator = generator;
    }

    @Override
    public void act(DirectoryCache cache) {
        Path path = this.generator.getOutputFolder();

        for(RegistryObject<Biome> entry : TGBiomeRegister.BIOMES.getEntries()) {
            Path path1 = getPath(path, entry.getId());
            Biome biome = entry.get();
            Function<Supplier<Biome>, DataResult<JsonElement>> function = JsonOps.INSTANCE.withEncoder(Biome.BIOME_CODEC);

            try {
                Optional<JsonElement> optional = function.apply(() -> {
                    return biome;
                }).result();
                if (optional.isPresent()) {
                    IDataProvider.save(GSON, cache, optional.get(), path1);
                } else {
                    TravelogueGensokyo.LOGGER.error("Couldn't serialize biome {}", (Object)path1);
                }
            } catch (IOException ioexception) {
                TravelogueGensokyo.LOGGER.error("Couldn't save biome {}", path1, ioexception);
            }
        }

    }

    private static Path getPath(Path path, ResourceLocation biomeLocation) {
        return path.resolve("data/" + TravelogueGensokyo.MODID + "/worldgen/biome/" + biomeLocation.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Travelogue Gensokyo Biome";
    }
}
