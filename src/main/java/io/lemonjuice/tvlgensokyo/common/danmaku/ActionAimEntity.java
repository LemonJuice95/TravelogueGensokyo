package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.utils.TGEntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;

import java.util.Random;

public class ActionAimEntity extends Action{
    public final double speed;
    public final float jitter;

    public ActionAimEntity(double speed, float jitter) {
        super("shoot_entity");

        this.speed = speed;
        this.jitter = jitter;
    }

    @Override
    public CompoundNBT toCompoundNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("ActionType", this.getName());
        nbt.putFloat("Jitter", this.jitter);
        nbt.putDouble("Speed", this.speed);
        return nbt;
    }

    public static ActionAimEntity fromCompoundNBT(CompoundNBT nbt) {
        double speed = nbt.getDouble("Speed");
        float jitter = nbt.getFloat("Jitter");
        return new ActionAimEntity(speed, jitter);
    }

    @Override
    public void applyAction(EntityDanmaku danmaku) {
        Entity entity = TGEntityUtils.getClosestEntity(danmaku, danmaku::canBeHit, 32.0F);
        if(entity != null) {
            Random random = new Random();
            float jitterPitch = random.nextFloat() / 2.0F * this.jitter * (random.nextBoolean() ? -1.0F : 1.0F) * (float)Math.PI;
            float jitterYaw = random.nextFloat() / 2.0F * this.jitter * (random.nextBoolean() ? -1.0F : 1.0F) * (float)Math.PI;
        }
    }
}
