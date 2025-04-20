package io.lemonjuice.tvlgensokyo.common.entity.projectile;

import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.common.misc.TGProjectileDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.Random;

public class EntityAmulet extends TGProjectileEntity {
    private static final DataParameter<Boolean> FISSILE = EntityDataManager.createKey(EntityAmulet.class, DataSerializers.BOOLEAN);

    public EntityAmulet(EntityType<? extends EntityAmulet> type, World world) {
        super(type, world);
        this.setNoGravity(true);
    }

    public EntityAmulet(World world, boolean fissile, Entity owner, TGGroups group, int maxTick) {
        super(TGEntityRegister.AMULET.get(), world, owner, group, maxTick);
        this.dataManager.set(FISSILE, fissile);
        this.setNoGravity(true);
    }

    @Override
    public void onImpact(RayTraceResult result) {
        if(!this.world.isRemote) {
            if (this.dataManager.get(FISSILE)) {
                this.fission();
            }
            if (result.getType() == RayTraceResult.Type.ENTITY) {
                Entity entity = ((EntityRayTraceResult)result).getEntity();
                entity.attackEntityFrom(TGProjectileDamageSource.causeAmuletDamage(this), 2.0F);
            }
            this.remove();
        }
    }

    private void fission() {
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            EntityAmulet amulet = new EntityAmulet(this.world, false, this.owner, this.group, this.dataManager.get(MAX_TICK));

            int pitch = -random.nextInt(20);
            int yaw = random.nextInt(360) - 180;

            amulet.shoot(this.getPosX(), this.getPosY() + 0.1, this.getPosZ(), pitch, yaw, 1.2);
            this.world.addEntity(amulet);
        }
    }

    @Override
    public void tick() {
        if(this.existingTicks >= this.dataManager.get(MAX_TICK)) {
            if(this.dataManager.get(FISSILE)) {
                this.fission();
            }
            this.remove();
        }

        super.tick();
    }

    @Override
    protected void registerData() {
        this.dataManager.register(FISSILE, true);
        this.dataManager.register(MAX_TICK, 50);
    }

    @Override
    protected void writeAdditional(CompoundNBT nbt) {
        nbt.putBoolean("Fissile", this.dataManager.get(FISSILE));
        super.writeAdditional(nbt);
    }

    @Override
    protected void readAdditional(CompoundNBT nbt) {
        if(nbt.contains("Fissile")) {
            this.dataManager.set(FISSILE, nbt.getBoolean("Fissile"));
        }
        super.readAdditional(nbt);
    }
}
