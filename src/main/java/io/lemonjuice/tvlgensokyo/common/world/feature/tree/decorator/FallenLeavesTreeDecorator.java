package io.lemonjuice.tvlgensokyo.common.world.feature.tree.decorator;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FallenLeavesTreeDecorator extends TreeDecorator {
    public static final Codec<FallenLeavesTreeDecorator> CODEC = BlockStateProvider.CODEC.fieldOf("provider").xmap(FallenLeavesTreeDecorator::new, (decorator) -> {
        return decorator.provider;
    }).codec();

    private final BlockStateProvider provider;

    public FallenLeavesTreeDecorator(BlockStateProvider provider) {
        this.provider = provider;
    }

    //generate
    @Override
    @SuppressWarnings("deprecation")
    public void func_225576_a_(ISeedReader world, Random rand, List<BlockPos> trunkBlocks, List<BlockPos> leafBlocks, Set<BlockPos> decorations, MutableBoundingBox mutableBoundingBox) {
        List<Pair<Integer, Integer>> leaves = new ArrayList<>();
        int lowestLeaf = 2147483647;
        for(BlockPos i : leafBlocks) {
            Pair<Integer, Integer> pos = new Pair<>(i.getX(), i.getZ());
            if(!leaves.contains(pos)) {
                leaves.add(pos);
            }
            lowestLeaf = Math.min(lowestLeaf, i.getY());
        }
        for(Pair<Integer, Integer> i : leaves) {
            if(rand.nextInt() % 7 == 0) {
                BlockPos.Mutable pos = new BlockPos(i.getFirst(), lowestLeaf - 1, i.getSecond()).toMutable();
                do {
                    pos.move(0, -1, 0);
                } while (world.getBlockState(pos).getBlock().matchesBlock(Blocks.AIR) && pos.getY() > 0);

                if (world.getBlockState(pos).getBlock() == Blocks.GRASS) {
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 23);
                    pos.move(0, -1, 0);
                }

                if (world.getBlockState(pos.up()).isAir()) {
                    if (Block.doesSideFillSquare(world.getBlockState(pos).getCollisionShapeUncached(world, pos), Direction.UP)) {
                        this.func_227423_a_(world, pos.up(), this.provider.getBlockState(rand, pos.up()), decorations, mutableBoundingBox);
                    }
                }
            }
        }
    }

    @Override
    protected TreeDecoratorType<FallenLeavesTreeDecorator> getDecoratorType() {
        return TGTreeDecoratorTypes.FALLEN_LEAVES;
    }
}
