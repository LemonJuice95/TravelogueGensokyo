package io.lemonjuice.tvlgensokyo.common.plugins.jei.category;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.item.crafting.PoundingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

public class PoundingRecipeCategory implements IRecipeCategory<PoundingRecipe> {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/container/pounding.png");

    private final IDrawable bgDraw;
    private final IDrawable iconDraw;

    public PoundingRecipeCategory(IGuiHelper helper) {
        this.bgDraw = helper.drawableBuilder(BG_TEXTURE, 0, 0, 125, 85).build();
        this.iconDraw = helper.createDrawableIngredient(new ItemStack(TGBlockRegister.STONE_MORTAR.get()));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PoundingRecipe recipe, IIngredients ingredients) {
        IGuiIngredientGroup<ItemStack> itemGroup = recipeLayout.getIngredientsGroup(VanillaTypes.ITEM);
        IGuiFluidStackGroup fluidGroup = recipeLayout.getFluidStacks();

        itemGroup.init(0, true, 28, 31);
        itemGroup.set(0, Arrays.asList(recipe.getIngredients().get(0).getMatchingStacks()));

        if(recipe.getFluid() != Fluids.EMPTY) {
            fluidGroup.init(1, true, 29, 59, 16, 8, 1000, false, null);
            fluidGroup.set(1, Arrays.asList(new FluidStack(recipe.getFluid(), 1000)));
        }

        itemGroup.init(2, false, 82, 31);
        itemGroup.set(2, Arrays.asList(recipe.getRecipeOutput()));
    }

    @Override
    public void setIngredients(PoundingRecipe recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setInput(VanillaTypes.FLUID, new FluidStack(recipe.getFluid(), 1000));
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
    }

    @Override
    public IDrawable getIcon() {
        return this.iconDraw;
    }

    @Override
    public IDrawable getBackground() {
        return this.bgDraw;
    }

    @Override
    public String getTitle() {
        return new TranslationTextComponent("block.tvlgensokyo.stone_mortar").getString();
    }

    @Override
    public Class<? extends PoundingRecipe> getRecipeClass() {
        return PoundingRecipe.class;
    }

    @Override
    public ResourceLocation getUid() {
        return PoundingRecipe.UID;
    }
}
