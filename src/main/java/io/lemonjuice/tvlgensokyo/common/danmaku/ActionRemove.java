package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import net.minecraft.nbt.CompoundNBT;

public class ActionRemove extends Action{
    public ActionRemove() {
        super("remove");
    }

    @Override
    public CompoundNBT toCompoundNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("ActionType", this.getName());
        return nbt;
    }

    public static ActionRemove fromCompoundNBT(CompoundNBT nbt) {
        return new ActionRemove();
    }

    @Override
    public void applyAction(EntityDanmaku danmaku) {
        danmaku.remove();
    }
}
