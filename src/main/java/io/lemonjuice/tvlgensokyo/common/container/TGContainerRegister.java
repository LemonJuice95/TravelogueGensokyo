package io.lemonjuice.tvlgensokyo.common.container;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.BankInGapContainerScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.SpellBookBindingTableContainerScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.container.SpellWritingTableContainerScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TGContainerRegister {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TravelogueGensokyo.MODID);

    public static final RegistryObject<ContainerType<PowerProviderContainer>> POWER_PROVIDER = CONTAINERS.register("power_provider", () -> {
        return IForgeContainerType.create((windowId, inv, data) -> new PowerProviderContainer(windowId, inv.player, data.readBoolean()));
    });

    public static final RegistryObject<ContainerType<SpellBookBindingTableContainer>> SPELL_BOOK_BINDING_TABLE = CONTAINERS.register("spell_book_binding_table", () -> {
       return IForgeContainerType.create(((windowId, inv, data) -> {
           return new SpellBookBindingTableContainer(windowId, inv.player, data.readBlockPos());
       }));
    });

    public static final RegistryObject<ContainerType<SpellWritingTableContainer>> SPELL_WRITING_TABLE = CONTAINERS.register("spell_writing_table", () -> {
        return IForgeContainerType.create((windowId, inv, data) -> {
            return new SpellWritingTableContainer(windowId, inv.player, IWorldPosCallable.DUMMY);
        });
    });

    public static final RegistryObject<ContainerType<BankInGapContainer>> BANK_IN_GAP = CONTAINERS.register("bank_in_gap", () -> {
        return IForgeContainerType.create(((windowId, inv, data) -> {
            return new BankInGapContainer(windowId, inv.player, Hand.values()[data.readInt()]);
        }));
    });


    public static void registerContainerScreens() {
//        ScreenManager.registerFactory(POWER_PROVIDER.get(), PowerProviderContainerScreen::new);
        ScreenManager.registerFactory(SPELL_BOOK_BINDING_TABLE.get(), SpellBookBindingTableContainerScreen::new);
        ScreenManager.registerFactory(SPELL_WRITING_TABLE.get(), SpellWritingTableContainerScreen::new);
        ScreenManager.registerFactory(BANK_IN_GAP.get(), BankInGapContainerScreen::new);
    }
}
