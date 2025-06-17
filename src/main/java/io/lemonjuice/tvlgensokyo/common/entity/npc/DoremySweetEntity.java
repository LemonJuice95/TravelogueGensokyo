package io.lemonjuice.tvlgensokyo.common.entity.npc;

import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.INPC;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class DoremySweetEntity extends CreatureEntity implements INPC, IHasGroup {
    public DoremySweetEntity(EntityType<? extends DoremySweetEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 4.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
    }

    @Override
    public TGGroups getGroup() {
        return TGGroups.FRIENDLY;
    }
}
