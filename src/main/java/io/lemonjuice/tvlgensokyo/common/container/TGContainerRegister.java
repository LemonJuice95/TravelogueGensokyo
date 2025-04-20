package io.lemonjuice.tvlgensokyo.common.container;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.PowerProviderContainerScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.SpellBookBindingTableContainerScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.SpellWritingTableContainerScreen;
import io.lemonjuice.tvlgensokyo.common.block.tileentity.SpellBookBindingTableTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGContainerRegister {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TravelogueGensokyo.MODID);

    public static final RegistryObject<ContainerType<ContainerPowerProvider>> POWER_PROVIDER = CONTAINERS.register("power_provider", () -> {
        return IForgeContainerType.create((windowId, inv, data) -> new ContainerPowerProvider(windowId, inv.player, data.readBoolean()));
    });

    public static final RegistryObject<ContainerType<ContainerSpellBookBindingTable>> SPELL_BOOK_BINDING_TABLE = CONTAINERS.register("spell_book_binding_table", () -> {
       return IForgeContainerType.create(((windowId, inv, data) -> {
           World world = Minecraft.getInstance().world;
           BlockPos pos = data.readBlockPos();
           TileEntity tileEntity = world.getTileEntity(pos);
           return new ContainerSpellBookBindingTable(windowId, inv.player, (SpellBookBindingTableTileEntity) tileEntity);
       }));
    });

    public static final RegistryObject<ContainerType<ContainerSpellWritingTable>> SPELL_WRITING_TABLE = CONTAINERS.register("spell_writing_table", () -> {
        return IForgeContainerType.create((windowId, inv, data) -> {
            World world = Minecraft.getInstance().world;
            return new ContainerSpellWritingTable(windowId, inv.player, IWorldPosCallable.DUMMY);
        });
    });


    public static void registerContainerScreens() {
        ScreenManager.registerFactory(POWER_PROVIDER.get(), PowerProviderContainerScreen::new);
        ScreenManager.registerFactory(SPELL_BOOK_BINDING_TABLE.get(), SpellBookBindingTableContainerScreen::new);
        ScreenManager.registerFactory(SPELL_WRITING_TABLE.get(), SpellWritingTableContainerScreen::new);
    }
}
