package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.entity.misc.EntityWind;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

public class ItemAyasFan extends Item implements IRenderPowerHUD {
    public ItemAyasFan() {
        super(new Item.Properties().group(TGItemGroups.MISC).maxStackSize(1));
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return 250;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if(TGCapabilityUtils.getPower(player) >= TGMathUtils.calculatePowerCost(this.getPowerCost(stack), player, stack) || player.isCreative()) {
            float pitch = player.rotationPitch;
            float yaw = player.rotationYaw;

            double motionX = 1.5 * -MathHelper.sin(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
            double motionY = 1.5 * -MathHelper.sin(pitch * ((float) Math.PI / 180F));
            double motionZ = 1.5 * MathHelper.cos(yaw * ((float) Math.PI / 180F)) * MathHelper.cos(pitch * ((float) Math.PI / 180F));
            Vector3d motionVec = new Vector3d(motionX, motionY, motionZ);

            if (!world.isRemote) {
                player.getCooldownTracker().setCooldown(this, 20);
                EntityWind wind = new EntityWind(world, player);
                wind.setPosition(player.getPosX(), player.getPosY() + 0.5, player.getPosZ());
                wind.setMotion(motionVec);
                wind.setTicksLeft(25);
                world.addEntity(wind);
                if(!player.isCreative())
                    TGCapabilityUtils.addPower(player, -TGMathUtils.calculatePowerCost(this.getPowerCost(stack), player, stack));
            } else {
                double x = player.getPosX();
                double y = player.getPosY() + 1.0;
                double z = player.getPosZ();

                Random random = new Random();
                for (int i = 1; i <= 9; i++) {
                    world.addParticle(ParticleTypes.CLOUD, x + random.nextInt(2) - 1.5 + random.nextDouble(), y + random.nextInt(2) - 1.5 + random.nextDouble(), z + random.nextInt(2) - 1.5 + random.nextDouble(), motionX, motionY, motionZ);
                }
            }
            return ActionResult.resultSuccess(stack);
        } else {
            return ActionResult.resultFail(stack);
        }
    }
}
