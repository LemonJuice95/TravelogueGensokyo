package io.lemonjuice.tvlgensokyo.common.misc.tags;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;

public class TGBlockTags {
    //tvlgensokyo tags
    public static final ITag.INamedTag<Block> MAPLE_LOGS = createTGTag("maple_log");
    public static final ITag.INamedTag<Block> SAKURA_LOGS = createTGTag("sakura_log");

    //forge tags

    private static ITag.INamedTag<Block> createTGTag(String name){
        return BlockTags.createOptional(new ResourceLocation(TravelogueGensokyo.MODID, name));
    }

    private static ITag.INamedTag<Block> createForgeTag(String name) {
        return BlockTags.createOptional(new ResourceLocation("forge", name));
    }
}
