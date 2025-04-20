package io.lemonjuice.tvlgensokyo.common.entity.ai.goal;

import io.lemonjuice.tvlgensokyo.common.entity.api.ITalkerEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;

public class TalkWithPlayerGoal extends Goal {
    private final MobEntity entity;

    public TalkWithPlayerGoal(MobEntity entity) {
        this.entity = entity;
    }

    @Override
    public boolean shouldExecute() {
        PlayerEntity player = null;
        if(!this.entity.isAlive()) {
            return false;
        } else if(this.entity.isInWater()) {
            return false;
        } else if(!this.entity.isOnGround()) {
            return false;
        } else if(this.entity.velocityChanged) {
            return false;
        } else {
            if (this.entity instanceof IMerchant) {
                player = ((IMerchant) entity).getCustomer();
            } else if(this.entity instanceof ITalkerEntity) {
                player = ((ITalkerEntity) entity).getTalkingPlayer();
            }
            if(player == null) {
                return false;
            } else if (this.entity.getDistanceSq(player) > 16.0D) {
                return false;
            } else {
                return player.openContainer != null;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.shouldExecute();
    }

    @Override
    public void tick() {
        this.entity.getNavigator().clearPath();
    }

    @Override
    public void resetTask() {
        if(this.entity instanceof IMerchant)
            ((IMerchant) this.entity).setCustomer(null);
        else if(this.entity instanceof ITalkerEntity)
            ((ITalkerEntity) this.entity).setTalkingPlayer(null);
    }
}
