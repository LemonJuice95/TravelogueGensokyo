package io.lemonjuice.tvlgensokyo.common.container;

import io.lemonjuice.tvlgensokyo.common.item.misc.PowerPointItem;
import io.lemonjuice.tvlgensokyo.common.item.misc.PowerProviderItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

@Deprecated
public class PowerProviderContainer extends TGContainerBase {
    private PlayerEntity player;
    private ItemStack stack;
    private Hand hand;
    private Inventory input = new Inventory(1);

    public PowerProviderContainer(int id, PlayerEntity player, boolean hand) { //True:MainHand False:OffHand
        super(TGContainerRegister.POWER_PROVIDER.get(), id);
        this.player = player;
        this.hand = hand ? Hand.MAIN_HAND : Hand.OFF_HAND;
        this.stack = player.getHeldItem(this.hand);
        int selectedSlot = player.inventory.currentItem;


        this.addSlot(new Slot(input, 0, 80, 20) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof PowerPointItem;
            }
        });
        this.addPlayersInventory(player, 8, 77);
        if (hand) {
            this.inventorySlots.set(28 + selectedSlot, new Slot(player.inventory, selectedSlot, 18 * selectedSlot + 8, 135) {
                @Override
                public boolean canTakeStack(PlayerEntity p_82869_1_) {
                    return false;
                }
            });
        }
    }

    public void onPowerInject() {
        Slot slot = this.getSlot(0);
        ItemStack stack = slot.getStack();
        while(!stack.isEmpty() && PowerProviderItem.injectPower(this.stack, 50) && stack.getItem() instanceof PowerPointItem) {
            stack.shrink(1);
        }
        this.putStackInSlot(0, stack);
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return player.getHeldItem(hand).getItem() instanceof PowerProviderItem;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        Slot slot = this.getSlot(0);
        PlayerInventory playerinventory =player.inventory;
        if (!playerinventory.getItemStack().isEmpty()) {
            player.addItemStackToInventory(playerinventory.getItemStack());
            playerinventory.setItemStack(ItemStack.EMPTY);
        }
        if(slot.getHasStack()) {
            player.addItemStackToInventory(slot.getStack());
            slot.putStack(ItemStack.EMPTY);
            this.inventorySlots.set(0, slot);
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if(slot.getHasStack()) {
            ItemStack stack_ = slot.getStack();
            stack = stack_.copy();
            if(index == 0) {
                if(!mergeItemStack(stack_, 1, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if(index <= 27) {
                if(this.inventorySlots.get(0).isItemValid(stack_)) {
                    if (!mergeItemStack(stack_, 0, 1, false) &&
                            !mergeItemStack(stack_, 28, this.inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(!mergeItemStack(stack_, 28, this.inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if(this.inventorySlots.get(0).isItemValid(stack_)) {
                    if(!mergeItemStack(stack_, 0, 1, false) &&
                       !mergeItemStack(stack_, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(!mergeItemStack(stack_, 1, 28, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            if(stack_.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return stack;
    }

}
