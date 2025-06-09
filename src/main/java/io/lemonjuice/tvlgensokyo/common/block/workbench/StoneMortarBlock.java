package io.lemonjuice.tvlgensokyo.common.block.workbench;

import io.lemonjuice.tvlgensokyo.common.block.tileentity.StoneMortarTileEntity;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StoneMortarBlock extends Block {
    public StoneMortarBlock() {
        super(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(3.5F));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(!(world.getTileEntity(pos) instanceof StoneMortarTileEntity)) {
            return ActionResultType.PASS;
        }

        StoneMortarTileEntity tileEntity = (StoneMortarTileEntity) world.getTileEntity(pos);

        boolean flag = false;
        ItemStack stack = player.getHeldItem(hand);
        if(stack.getItem() == TGItemRegister.PESTLE.get()) {
            tileEntity.tryMake();
            return ActionResultType.func_233537_a_(world.isRemote);
        }

        if(stack.isEmpty() || player.isSneaking()) {
            flag = tileEntity.pickUpItem(player, hand);
        } else if(stack.getItem() instanceof BucketItem) {
            if(((BucketItem) stack.getItem()).getFluid() == Fluids.EMPTY) {
                flag = tileEntity.pickupFluid(player, hand);
            } else {
                flag = tileEntity.putFluid(player, hand);
            }
        } else {
            flag = tileEntity.putItem(player, hand);
        }

        return flag ? ActionResultType.func_233537_a_(world.isRemote) : ActionResultType.PASS;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new StoneMortarTileEntity();
    }
}
