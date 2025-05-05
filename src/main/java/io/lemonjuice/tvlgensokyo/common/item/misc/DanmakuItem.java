package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import io.lemonjuice.tvlgensokyo.common.entity.api.IHasGroup;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.projectile.EntityDanmaku;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import static net.minecraft.entity.Entity.horizontalMag;

public class DanmakuItem extends Item implements IRenderPowerHUD, IScrollable {
    private final Danmaku.Type type;

    public DanmakuItem(Danmaku.Type type) {
        super(new Item.Properties().group(TGItemGroups.COMBAT).maxStackSize(9));
        this.type = type;
    }

    @Override
    public void specialScroll(ItemStack stack, double direction) {
        if(direction > 0.0D) {
            direction = 1.0D;
        } else if(direction < 0.0D) {
            direction = -1.0D;
        }


        CompoundNBT nbt = stack.getOrCreateTag();
        Danmaku.Color color = Danmaku.Color.values()[nbt.getInt("Color")];
        int a = color.ordinal() + (int)direction;
        while(a >= Danmaku.Color.values().length)
            a -= Danmaku.Color.values().length;
        while(a < 0)
            a += Danmaku.Color.values().length;
        color = Danmaku.Color.values()[a];
        nbt.putInt("Color", color.ordinal());
        stack.write(nbt);
    }

    public static Danmaku.Color getColor(ItemStack stack) {
        try {
            int a = stack.getOrCreateTag().getInt("Color");
            if (a >= Danmaku.Color.values().length || a < 0)
                return Danmaku.Color.WHITE;
            return Danmaku.Color.values()[a];
        } catch (NullPointerException e) {
            return Danmaku.Color.WHITE;
        }
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return stack.getCount() * 10;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(TGCapabilityUtils.getPower(player) >= TGMathUtils.calculatePowerCost(this.getPowerCost(stack), player, stack) || player.isCreative()) {
            player.getCooldownTracker().setCooldown(this, 10);
            if(stack.getCount() % 2 != 0) {
                int c = (stack.getCount() - 1) / 2;
                Danmaku danmaku = new Danmaku(this.type, getColor(stack), 120, 5.0F);
                for(int i = -c; i <= c; i++) {
                    this.shootDanmaku(danmaku, world, i, player, 12.0F);
                }
            } else {
                int c = stack.getCount() - 1;
                Danmaku danmaku = new Danmaku(this.type, getColor(stack), 120, 5.0F);
                for(int i = -c; i <= c; i++) {
                    if(i % 2 != 0) {
                        this.shootDanmaku(danmaku, world, i, player, 6.0F);
                    }
                }
            }
            if(!player.isCreative())
                TGCapabilityUtils.addPower(player, -TGMathUtils.calculatePowerCost(this.getPowerCost(stack), player, stack));
            return ActionResult.resultSuccess(stack);
        }

        return ActionResult.resultFail(stack);
    }

    private void shootDanmaku(Danmaku danmaku, World world, int i, PlayerEntity player, float rotation) {
        EntityDanmaku danmakuEntity = new EntityDanmaku(world, danmaku, IHasGroup.TGGroups.FRIENDLY, player);
        Vector3d vector3d1 = player.getUpVector(1.0F);
        Quaternion quaternion = new Quaternion(new Vector3f(vector3d1), i * rotation, true);
        Vector3d vector3d = player.getLook(1.0F);
        Vector3f vector3f = new Vector3f(vector3d);
        vector3f.transform(quaternion);
        Vector3d motion = new Vector3d(vector3f.getX(), vector3f.getY(), vector3f.getZ()).normalize().scale(0.6);
        danmakuEntity.setMotion(motion);
        danmakuEntity.setPosition(player.getPosX(), player.getPosY() + player.getEyeHeight(), player.getPosZ());
        float f = MathHelper.sqrt(horizontalMag(vector3d));
        danmakuEntity.rotationYaw = -(float)(MathHelper.atan2(motion.x, motion.z) * (double)(180F / (float)Math.PI));
        danmakuEntity.rotationPitch = -(float)(MathHelper.atan2(motion.y, f) * (double)(180F / (float)Math.PI));
        world.addEntity(danmakuEntity);
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt("Color", Danmaku.Color.WHITE.ordinal());
        stack.setTag(nbt);
    }
}
