package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import net.minecraft.nbt.CompoundNBT;

public class ActionChangeDanmakuType extends Action {
    public final Danmaku.Type type;

    public ActionChangeDanmakuType(Danmaku.Type type) {
        super("change_type");

        this.type = type;
    }

    //FIXME 使用删除自身并添加新实体的方式实现
    @Override
    public void applyAction(DanmakuEntity danmaku) {
        danmaku.danmaku.type = this.type;
        danmaku.getDataManager().set(DanmakuEntity.DANMAKU_TYPE, this.type.ordinal());
    }

    public static class Serializer extends Action.Serializer<ActionChangeDanmakuType> {
        @Override
        public ActionChangeDanmakuType read(CompoundNBT nbt) {
            Danmaku.Type type = Danmaku.Type.valueOf(nbt.getString("DanmakuType"));
            return new ActionChangeDanmakuType(type);
        }

        @Override
        public CompoundNBT write(ActionChangeDanmakuType action) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("ActionType", action.getName());
            nbt.putString("DanmakuType", action.type.toString());
            return nbt;
        }
    }
}
