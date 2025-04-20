package io.lemonjuice.tvlgensokyo.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SpellWritingInventory implements IInventory {
    private final NonNullList<ItemStack> rimStacks;
    private ItemStack paperStack;
    private final Container eventHandler;

    public SpellWritingInventory(Container eventHandler) {
        this.rimStacks = NonNullList.withSize(6, ItemStack.EMPTY);
        this.paperStack = ItemStack.EMPTY;
        this.eventHandler = eventHandler;
    }

    @Override
    public int getSizeInventory() {
        return this.rimStacks.size() + 1;
    }

    @Override
    public boolean isEmpty() {
        if(!this.paperStack.isEmpty())
            return false;
        for(ItemStack i : this.rimStacks)
            if(!i.isEmpty())
                return false;
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if(index == 0)
            return this.paperStack;
        return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.rimStacks.get(index - 1);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = this.getStackInSlot(index);
        if(stack.isEmpty())
            return ItemStack.EMPTY;
        else {
            this.setInventorySlotContents(index, ItemStack.EMPTY);
            return stack;
        }
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        ItemStack itemstack;
        if(index == 0)
            itemstack = this.paperStack.split(count);
        else
            itemstack = ItemStackHelper.getAndSplit(this.rimStacks, index - 1, count);
        if (!itemstack.isEmpty())
            this.eventHandler.onCraftMatrixChanged(this);
        return itemstack;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if(index == 0)
            this.paperStack = stack;
        else if(index < this.getSizeInventory())
            this.rimStacks.set(index - 1, stack);

        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return true;
    }

    @Override
    public void clear() {
        this.rimStacks.clear();
        this.paperStack = ItemStack.EMPTY;
    }
}
