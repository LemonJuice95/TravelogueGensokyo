package io.lemonjuice.tvlgensokyo.common.world.feature.tree.placer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;
import net.minecraft.world.gen.trunkplacer.TrunkPlacerType;

import java.util.*;

public class SakuraTrunkPlacer extends AbstractTrunkPlacer {
    public static final Codec<SakuraTrunkPlacer> CODEC = RecordCodecBuilder.create((builder) -> {
        return getAbstractTrunkCodec(builder).apply(builder, SakuraTrunkPlacer::new);
    });

    public SakuraTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
        super(baseHeight, heightRandA, heightRandB);
    }

    @Override
    protected TrunkPlacerType<?> getPlacerType() {
        return TGTreePlacerRegister.SAKURA_TRUNK_PLACER;
    }

    //力大砖飞（）
    @Override
    public List<FoliagePlacer.Foliage> getFoliages(IWorldGenerationReader reader, Random rand, int treeHeight, BlockPos pos, Set<BlockPos> logPositions, MutableBoundingBox area, BaseTreeFeatureConfig config) {
        func_236909_a_(reader, pos.down()); //setDirtAt

        int branchCount = rand.nextInt(3) + 1;
        int branchStartHeight = treeHeight + rand.nextInt(2) - 5;
        int secondBranchStartHeight = treeHeight - 5;
        if(secondBranchStartHeight >= branchStartHeight)
            secondBranchStartHeight++;
        Direction branchDirection = Direction.values()[rand.nextInt(4) + 2];

        ArrayList<FoliagePlacer.Foliage> foliageList = new ArrayList<>();

        int middleTrunkHeight = (branchCount > 1 ? Math.max(branchStartHeight, secondBranchStartHeight) : branchStartHeight) + 1;

        if(branchCount == 3) {
            middleTrunkHeight = treeHeight;
            foliageList.add(new FoliagePlacer.Foliage(pos.up(treeHeight), 0, false));
        }

        for(int i = 0; i < middleTrunkHeight; i++) {
            //placeLog
            func_236911_a_(reader, rand, pos.up(i), logPositions, area, config);
        }

        foliageList.add(this.generateBranch(reader, rand, treeHeight, branchStartHeight, middleTrunkHeight, branchDirection, pos, logPositions, area, config));

        if(branchCount > 1) {
            foliageList.add(this.generateBranch(reader, rand, treeHeight, secondBranchStartHeight, middleTrunkHeight, branchDirection.getOpposite(), pos, logPositions, area, config));
        }

        return foliageList;
    }

    private FoliagePlacer.Foliage generateBranch(IWorldGenerationReader reader, Random rand, int treeHeight, int startHeight, int middleTrunkHeight,  Direction direction, BlockPos pos, Set<BlockPos> logPositions, MutableBoundingBox area, BaseTreeFeatureConfig config) {
        int branchEndHeight = treeHeight + rand.nextInt(2) - 3;
        boolean flag1 = startHeight < middleTrunkHeight - 1 || branchEndHeight < treeHeight;

        int horizontalLength = rand.nextInt(3) + 2 + (flag1 ? 1 : 0);

        BlockPos.Mutable genPos = pos.up(startHeight).toMutable();
        BlockPos endPos = pos.offset(direction, horizontalLength).up(branchEndHeight);

        int k = flag1 ? 2 : 1;

        for(int i = 0; i < k; i++) {
            genPos.move(direction);
            placeLog(reader, rand, genPos, logPositions, area, config, direction.getAxis());
        }

        Direction directionVertical = endPos.getY() > genPos.getY() ? Direction.UP : Direction.DOWN;

        while(true) {
            int distance = genPos.manhattanDistance(endPos);
            if(distance == 0) {
                return new FoliagePlacer.Foliage(genPos.up(), 0, false);
            }

            float f = (float) Math.abs(endPos.getY() - genPos.getY()) / (float) distance;
            boolean isVertical = rand.nextFloat() < f;
            genPos.move(isVertical ? directionVertical : direction);
            placeLog(reader, rand, genPos, logPositions, area, config, isVertical ? directionVertical.getAxis() : direction.getAxis());
        }
    }


    protected static boolean placeLog(IWorldGenerationReader reader, Random rand, BlockPos pos, Set<BlockPos> logPositions, MutableBoundingBox area, BaseTreeFeatureConfig config, Direction.Axis direction) {
        if (TreeFeature.isReplaceableAt(reader, pos)) {
            BlockState logState = config.trunkProvider.getBlockState(rand, pos);
            if(logState.hasProperty(RotatedPillarBlock.AXIS)) {
                logState = logState.with(RotatedPillarBlock.AXIS, direction);
            }
            func_236913_a_(reader, pos, logState, area);
            logPositions.add(pos.toImmutable());
            return true;
        } else {
            return false;
        }
    }
}
