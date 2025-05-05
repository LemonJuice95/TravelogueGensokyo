package io.lemonjuice.tvlgensokyo.common.block;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;
import java.util.function.BiFunction;

public class PinkPetalsBlock extends FallenLeavesBlock implements IGrowable {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = IntegerProperty.create("flower_amount", 1, 4);

    private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = (direction, amount) -> {
        VoxelShape[] avoxelshape = new VoxelShape[]{
                makeCuboidShape(8.0, 0.0, 8.0, 16.0, 3.0, 16.0),
                makeCuboidShape(8.0, 0.0, 0.0, 16.0, 3.0, 8.0),
                makeCuboidShape(0.0, 0.0, 0.0, 8.0, 3.0, 8.0),
                makeCuboidShape(0.0, 0.0, 8.0, 8.0, 3.0, 16.0)
        };

        VoxelShape voxelshape = VoxelShapes.empty();

        for(int i = 0; i < amount; i++) {
            int j = Math.floorMod(i - direction.getHorizontalIndex(), 4);
            voxelshape = VoxelShapes.or(voxelshape, avoxelshape[j]);
        }
        return voxelshape.simplify();
    };


    public PinkPetalsBlock() {
        super(AbstractBlock.Properties.create(Material.LEAVES)
                .hardnessAndResistance(0.2F)
                .sound(SoundType.PLANT)
                .notSolid()
                .doesNotBlockMovement()
                .setAllowsSpawn(TGBlockRegister::neverAllowSpawn)
                .setSuffocates((state, reader, pos) -> false)
                .setBlocksVision((state, reader, pos) -> false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext selected) {
        Direction direction = state.get(FACING);
        int amount = state.get(AMOUNT);
        return SHAPE_BY_PROPERTIES.apply(direction, amount);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState state = context.getWorld().getBlockState(context.getPos());
        if(state.getBlock().matchesBlock(this)) {
            return state.with(AMOUNT, Math.min(4, state.get(AMOUNT) + 1));
        }
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return useContext.getItem().getItem().equals(this.asItem()) && state.get(AMOUNT) < 4 && !useContext.hasSecondaryUseForPlayer() || super.isReplaceable(state, useContext);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        if(!world.isRemote) {
            if(state.get(AMOUNT) < 4) {
                world.setBlockState(pos, state.with(AMOUNT, state.get(AMOUNT) + 1));
            } else {
                spawnAsEntity(world, pos, new ItemStack(this));
            }
        }
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return this.canGrow(worldIn, pos, state, worldIn.isRemote);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AMOUNT, FACING);
        super.fillStateContainer(builder);
    }
}