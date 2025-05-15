package io.lemonjuice.tvlgensokyo.common.world.feature.tree.placer;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.foliageplacer.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SakuraFoliagePlacer extends FoliagePlacer {
    public static final Codec<SakuraFoliagePlacer> CODEC = RecordCodecBuilder.create(
        builder -> func_242830_b(builder)
                .and(
                        builder.group(
                                FeatureSpread.createCodec(0, 16, 8).fieldOf("height").forGetter(placer -> placer.height),
                                Codec.floatRange(0.0F, 1.0F).fieldOf("wide_bottom_layer_hole_chance").forGetter(placer -> placer.wideBottomLayerHoleChance),
                                Codec.floatRange(0.0F, 1.0F).fieldOf("corner_hole_chance").forGetter(placer -> placer.cornerHoleChance),
                                Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter(placer -> placer.hangingLeavesChance),
                                Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter(placer -> placer.hangingLeavesExtensionChance)
                        )
                ).apply(builder, SakuraFoliagePlacer::new)
    );

    private final FeatureSpread height;
    private final float wideBottomLayerHoleChance;
    private final float cornerHoleChance;
    private final float hangingLeavesChance;
    private final float hangingLeavesExtensionChance;

    public SakuraFoliagePlacer(FeatureSpread radius, FeatureSpread offset, FeatureSpread height, float wideBottomLayerHoleChance, float cornerHoleChance, float hangingLeavesChance, float hangingLeavesExtensionChance) {
        super(radius, offset);
        this.height = height;

        this.wideBottomLayerHoleChance = wideBottomLayerHoleChance;
        this.cornerHoleChance = cornerHoleChance;
        this.hangingLeavesChance = hangingLeavesChance;
        this.hangingLeavesExtensionChance = hangingLeavesExtensionChance;
    }

    //getFoliageHeight
    @Override
    public int func_230374_a_(Random random, int trunkHeight, BaseTreeFeatureConfig config) {
        return this.height.getSpread(random);
    }

    @Override
    protected FoliagePlacerType<?> getPlacerType() {
        return TGTreePlacerRegister.SAKURA_FOLIAGE.get();
    }

    //generateFoliage
    @Override
    protected void func_230372_a_(IWorldGenerationReader reader, Random random, BaseTreeFeatureConfig config, int trunkHeight, Foliage foliage, int foliageHeight, int radius, Set<BlockPos> leavesPositions, int offset, MutableBoundingBox area) {
        boolean isDoubleTrunk = foliage.func_236765_c_();
        BlockPos pos = foliage.func_236763_a_().up(offset);
        int i = radius + foliage.func_236764_b_() - 1;

        //placeLeavesRow
        this.func_236753_a_(reader, random, config, pos, i - 2, leavesPositions, foliageHeight - 3, isDoubleTrunk, area);
        this.func_236753_a_(reader, random, config, pos, i - 1, leavesPositions, foliageHeight - 4, isDoubleTrunk, area);

        for(int j = foliageHeight - 5; j >= 0; j--) {
            this.func_236753_a_(reader, random, config, pos, i, leavesPositions, j, isDoubleTrunk, area);
        }

        this.placeLeavesRowWithHangingLeavesBelow(reader, random, config, pos, i, leavesPositions, -1, isDoubleTrunk, area, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
        this.placeLeavesRowWithHangingLeavesBelow(reader, random, config, pos, i - 1, leavesPositions, -2, isDoubleTrunk, area, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
    }

    private void placeLeavesRowWithHangingLeavesBelow(IWorldGenerationReader reader, Random random, BaseTreeFeatureConfig config, BlockPos pos, int radius, Set<BlockPos> leavesPositions, int offset, boolean isDoubleTrunk, MutableBoundingBox area, float hangingLeavesChance, float hangingLeavesExtensionChance) {
        //placeLeavesRow
        this.func_236753_a_(reader, random, config, pos, radius, leavesPositions, offset, isDoubleTrunk, area);
        int i = isDoubleTrunk ? 1 : 0;
        BlockPos pos1 = pos.down();
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            Direction direction1 = direction.rotateY();
            int j = direction1.getAxisDirection() == Direction.AxisDirection.POSITIVE ? radius + i : radius;
            mutableBlockPos.setAndOffset(pos, 0, offset - 1, 0).move(direction1, j).move(direction, -radius);

            for(int k = -radius; k < radius + i; k++) {
                boolean flag =  leavesPositions.contains(mutableBlockPos.move(Direction.UP));
                mutableBlockPos.move(Direction.DOWN);
                if(flag && tryPlaceExtension(reader, random, config, leavesPositions, hangingLeavesExtensionChance, pos1, mutableBlockPos, area)) {
                    mutableBlockPos.move(Direction.DOWN);
                    tryPlaceExtension(reader, random, config, leavesPositions, hangingLeavesExtensionChance, pos1, mutableBlockPos, area);
                    mutableBlockPos.move(Direction.UP);
                }

                mutableBlockPos.move(direction);
            }
        }
    }

    private static boolean tryPlaceExtension(IWorldGenerationReader reader, Random random, BaseTreeFeatureConfig config, Set<BlockPos> leavesPositions, float hangingLeavesExtensionChance, BlockPos pos, BlockPos.Mutable mutableBlockPos, MutableBoundingBox area) {
        if(mutableBlockPos.manhattanDistance(pos) >= 7)
            return false;
        if(random.nextFloat() > hangingLeavesExtensionChance)
            return false;

        if(TreeFeature.isReplaceableAt(reader, mutableBlockPos)) {
            reader.setBlockState(mutableBlockPos, config.leavesProvider.getBlockState(random, mutableBlockPos), 19);
            area.expandTo(new MutableBoundingBox(mutableBlockPos, mutableBlockPos));
            leavesPositions.add(mutableBlockPos.toImmutable());
        }
        return true;
    }

    //shouldSkipPosition
    @Override
    protected boolean func_230373_a_(Random random, int dx, int y, int dz, int radius, boolean isGiantTrunk) {
        if(y == -1 && (dx == radius || dz == radius) && random.nextFloat() < this.wideBottomLayerHoleChance) {
            return true;
        } else {
            boolean flag = dx == radius && dz == radius;
            boolean flag1 = radius > 2;
            return flag1
                    ? flag || dx + dz > radius * 2 - 2 && random.nextFloat() < this.cornerHoleChance
                    : flag && random.nextFloat() < this.cornerHoleChance;
        }
    }
}