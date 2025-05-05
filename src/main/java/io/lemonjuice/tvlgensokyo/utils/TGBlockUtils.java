package io.lemonjuice.tvlgensokyo.utils;

import io.lemonjuice.tvlgensokyo.common.block.SweetBedBlock;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import net.minecraft.block.*;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class TGBlockUtils {
    public static final Map<Block, SweetBedBlock> SWEET_BED_MAP = new HashMap<>();
    private static final Map<Block, Block> STRIPPED_BLOCK_MAP = new HashMap<>();

    static {
        SWEET_BED_MAP.put(Blocks.ORANGE_BED, TGBlockRegister.SWEET_BED_ORANGE.get());
        SWEET_BED_MAP.put(Blocks.MAGENTA_BED, TGBlockRegister.SWEET_BED_MAGENTA.get());
        SWEET_BED_MAP.put(Blocks.LIGHT_BLUE_BED, TGBlockRegister.SWEET_BED_LIGHT_BLUE.get());
        SWEET_BED_MAP.put(Blocks.YELLOW_BED, TGBlockRegister.SWEET_BED_YELLOW.get());
        SWEET_BED_MAP.put(Blocks.LIME_BED, TGBlockRegister.SWEET_BED_LIME.get());
        SWEET_BED_MAP.put(Blocks.PINK_BED, TGBlockRegister.SWEET_BED_PINK.get());
        SWEET_BED_MAP.put(Blocks.GRAY_BED, TGBlockRegister.SWEET_BED_GRAY.get());
        SWEET_BED_MAP.put(Blocks.LIGHT_GRAY_BANNER, TGBlockRegister.SWEET_BED_LIGHT_GRAY.get());
        SWEET_BED_MAP.put(Blocks.CYAN_BED, TGBlockRegister.SWEET_BED_CYAN.get());
        SWEET_BED_MAP.put(Blocks.PURPLE_BED, TGBlockRegister.SWEET_BED_PURPLE.get());
        SWEET_BED_MAP.put(Blocks.BLUE_BED, TGBlockRegister.SWEET_BED_BLUE.get());
        SWEET_BED_MAP.put(Blocks.BROWN_BED, TGBlockRegister.SWEET_BED_BROWN.get());
        SWEET_BED_MAP.put(Blocks.GREEN_BED, TGBlockRegister.SWEET_BED_GREEN.get());
        SWEET_BED_MAP.put(Blocks.RED_BED, TGBlockRegister.SWEET_BED_RED.get());
        SWEET_BED_MAP.put(Blocks.BLACK_BED, TGBlockRegister.SWEET_BED_BLACK.get());
        SWEET_BED_MAP.put(Blocks.WHITE_BED, TGBlockRegister.SWEET_BED_WHITE.get());

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
