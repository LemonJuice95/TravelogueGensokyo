package io.lemonjuice.tvlgensokyo.common.item.weapon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGMathUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import javax.annotation.Nullable;
import java.util.List;

public class ItemLaevatein extends TGCombatWeaponBase implements IRenderPowerHUD {

    public ItemLaevatein() {
        super(11, -3.2F);
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return 400;
    }

    @Override
    public void onCreated(ItemStack stack, World world, PlayerEntity player) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putBoolean("Activated", false);
        stack.setTag(nbt);
    }

    /**
     * More handling operations moved into {@link io.lemonjuice.tvlgensokyo.common.events.EntityEvents#onLivingHurt(LivingHurtEvent)}
     */
    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        CompoundNBT nbt = stack.getOrCreateTag();
        boolean isActivated = nbt.getBoolean("Activated");

        if(attacker instanceof PlayerEntity && isActivated /*&& ((PlayerEntity)attacker).getCooledAttackStrength(0.0F) >= 1.0F*/) {
            if(!attacker.world.isRemote) {
                if(!((PlayerEntity) attacker).isCreative())
                    TGCapabilityUtils.addPower((PlayerEntity) attacker, -TGMathUtils.calculatePowerCost(this.getPowerCost(stack), (PlayerEntity) attacker, stack));
                target.setFire(15);
            }
        }
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        boolean flag = false;
        ItemStack stackRaw = player.getHeldItem(hand);
        if(isActivated(stackRaw)) {
            flag = true;
        } else {
            if(player.isCreative() || TGCapabilityUtils.getPower(player) >= TGMathUtils.calculatePowerCost(this.getPowerCost(stackRaw), player, stackRaw)) {
                flag = true;
            }
        }

        if(flag) {
            ItemStack stack = this.activateStatSwitch(stackRaw);
            player.setHeldItem(hand, stack);
            return ActionResult.resultSuccess(stack);
        } else {
            return ActionResult.resultFail(stackRaw);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if(entity instanceof PlayerEntity && entity.isAlive() && TGCapabilityUtils.getPower(((PlayerEntity) entity)) < TGMathUtils.calculatePowerCost(this.getPowerCost(stack), (PlayerEntity) entity, stack) && isActivated(stack) && !((PlayerEntity)entity).isCreative()) {
            ItemStack stack_ = this.activateStatSwitch(stack);
            entity.replaceItemInInventory(itemSlot, stack_);
        }
    }

    public static boolean isActivated(ItemStack stack) {
        if(!stack.hasTag()) {
            CompoundNBT nbt = stack.getOrCreateTag();
            nbt.putBoolean("Activated", false);
            stack.write(nbt);
            return false;
        } else {
            CompoundNBT nbt = stack.getTag();
            assert nbt != null;
            return nbt.getBoolean("Activated");
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        if(isActivated(stack)) {
            tooltip.add(new StringTextComponent(""));
            tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.activated").mergeStyle(TextFormatting.GOLD));
        }
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributesModifiers = super.getAttributeModifiers(slot);
        if(isActivated(stack) && slot == EquipmentSlotType.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
            attributesModifiers.forEach((key, value) -> {
                if(key == Attributes.ATTACK_DAMAGE) {
                    builder.put(key, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon Modifier", 1.2 * value.getAmount(), AttributeModifier.Operation.ADDITION));
                }
                else {
                    builder.put(key, value);
                }
            });
            return builder.build();
        }
        return attributesModifiers;
    }

    private ItemStack activateStatSwitch(ItemStack stack) {
        CompoundNBT nbt = stack.getOrCreateTag();
        boolean oldStat = isActivated(stack);
        nbt.putBoolean("Activated", !oldStat);
        stack.setTag(nbt);
        return stack;
    }

}
