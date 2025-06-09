package io.lemonjuice.tvlgensokyo.common.block.tileentity;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGTileEntityRegister {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TravelogueGensokyo.MODID);

    public static final RegistryObject<TileEntityType<SweetBedTileEntity>> SWEET_BED = TILE_ENTITIES.register("sweet_bed", () -> TileEntityType.Builder.create(SweetBedTileEntity::new,
                    TGBlockRegister.SWEET_BED_WHITE.get(),
                    TGBlockRegister.SWEET_BED_ORANGE.get(),
                    TGBlockRegister.SWEET_BED_MAGENTA.get(),
                    TGBlockRegister.SWEET_BED_LIGHT_BLUE.get(),
                    TGBlockRegister.SWEET_BED_YELLOW.get(),
                    TGBlockRegister.SWEET_BED_LIME.get(),
                    TGBlockRegister.SWEET_BED_PINK.get(),
                    TGBlockRegister.SWEET_BED_GRAY.get(),
                    TGBlockRegister.SWEET_BED_LIGHT_GRAY.get(),
                    TGBlockRegister.SWEET_BED_CYAN.get(),
                    TGBlockRegister.SWEET_BED_PURPLE.get(),
                    TGBlockRegister.SWEET_BED_BLUE.get(),
                    TGBlockRegister.SWEET_BED_BROWN.get(),
                    TGBlockRegister.SWEET_BED_GREEN.get(),
                    TGBlockRegister.SWEET_BED_RED.get(),
                    TGBlockRegister.SWEET_BED_BLACK.get())
            .build(null));

    public static final RegistryObject<TileEntityType<TGSignTileEntity>> TG_SIGN = TILE_ENTITIES.register("sign", () -> TileEntityType.Builder.create(TGSignTileEntity::new,
                    TGBlockRegister.MAPLE_SIGN.get(),
                    TGBlockRegister.MAPLE_WALL_SIGN.get(),
                    TGBlockRegister.SAKURA_SIGN.get(),
                    TGBlockRegister.SAKURA_WALL_SIGN.get())
            .build(null));

    public static final RegistryObject<TileEntityType<StoneMortarTileEntity>> STONE_MORTAR = TILE_ENTITIES.register("stone_mortar", () -> TileEntityType.Builder.create(StoneMortarTileEntity::new,
                    TGBlockRegister.STONE_MORTAR.get())
            .build(null));


    public static final RegistryObject<TileEntityType<SpellBookBindingTableTileEntity>> SPELL_BOOK_BINDING_TABLE = TILE_ENTITIES.register("spell_book_binding_table", () -> TileEntityType.Builder.create(SpellBookBindingTableTileEntity::new, TGBlockRegister.SPELL_BOOK_BINDING_TABLE.get()).build(null));

}