package io.lemonjuice.tvlgensokyo.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SakuraLeavesBlock extends FallableLeavesBlock {
    public SakuraLeavesBlock() {
        super(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(TGBlockRegister::allowsSpawnOnLeaves).setSuffocates((state, reader, pos) -> false).setBlocksVision((state, reader, pos) -> false), TGBlockRegister.PINK_PETALS.get().getDefaultState());
    }

    @Override
    protected void fall(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int x = pos.getX() + random.nextInt(3) - 1;
        int z = pos.getZ() + random.nextInt(3) - 1;
        int y = pos.getY();
        while (world.getBlockState(new BlockPos(x, y - 1, z)).getBlock() == Blocks.AIR && y > 0)
            y--;
        BlockPos fallAt = new BlockPos(x, y, z);
        BlockState stateAt = world.getBlockState(fallAt);
        BlockState stateDown = world.getBlockState(fallAt.down());
        if(stateDown.getBlock() == TGBlockRegister.PINK_PETALS.get() && stateDown.get(PinkPetalsBlock.AMOUNT) < 4) {
            world.setBlockState(fallAt.down(), stateDown.with(PinkPetalsBlock.AMOUNT, stateDown.get(PinkPetalsBlock.AMOUNT) + 1));
            return;
        }
        if (stateAt.getBlock() == Blocks.AIR && this.fallenLeaves.isValidPosition(world, fallAt)) {
            world.setBlockState(fallAt, this.fallenLeaves.with(PinkPetalsBlock.FACING, Direction.values()[random.nextInt(4) + 2]));
        }
    }
}
