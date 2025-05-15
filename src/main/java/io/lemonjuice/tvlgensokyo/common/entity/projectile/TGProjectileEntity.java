package io.lemonjuice.tvlgensokyo.common.entity.projectile;

import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.common.entity.api.IHasOwner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Optional;
import java.util.UUID;

public abstract class TGProjectileEntity extends Entity implements IHasOwner, IHasGroup {
    protected int existingTicks = 0;
    protected Entity owner;
    protected UUID ownerId;
    protected TGGroups group;
    protected static final DataParameter<Integer> MAX_TICK = EntityDataManager.createKey(TGProjectileEntity.class, DataSerializers.VARINT);

    public TGProjectileEntity(EntityType<? extends TGProjectileEntity> type, World world) {
        super(type, world);
        this.group = TGGroups.FRIENDLY;
        this.owner = this;
        this.ownerId = this.entityUniqueID;
    }

    public TGProjectileEntity(EntityType<?> type, World world, Entity owner, TGGroups group, int maxTick) {
        super(type, world);
        this.group = group;
        this.dataManager.set(MAX_TICK, maxTick);
        this.setOwner(owner);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        this.owner = this;
        this.ownerId = this.entityUniqueID;
        if(nbt.contains("Owner")) {
            this.ownerId = UUIDCodec.decodeUUID(((IntArrayNBT)nbt.get("Owner")).getIntArray());
            this.owner = ((ServerWorld) this.world).getEntityByUuid(this.ownerId);
        }
        if(nbt.contains("Group")) {
            this.group = TGGroups.values()[nbt.getInt("Group")];
        }
        if(nbt.contains("MaxTick")) {
            this.dataManager.set(MAX_TICK, nbt.getInt("MaxTick"));
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        nbt.putInt("Group", this.group.ordinal());
        nbt.put("Owner", new IntArrayNBT(UUIDCodec.encodeUUID(this.ownerId)));
        nbt.putInt("MaxTick",this.dataManager.get(MAX_TICK));
    }

    @Override
    public TGGroups getGroup() {
        return this.group;
    }

    public void setOwner(Entity owner) {
        this.owner = owner != null ? owner : this;
        this.ownerId = this.owner.getUniqueID();
    }

    @Override
    public Entity getOwner() {
        return this.owner;
    }

    @Override
    public Optional<UUID> getOwnerUUID() {
        return Optional.ofNullable(this.ownerId);
    }

    public abstract void onImpact(RayTraceResult rayTraceResult);

    @Override
    public void tick() {
        this.existingTicks++;

        this.moveAsMotion();

        RayTraceResult rayTraceResult = ProjectileHelper.func_234618_a_(this, this::canBeHit);
        if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
            this.onImpact(rayTraceResult);
        }

        super.tick();
    }

    protected void moveAsMotion() {
        Vector3d motion = this.getMotion();
        double d1 = this.getPosX();
        double d2 = this.getPosY();
        double d3 = this.getPosZ();
        double d4 = motion.x;
        double d5 = motion.y;
        double d6 = motion.z;
        this.setPosition(d1 + d4, d2 + d5, d3 + d6);
    }

    public void shoot(double x, double y, double z, float pitch, float yaw, double speed) {
        double motionX = speed * -MathHelper.sin(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
        double motionY = speed * -MathHelper.sin(pitch * ((float) Math.PI / 180F));
        double motionZ = speed * MathHelper.cos(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));

        this.setMotion(motionX, motionY, motionZ);
        this.setPosition(x, y, z);
        this.rotationPitch = pitch;
        this.rotationYaw = yaw;
    }

    @Override
    public boolean canBeAttackedWithItem() {
        return false;
    }

    protected boolean canBeHit(Entity entity) {
        boolean flag = this.group == TGGroups.FRIENDLY && (entity instanceof PlayerEntity);
        boolean flag1 = this.group == TGGroups.HOSTILE && (entity instanceof MonsterEntity);
        boolean flag2 = true;
        boolean flag3 = (entity instanceof TameableEntity) && ((TameableEntity) entity).isTamed();
        if(entity instanceof IHasGroup) {
            flag2 = ((IHasGroup) entity).getGroup() != this.group || ((IHasGroup) entity).getGroup() == TGGroups.NEUTRAL || this.group == TGGroups.NEUTRAL;
        }
        flag = flag || flag3;

        return entity.canBeAttackedWithItem() && entity.isAlive() && !flag && !flag1 && flag2;
    }


}
