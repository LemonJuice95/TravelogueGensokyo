package io.lemonjuice.tvlgensokyo.common.item.crafting;

import com.google.gson.JsonObject;
import io.lemonjuice.tvlgensokyo.common.block.tileentity.StoneMortarTileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class PoundingRecipe implements IRecipe<StoneMortarTileEntity> {

    private final ResourceLocation id;
    private final Ingredient ingredient;
    private final Fluid fluid;
    private final ItemStack result;

    public PoundingRecipe(ResourceLocation id, Ingredient ingredient, Fluid fluid, ItemStack result) {
        this.id = id;
        this.ingredient = ingredient;
        this.fluid = fluid;
        this.result = result;
    }

    @Override
    public boolean matches(StoneMortarTileEntity inv, World world) {
        Fluid fluid = inv.getFluid();
        ItemStack stack = inv.getHoldingStack();
        return fluid == this.fluid && this.ingredient.test(stack);
    }

    @Override
    public ItemStack getCraftingResult(StoneMortarTileEntity inv) {
        return this.result.copy();
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return TGRecipeRegister.POUNDING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return TGRecipeRegister.POUNDING_TYPE;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<PoundingRecipe> {
        @Override
        public PoundingRecipe read(ResourceLocation recipeId, JsonObject json) {
            Ingredient ingredient = Ingredient.deserialize(JSONUtils.getJsonObject(json, "ingredient"));
            Fluid fluid = Registry.FLUID.getOrDefault(new ResourceLocation(JSONUtils.getString(json, "fluid", "minecraft:empty")));
            ItemStack result = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
            return new PoundingRecipe(recipeId, ingredient, fluid, result);
        }

        @Nullable
        @Override
        public PoundingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient ingredient = Ingredient.read(buffer);
            Fluid fluid = Registry.FLUID.getOrDefault(buffer.readResourceLocation());
            ItemStack result = buffer.readItemStack();
            return new PoundingRecipe(recipeId, ingredient, fluid, result);
        }

        @Override
        public void write(PacketBuffer buffer, PoundingRecipe recipe) {
            recipe.ingredient.write(buffer);
            buffer.writeResourceLocation(Registry.FLUID.getKey(recipe.fluid));
            buffer.writeItemStack(recipe.result);
        }
    }
}
