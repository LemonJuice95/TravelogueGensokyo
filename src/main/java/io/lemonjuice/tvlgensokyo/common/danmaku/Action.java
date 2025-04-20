package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import net.minecraft.nbt.CompoundNBT;

public abstract class Action {
    private final String name;

    protected Action(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract CompoundNBT toCompoundNBT();

    public abstract void applyAction(EntityDanmaku danmaku);
}
