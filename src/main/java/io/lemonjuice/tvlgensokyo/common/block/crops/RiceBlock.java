package io.lemonjuice.tvlgensokyo.common.block.crops;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Random;

public class RiceBlock extends Block implements IGrowable, IWaterLoggable, IPlantable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_3;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

    public RiceBlock() {
        super(AbstractBlock.Properties.create(Material.PLANTS).zeroHardnessAndResistance().doesNotBlockMovement().tickRandomly().sound(SoundType.CROP));
        this.setDefaultState(this.stateContainer.getBaseState().with(AGE, 0).with(WATERLOGGED, false).with(HALF, DoubleBlockHalf.LOWER));
    }

    public int getMaxAge() {
        return 3;
    }

    public boolean isMaxAge(BlockState state) {
        return state.get(AGE) >= this.getMaxAge();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_AGE[state.get(AGE)];
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        BlockState ground = world.getBlockState(pos.down());
        BlockState upState = world.getBlockState(pos.up());
        boolean flag = ground.matchesBlock(Blocks.GRASS_BLOCK) || ground.matchesBlock(Blocks.DIRT) || ground.matchesBlock(Blocks.COARSE_DIRT) || ground.matchesBlock(Blocks.PODZOL) || ground.matchesBlock(Blocks.FARMLAND);
        if(state.get(HALF) == DoubleBlockHalf.LOWER) {
            return flag && world.getFluidState(pos).getFluid() == Fluids.WATER && (upState.matchesBlock(this) || upState.matchesBlock(Blocks.AIR));
        } else {
            return world.getFluidState(pos).isEmpty() && ground.matchesBlock(this) && ground.get(HALF) == DoubleBlockHalf.LOWER && world.getFluidState(pos).isEmpty();
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return !state.isValidPosition(world, pos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, pos, facingPos);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        if(world.getFluidState(pos).getFluid() == Fluids.WATER)
            return this.getDefaultState().with(WATERLOGGED, true);

        return super.getStateForPlacement(context);
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    private boolean canGrowUp(IBlockReader world, BlockPos pos, BlockState state) {
        BlockState stateUp = world.getBlockState(pos.up());
        return state.get(HALF) == DoubleBlockHalf.LOWER && (stateUp.matchesBlock(Blocks.AIR) || stateUp.matchesBlock(this) && !this.isMaxAge(stateUp) && stateUp.get(HALF) == DoubleBlockHalf.UPPER);
    }

    @Override
    public boolean canGrow(IBlockReader world, BlockPos pos, BlockState state, boolean isClient) {
        if(!this.isMaxAge(state))
            return true;
        else
            return this.canGrowUp(world, pos, state);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!world.isAreaLoaded(pos, 1)) return;
        if (world.getLightSubtracted(pos, 0) >= 9) {
            int i = state.get(AGE);
            if(state.get(HALF) == DoubleBlockHalf.LOWER) {
                if (i < this.getMaxAge() - 1) {
                    float f = getGrowthChance(this, world, pos);
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                        world.setBlockState(pos, state.with(AGE, i + 1), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
                    }
                } else if (this.canGrowUp(world, pos, state) && world.getBlockState(pos.up()).matchesBlock(Blocks.AIR)) {
                    float f = getGrowthChance(this, world, pos);
                    if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                        if (i < this.getMaxAge())
                            world.setBlockState(pos, state.with(AGE, i + 1), 2);
                        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 2);
                        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
                    }
                }
            } else if (i < this.getMaxAge()) {
                float f = getGrowthChance(this, world, pos);
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(world, pos, state, random.nextInt((int) (25.0F / f) + 1) == 0)) {
                    world.setBlockState(pos, state.with(AGE, i + 1), 2);
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(world, pos, state);
                }
            }
        }
    }

    @Override
    public void grow(ServerWorld world, Random rand, BlockPos pos, BlockState state) {
        int i = state.get(AGE) + MathHelper.nextInt(rand, 1, 3);
        int j = this.getMaxAge();
        if (i >= j) {
            if(this.canGrowUp(world, pos, state)) {
                BlockState stateUp = world.getBlockState(pos.up());
                if(stateUp.matchesBlock(Blocks.AIR)) {
                    world.setBlockState(pos.up(), this.getDefaultState().with(AGE, i - j).with(HALF, DoubleBlockHalf.UPPER));
                } else if(stateUp.matchesBlock(this)) {
                    world.setBlockState(pos.up(), stateUp.with(AGE, Math.min(stateUp.get(AGE) + i - state.get(AGE), this.getMaxAge())));
                }
            }
            i = j;
        }
        world.setBlockState(pos, state.with(AGE, i));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
        builder.add(WATERLOGGED);
        builder.add(HALF);
        super.fillStateContainer(builder);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
        return type == PathType.AIR && !this.canCollide || super.allowsMovement(state, world, pos, type);
    }

    @Override
    public BlockState getPlant(IBlockReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != this) return this.getDefaultState();
        return state;
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(!world.isRemote) {
            DoubleBlockHalf doubleblockhalf = state.get(HALF);
            if (doubleblockhalf == DoubleBlockHalf.UPPER) {
                if(player.isCreative()) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 35);
                }
                BlockPos blockpos = pos.down();
                BlockState blockstate = world.getBlockState(blockpos);
                boolean flag = world.getFluidState(blockpos).getFluid() == Fluids.WATER;
                if (blockstate.getBlock().matchesBlock(this) && blockstate.get(HALF) == DoubleBlockHalf.LOWER) {
                    world.setBlockState(blockpos, flag ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState(),  3);
                    world.playEvent(player, 2001, blockpos, Block.getStateId(blockstate));
                }
            } else {
                if(player.isCreative() && world.getBlockState(pos.up()).matchesBlock(this)) {
                    world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 35);
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @Override
    public boolean canContainFluid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluidIn) {
        return IWaterLoggable.super.canContainFluid(world, pos, state, fluidIn) || state.get(HALF) == DoubleBlockHalf.UPPER;
    }

    @Override
    public boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if(state.get(HALF) == DoubleBlockHalf.UPPER) {
            spawnDrops(state, world, pos, null);
            world.setBlockState(pos, fluidStateIn.getBlockState(), 3);
            return true;
        }
        return IWaterLoggable.super.receiveFluid(world, pos, state, fluidStateIn);
    }

    @Override
    public Fluid pickupFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(BlockStateProperties.WATERLOGGED) && state.get(HALF) == DoubleBlockHalf.LOWER) {
            spawnDrops(state, world, pos, null);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            return Fluids.WATER;
        }
        return IWaterLoggable.super.pickupFluid(world, pos, state);
    }

    protected static float getGrowthChance(Block blockIn, IBlockReader world, BlockPos pos) {
        float f = 1.0F;
        BlockPos blockpos = pos.down();

        for(int i = -1; i <= 1; ++i) {
            for(int j = -1; j <= 1; ++j) {
                float f1 = 0.0F;
                BlockState blockstate = world.getBlockState(blockpos.add(i, 0, j));
                if (blockstate.canSustainPlant(world, blockpos.add(i, 0, j), net.minecraft.util.Direction.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
                    f1 = 1.0F;
                    if (blockstate.isFertile(world, pos.add(i, 0, j))) {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        boolean flag = blockIn == world.getBlockState(blockpos3).getBlock() || blockIn == world.getBlockState(blockpos4).getBlock();
        boolean flag1 = blockIn == world.getBlockState(blockpos1).getBlock() || blockIn == world.getBlockState(blockpos2).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == world.getBlockState(blockpos3.north()).getBlock() || blockIn == world.getBlockState(blockpos4.north()).getBlock() || blockIn == world.getBlockState(blockpos4.south()).getBlock() || blockIn == world.getBlockState(blockpos3.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }
    
}
