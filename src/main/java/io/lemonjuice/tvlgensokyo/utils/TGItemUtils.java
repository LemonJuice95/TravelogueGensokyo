package io.lemonjuice.tvlgensokyo.utils;

import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.ArrayList;

public class TGItemUtils {
    public static final ArrayList<RegistryObject<Item>> SPELL_BOOKS_OBJECT = new ArrayList<>();
    public static final ArrayList<Item> SPELL_BOOKS = new ArrayList<>();

    static {
        SPELL_BOOKS_OBJECT.add(TGItemRegister.BASIC_SPELL_BOOK);
        SPELL_BOOKS_OBJECT.add(TGItemRegister.BASIC_THICK_SPELL_BOOK);
    }
}
