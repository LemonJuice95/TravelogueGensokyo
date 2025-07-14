package io.lemonjuice.tvlgensokyo.common.block.crops;

import net.minecraft.block.CropsBlock;
import net.minecraft.util.IItemProvider;

import java.util.function.Supplier;

public class Age7CropsBlock extends CropsBlock {
    private final Supplier<? extends IItemProvider> seedItem;

    public Age7CropsBlock(Supplier<? extends IItemProvider> seedItem, Properties properties) {
        super(properties);
        this.seedItem = seedItem;
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return this.seedItem.get();
    }
}
