package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import net.minecraft.nbt.CompoundNBT;

public class ActionRemove extends Action{
    public ActionRemove() {
        super("remove");
    }

    @Override
    public void applyAction(DanmakuEntity danmaku) {
        danmaku.remove();
    }

    public static class Serializer extends Action.Serializer<ActionRemove> {
        @Override
        public ActionRemove read(CompoundNBT nbt) {
            return new ActionRemove();
        }

        @Override
        public CompoundNBT write(ActionRemove action) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("ActionType", action.getName());
            return nbt;
        }
    }
}
