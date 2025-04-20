package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import net.minecraft.nbt.CompoundNBT;

public class ActionChangeDanmakuType extends Action {
    public final Danmaku.Type type;

    public ActionChangeDanmakuType(Danmaku.Type type) {
        super("change_type");

        this.type = type;
    }

    @Override
    public CompoundNBT toCompoundNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("ActionType", this.getName());
        nbt.putString("DanmakuType", this.type.toString());
        return null;
    }

    public static ActionChangeDanmakuType fromCompoundNBT(CompoundNBT nbt) {
        Danmaku.Type type = Danmaku.Type.valueOf(nbt.getString("DanmakuType"));
        return new ActionChangeDanmakuType(type);
    }

    @Override
    public void applyAction(EntityDanmaku danmaku) {
        danmaku.danmaku.type = this.type;
    }
}
