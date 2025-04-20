package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
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
    public CompoundNBT toCompoundNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putFloat("Pitch", this.pitch);
        nbt.putFloat("Yaw", this.yaw);
        nbt.putString("ActionType", this.getName());
        return nbt;
    }

    public static ActionRotate fromCompoundNBT(CompoundNBT nbt) {
        float pitch = nbt.getFloat("Pitch");
        float yaw = nbt.getFloat("Yaw");
        return new ActionRotate(pitch, yaw);
    }

    @Override
    public void applyAction(EntityDanmaku danmaku) {
        if (!danmaku.world.isRemote) {
            danmaku.setRotationAndMotion(danmaku.rotationYaw + this.yaw, danmaku.rotationPitch - this.pitch);
        }
    }
}
