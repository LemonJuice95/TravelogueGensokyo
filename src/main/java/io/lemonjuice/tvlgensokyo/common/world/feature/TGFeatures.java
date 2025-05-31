package io.lemonjuice.tvlgensokyo.common.world.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.PinkPetalsBlock;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.decorator.FallenLeavesTreeDecorator;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.placer.SakuraFoliagePlacer;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.placer.SakuraTrunkPlacer;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockplacer.BlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidWithNoiseConfig;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class TGFeatures {

    public static final Feature<NoFeatureConfig> CROP_CIRCLE = new CropCircleFeature(NoFeatureConfig.CODEC);

    public static final ConfiguredFeature<?, ?> MASSIVE_SUNFLOWER = register("massive_sunflower", Feature.RANDOM_PATCH
            .withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SUNFLOWER.getDefaultState()), new DoublePlantBlockPlacer())).tries(128).preventProjection().build())
            .withPlacement(Features.Placements.VEGETATION_PLACEMENT)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .count(30));



    //Trees
    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE = register("maple_tree", Feature.TREE
            .withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(TGBlockRegister.MAPLE_LOG.get().getDefaultState()),
                                                                  new SimpleBlockStateProvider(TGBlockRegister.MAPLE_LEAVES.get().getDefaultState()),
                                                                  new BlobFoliagePlacer(FeatureSpread.create(2), FeatureSpread.create(0), 3),
                                                                  new StraightTrunkPlacer(6, 2, 0),
                                                                  new TwoLayerFeature(1, 1, 1))
                                                         .setIgnoreVines()
                                                         .build()));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> MAPLE_TREE_WITH_FALLEN_LEAVES = register("maple_tree_with_fallen_leaves", Feature.TREE
            .withConfiguration(MAPLE_TREE.getConfig().copy(ImmutableList.of(new FallenLeavesTreeDecorator(new SimpleBlockStateProvider(TGBlockRegister.FALLEN_MAPLE_LEAVES.get().getDefaultState()))))));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SAKURA_TREE = register("sakura_tree", Feature.TREE
            .withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(TGBlockRegister.SAKURA_LOG.get().getDefaultState()),
                                                                  new SimpleBlockStateProvider(TGBlockRegister.SAKURA_LEAVES.get().getDefaultState()),
                                                                  new SakuraFoliagePlacer(FeatureSpread.create(4), FeatureSpread.create(0), FeatureSpread.create(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                                                                  new SakuraTrunkPlacer(7, 1, 0),
                                                                  new TwoLayerFeature(1, 0, 2))
                                                         .setIgnoreVines()
                                                         .build()));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SAKURA_TREE_WITH_PINK_PETALS = register("sakura_tree_with_pink_petals", Feature.TREE
            .withConfiguration(SAKURA_TREE.getConfig().copy(ImmutableList.of(new FallenLeavesTreeDecorator(new WeightedBlockStateProvider()
                    .addWeightedBlockstate(TGBlockRegister.PINK_PETALS.get().getDefaultState(), 4)
                    .addWeightedBlockstate(TGBlockRegister.PINK_PETALS.get().getDefaultState().with(PinkPetalsBlock.AMOUNT, 2), 2)
                    .addWeightedBlockstate(TGBlockRegister.PINK_PETALS.get().getDefaultState().with(PinkPetalsBlock.AMOUNT, 3), 2)
                    .addWeightedBlockstate(TGBlockRegister.PINK_PETALS.get().getDefaultState().with(PinkPetalsBlock.AMOUNT, 4), 1)
            )))));

    //Configured features which will be used in worldgen
    public static final ConfiguredFeature<?, ?> MULTI_MAPLE_TREE = register("multi_maple_tree", Feature.RANDOM_SELECTOR
            .withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(TGFeatures.MAPLE_TREE.withChance(0.5F), TGFeatures.MAPLE_TREE_WITH_FALLEN_LEAVES.withChance(0.5F)), TGFeatures.MAPLE_TREE_WITH_FALLEN_LEAVES))
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.05F, 4))));

    public static final ConfiguredFeature<?, ?> MULTI_SAKURA_TREE = register("multi_sakura_tree", Feature.RANDOM_SELECTOR
            .withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(TGFeatures.SAKURA_TREE.withChance(0.5F), TGFeatures.SAKURA_TREE_WITH_PINK_PETALS.withChance(0.5F)), TGFeatures.SAKURA_TREE_WITH_PINK_PETALS))
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT)
            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(3, 0.05F, 2))));

    public static final ConfiguredFeature<?, ?> CONFIGURED_CROP_CIRCLE = register("crop_circle", TGFeatures.CROP_CIRCLE
            .withConfiguration(new NoFeatureConfig())
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT));

    public static final ConfiguredFeature<?, ?> BAMBOO = register("bamboo", Feature.RANDOM_SELECTOR
            .withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(
                    Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.1F)).withChance(0.7F),
                    Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BAMBOO_SAPLING.getDefaultState()), SimpleBlockPlacer.PLACER).tries(10).whitelist(ImmutableSet.of(Blocks.PODZOL)).build())).withPlacement(Features.Placements.PATCH_PLACEMENT).withChance(0.3F)),
                    Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.1F))))
            .withPlacement(Features.Placements.BAMBOO_PLACEMENT)
            .square()
            .withPlacement(Placement.COUNT_EXTRA.configure(new AtSurfaceWithExtraConfig(48, 0.2F, 16))));


    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String key, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(TravelogueGensokyo.MODID, key), feature);
    }
}
