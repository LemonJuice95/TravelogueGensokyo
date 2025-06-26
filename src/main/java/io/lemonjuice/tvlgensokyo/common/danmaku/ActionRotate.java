package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
import net.minecraft.nbt.CompoundNBT;

public class ActionRotate extends Action{
    public final float pitch;
    public final float yaw;

    public ActionRotate(float pitch, float yaw) {
        super("rotate");

        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public void applyAction(DanmakuEntity danmaku) {
        if (!danmaku.world.isRemote) {
            danmaku.setRotationAndMotion(danmaku.rotationYaw + this.yaw, danmaku.rotationPitch - this.pitch);
        }
    }

    public static class Serializer extends Action.Serializer<ActionRotate> {
        @Override
        public ActionRotate read(CompoundNBT nbt) {
            float pitch = nbt.getFloat("Pitch");
            float yaw = nbt.getFloat("Yaw");
            return new ActionRotate(pitch, yaw);
        }

        @Override
        public CompoundNBT write(ActionRotate action) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putFloat("Pitch", action.pitch);
            nbt.putFloat("Yaw", action.yaw);
            nbt.putString("ActionType", action.getName());
            return nbt;
        }
    }
}
