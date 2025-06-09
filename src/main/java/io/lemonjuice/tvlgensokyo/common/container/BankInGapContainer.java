package io.lemonjuice.tvlgensokyo.common.container;

import io.lemonjuice.tvlgensokyo.common.item.misc.BankInGapItem;
import io.lemonjuice.tvlgensokyo.common.item.misc.CurrencyItem;
import io.lemonjuice.tvlgensokyo.common.misc.CurrencyType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.IntReferenceHolder;

public class BankInGapContainer extends TGContainerBase {
    private final PlayerEntity player;
    private final ItemStack stack;
    private final Hand hand;
    private final Inventory input = new Inventory(1);
    private final IntReferenceHolder balance = IntReferenceHolder.single();

    public BankInGapContainer(int id, PlayerEntity player, Hand hand) {
        super(TGContainerRegister.BANK_IN_GAP.get(), id);
        this.player = player;
        this.stack = player.getHeldItem(hand);
        this.hand = hand;
        this.balance.set(BankInGapItem.getBalance(this.stack));

        this.trackInt(this.balance);

        this.addSlot(new Slot(this.input, 0, 26, 41) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof CurrencyItem;
            }

            @Override
            public void onSlotChanged() {
                if(this.getStack().getItem() instanceof CurrencyItem) {
                    depositCurrency(((CurrencyItem) this.getStack().getItem()), this.getStack().getCount());
                    this.getStack().setCount(0);
                }
                super.onSlotChanged();
            }
        });

        this.addPlayersInventory(player, 8, 99);

        if (hand == Hand.MAIN_HAND) {
            int selectedSlot = player.inventory.currentItem;
            this.inventorySlots.set(28 + selectedSlot, new Slot(player.inventory, selectedSlot, 18 * selectedSlot + 8, 157) {
                @Override
                public boolean canTakeStack(PlayerEntity player) {
                    return false;
                }
            });
        }
    }

    public int getBalance() {
        return this.balance.get();
    }

    public void depositCurrency(CurrencyItem item, int count) {
        BankInGapItem.addBalance(this.stack, item.value * count);
        this.balance.set(BankInGapItem.getBalance(this.stack));
        this.detectAndSendChanges();
    }

    public void withdrawCurrency(PlayerEntity player, CurrencyType type, int count) {
        CurrencyItem item = type.currencyItem;
        int value = item.value * count;
        if(BankInGapItem.getBalance(this.stack) >= value) {
            if(!player.world.isRemote) {
                if (!player.isAlive() || player instanceof ServerPlayerEntity && ((ServerPlayerEntity) player).hasDisconnected()) {
                    player.dropItem(new ItemStack(item, count), false);
                } else {
                    player.inventory.placeItemBackInInventory(player.world, new ItemStack(item, count));
                }
            }

            BankInGapItem.addBalance(this.stack, -value);
            this.balance.set(BankInGapItem.getBalance(this.stack));
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        super.onContainerClosed(player);
        this.clearContainer(player, player.world, this.input);
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return player.getHeldItem(this.hand).getItem() instanceof BankInGapItem;
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
