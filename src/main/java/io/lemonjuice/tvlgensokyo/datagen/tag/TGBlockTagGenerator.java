package io.lemonjuice.tvlgensokyo.datagen.tag;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.misc.tags.TGBlockTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TGBlockTagGenerator extends BlockTagsProvider {
    public TGBlockTagGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, TravelogueGensokyo.MODID, helper);
    }

    @Override
    protected void registerTags() {
        //tvlgensokyo tags
        this.getOrCreateBuilder(TGBlockTags.MAPLE_LOGS).add(TGBlockRegister.MAPLE_LOG.get(), TGBlockRegister.STRIPPED_MAPLE_LOG.get(), TGBlockRegister.MAPLE_WOOD.get(), TGBlockRegister.STRIPPED_MAPLE_WOOD.get());
        this.getOrCreateBuilder(TGBlockTags.SAKURA_LOGS).add(TGBlockRegister.SAKURA_LOG.get(), TGBlockRegister.STRIPPED_SAKURA_LOG.get(), TGBlockRegister.SAKURA_WOOD.get(), TGBlockRegister.STRIPPED_SAKURA_WOOD.get());

        //forge tags
        this.getOrCreateBuilder(Tags.Blocks.FENCES).add(TGBlockRegister.MAPLE_FENCE.get(), TGBlockRegister.SAKURA_FENCE.get());
        this.getOrCreateBuilder(Tags.Blocks.FENCES_WOODEN).add(TGBlockRegister.MAPLE_FENCE.get(), TGBlockRegister.SAKURA_FENCE.get());
        this.getOrCreateBuilder(Tags.Blocks.FENCE_GATES).add(TGBlockRegister.MAPLE_FENCE_GATE.get(), TGBlockRegister.SAKURA_FENCE_GATE.get());
        this.getOrCreateBuilder(Tags.Blocks.FENCE_GATES_WOODEN).add(TGBlockRegister.MAPLE_FENCE_GATE.get(), TGBlockRegister.SAKURA_FENCE_GATE.get());

        //mc tags
        this.getOrCreateBuilder(BlockTags.LOGS).add(TGBlockRegister.MAPLE_LOG.get(), TGBlockRegister.STRIPPED_MAPLE_LOG.get(), TGBlockRegister.MAPLE_WOOD.get(), TGBlockRegister.STRIPPED_MAPLE_WOOD.get());
        this.getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(TGBlockRegister.MAPLE_LOG.get(), TGBlockRegister.STRIPPED_MAPLE_LOG.get(), TGBlockRegister.MAPLE_WOOD.get(), TGBlockRegister.STRIPPED_MAPLE_WOOD.get());
        this.getOrCreateBuilder(BlockTags.PLANKS).add(TGBlockRegister.MAPLE_PLANKS.get());
        this.getOrCreateBuilder(BlockTags.LEAVES).add(TGBlockRegister.MAPLE_LEAVES.get(), TGBlockRegister.FALLEN_MAPLE_LEAVES.get());
        this.getOrCreateBuilder(BlockTags.SLABS).add(TGBlockRegister.MAPLE_SLAB.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(TGBlockRegister.MAPLE_SLAB.get());
        this.getOrCreateBuilder(BlockTags.STAIRS).add(TGBlockRegister.MAPLE_STAIRS.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(TGBlockRegister.MAPLE_STAIRS.get());
        this.getOrCreateBuilder(BlockTags.SIGNS).add(TGBlockRegister.MAPLE_SIGN.get(), TGBlockRegister.MAPLE_WALL_SIGN.get());
        this.getOrCreateBuilder(BlockTags.STANDING_SIGNS).add(TGBlockRegister.MAPLE_SIGN.get());
        this.getOrCreateBuilder(BlockTags.WALL_SIGNS).add(TGBlockRegister.MAPLE_WALL_SIGN.get());
        this.getOrCreateBuilder(BlockTags.FENCES).add(TGBlockRegister.MAPLE_FENCE.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(TGBlockRegister.MAPLE_FENCE.get());
        this.getOrCreateBuilder(BlockTags.BUTTONS).add(TGBlockRegister.MAPLE_BUTTON.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(TGBlockRegister.MAPLE_BUTTON.get());
        this.getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(TGBlockRegister.MAPLE_PRESSURE_PLATE.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(TGBlockRegister.MAPLE_PRESSURE_PLATE.get());
        this.getOrCreateBuilder(BlockTags.FENCE_GATES).add(TGBlockRegister.MAPLE_FENCE_GATE.get());
        this.getOrCreateBuilder(BlockTags.SAPLINGS).add(TGBlockRegister.MAPLE_SAPLING.get());

        this.getOrCreateBuilder(BlockTags.LOGS).add(TGBlockRegister.SAKURA_LOG.get(), TGBlockRegister.STRIPPED_SAKURA_LOG.get(), TGBlockRegister.SAKURA_WOOD.get(), TGBlockRegister.STRIPPED_SAKURA_WOOD.get());
        this.getOrCreateBuilder(BlockTags.LOGS_THAT_BURN).add(TGBlockRegister.SAKURA_LOG.get(), TGBlockRegister.STRIPPED_SAKURA_LOG.get(), TGBlockRegister.SAKURA_WOOD.get(), TGBlockRegister.STRIPPED_SAKURA_WOOD.get());
        this.getOrCreateBuilder(BlockTags.PLANKS).add(TGBlockRegister.SAKURA_PLANKS.get());
        this.getOrCreateBuilder(BlockTags.LEAVES).add(TGBlockRegister.SAKURA_LEAVES.get());
        this.getOrCreateBuilder(BlockTags.SLABS).add(TGBlockRegister.SAKURA_SLAB.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_SLABS).add(TGBlockRegister.SAKURA_SLAB.get());
        this.getOrCreateBuilder(BlockTags.STAIRS).add(TGBlockRegister.SAKURA_STAIRS.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_STAIRS).add(TGBlockRegister.SAKURA_STAIRS.get());
        this.getOrCreateBuilder(BlockTags.SIGNS).add(TGBlockRegister.SAKURA_SIGN.get(), TGBlockRegister.SAKURA_WALL_SIGN.get());
        this.getOrCreateBuilder(BlockTags.STANDING_SIGNS).add(TGBlockRegister.SAKURA_SIGN.get());
        this.getOrCreateBuilder(BlockTags.WALL_SIGNS).add(TGBlockRegister.SAKURA_WALL_SIGN.get());
        this.getOrCreateBuilder(BlockTags.FENCES).add(TGBlockRegister.SAKURA_FENCE.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_FENCES).add(TGBlockRegister.SAKURA_FENCE.get());
        this.getOrCreateBuilder(BlockTags.BUTTONS).add(TGBlockRegister.SAKURA_BUTTON.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_BUTTONS).add(TGBlockRegister.SAKURA_BUTTON.get());
        this.getOrCreateBuilder(BlockTags.PRESSURE_PLATES).add(TGBlockRegister.SAKURA_PRESSURE_PLATE.get());
        this.getOrCreateBuilder(BlockTags.WOODEN_PRESSURE_PLATES).add(TGBlockRegister.SAKURA_PRESSURE_PLATE.get());
        this.getOrCreateBuilder(BlockTags.FENCE_GATES).add(TGBlockRegister.SAKURA_FENCE_GATE.get());
    }


    @Override
    public String getName() {
        return "Travelogue Gensokyo Block Tags";
    }
}
