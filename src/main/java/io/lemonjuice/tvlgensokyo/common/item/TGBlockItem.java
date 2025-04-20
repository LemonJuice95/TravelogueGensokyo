package io.lemonjuice.tvlgensokyo.common.item;

import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class TGBlockItem extends BlockItem {
    public TGBlockItem(Block block) {
        super(block, new Item.Properties().group(TGItemGroups.BLOCK));
    }
}
