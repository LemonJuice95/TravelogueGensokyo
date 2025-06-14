package io.lemonjuice.tvlgensokyo.common.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.inventory.SpellWritingInventory;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.SpellBookPageItem;
import io.lemonjuice.tvlgensokyo.common.spell.Spell;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class SpellWritingRecipe implements IRecipe<SpellWritingInventory>{
    public static final ResourceLocation UID = new ResourceLocation(TravelogueGensokyo.MODID, "spell_writing");

    private final ResourceLocation id;
    private final NonNullList<Ingredient> recipeItems;
    private final ItemStack recipeOutput;

    public SpellWritingRecipe(ResourceLocation id, NonNullList<Ingredient> recipeItems, Spell recipeOutput) {
        this.id = id;
        this.recipeItems = recipeItems;
        this.recipeOutput = SpellBookPageItem.setSpell(new ItemStack(TGItemRegister.SPELL_BOOK_PAGE.get()), recipeOutput);
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeType<?> getType() {
        return TGRecipeRegister.SPELL_WRITING_TYPE;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return TGRecipeRegister.SPELL_WRITING_SERIALIZER.get();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
    }

    @Override
    public boolean matches(SpellWritingInventory inv, World worldIn) {
        for(int i = 0; i < 7; i++)  {
            Ingredient ingredient = this.recipeItems.get(i);
            if(!ingredient.test(inv.getStackInSlot(i)))
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getCraftingResult(SpellWritingInventory inv) {
        return this.recipeOutput.copy();
    }

    private static NonNullList<Ingredient> deserializeIngredients(JsonObject json) {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(7, Ingredient.EMPTY);
        ingredients.set(0, Ingredient.fromItems(Items.PAPER));

        for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            if(entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid ingredient entry: Must be a number from 1 to 6");
            }

            int index = (int)entry.getKey().charAt(0);
            if(index < '1' || index > '6') {
                throw new JsonSyntaxException("Invalid ingredient entry: Must be a number from 1 to 6");
            }

            ingredients.set(index - '0', Ingredient.deserialize(entry.getValue()));
        }

        return ingredients;
    }

    private static Spell deserializeResult(JsonObject json) {
        String s = JSONUtils.getString(json, "spell");
        Spell spell = Optional.ofNullable(Spell.getSpellFromName(new ResourceLocation(s))).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown Spell '" + s + "'");
        });

        return spell;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SpellWritingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(TravelogueGensokyo.MODID, "spell_writing");

        @Override
        public SpellWritingRecipe read(ResourceLocation recipeId, JsonObject json) {
            NonNullList<Ingredient> ingredients = deserializeIngredients(JSONUtils.getJsonObject(json, "ingredients"));
            Spell result = deserializeResult(JSONUtils.getJsonObject(json, "result"));
            return new SpellWritingRecipe(recipeId, ingredients, result);
        }

        @Nullable
        @Override
        public SpellWritingRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> ingredients = NonNullList.withSize(7, Ingredient.EMPTY);
            for(int i = 0; i < 7; i++) {
                ingredients.set(i, Ingredient.read(buffer));
            }

            Spell result = Spell.getSpellFromName(buffer.readResourceLocation());

            return new SpellWritingRecipe(recipeId, ingredients, result);
        }

        @Override
        public void write(PacketBuffer buffer, SpellWritingRecipe recipe) {
            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.write(buffer);
            }

            buffer.writeResourceLocation(SpellBookPageItem.getSpell(recipe.recipeOutput).getName());
        }
    }
}
