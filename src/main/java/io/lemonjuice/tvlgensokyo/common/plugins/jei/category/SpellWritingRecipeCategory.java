package io.lemonjuice.tvlgensokyo.common.plugins.jei.category;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.item.crafting.SpellWritingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiIngredientGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Arrays;

public class SpellWritingRecipeCategory implements IRecipeCategory<SpellWritingRecipe> {
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/container/spell_writing_table.png");

    private final IDrawable bgDraw;
    private final IDrawable bg2Draw;
    private final IDrawable iconDraw;

    public SpellWritingRecipeCategory(IGuiHelper helper) {
        this.bgDraw = helper.createBlankDrawable(180, 130);
        this.bg2Draw = helper.drawableBuilder(BG_TEXTURE, 12, 15, 157, 105).build();
        this.iconDraw = helper.createDrawableIngredient(new ItemStack(TGBlockRegister.SPELL_WRITING_TABLE.get()));
    }

    @Override
    public void setRecipe(IRecipeLayout iRecipeLayout, SpellWritingRecipe spellWritingRecipe, IIngredients iIngredients) {
        IGuiIngredientGroup<ItemStack> guiIngredientGroup = iRecipeLayout.getIngredientsGroup(VanillaTypes.ITEM);

        guiIngredientGroup.init(0, true, 57, 59);
        guiIngredientGroup.set(0, new ItemStack(Items.PAPER));

        guiIngredientGroup.init(1, true, 57, 15);
        guiIngredientGroup.set(1, Arrays.asList(spellWritingRecipe.getIngredients().get(0).getMatchingStacks()));

        guiIngredientGroup.init(2, true, 99, 38);
        guiIngredientGroup.set(2, Arrays.asList(spellWritingRecipe.getIngredients().get(1).getMatchingStacks()));

        guiIngredientGroup.init(3, true, 99, 80);
        guiIngredientGroup.set(3, Arrays.asList(spellWritingRecipe.getIngredients().get(2).getMatchingStacks()));

        guiIngredientGroup.init(4, true, 57, 102);
        guiIngredientGroup.set(4, Arrays.asList(spellWritingRecipe.getIngredients().get(3).getMatchingStacks()));

        guiIngredientGroup.init(5, true, 14, 80);
        guiIngredientGroup.set(5, Arrays.asList(spellWritingRecipe.getIngredients().get(4).getMatchingStacks()));

        guiIngredientGroup.init(6, true, 14, 38);
        guiIngredientGroup.set(6, Arrays.asList(spellWritingRecipe.getIngredients().get(5).getMatchingStacks()));

        guiIngredientGroup.init(7, false, 149, 59);
        guiIngredientGroup.set(7, spellWritingRecipe.getRecipeOutput());

        guiIngredientGroup.set(iIngredients);

    }

    @Override
    public void setIngredients(SpellWritingRecipe spellWritingRecipe, IIngredients iIngredients) {
        iIngredients.setInputIngredients(spellWritingRecipe.getIngredients());
        iIngredients.setOutput(VanillaTypes.ITEM, spellWritingRecipe.getRecipeOutput());
    }

    @Override
    public void draw(SpellWritingRecipe recipe, MatrixStack matrixStack, double mouseX, double mouseY) {
        matrixStack.push();
        this.bg2Draw.draw(matrixStack, 14, 15);
        matrixStack.pop();
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
        return new TranslationTextComponent("block.tvlgensokyo.spell_writing_table").getString();
    }

    @Override
    public Class<? extends SpellWritingRecipe> getRecipeClass() {
        return SpellWritingRecipe.class;
    }

    @Override
    public ResourceLocation getUid() {
        return SpellWritingRecipe.UID;
    }
}
