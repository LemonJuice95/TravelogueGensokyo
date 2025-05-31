package io.lemonjuice.tvlgensokyo.common.world;

import net.minecraft.world.gen.area.IAreaFactory;

public class WorldGenInfoHolder {
    private static final WorldGenInfoHolder INSTANCE = new WorldGenInfoHolder();
    public long seed = 0;

    public static WorldGenInfoHolder getInstance() {
        return INSTANCE;
    }

    public void setSeed(long seed) {
        this.seed = seed;
    }
}
