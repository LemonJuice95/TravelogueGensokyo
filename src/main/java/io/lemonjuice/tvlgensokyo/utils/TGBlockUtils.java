package io.lemonjuice.tvlgensokyo.utils;

import io.lemonjuice.tvlgensokyo.common.block.BlockSweetBed;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import net.minecraft.block.*;
import net.minecraft.item.DyeColor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TGBlockUtils {
    public static final Map<DyeColor, BlockSweetBed> SWEET_BED_MAP = new HashMap<>();
    private static final Map<Block, Block> STRIPPED_BLOCK_MAP = new HashMap<>();

    static {
        SWEET_BED_MAP.put(DyeColor.ORANGE, TGBlockRegister.SWEET_BED_ORANGE.get());
        SWEET_BED_MAP.put(DyeColor.MAGENTA, TGBlockRegister.SWEET_BED_MAGENTA.get());
        SWEET_BED_MAP.put(DyeColor.LIGHT_BLUE, TGBlockRegister.SWEET_BED_LIGHT_BLUE.get());
        SWEET_BED_MAP.put(DyeColor.YELLOW, TGBlockRegister.SWEET_BED_YELLOW.get());
        SWEET_BED_MAP.put(DyeColor.LIME, TGBlockRegister.SWEET_BED_LIME.get());
        SWEET_BED_MAP.put(DyeColor.PINK, TGBlockRegister.SWEET_BED_PINK.get());
        SWEET_BED_MAP.put(DyeColor.GRAY, TGBlockRegister.SWEET_BED_GRAY.get());
        SWEET_BED_MAP.put(DyeColor.LIGHT_GRAY, TGBlockRegister.SWEET_BED_LIGHT_GRAY.get());
        SWEET_BED_MAP.put(DyeColor.CYAN, TGBlockRegister.SWEET_BED_CYAN.get());
        SWEET_BED_MAP.put(DyeColor.PURPLE, TGBlockRegister.SWEET_BED_PURPLE.get());
        SWEET_BED_MAP.put(DyeColor.BLUE, TGBlockRegister.SWEET_BED_BLUE.get());
        SWEET_BED_MAP.put(DyeColor.BROWN, TGBlockRegister.SWEET_BED_BROWN.get());
        SWEET_BED_MAP.put(DyeColor.GREEN, TGBlockRegister.SWEET_BED_GREEN.get());
        SWEET_BED_MAP.put(DyeColor.RED, TGBlockRegister.SWEET_BED_RED.get());
        SWEET_BED_MAP.put(DyeColor.BLACK, TGBlockRegister.SWEET_BED_BLACK.get());
        SWEET_BED_MAP.put(DyeColor.WHITE, TGBlockRegister.SWEET_BED_WHITE.get());

        STRIPPED_BLOCK_MAP.put(TGBlockRegister.MAPLE_LOG.get(), TGBlockRegister.STRIPPED_MAPLE_LOG.get());
        STRIPPED_BLOCK_MAP.put(TGBlockRegister.MAPLE_WOOD.get(), TGBlockRegister.STRIPPED_MAPLE_WOOD.get());
    }

    @Nullable
    public static BlockState getLogStripped(BlockState origin) {
        BlockState newState = null;

        if(STRIPPED_BLOCK_MAP.containsKey(origin.getBlock())) {
            newState = STRIPPED_BLOCK_MAP.get(origin.getBlock()).getDefaultState();
            newState = newState.with(RotatedPillarBlock.AXIS, origin.get(RotatedPillarBlock.AXIS));
        }

        return newState;
    }
}
