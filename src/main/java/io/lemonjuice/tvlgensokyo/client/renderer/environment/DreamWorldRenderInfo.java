package io.lemonjuice.tvlgensokyo.client.renderer.environment;

import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.ISkyRenderHandler;

import javax.annotation.Nullable;

public class DreamWorldRenderInfo extends DimensionRenderInfo {
    private ISkyRenderHandler skyRenderer;

    public DreamWorldRenderInfo() {
        super(Float.NaN, false, FogType.NONE, false, false);
    }

    @Override
    public Vector3d func_230494_a_(Vector3d biomeFogColor, float dayLight) { // For modifying biome fog color with day cycle
        return biomeFogColor.mul(dayLight * 0.94F + 0.06F, (dayLight * 0.94F + 0.06F), (dayLight * 0.91F + 0.09F));
    }

    @Override
    public boolean func_230493_a_(int x, int y) { //True = near fog
        return false;
    }

    @Nullable
    @Override
    public float[] func_230492_a_(float dayCycle, float p_230492_2_) { //fog color
        return null;
    }

    @Nullable
    @Override
    public ISkyRenderHandler getSkyRenderHandler() {
        if(this.skyRenderer == null)
            this.skyRenderer = new DreamWorldSkyRenderer();
        return this.skyRenderer;
    }
}
