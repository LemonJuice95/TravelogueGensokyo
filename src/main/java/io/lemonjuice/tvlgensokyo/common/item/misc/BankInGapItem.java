package io.lemonjuice.tvlgensokyo.common.item.misc;

import io.lemonjuice.tvlgensokyo.common.container.BankInGapContainer;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class BankInGapItem extends Item {
    public BankInGapItem() {
        super(new Item.Properties().group(TGItemGroups.MISC).maxStackSize(1));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(!world.isRemote) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return stack.getDisplayName();
                }

                @Nullable
                @Override
                public Container createMenu(int windowId, PlayerInventory inventory, PlayerEntity playerIn) {
                    return new BankInGapContainer(windowId, inventory.player, hand);
                }
            }, buffer -> {
                buffer.writeInt(hand.ordinal());
            });
        }

        return ActionResult.func_233538_a_(stack, world.isRemote);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new TranslationTextComponent("tooltip.tvlgensokyo.balance", getBalance(stack)));
    }

    public static int getBalance(ItemStack stack) {
        if(stack.getItem() instanceof BankInGapItem) {
            if(stack.getOrCreateTag().contains("Balance")) {
                return stack.getOrCreateTag().getInt("Balance");
            }
            stack.getOrCreateTag().putInt("Balance", 0);
        }
        return 0;
    }

    public static void setBalance(ItemStack stack, int balance) {
        if(stack.getItem() instanceof BankInGapItem) {
            stack.getOrCreateTag().putInt("Balance", balance);
        }
    }

    public static void addBalance(ItemStack stack, int balance) {
        if(stack.getItem() instanceof BankInGapItem) {
            stack.getOrCreateTag().putInt("Balance", getBalance(stack) + balance);
        }
    }
}
