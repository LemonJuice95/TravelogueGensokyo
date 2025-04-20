package io.lemonjuice.tvlgensokyo.common.world.feature.tree;

import io.lemonjuice.tvlgensokyo.common.world.feature.TGFeatures;
import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class MapleTree extends Tree {
    @Nullable
    @Override
    protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random randomIn, boolean largeHive) {
        return TGFeatures.MAPLE_TREE;
    }
}