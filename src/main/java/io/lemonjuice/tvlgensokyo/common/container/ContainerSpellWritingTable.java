package io.lemonjuice.tvlgensokyo.common.container;

import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.container.slot.SpellWritingResultSlot;
import io.lemonjuice.tvlgensokyo.common.inventory.SpellWritingInventory;
import io.lemonjuice.tvlgensokyo.common.item.crafting.SpellWritingRecipe;
import io.lemonjuice.tvlgensokyo.common.item.crafting.TGRecipeRegister;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

import java.util.Optional;

public class ContainerSpellWritingTable extends TGContainerBase {

    private final SpellWritingInventory craftMatrix = new SpellWritingInventory(this);
    private final CraftResultInventory craftResult = new CraftResultInventory();
    private final IWorldPosCallable worldPosCallable;
    private final PlayerEntity player;


    public ContainerSpellWritingTable(int id, PlayerEntity player, IWorldPosCallable worldPosCallable) {
        super(TGContainerRegister.SPELL_WRITING_TABLE.get(), id);
        this.worldPosCallable = worldPosCallable;
        this.player = player;

        this.addSlot(new SpellWritingResultSlot(this.player, this.craftMatrix, this.craftResult, 0, 148, 60));
        this.addSlot(new Slot(this.craftMatrix, 0, 56, 60) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return stack.getItem().equals(Items.PAPER);
            }
        });

        this.addSlot(new Slot(this.craftMatrix, 1, 56, 16));
        this.addSlot(new Slot(this.craftMatrix, 2, 98, 39));
        this.addSlot(new Slot(this.craftMatrix, 3, 98, 81));
        this.addSlot(new Slot(this.craftMatrix, 4, 56, 103));
        this.addSlot(new Slot(this.craftMatrix, 5, 13, 81));
        this.addSlot(new Slot(this.craftMatrix, 6, 13, 39));

        this.addPlayersInventory(player, 8, 133);
    }

    protected static void updateCraftingResult(int id, World world, PlayerEntity player, SpellWritingInventory inventory, CraftResultInventory inventoryResult) {
        if (!world.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<SpellWritingRecipe> optional = world.getServer().getRecipeManager().getRecipe(TGRecipeRegister.SPELL_WRITING_TYPE, inventory, world);
            if (optional.isPresent()) {
                SpellWritingRecipe recipe = optional.get();
                if (inventoryResult.canUseRecipe(world, serverplayerentity, recipe)) {
                    itemstack = recipe.getCraftingResult(inventory);
                }
            }

            inventoryResult.setInventorySlotContents(0, itemstack);
            serverplayerentity.connection.sendPacket(new SSetSlotPacket(id, 0, itemstack));
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.worldPosCallable.consume((world, pos) -> {
            updateCraftingResult(this.windowId, world, this.player, this.craftMatrix, this.craftResult);
        });
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.worldPosCallable.consume((p_217068_2_, p_217068_3_) -> {
            this.clearContainer(playerIn, p_217068_2_, this.craftMatrix);
        });
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.worldPosCallable.consume((world, pos) -> {
                    itemstack1.getItem().onCreated(itemstack1, world, playerIn);
                });
                if (!this.mergeItemStack(itemstack1, 8, 44, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index >= 8 && index < 44) {
                int startIndex1 = itemstack1.getItem().equals(Items.PAPER) ? 1 : 2;
                if (!this.mergeItemStack(itemstack1, startIndex1, 8, false)) {
                    if (index < 35) {
                        if (!this.mergeItemStack(itemstack1, 35, 44, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.mergeItemStack(itemstack1, 8, 35, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemstack1, 8, 44, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);
            if (index == 0) {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return isWithinUsableDistance(this.worldPosCallable, player, TGBlockRegister.SPELL_WRITING_TABLE.get());
    }
}
