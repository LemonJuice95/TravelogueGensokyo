package io.lemonjuice.tvlgensokyo.common.entity.misc;

import io.lemonjuice.tvlgensokyo.common.entity.interfaces.IHasOwner;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.utils.TGEntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.UUIDCodec;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class WindEntity extends Entity implements IHasOwner {
    private Entity owner;
    private UUID ownerId;

    private static final DataParameter<Integer> TICKS_LEFT = EntityDataManager.createKey(WindEntity.class, DataSerializers.VARINT);

    public WindEntity(EntityType<? extends WindEntity> type, World world) {
        super(type, world);
        this.setDefaultOwner();
        this.setNoGravity(true);
    }

    public WindEntity(World world, Entity owner) {
        super(TGEntityRegister.WIND.get(), world);
        this.setOwner(owner);
        this.setNoGravity(true);
    }

    public void setTicksLeft(int ticksLeft) {
        this.dataManager.set(TICKS_LEFT, ticksLeft);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(TICKS_LEFT, 0);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        nbt.putInt("MaxTick", this.dataManager.get(TICKS_LEFT));
        nbt.put("Owner", new IntArrayNBT(UUIDCodec.encodeUUID(this.ownerId)));
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        if(!this.world.isRemote) {
            this.owner = this;
            this.ownerId = this.entityUniqueID;
            if(nbt.contains("Owner")) {
                this.ownerId = UUIDCodec.decodeUUID(((IntArrayNBT)nbt.get("Owner")).getIntArray());
                this.owner = ((ServerWorld)world).getEntityByUuid(this.ownerId);
            }
            if(nbt.contains("MaxTick")) {
                this.dataManager.set(TICKS_LEFT, nbt.getInt("MaxTick"));
            }
        }
    }

    @Override
    @Nullable
    public Entity getOwner() {
        return this.owner;
    }

    @Override
    @Nullable
    public Optional<UUID> getOwnerUUID() {
        return Optional.ofNullable(this.ownerId);
    }

    public void setOwner(Entity owner) {
        this.owner = owner;
        this.ownerId = owner.getUniqueID();
    }

    public void setDefaultOwner() {
        this.owner = this;
        this.ownerId = this.entityUniqueID;
    }


    private boolean canBeHit(Entity entity) {
        if(!entity.isSpectator()) {
            Entity entity1 = this.getOwner();
            return entity1 == null || ((!entity1.isRidingSameEntity(entity)) && !entity.equals(entity1));
        } else {
            return false;
        }
    }

    @Override
    public void tick() {
        Vector3d movement = this.getMotion();
        Vector3d start = this.getPositionVec();
        Vector3d end = start.add(movement);

        ArrayList<EntityRayTraceResult> results = TGEntityUtils.rayTraceAllEntities(this, start, end, this::canBeHit);
        for(EntityRayTraceResult rayTraceResult : results) {
            if (rayTraceResult != null) {
                if (rayTraceResult.getType() == RayTraceResult.Type.ENTITY) {
                    Entity entity = rayTraceResult.getEntity();
                    if (entity != this.getOwner()) {
                        entity.setMotion(movement);
                    }
                }
            }
        }

        double d1 = movement.x;
        double d2 = movement.y;
        double d3 = movement.z;
        double d4 = this.getPosX();
        double d5 = this.getPosY();
        double d6 = this.getPosZ();

        this.dataManager.set(TICKS_LEFT, this.dataManager.get(TICKS_LEFT) - 1);
        if(this.dataManager.get(TICKS_LEFT) <= 0) {
            this.remove();
        }

        this.setPosition(d1 + d4, d2 + d5, d3 + d6);
        super.tick();
    }
}
