package io.lemonjuice.tvlgensokyo.common.block;

import io.lemonjuice.tvlgensokyo.common.block.tileentity.TGSignTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class BlockTGWallSign extends WallSignBlock {
    public BlockTGWallSign(AbstractBlock.Properties properties, WoodType type) {
        super(properties, type);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader reader) {
        return new TGSignTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
}
