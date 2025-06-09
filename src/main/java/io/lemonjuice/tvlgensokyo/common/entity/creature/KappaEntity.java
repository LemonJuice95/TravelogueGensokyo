package io.lemonjuice.tvlgensokyo.common.entity.creature;

import io.lemonjuice.tvlgensokyo.common.entity.ai.goal.TalkWithPlayerGoal;
import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.UUID;

public class KappaEntity extends CreatureEntity implements IHasGroup, IAngerable, IRangedAttackMob, IMerchant {
    private UUID angerTargetUUID = null;
    private LivingEntity angerTarget = null;
    private final MerchantOffers offers = new MerchantOffers();
    private PlayerEntity customer;

    private static final RangedInteger ANGER_TIME_RANGE = RangedInteger.createRangedInteger(600, 800);

    public static final DataParameter<Integer> ANGER_TIME = EntityDataManager.createKey(KappaEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Boolean> TRADED = EntityDataManager.createKey(KappaEntity.class, DataSerializers.BOOLEAN);

    public KappaEntity(EntityType<? extends KappaEntity> type, World world) {
        super(type, world);
        this.initOffers();
    }

    public KappaEntity(World world) {
        super(TGEntityRegister.KAPPA.get(), world);
        this.initOffers();
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity player, Hand hand) {
        if(this.isAngry()) {
            ItemStack itemStack = player.getHeldItem(hand);
            if(itemStack.getItem() == TGItemRegister.CUCUMBER.get()) {
                this.stopAnger();
                for(int i = 0; i < 15; ++i) {
                    double d0 = this.rand.nextGaussian() * 0.02D;
                    double d1 = this.rand.nextGaussian() * 0.02D;
                    double d2 = this.rand.nextGaussian() * 0.02D;
                    this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getPosXRandom(1.0D), this.getPosYRandom() + 0.5D, this.getPosZRandom(1.0D), d0, d1, d2);
                }
                itemStack.shrink(1);
                return ActionResultType.SUCCESS;
            }
        } else {
            this.setCustomer(player);
            this.openMerchantContainer(player, this.getDisplayName(), 1);
            return ActionResultType.SUCCESS;
        }

        return ActionResultType.PASS;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if(!this.world.isRemote) {
            this.func_241359_a_((ServerWorld) this.world, false);
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 0.0D, 30, 50, 15.0F));
        this.goalSelector.addGoal(2, new TalkWithPlayerGoal(this));
        this.goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 4.0F));
        this.goalSelector.addGoal(4, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setCallsForHelp());
        this.targetSelector.addGoal(2, new ResetAngerGoal<>(this, true));
    }

    private void initOffers() {
        this.offers.add(new MerchantOffer(new ItemStack(TGItemRegister.CUCUMBER.get(), this.rand.nextInt(5) + 8), new ItemStack(TGItemRegister.COPPER_COIN.get()), Integer.MAX_VALUE, 0, 0.05F));
    }



    @Override
    public void onTrade(MerchantOffer offer) {
        offer.increaseUses();
        this.dataManager.set(TRADED, true);
    }

    @Override
    public void verifySellingItem(ItemStack stack) {
    }

    public static AttributeModifierMap.MutableAttribute createAttribute() {
        return LivingEntity.registerAttributes()
                .createMutableAttribute(Attributes.MAX_HEALTH, 30.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE);
    }

    @Override
    public void attackEntityWithRangedAttack(LivingEntity target, float distanceFactor) {
        Danmaku d = new Danmaku(Danmaku.Type.SMALL_JADE, Danmaku.Color.values()[this.rand.nextInt(8)], 200, 4.0F);
        EntityDanmaku danmaku = new EntityDanmaku(this.world, d, this.getGroup(), this);
        danmaku.setPosition(this.getPosX(), this.getPosYEye() - 0.25F, this.getPosZ());
        double dx = target.getPosX() - this.getPosX();
        double dy = target.getPosY() - this.getPosY();
        double dz = target.getPosZ() - this.getPosZ();
        Vector3d vec3d = new Vector3d(dx, dy, dz).normalize().scale(0.5D);
        danmaku.rotationYaw = -(float)(MathHelper.atan2(vec3d.x, vec3d.z) * (double)(180F / (float)Math.PI));
        danmaku.rotationPitch = -(float)(MathHelper.atan2(vec3d.y, MathHelper.sqrt(vec3d.x * vec3d.x + vec3d.z + vec3d.z)) * (double)(180F / (float)Math.PI));
        danmaku.setMotion(vec3d);
        this.world.addEntity(danmaku);
    }

    @Override
    public World getWorld() {
        return this.world;
    }

    @Override
    public int getXp() {
        return 0;
    }

    @Override
    public void setXP(int xpIn) {
    }

    @Override
    public boolean hasXPBar() {
        return false;
    }

    @Override
    public SoundEvent getYesSound() {
        return null;
    }

    @Override
    public void setClientSideOffers(@Nullable MerchantOffers offers) {
    }



    @Override
    public MerchantOffers getOffers() {
        return this.offers;
    }

    @Override
    public void setCustomer(PlayerEntity customer) {
        this.customer = customer;
    }

    @Nullable
    @Override
    public PlayerEntity getCustomer() {
        return this.customer;
    }

    public void stopAnger() {
        this.resetTargets();
        this.attackingPlayer = null;
        this.goalSelector.getRunningGoals().forEach((goal) -> {
            if(goal.getGoal() instanceof RangedAttackGoal)
                goal.resetTask();
        });
        this.targetSelector.getRunningGoals().forEach((goal) -> {
            if(goal.getGoal() instanceof TargetGoal)
                goal.resetTask();
        });
    }

    @Override
    public void setAngerTime(int time) {
        this.dataManager.set(ANGER_TIME, time);
    }

    @Override
    public int getAngerTime() {
        return this.dataManager.get(ANGER_TIME);
    }

    @Override
    public void setAngerTarget(@Nullable UUID target) {
        this.angerTarget = null;
        this.angerTargetUUID = null;
        if(target != null && !this.world.isRemote) {
            Entity entity = ((ServerWorld)this.world).getEntityByUuid(target);
            if(entity instanceof LivingEntity) {
                this.angerTargetUUID = target;
                this.angerTarget = (LivingEntity)((ServerWorld) this.world).getEntityByUuid(target);
            }
        }
    }

    @Nullable
    @Override
    public UUID getAngerTarget() {
        return this.angerTargetUUID;
    }

    @Override
    public void readAdditional(CompoundNBT nbt) {
        super.readAdditional(nbt);
        if(nbt.contains("Traded")) {
            this.dataManager.set(TRADED, nbt.getBoolean("Traded"));
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbt) {
        super.writeAdditional(nbt);
        nbt.putBoolean("Traded", this.dataManager.get(TRADED));
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TRADED, false);
        this.dataManager.register(ANGER_TIME, 0);
    }

    @Override
    public boolean canDespawn(double distanceToClosestPlayer) {
        return !this.dataManager.get(TRADED);
    }

    @Override
    public void func_230258_H__() {
        this.setAngerTime(ANGER_TIME_RANGE.getRandomWithinRange(this.rand));
    }

    @Override
    public TGGroups getGroup() {
        return TGGroups.NEUTRAL;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }
}
