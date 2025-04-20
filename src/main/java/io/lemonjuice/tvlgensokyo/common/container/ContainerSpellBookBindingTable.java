package io.lemonjuice.tvlgensokyo.common.container;

import io.lemonjuice.tvlgensokyo.common.item.weapon.ItemSpellBook;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSpellBookBindingTable extends TGContainerBase{

    private PlayerEntity player;
    private IInventory spellBook;
    private int playerInvStartIndex;

    public ContainerSpellBookBindingTable(int id, PlayerEntity player, IInventory inventory) {
        super(TGContainerRegister.SPELL_BOOK_BINDING_TABLE.get(), id);
        this.player = player;
        this.spellBook = inventory;
        this.refreshSlots();
    }

    public void refreshSlots() {
        this.inventorySlots.clear();
        this.inventoryItemStacks.clear();

        this.addSlot(new Slot(this.spellBook, 0, 26, 36) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() instanceof ItemSpellBook;
            }

            @Override
            public void onSlotChanged() {
                super.onSlotChanged();
                refreshSlots();
            }

            @Override
            public int getItemStackLimit(ItemStack stack) {
                return 1;
            }
        });

        this.playerInvStartIndex = 1;

        ItemStack stack = this.spellBook.getStackInSlot(0);
        if(!stack.isEmpty() && stack.getItem() instanceof ItemSpellBook) {
            int slotCount = ((ItemSpellBook) stack.getItem()).slotCount;
            if(slotCount <= 4) {
                for(int i = 0; i < slotCount; i++) {
                    this.addSlot(new Slot(((ItemSpellBook) stack.getItem()).getInventory(stack), i, 87 + i / 2 * 27, i % 2 == 0 ? 27 : 45) {
                        @Override
                        public int getItemStackLimit(ItemStack p_178170_1_) {
                            return 1;
                        }
                    });
                    this.playerInvStartIndex++;
                }
            } else {
                for(int i = 0; i < 4; i++) {
                    this.addSlot(new Slot(((ItemSpellBook) stack.getItem()).getInventory(stack), i, 69 + i / 2 * 18, i % 2 == 0 ? 27 : 45) {
                        @Override
                        public int getItemStackLimit(ItemStack p_178170_1_) {
                            return 1;
                        }
                    });
                    this.playerInvStartIndex++;
                }

                for(int i = 4; i < slotCount; i++) {
                    this.addSlot(new Slot(((ItemSpellBook) stack.getItem()).getInventory(stack), i, 114 + (i - 4) / 2 * 18, i % 2 == 0 ? 27 : 45) {
                        @Override
                        public int getItemStackLimit(ItemStack p_178170_1_) {
                            return 1;
                        }
                    });
                    this.playerInvStartIndex++;
                }
            }
        }

        this.addPlayersInventory(player, 8, 88);
    }

    public int getSpellPageSlotCount() {
        Slot slot = this.getSlot(0);
        if(slot.getHasStack() && slot.getStack().getItem() instanceof ItemSpellBook) {
            return ((ItemSpellBook)slot.getStack().getItem()).slotCount;
        } else {
            return 0;
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.getSlot(index);
        if(slot.getHasStack()) {
            ItemStack stack_ = slot.getStack();
            stack = stack_.copy();

            if (index < this.playerInvStartIndex) {
                if (!mergeItemStack(stack_, this.playerInvStartIndex, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if(index < this.playerInvStartIndex + 27) {
                if(this.inventorySlots.get(0).isItemValid(stack_)) {
                    if(!mergeItemStack(stack_, 0, 1, false) && !mergeItemStack(stack_, this.playerInvStartIndex + 27, this.inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(this.playerInvStartIndex > 1 && this.inventorySlots.get(1).isItemValid(stack_)){
                    if(!mergeItemStack(stack_, 1, this.playerInvStartIndex, false) && !mergeItemStack(stack_, this.playerInvStartIndex + 27, this.inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(!mergeItemStack(stack_, this.playerInvStartIndex + 27, this.inventorySlots.size(), false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else {
                if(this.inventorySlots.get(0).isItemValid(stack_)) {
                    if(!mergeItemStack(stack_, 0, 1, false) && !mergeItemStack(stack_, this.playerInvStartIndex, this.playerInvStartIndex + 27,false)) {
                        return ItemStack.EMPTY;
                    }
                } else if(this.playerInvStartIndex > 1 && this.inventorySlots.get(1).isItemValid(stack_)){
                    if(!mergeItemStack(stack_, 1, this.playerInvStartIndex, false) && !mergeItemStack(stack_, this.playerInvStartIndex, this.playerInvStartIndex + 27, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if(!mergeItemStack(stack_, this.playerInvStartIndex, this.playerInvStartIndex + 27, false)) {
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

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.spellBook.isUsableByPlayer(playerIn);
    }
}
