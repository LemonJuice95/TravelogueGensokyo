package io.lemonjuice.tvlgensokyo.common.events;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.weapon.ItemLaevatein;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents {
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if(entity instanceof MonsterEntity && !entity.world.isRemote) {
            if(event.getSource().getTrueSource() instanceof PlayerEntity) {
                Random random = new Random();
                ItemEntity item = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(TGItemRegister.POWER_POINT.get(), random.nextInt() % 3 + 1));
                entity.world.addEntity(item);
            } else {
                Random random = new Random();
                if(random.nextInt() % 2 == 0) {
                    ItemEntity item = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(TGItemRegister.POWER_POINT.get()));
                    entity.world.addEntity(item);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity source = event.getSource().getTrueSource();
        if(source instanceof PlayerEntity) {
            ItemStack stack = ((PlayerEntity) source).getHeldItemMainhand();
            if(!source.world.isRemote && stack.getItem() instanceof ItemLaevatein && ItemLaevatein.isActivated(stack)) {
                LivingEntity target = event.getEntityLiving();
                target.setFire(15);

                float f2 = 3.5F;
                int k1 = MathHelper.floor(target.getPosX() - (double)f2);
                int l1 = MathHelper.floor(target.getPosX() + (double)f2);
                int i2 = MathHelper.floor(target.getPosY() - 2.5D);
                int i1 = MathHelper.floor(target.getPosY() + 2.5D);
                int j2 = MathHelper.floor(target.getPosZ() - (double)f2);
                int j1 = MathHelper.floor(target.getPosZ() + (double)f2);
                List<Entity> list = target.world.getEntitiesWithinAABBExcludingEntity(target, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
                for(Entity entity : list) {
                    if(entity instanceof LivingEntity) {
                        if(!(entity instanceof PlayerEntity) && !(entity instanceof TameableEntity && ((TameableEntity) entity).isTamed())) {
                            entity.setFire(15);
                            entity.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) source).setDamageBypassesArmor(), event.getAmount());
                        }
                    }
                }
            }
        }
    }

}
