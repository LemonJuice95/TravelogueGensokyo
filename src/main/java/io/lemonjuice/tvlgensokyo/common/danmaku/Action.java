package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import net.minecraft.nbt.CompoundNBT;

public abstract class Action {
    private final String name;

    protected Action(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void applyAction(DanmakuEntity danmaku);

    public static abstract class Serializer<T extends Action> {
        public abstract T read(CompoundNBT nbt);
        public abstract CompoundNBT write(T action);
    }
}
