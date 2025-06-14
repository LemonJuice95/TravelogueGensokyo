package io.lemonjuice.tvlgensokyo.common.block.tileentity;

import io.lemonjuice.tvlgensokyo.common.item.crafting.PoundingRecipe;
import io.lemonjuice.tvlgensokyo.common.item.crafting.TGRecipeRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StoneMortarTileEntity extends TileEntity implements IInventory {
    public static final List<Fluid> ALLOWED_FLUID = new ArrayList<>();

    static {
        ALLOWED_FLUID.add(Fluids.EMPTY);
        ALLOWED_FLUID.add(Fluids.WATER);
    }

    private ItemStack itemStack;
    private Fluid fluid;

    public StoneMortarTileEntity() {
        super(TGTileEntityRegister.STONE_MORTAR.get());
        this.itemStack = ItemStack.EMPTY;
        this.fluid = Fluids.EMPTY;
    }

    public void tryMake() {
        if (this.world != null) {
            Optional<PoundingRecipe> recipe = this.world.getRecipeManager().getRecipe(TGRecipeRegister.POUNDING_TYPE, this, this.world);
            if(recipe.isPresent()) {
                this.itemStack.shrink(1);
                this.setFluid(Fluids.EMPTY);
                if(!this.world.isRemote) {
                    ItemEntity itemEntity = new ItemEntity(this.world, this.getPos().getX(), this.getPos().getY() + 0.1, this.getPos().getZ(), recipe.get().getCraftingResult(this));
                    Random random = new Random();
                    double d1 = random.nextFloat() * 0.5F;
                    float f1 = random.nextFloat() * ((float)Math.PI * 2F);
                    itemEntity.setMotion(-MathHelper.sin(f1) * d1, 0.2, MathHelper.cos(f1) * d1);
                    this.world.addEntity(itemEntity);
                }
            }
        }
        this.markDirty();
    }

    public boolean putItem(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(this.itemStack.isEmpty()) {
            this.setHoldingStack(stack);
            player.setHeldItem(hand, ItemStack.EMPTY);
            return true;
        } else if(this.itemStack.getItem() == stack.getItem() && ItemStack.areItemStackTagsEqual(this.itemStack, stack) && this.itemStack.getCount() < this.itemStack.getMaxStackSize()) {
            int count1 = this.itemStack.getCount();
            int count2 = stack.getCount();
            int maxStackSize = this.itemStack.getMaxStackSize();
            if(count1 + count2 <= maxStackSize) {
                this.itemStack.setCount(count1 + count2);
                player.setHeldItem(hand, ItemStack.EMPTY);
            } else {
                this.itemStack.setCount(maxStackSize);
                player.getHeldItem(hand).setCount(count1 + count2 - maxStackSize);
            }
            this.markDirty();
            return true;
        }

        return false;
    }

    public boolean pickUpItem(PlayerEntity player, Hand hand) {
        if(this.itemStack.isEmpty()) {
            return false;
        }

        if(player.getHeldItem(hand).isEmpty()) {
            player.setHeldItem(hand, this.itemStack);
        } else {
            player.inventory.placeItemBackInInventory(player.world, this.itemStack);
        }

        this.setHoldingStack(ItemStack.EMPTY);
        return true;
    }

    public boolean putFluid(PlayerEntity player, Hand hand) {
        if(this.fluid != Fluids.EMPTY) {
            return false;
        }

        ItemStack stack = player.getHeldItem(hand);
        if(stack.getItem() instanceof BucketItem) {
            Fluid fluid = ((BucketItem) stack.getItem()).getFluid();
            if(fluid != Fluids.EMPTY && this.setFluid(fluid)) {
                ((BucketItem)stack.getItem()).onLiquidPlaced(player.world, stack, this.getPos());
                player.setHeldItem(hand, ((BucketItem)stack.getItem()).emptyBucket(stack, player));
                return true;
            }
        }
        return false;
    }

    public boolean pickupFluid(PlayerEntity player, Hand hand) {
        if(this.fluid == Fluids.EMPTY) {
            return false;
        }

        ItemStack stack = player.getHeldItem(hand);
        if(stack.getItem() instanceof BucketItem) {
            Fluid fluid = ((BucketItem) stack.getItem()).getFluid();
            if(fluid == Fluids.EMPTY) {
                if (stack.getCount() == 1) {
                    player.setHeldItem(hand, new ItemStack(this.fluid.getFilledBucket()));
                } else {
                    stack.shrink(1);
                    player.addItemStackToInventory(new ItemStack(this.fluid.getFilledBucket()));
                }
                this.setFluid(Fluids.EMPTY);
                return true;
            }
        }
        return false;
    }

    public Fluid getFluid() {
        return this.fluid;
    }

    public boolean setFluid(Fluid fluid) {
        if(ALLOWED_FLUID.contains(fluid)) {
            this.fluid = fluid;
            this.markDirty();
            return true;
        }
        return false;
    }

    public ItemStack getHoldingStack() {
        return this.itemStack;
    }

    public void setHoldingStack(ItemStack stack) {
        this.itemStack = stack;
        this.markDirty();
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        this.itemStack = ItemStack.read(nbt.getCompound("Item"));
        this.fluid = Registry.FLUID.getOrDefault(new ResourceLocation(nbt.getString("Fluid")));
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.put("Item", this.itemStack.write(new CompoundNBT()));
        nbt.putString("Fluid", Registry.FLUID.getKey(this.fluid).toString());
        return super.write(nbt);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.handleUpdateTag(this.getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public int getSizeInventory() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.itemStack.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if(index == 0) {
            return this.itemStack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        if(index == 0 && count > 0 && !this.itemStack.isEmpty()) {
            ItemStack stack = this.itemStack.split(count);
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
        if(index == 0) {
            setHoldingStack(stack);
        }
        this.markDirty();
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return false;
    }

    @Override
    public void clear() {
        this.setHoldingStack(ItemStack.EMPTY);
    }
}
