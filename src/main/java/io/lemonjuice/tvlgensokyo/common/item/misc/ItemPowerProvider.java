package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.api.interfaces.IRenderPowerHUD;
import io.lemonjuice.tvlgensokyo.common.container.ContainerPowerProvider;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

@Deprecated
public class ItemPowerProvider extends Item implements IRenderPowerHUD {
    private final int maxPower;
    private final int powerPerTick;

    public ItemPowerProvider(int maxPower, int powerPerTick) {
        super(new Item.Properties().group(TGItemGroups.MISC).maxStackSize(1));
        this.maxPower = maxPower;
        this.powerPerTick = powerPerTick;
    }

    public int getMaxPower() {
        return this.maxPower;
    }

    public int getPowerPerTick() {
        return this.powerPerTick;
    }

    @Override
    public int getPowerCost(ItemStack stack) {
        return 0;
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity playerIn) {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putInt("power", 0);
        stack.setTag(nbt);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return  1.0 - (double) getPowerStorage(stack) / (double) this.maxPower;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.power_provider.storage", getPowerStorage(stack), this.maxPower).mergeStyle(TextFormatting.GREEN));
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.power_provider.speed", this.powerPerTick).mergeStyle(TextFormatting.DARK_AQUA));
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.power_provider.using").mergeStyle(TextFormatting.DARK_GRAY));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote()) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return stack.getDisplayName();
                }

                @Nullable
                @Override
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    return new ContainerPowerProvider(p_createMenu_1_, p_createMenu_3_, hand == Hand.MAIN_HAND);
                }
            }, buffer -> {
                buffer.writeBoolean(hand == Hand.MAIN_HAND);
            });
        }
        return ActionResult.resultPass(stack);
    }

    public static boolean injectPower(ItemStack stack, int power) {
        if(stack.getItem() instanceof ItemPowerProvider) {
            ItemPowerProvider item = (ItemPowerProvider) stack.getItem();
            int storage = getPowerStorage(stack);
            if(storage + power <= item.maxPower && storage + power >= 0) {
                CompoundNBT nbt = stack.getOrCreateTag();
                nbt.putInt("power", storage + power);
                stack.setTag(nbt);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static int getPowerStorage(ItemStack stack) {
        CompoundNBT nbt;
        if(stack.getTag() == null) {
            nbt = stack.getOrCreateTag();
            nbt.putInt("power", 0);
            stack.setTag(nbt);
            return 0;
        } else {
            nbt = stack.getTag();
            return nbt.getInt("power");
        }
    }
}
