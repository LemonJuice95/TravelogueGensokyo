package io.lemonjuice.tvlgensokyo.common.entity.projectile;

import io.lemonjuice.tvlgensokyo.common.danmaku.Action;
import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.misc.TGProjectileDamageSource;
import io.lemonjuice.tvlgensokyo.utils.TGDanmakuUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.List;

public class DanmakuEntity extends TGProjectileEntity {

    public Danmaku danmaku;
    public static final DataParameter<Integer> DANMAKU_TYPE = EntityDataManager.createKey(DanmakuEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> DANMAKU_COLOR = EntityDataManager.createKey(DanmakuEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Float> SPEED = EntityDataManager.createKey(DanmakuEntity.class, DataSerializers.FLOAT);

    public DanmakuEntity(EntityType<? extends DanmakuEntity> type, World world) {
        super(type, world);
        this.danmaku = new Danmaku(Danmaku.Type.DOT, Danmaku.Color.WHITE, 1, 0.0F);
    }

    public DanmakuEntity(World world, Danmaku danmaku, TGGroups group, Entity owner) {
        super(TGDanmakuUtils.DANMAKU_ENTITY_MAP.get(danmaku.type), world, owner, group, danmaku.maxTick);

        this.danmaku = danmaku;
        this.dataManager.set(MAX_TICK, danmaku.maxTick);
        this.dataManager.set(DANMAKU_COLOR, danmaku.color.ordinal());
        this.dataManager.set(DANMAKU_TYPE, danmaku.type.ordinal());
    }

    @Override
    public void onImpact(RayTraceResult rayTraceResult) {
        if(rayTraceResult.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)rayTraceResult).getEntity();
            entity.attackEntityFrom(TGProjectileDamageSource.causeDanmakuDamage(this), this.danmaku.damage);
        }
        this.remove();
    }

    public void updateMotion() {
        double speed = (double) this.dataManager.get(SPEED);
        double x = speed * -MathHelper.sin(this.rotationYaw * ((float) Math.PI / 180F)) * MathHelper.cos(this.rotationPitch * ((float) Math.PI / 180F));
        double y = speed * -MathHelper.sin(this.rotationPitch * ((float) Math.PI / 180F));
        double z = speed * MathHelper.cos(this.rotationYaw * ((float) Math.PI / 180F)) * MathHelper.cos(this.rotationPitch * ((float) Math.PI / 180F));
        this.setMotion(x, y, z);
    }

    public void setRotationAndMotion(float yaw, float pitch) {
        super.setRotation(yaw, pitch);
        this.updateMotion();
    }

    public void setSpeed(float speed) {
        this.dataManager.set(SPEED, speed);
        this.updateMotion();
    }

    @Override
    public void tick() {
        if(this.danmaku.actions.containsKey(this.existingTicks)) {
            List<Action> applyingAction = this.danmaku.actions.get(this.existingTicks);
            applyingAction.forEach((a) -> {
                a.applyAction(this);
            });
        }

        if(this.existingTicks >= this.dataManager.get(MAX_TICK)) {
            this.remove();
        }

        if(this.existingTicks >= 1980 || this.owner == null || !this.owner.isAlive()) {
            this.remove();
        }

        super.tick();
    }



    @Override
    protected void registerData() {
        this.dataManager.register(SPEED, 0.0F);
        this.dataManager.register(DANMAKU_COLOR, 0);
        this.dataManager.register(DANMAKU_TYPE, 0);
        this.dataManager.register(MAX_TICK, 1);
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        this.owner = this;
        this.ownerId = this.entityUniqueID;
        if(nbt.contains("Owner")) {
            this.ownerId = UUIDCodec.decodeUUID(((IntArrayNBT)nbt.get("Owner")).getIntArray());
            this.owner = ((ServerWorld) this.world).getEntityByUuid(this.ownerId);
        }
        if(nbt.contains("Danmaku")) {
            this.danmaku = new Danmaku(nbt.getCompound("Danmaku"));
            this.dataManager.set(MAX_TICK, this.danmaku.maxTick);
            this.dataManager.set(DANMAKU_COLOR, danmaku.color.ordinal());
            this.dataManager.set(DANMAKU_TYPE, danmaku.type.ordinal());
        }
        if(nbt.contains("Group")) {
            this.group = TGGroups.values()[nbt.getInt("Group")];
        }
        if(nbt.contains("Speed")) {
            this.dataManager.set(SPEED, nbt.getFloat("Speed"));
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        nbt.putInt("Group", this.group.ordinal());
        nbt.put("Owner", new IntArrayNBT(UUIDCodec.encodeUUID(this.ownerId)));
        nbt.put("Danmaku", this.danmaku.toCompoundNBT());
        nbt.putFloat("Speed", this.dataManager.get(SPEED));
    }

    @Override
    public boolean canBeHit(Entity entity) {
        return super.canBeHit(entity) && (entity instanceof LivingEntity);
    }
}
