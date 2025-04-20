package io.lemonjuice.tvlgensokyo.common.item.group;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.enchantments.TGEnchantmentTypes;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;

public class TGItemGroups {
    public static final ItemGroup MISC = createGroup("misc", TGItemRegister.AYAS_FAN).setRelevantEnchantmentTypes(TGEnchantmentTypes.getGCEnchantmentTypes());
    public static final ItemGroup FOOD_AND_AGRICULTURE = createGroup("food_and_agriculture", TGItemRegister.ROASTED_HAGFISH);
    public static final ItemGroup COMBAT = createGroup("combat", null);
    public static final ItemGroup BLOCK = createGroup("block", null);
    public static final ItemGroup BOOK_PAGE = createGroup("book_page", null);

    private static ItemGroup createGroup(String key, RegistryObject<? extends Item> icon) {
        return new ItemGroup(TravelogueGensokyo.MODID + "." + key) {
            @Override
            public ItemStack createIcon() {
                return icon != null ? new ItemStack(icon.get()) : ItemStack.EMPTY;
            }
        };
    }
}
