package io.lemonjuice.tvlgensokyo.common.block.tileentity;

import io.lemonjuice.tvlgensokyo.common.container.ContainerSpellBookBindingTable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;

public class SpellBookBindingTableTileEntity extends LockableTileEntity {
    private ItemStack spellBook;

    public SpellBookBindingTableTileEntity() {
        super(TGTileEntityRegister.SPELL_BOOK_BINDING_TABLE.get());
        spellBook = ItemStack.EMPTY;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("block.tvlgensokyo.spell_book_binding_table");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new ContainerSpellBookBindingTable(id, playerInventory.player, this);
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.spellBook.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if(index == 0)
            return this.spellBook;
        else
            return ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(index == 0 && count > 0 && !this.spellBook.isEmpty()) {
            ItemStack stack = this.spellBook.split(count);
            this.markDirty();
            return stack;
        }
        return ItemStack.EMPTY;
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
    public void setInventorySlotContents(int index, ItemStack stack) {
        if(index == 0)
            this.spellBook = stack;
        /*if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }*/

        this.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if (this.world.getTileEntity(this.pos) != this) {
            return false;
        } else {
            return !(player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) > 64.0D);
        }
    }

    @Override
    public void clear() {
        this.spellBook = ItemStack.EMPTY;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
        return new ContainerSpellBookBindingTable(id, player, this);
    }

    @Override
    public boolean receiveClientEvent(int id, int type) {
        return super.receiveClientEvent(id, type);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.spellBook = ItemStack.read(nbt.getCompound("SpellBook"));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("SpellBook", this.spellBook.write(new CompoundNBT()));
        return super.write(compound);
    }

}