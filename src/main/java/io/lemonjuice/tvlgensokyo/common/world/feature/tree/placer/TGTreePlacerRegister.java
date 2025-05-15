package io.lemonjuice.tvlgensokyo.common.world.feature.tree.placer;

import com.mojang.serialization.Codec;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGTreePlacerRegister {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPES = DeferredRegister.create(ForgeRegistries.FOLIAGE_PLACER_TYPES, TravelogueGensokyo.MODID);

    public static final RegistryObject<FoliagePlacerType<SakuraFoliagePlacer>> SAKURA_FOLIAGE = FOLIAGE_PLACER_TYPES.register("sakura", () -> new FoliagePlacerType<>(SakuraFoliagePlacer.CODEC));


    public static final TrunkPlacerType<SakuraTrunkPlacer> SAKURA_TRUNK_PLACER = registerTrunk(new ResourceLocation(TravelogueGensokyo.MODID, "sakura_trunk_placer"), SakuraTrunkPlacer.CODEC);

    private static <T extends AbstractTrunkPlacer> TrunkPlacerType<T> registerTrunk(ResourceLocation name, Codec<T> codec) {
        return Registry.register(Registry.TRUNK_REPLACER, name, new TrunkPlacerType<>(codec));
    }
}
