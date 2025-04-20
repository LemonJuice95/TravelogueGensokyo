package io.lemonjuice.tvlgensokyo.datagen.model;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class TGItemModelGenerator extends ItemModelProvider {
    public TGItemModelGenerator(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, TravelogueGensokyo.MODID, helper);
    }

    @Override
    protected void registerModels() {

    }

    private ItemModelBuilder generated(String name, ResourceLocation... layers) {
        ItemModelBuilder builder = this.withExistingParent(name, "item/generated");
        for(int i = 0; i < layers.length; i++) {
            builder = builder.texture("layer" + i, layers[i]);
        }
        return builder;
    }
}