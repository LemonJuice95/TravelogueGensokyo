package io.lemonjuice.tvlgensokyo.client.renderer.environment;

import net.minecraft.client.world.DimensionRenderInfo;
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
}
