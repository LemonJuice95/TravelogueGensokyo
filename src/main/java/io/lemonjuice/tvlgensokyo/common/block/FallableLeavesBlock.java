package io.lemonjuice.tvlgensokyo.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class FallableLeavesBlock extends LeavesBlock {
    protected final BlockState fallenLeaves;
    protected BasicParticleType fallingLeafParticle = null;

    public FallableLeavesBlock(AbstractBlock.Properties properties, BlockState fallenLeaves) {
        super(properties);
        this.fallenLeaves = fallenLeaves;
    }

    public FallableLeavesBlock(AbstractBlock.Properties properties, BlockState fallenLeaves, BasicParticleType fallingLeafParticle) {
        super(properties);
        this.fallenLeaves = fallenLeaves;
        this.fallingLeafParticle = fallingLeafParticle;
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(random.nextInt(10) == 0) {
            this.fall(state, world, pos, random);
        }
        super.randomTick(state, world, pos, random);
    }

    protected void fall(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int x = pos.getX() + random.nextInt(3) - 1;
        int z = pos.getZ() + random.nextInt(3) - 1;
        int y = pos.getY() - 1;
        while (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == Blocks.AIR && y > 0)
            y--;
        BlockPos fallAt = new BlockPos(x, y, z);
        if (world.getBlockState(fallAt).getBlock() == Blocks.AIR && this.fallenLeaves.isValidPosition(world, fallAt)) {
            world.setBlockState(fallAt, this.fallenLeaves);
        }
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        super.animateTick(state, world, pos, random);
        if(this.fallingLeafParticle != null) {
            if(random.nextInt(50) == 1) {
                BlockPos blockpos = pos.down();
                BlockState blockstate = world.getBlockState(blockpos);
                if (!blockstate.isSolid() || !blockstate.isSolidSide(world, blockpos, Direction.UP)) {
                    double x = pos.getX() + random.nextDouble();
                    double y = pos.getY() - 0.05D;
                    double z = pos.getZ() + random.nextDouble();
                    double xSpeed = (double)(random.nextInt(31) - 15) / 1000;
                    double zSpeed = (double)(random.nextInt(31) - 15) / 1000;
                    world.addParticle(this.fallingLeafParticle, x, y, z, xSpeed, 0.0D, zSpeed);
                }
            }
        }
    }
}
