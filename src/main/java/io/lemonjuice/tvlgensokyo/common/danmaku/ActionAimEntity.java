package io.lemonjuice.tvlgensokyo.common.danmaku;

import io.lemonjuice.tvlgensokyo.common.entity.projectile.DanmakuEntity;
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

    public static ActionAimEntity fromCompoundNBT(CompoundNBT nbt) {
        double speed = nbt.getDouble("Speed");
        float jitter = nbt.getFloat("Jitter");
        return new ActionAimEntity(speed, jitter);
    }

    @Override
    public void applyAction(DanmakuEntity danmaku) {
        Entity entity = TGEntityUtils.getClosestEntity(danmaku, danmaku::canBeHit, 32.0F);
        if(entity != null) {
            Random random = new Random();
            float jitterPitch = random.nextFloat() / 2.0F * this.jitter * (random.nextBoolean() ? -1.0F : 1.0F) * (float)Math.PI;
            float jitterYaw = random.nextFloat() / 2.0F * this.jitter * (random.nextBoolean() ? -1.0F : 1.0F) * (float)Math.PI;
        }
    }

    public static class Serializer extends Action.Serializer<ActionAimEntity> {

        @Override
        public ActionAimEntity read(CompoundNBT nbt) {
            double speed = nbt.getDouble("Speed");
            float jitter = nbt.getFloat("Jitter");
            return new ActionAimEntity(speed, jitter);
        }

        @Override
        public CompoundNBT write(ActionAimEntity action) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putString("ActionType", action.getName());
            nbt.putFloat("Jitter", action.jitter);
            nbt.putDouble("Speed", action.speed);
            return nbt;
        }
    }
}
