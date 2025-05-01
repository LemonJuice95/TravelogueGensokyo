package io.lemonjuice.tvlgensokyo.common.item.crafting;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGRecipeRegister {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TravelogueGensokyo.MODID);

    //Recipe Types
    public static final IRecipeType<SpellWritingRecipe> SPELL_WRITING_TYPE = IRecipeType.register("tvlgensokyo:spell_writing");

    //Serializers
    public static final RegistryObject<SpellWritingRecipe.Serializer> SPELL_WRITING_SERIALIZER = RECIPE_SERIALIZERS.register("spell_writing", SpellWritingRecipe.Serializer::new);

}
