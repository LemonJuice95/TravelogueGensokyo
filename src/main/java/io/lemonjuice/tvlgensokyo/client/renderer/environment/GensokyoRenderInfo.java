package io.lemonjuice.tvlgensokyo.client.renderer.environment;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.IWeatherRenderHandler;

import javax.annotation.Nullable;

public class GensokyoRenderInfo extends DimensionRenderInfo.Overworld {
    private IWeatherRenderHandler weatherRenderHandler;

    @Nullable
    @Override
    public IWeatherRenderHandler getWeatherRenderHandler() {
        if(this.weatherRenderHandler == null) {
            this.weatherRenderHandler = new GensokyoWeatherRenderer();
        }
        return this.weatherRenderHandler;
    }

//    @Override
//    public boolean func_230493_a_(int vecX, int vexZ) {
//        PlayerEntity player = TravelogueGensokyo.PROXY.getPlayer();
//        return TGBiomeUtils.isSameBiome(player.world.getBiome(player.getPosition()), TGBiomeRegister.MISTY_LAKE);
//    }
}
