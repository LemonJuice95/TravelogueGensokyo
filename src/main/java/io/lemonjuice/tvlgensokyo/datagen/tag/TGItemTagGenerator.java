package io.lemonjuice.tvlgensokyo.datagen.tag;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.misc.tags.TGItemTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TGItemTagGenerator extends ItemTagsProvider {
    public TGItemTagGenerator(DataGenerator generator, BlockTagsProvider provider, ExistingFileHelper helper) {
        super(generator, provider, TravelogueGensokyo.MODID, helper);
    }

    @Override
    protected void registerTags() {
        //tvlgensokyo tags
        this.getOrCreateBuilder(TGItemTags.MAPLE_LOGS).add(TGItemRegister.MAPLE_LOG.get(), TGItemRegister.STRIPPED_MAPLE_LOG.get(), TGItemRegister.MAPLE_WOOD.get(), TGItemRegister.STRIPPED_MAPLE_WOOD.get());
        this.getOrCreateBuilder(TGItemTags.SAKURA_LOGS).add(TGItemRegister.SAKURA_LOG.get(), TGItemRegister.STRIPPED_SAKURA_LOG.get(), TGItemRegister.SAKURA_WOOD.get(), TGItemRegister.STRIPPED_SAKURA_WOOD.get());

        //forge tags
        this.getOrCreateBuilder(TGItemTags.CUCUMBERS).add(TGItemRegister.CUCUMBER.get());
        this.getOrCreateBuilder(TGItemTags.CUCUMBER_SEEDS).add(TGItemRegister.CUCUMBER_SEEDS.get());
        this.getOrCreateBuilder(Tags.Items.FENCES).add(TGItemRegister.MAPLE_FENCE.get());
        this.getOrCreateBuilder(Tags.Items.FENCES_WOODEN).add(TGItemRegister.MAPLE_FENCE.get());
        this.getOrCreateBuilder(Tags.Items.FENCE_GATES).add(TGItemRegister.MAPLE_FENCE_GATE.get());
        this.getOrCreateBuilder(Tags.Items.FENCE_GATES_WOODEN).add(TGItemRegister.MAPLE_FENCE_GATE.get());

        //mc tags
        this.getOrCreateBuilder(ItemTags.LOGS).add(TGItemRegister.MAPLE_LOG.get(), TGItemRegister.STRIPPED_MAPLE_LOG.get(), TGItemRegister.MAPLE_WOOD.get(), TGItemRegister.STRIPPED_MAPLE_WOOD.get());
        this.getOrCreateBuilder(ItemTags.LOGS_THAT_BURN).add(TGItemRegister.MAPLE_LOG.get(), TGItemRegister.STRIPPED_MAPLE_LOG.get(), TGItemRegister.MAPLE_WOOD.get(), TGItemRegister.STRIPPED_MAPLE_WOOD.get());
        this.getOrCreateBuilder(ItemTags.PLANKS).add(TGItemRegister.MAPLE_PLANKS.get());
        this.getOrCreateBuilder(ItemTags.LEAVES).add(TGItemRegister.MAPLE_LEAVES.get());
        this.getOrCreateBuilder(ItemTags.SLABS).add(TGItemRegister.MAPLE_SLAB.get());
        this.getOrCreateBuilder(ItemTags.WOODEN_SLABS).add(TGItemRegister.MAPLE_SLAB.get());
        this.getOrCreateBuilder(ItemTags.STAIRS).add(TGItemRegister.MAPLE_STAIRS.get());
        this.getOrCreateBuilder(ItemTags.WOODEN_STAIRS).add(TGItemRegister.MAPLE_STAIRS.get());
        this.getOrCreateBuilder(ItemTags.SIGNS).add(TGItemRegister.MAPLE_SIGN.get());
        this.getOrCreateBuilder(ItemTags.FENCES).add(TGItemRegister.MAPLE_FENCE.get());
        this.getOrCreateBuilder(ItemTags.WOODEN_FENCES).add(TGItemRegister.MAPLE_FENCE.get());
        this.getOrCreateBuilder(ItemTags.BUTTONS).add(TGItemRegister.MAPLE_BUTTON.get());
        this.getOrCreateBuilder(ItemTags.WOODEN_BUTTONS).add(TGItemRegister.MAPLE_BUTTON.get());
        this.getOrCreateBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(TGItemRegister.MAPLE_PRESSURE_PLATE.get());
        this.getOrCreateBuilder(ItemTags.SAPLINGS).add(TGItemRegister.MAPLE_SAPLING.get());

        this.getOrCreateBuilder(ItemTags.LOGS).add(TGItemRegister.SAKURA_LOG.get(), TGItemRegister.STRIPPED_SAKURA_LOG.get(), TGItemRegister.SAKURA_WOOD.get(), TGItemRegister.STRIPPED_SAKURA_WOOD.get());
        this.getOrCreateBuilder(ItemTags.LOGS_THAT_BURN).add(TGItemRegister.SAKURA_LOG.get(), TGItemRegister.STRIPPED_SAKURA_LOG.get(), TGItemRegister.SAKURA_WOOD.get(), TGItemRegister.STRIPPED_SAKURA_WOOD.get());
        this.getOrCreateBuilder(ItemTags.PLANKS).add(TGItemRegister.SAKURA_PLANKS.get());
    }

    @Override
    public String getName() {
        return "Travelogue Gensokyo Item Tags";
    }
}
