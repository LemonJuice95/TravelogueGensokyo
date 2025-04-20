package io.lemonjuice.tvlgensokyo.common.entity.projectile;

import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.common.misc.TGProjectileDamageSource;
import io.lemonjuice.tvlgensokyo.utils.TGEntityUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;

public class EntityGungnir extends TGProjectileEntity {

    public static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityGungnir.class, DataSerializers.FLOAT);

    public EntityGungnir(EntityType<? extends EntityGungnir> type, World world) {
        super(type, world);
    }

    public EntityGungnir(World world, Entity owner, TGGroups group, int maxTick) {
        super(TGEntityRegister.GUNGNIR.get(), world, owner, group, maxTick);
    }

    public void setDamage(float damage) {
        this.dataManager.set(DAMAGE, damage);
    }

    @Override
    public void onImpact(RayTraceResult result) {
        if(result.getType() == RayTraceResult.Type.BLOCK) {
            Vector3d vector3d = result.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
            this.setMotion(vector3d);
            Vector3d vector3d1 = vector3d.normalize().scale((double)0.05F);
            this.setRawPosition(this.getPosX() - vector3d1.x, this.getPosY() - vector3d1.y, this.getPosZ() - vector3d1.z);
        }
        if(result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            entity.attackEntityFrom(TGProjectileDamageSource.causeGungnirDamage(this), this.dataManager.get(DAMAGE));
        }
    }

    @Override
    public void tick() {
        this.existingTicks++;
        ArrayList<EntityRayTraceResult> entityRayTraceResults = TGEntityUtils.rayTraceAllEntities(this, this.getPositionVec(), this.getPositionVec().add(this.getMotion()), this::canBeHit);
        RayTraceResult rayTraceResult = ProjectileHelper.func_234618_a_(this, this::canBeHit);
        if(rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
            this.onImpact(rayTraceResult);
        }

        if(!entityRayTraceResults.isEmpty()) {
            for(EntityRayTraceResult i : entityRayTraceResults) {
                this.onImpact(i);
            }
        }


        if(this.existingTicks >= this.dataManager.get(MAX_TICK)) {
            this.remove();
        }

        this.moveAsMotion();
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        nbt.putFloat("Damage", this.dataManager.get(DAMAGE));
        super.writeAdditional(nbt);
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        if(nbt.contains("Damage")) {
            this.dataManager.set(DAMAGE, nbt.getFloat("Damage"));
        }
        super.readAdditional(nbt);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(MAX_TICK, 400);
        this.dataManager.register(DAMAGE, 1.0F);
    }
}
