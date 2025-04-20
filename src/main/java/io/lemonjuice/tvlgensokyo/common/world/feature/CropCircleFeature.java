package io.lemonjuice.tvlgensokyo.common.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.*;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class CropCircleFeature extends Feature<NoFeatureConfig> {
    public CropCircleFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        for(BlockPos blockPos : BlockPos.getAllInBoxMutable(pos.add(-5, 0, -5), pos.add(6, 0, 6))) {
            //clear plants
            BlockPos blockPos1 = reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockPos);
            BlockState air = Blocks.AIR.getDefaultState();
            this.setBlockState(reader, blockPos1, air);
            this.setBlockState(reader, blockPos1.add(0, 1, 0), air);

            //place hay blocks
            if(rand.nextInt() % 9 == 0) {
                BlockState hayBlock = Blocks.HAY_BLOCK.getDefaultState();
                hayBlock = hayBlock.with(HayBlock.AXIS, rand.nextInt() % 2 == 0 ? Direction.Axis.X : Direction.Axis.Z);
                this.setBlockState(reader, reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockPos).add(0, -1, 0), hayBlock);
            }

            //place carpet
            if(rand.nextInt() % 5 == 0) {
                BlockState carpet = Blocks.YELLOW_CARPET.getDefaultState();
                this.setBlockState(reader, reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockPos), carpet);
            }
        }

        //place the table
        for(BlockPos blockPos : BlockPos.getAllInBoxMutable(pos, pos.add(1, 0, 1))) {
            BlockState cobblestone = Blocks.COBBLESTONE.getDefaultState();
            BlockState dirt = Blocks.DIRT.getDefaultState();
            this.setBlockState(reader, blockPos, cobblestone);
            this.setBlockState(reader, blockPos.add(0, -1, 0), dirt);
        }

        //place seats
        BlockState slab = Blocks.COBBLESTONE_SLAB.getDefaultState();
        slab = slab.with(SlabBlock.TYPE, SlabType.BOTTOM);
        this.setBlockState(reader, reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.add(-2, 0, rand.nextInt() % 2)), slab);
        this.setBlockState(reader, reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.add(rand.nextInt() % 2, 0, -2)), slab);
        this.setBlockState(reader, reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.add(rand.nextInt() % 2, 0, 3)), slab);
        this.setBlockState(reader, reader.getHeight(Heightmap.Type.WORLD_SURFACE_WG, pos.add(3, 0, rand.nextInt() % 2)), slab);

        return true;
    }
}
