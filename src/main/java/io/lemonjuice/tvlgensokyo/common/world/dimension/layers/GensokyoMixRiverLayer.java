package io.lemonjuice.tvlgensokyo.common.world.dimension.layers;

import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum GensokyoMixRiverLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    private Registry<Biome> biomeRegistry;

    public GensokyoMixRiverLayer setup(Registry<Biome> registry) {
        this.biomeRegistry = registry;
        return this;
    }



    //TODO 雾之湖完成后添加
    @Override
    public int apply(INoiseRandom context, IArea area, IArea river, int x, int z) {
        int value = area.getValue(this.getOffsetX(x), this.getOffsetZ(z));
        int riverValue = river.getValue(this.getOffsetX(x), this.getOffsetZ(z));

        if(riverValue == TGBiomeUtils.getBiomeId(TGBiomeRegister.GENSOKYO_RIVER, this.biomeRegistry)) {
            if(value == TGBiomeUtils.getBiomeId(TGBiomeRegister.YOUKAI_TRAIL, this.biomeRegistry)) {
                return TGBiomeUtils.getBiomeId(TGBiomeRegister.YOUKAI_TRAIL_RIVER, this.biomeRegistry);
            } else if(value == TGBiomeUtils.getBiomeId(TGBiomeRegister.YOUKAI_MOUNTAIN, this.biomeRegistry)) {
                return TGBiomeUtils.getBiomeId(TGBiomeRegister.GENBU_RAVINE, this.biomeRegistry);
            }
        }


        return value;
    }
}
