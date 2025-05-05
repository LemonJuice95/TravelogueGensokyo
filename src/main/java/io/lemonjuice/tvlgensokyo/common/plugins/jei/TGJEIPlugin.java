package io.lemonjuice.tvlgensokyo.common.plugins.jei;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.SpellWritingTableContainerScreen;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.container.SpellWritingTableContainer;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.crafting.SpellWritingRecipe;
import io.lemonjuice.tvlgensokyo.common.item.crafting.TGRecipeRegister;
import io.lemonjuice.tvlgensokyo.common.plugins.jei.category.SpellWritingRecipeCategory;
import io.lemonjuice.tvlgensokyo.common.plugins.jei.interpreter.SpellBookPageSubtypeInterpreter;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

@JeiPlugin
public class TGJEIPlugin implements IModPlugin {
    public static final ResourceLocation UID = new ResourceLocation(TravelogueGensokyo.MODID, "jei");

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(getRecipeManager().getRecipesForType(TGRecipeRegister.SPELL_WRITING_TYPE), SpellWritingRecipe.UID);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SpellWritingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(TGBlockRegister.SPELL_WRITING_TABLE.get().asItem().getDefaultInstance(), SpellWritingRecipe.UID);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(SpellWritingTableContainer.class, SpellWritingRecipe.UID, 1, 7, 8, 36);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(TGItemRegister.SPELL_BOOK_PAGE.get(), new SpellBookPageSubtypeInterpreter());
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(SpellWritingTableContainerScreen.class, 117, 60, 22, 15, new ResourceLocation[]{SpellWritingRecipe.UID});
    }

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    private static RecipeManager getRecipeManager() {
        ClientWorld world = Objects.requireNonNull(Minecraft.getInstance().world);
        return world.getRecipeManager();
    }
}
