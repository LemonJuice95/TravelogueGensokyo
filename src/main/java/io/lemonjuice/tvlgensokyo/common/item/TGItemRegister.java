package io.lemonjuice.tvlgensokyo.common.item;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.danmaku.Danmaku;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.common.item.misc.SpellBookPageItem;
import io.lemonjuice.tvlgensokyo.common.item.food.TGFoodBase;
import io.lemonjuice.tvlgensokyo.common.item.food.RoastedHagfishItem;
import io.lemonjuice.tvlgensokyo.common.item.group.TGItemGroups;
import io.lemonjuice.tvlgensokyo.common.item.misc.*;
import io.lemonjuice.tvlgensokyo.common.item.weapon.GungnirItem;
import io.lemonjuice.tvlgensokyo.common.item.weapon.LaevateinItem;
import io.lemonjuice.tvlgensokyo.common.item.weapon.SpellBookItem;
import net.minecraft.block.ComposterBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TravelogueGensokyo.MODID);

    //Misc
    public static final RegistryObject<Item> DEBUGGER = ITEMS.register("debugger", DebuggerItem::new);
    public static final RegistryObject<Item> AYAS_FAN = ITEMS.register("ayas_fan", AyasFanItem::new);
    public static final RegistryObject<Item> POWER_POINT = ITEMS.register("power_point", () -> new Item(new Item.Properties().group(TGItemGroups.MISC)));
    public static final RegistryObject<Item> AMULET = ITEMS.register("amulet", AmuletItem::new);
    public static final RegistryObject<Item> SWEET_PILLOW = ITEMS.register("sweet_pillow", SweetPillowItem::new);
    public static final RegistryObject<Item> BANK_IN_GAP = ITEMS.register("bank_in_gap", BankInGapItem::new);

    //Currency
    public static final RegistryObject<CurrencyItem> COPPER_COIN = ITEMS.register("copper_coin", () -> new CurrencyItem(1));
    public static final RegistryObject<CurrencyItem> SILVER_PIECE = ITEMS.register("silver_piece", () -> new CurrencyItem(10));
    public static final RegistryObject<CurrencyItem> SILVER_INGOT = ITEMS.register("silver_ingot", () -> new CurrencyItem(100));

    //SpawnEgg
    public static final RegistryObject<Item> KAPPA_SPAWN_EGG = ITEMS.register("kappa_spawn_egg", () -> spawnEgg(TGEntityRegister.KAPPA, 0x1d642a, 0x00c1bc));
    public static final RegistryObject<Item> GENSOKYO_VILLAGER_SPAWN_EGG = ITEMS.register("gensokyo_villager_spawn_egg", () -> spawnEgg(TGEntityRegister.GENSOKYO_VILLAGER, 0x563c33, 0xbd8b72));

    //Food & Agriculture
    public static final RegistryObject<Item> HAGFISH = ITEMS.register("hagfish", () -> new TGFoodBase(2, 0.4F));
    public static final RegistryObject<Item> ROASTED_HAGFISH = ITEMS.register("roasted_hagfish", RoastedHagfishItem::new);
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber", () -> new TGFoodBase(2, 0.4F));
    public static final RegistryObject<Item> CUCUMBER_SEEDS = ITEMS.register("cucumber_seeds", () -> new BlockNamedItem(TGBlockRegister.CUCUMBER.get(), new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> TURNIP = ITEMS.register("turnip", () -> new TGFoodBase(2, 0.4F));
    public static final RegistryObject<Item> TURNIP_SEEDS = ITEMS.register("turnip_seeds", () -> new BlockNamedItem(TGBlockRegister.TURNIP.get(), new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> EAR_OF_RICE = ITEMS.register("ear_of_rice", () -> new Item(new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> RICE = ITEMS.register("rice", () -> new BlockNamedItem(TGBlockRegister.RICE.get(), new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> MAPLE_SAPLING = ITEMS.register("maple_sapling", () -> new BlockItem(TGBlockRegister.MAPLE_SAPLING.get(), new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> SAKURA_SAPLING = ITEMS.register("sakura_sapling", () -> new BlockItem(TGBlockRegister.SAKURA_SAPLING.get(), new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> STONE_MORTAR = ITEMS.register("stone_mortar", () -> new BlockItem(TGBlockRegister.STONE_MORTAR.get(), new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));
    public static final RegistryObject<Item> PESTLE = ITEMS.register("pestle", () -> new Item(new Item.Properties().group(TGItemGroups.FOOD_AND_AGRICULTURE)));

    //Combat
    public static final RegistryObject<Item> LAEVATEIN = ITEMS.register("laevatein", LaevateinItem::new);
    public static final RegistryObject<Item> GUNGNIR = ITEMS.register("gungnir", GungnirItem::new);
    public static final RegistryObject<Item> BASIC_SPELL_BOOK = ITEMS.register("basic_spell_book", () -> new SpellBookItem(4, 1.0F, 1.0F, true));
    public static final RegistryObject<Item> BASIC_THICK_SPELL_BOOK = ITEMS.register("basic_thick_spell_book", () -> new SpellBookItem(8, 1.0F, 1.0F, true));
    public static final RegistryObject<Item> SPELL_BOOK_PAGE = ITEMS.register("spell_book_page", SpellBookPageItem::new);

    //Danmaku
    public static final RegistryObject<DanmakuItem> DANMAKU_DOT = ITEMS.register("danmaku_dot", () -> new DanmakuItem(Danmaku.Type.DOT));
    public static final RegistryObject<DanmakuItem> DANMAKU_SMALL_JADE = ITEMS.register("danmaku_small_jade", () -> new DanmakuItem(Danmaku.Type.SMALL_JADE));

    //Blocks
    public static final RegistryObject<Item> MAPLE_LOG = ITEMS.register("maple_log", () -> new TGBlockItem(TGBlockRegister.MAPLE_LOG.get()));
    public static final RegistryObject<Item> SAKURA_LOG = ITEMS.register("sakura_log", () -> new TGBlockItem(TGBlockRegister.SAKURA_LOG.get()));
    public static final RegistryObject<Item> MAPLE_LEAVES = ITEMS.register("maple_leaves", () -> new TGBlockItem(TGBlockRegister.MAPLE_LEAVES.get()));
    public static final RegistryObject<Item> SAKURA_LEAVES = ITEMS.register("sakura_leaves", () -> new TGBlockItem(TGBlockRegister.SAKURA_LEAVES.get()));
    public static final RegistryObject<Item> FALLEN_MAPLE_LEAVES = ITEMS.register("fallen_maple_leaves", () -> new TGBlockItem(TGBlockRegister.FALLEN_MAPLE_LEAVES.get()));
    public static final RegistryObject<Item> PINK_PETALS = ITEMS.register("pink_petals", () -> new TGBlockItem(TGBlockRegister.PINK_PETALS.get()));
    public static final RegistryObject<Item> STRIPPED_MAPLE_LOG = ITEMS.register("stripped_maple_log", () -> new TGBlockItem(TGBlockRegister.STRIPPED_MAPLE_LOG.get()));
    public static final RegistryObject<Item> STRIPPED_SAKURA_LOG = ITEMS.register("stripped_sakura_log", () -> new TGBlockItem(TGBlockRegister.STRIPPED_SAKURA_LOG.get()));
    public static final RegistryObject<Item> MAPLE_WOOD = ITEMS.register("maple_wood", () -> new TGBlockItem(TGBlockRegister.MAPLE_WOOD.get()));
    public static final RegistryObject<Item> SAKURA_WOOD = ITEMS.register("sakura_wood", () -> new TGBlockItem(TGBlockRegister.SAKURA_WOOD.get()));
    public static final RegistryObject<Item> STRIPPED_MAPLE_WOOD = ITEMS.register("stripped_maple_wood", () -> new TGBlockItem(TGBlockRegister.STRIPPED_MAPLE_WOOD.get()));
    public static final RegistryObject<Item> STRIPPED_SAKURA_WOOD = ITEMS.register("stripped_sakura_wood", () -> new TGBlockItem(TGBlockRegister.STRIPPED_SAKURA_WOOD.get()));
    public static final RegistryObject<Item> MAPLE_PLANKS = ITEMS.register("maple_planks", () -> new TGBlockItem(TGBlockRegister.MAPLE_PLANKS.get()));
    public static final RegistryObject<Item> SAKURA_PLANKS = ITEMS.register("sakura_planks", () -> new TGBlockItem(TGBlockRegister.SAKURA_PLANKS.get()));
    public static final RegistryObject<Item> MAPLE_SLAB = ITEMS.register("maple_slab", () -> new TGBlockItem(TGBlockRegister.MAPLE_SLAB.get()));
    public static final RegistryObject<Item> SAKURA_SLAB = ITEMS.register("sakura_slab", () -> new TGBlockItem(TGBlockRegister.SAKURA_SLAB.get()));
    public static final RegistryObject<Item> MAPLE_STAIRS = ITEMS.register("maple_stairs", () -> new TGBlockItem(TGBlockRegister.MAPLE_STAIRS.get()));
    public static final RegistryObject<Item> SAKURA_STAIRS = ITEMS.register("sakura_stairs", () -> new TGBlockItem(TGBlockRegister.SAKURA_STAIRS.get()));
    public static final RegistryObject<Item> MAPLE_SIGN = ITEMS.register("maple_sign", () -> new SignItem(new Item.Properties().group(TGItemGroups.BLOCK).maxStackSize(16), TGBlockRegister.MAPLE_SIGN.get(), TGBlockRegister.MAPLE_WALL_SIGN.get()));
    public static final RegistryObject<Item> SAKURA_SIGN = ITEMS.register("sakura_sign", () -> new SignItem(new Item.Properties().group(TGItemGroups.BLOCK).maxStackSize(16), TGBlockRegister.SAKURA_SIGN.get(), TGBlockRegister.SAKURA_WALL_SIGN.get()));
    public static final RegistryObject<Item> MAPLE_FENCE = ITEMS.register("maple_fence", () -> new TGBlockItem(TGBlockRegister.MAPLE_FENCE.get()));
    public static final RegistryObject<Item> SAKURA_FENCE = ITEMS.register("sakura_fence", () -> new TGBlockItem(TGBlockRegister.SAKURA_FENCE.get()));
    public static final RegistryObject<Item> MAPLE_FENCE_GATE = ITEMS.register("maple_fence_gate", () -> new TGBlockItem(TGBlockRegister.MAPLE_FENCE_GATE.get()));
    public static final RegistryObject<Item> SAKURA_FENCE_GATE = ITEMS.register("sakura_fence_gate", () -> new TGBlockItem(TGBlockRegister.SAKURA_FENCE_GATE.get()));
    public static final RegistryObject<Item> MAPLE_BUTTON = ITEMS.register("maple_button", () -> new TGBlockItem(TGBlockRegister.MAPLE_BUTTON.get()));
    public static final RegistryObject<Item> SAKURA_BUTTON = ITEMS.register("sakura_button", () -> new TGBlockItem(TGBlockRegister.SAKURA_BUTTON.get()));
    public static final RegistryObject<Item> MAPLE_PRESSURE_PLATE = ITEMS.register("maple_pressure_plate", () -> new TGBlockItem(TGBlockRegister.MAPLE_PRESSURE_PLATE.get()));
    public static final RegistryObject<Item> SAKURA_PRESSURE_PLATE = ITEMS.register("sakura_pressure_plate", () -> new TGBlockItem(TGBlockRegister.SAKURA_PRESSURE_PLATE.get()));
    public static final RegistryObject<Item> SAKURA_DOOR = ITEMS.register("sakura_door", () -> new TallBlockItem(TGBlockRegister.SAKURA_DOOR.get(), new Item.Properties().group(TGItemGroups.BLOCK)));
    public static final RegistryObject<Item> SAKURA_TRAPDOOR = ITEMS.register("sakura_trapdoor", () -> new TGBlockItem(TGBlockRegister.SAKURA_TRAPDOOR.get()));

    public static final RegistryObject<Item> SPELL_BOOK_BINDING_TABLE = ITEMS.register("spell_book_binding_table", () -> new TGBlockItem(TGBlockRegister.SPELL_BOOK_BINDING_TABLE.get()));
    public static final RegistryObject<Item> SPELL_WRITING_TABLE = ITEMS.register("spell_writing_table", () -> new TGBlockItem(TGBlockRegister.SPELL_WRITING_TABLE.get()));

    public static void registerCompostable() {
        ComposterBlock.CHANCES.put(CUCUMBER.get(), 0.65F);
        ComposterBlock.CHANCES.put(CUCUMBER_SEEDS.get(), 0.3F);
        ComposterBlock.CHANCES.put(EAR_OF_RICE.get(), 0.65F);
        ComposterBlock.CHANCES.put(RICE.get(), 0.3F);
        ComposterBlock.CHANCES.put(TURNIP.get(), 0.65F);
        ComposterBlock.CHANCES.put(TURNIP_SEEDS.get(), 0.3F);

        ComposterBlock.CHANCES.put(MAPLE_LEAVES.get(), 0.3F);
        ComposterBlock.CHANCES.put(FALLEN_MAPLE_LEAVES.get(), 0.3F);
        ComposterBlock.CHANCES.put(MAPLE_SAPLING.get(), 0.3F);

        ComposterBlock.CHANCES.put(SAKURA_LEAVES.get(), 0.3F);
        ComposterBlock.CHANCES.put(PINK_PETALS.get(), 0.3F);
        ComposterBlock.CHANCES.put(SAKURA_SAPLING.get(), 0.3F);
    }

    private static ForgeSpawnEggItem spawnEgg(RegistryObject<? extends EntityType<?>> entityType, int bgColor, int spotColor) {
        return new ForgeSpawnEggItem(entityType, bgColor, spotColor, new Item.Properties().group(TGItemGroups.MISC));
    }
}
