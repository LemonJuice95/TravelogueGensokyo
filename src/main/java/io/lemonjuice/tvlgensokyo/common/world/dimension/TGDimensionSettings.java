package io.lemonjuice.tvlgensokyo.common.world.dimension;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TGDimensionSettings {
    public static final Map<Structure<?>, StructureSeparationSettings> GENSOKYO_STRUCTURE_SETTINGS_MAP = new HashMap<>();

    public static final DimensionSettings GENOKYO_NOISE_SETTINGS = new DimensionSettings(
            new DimensionStructuresSettings(Optional.empty(), GENSOKYO_STRUCTURE_SETTINGS_MAP),
            new NoiseSettings(
                    256,
                    new ScalingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D),
                    new SlideSettings(-10, 3, 0),
                    new SlideSettings(-30, 0, 0),
                    1,
                    2,
                    1.0D,
                    -0.46875D,
                    true,
                    true,
                    false,
                    false
                    ),
            Blocks.STONE.getDefaultState(),
            Blocks.WATER.getDefaultState(),
            -10,
            0,
            63,
            false
            );


    static {

    }
}
