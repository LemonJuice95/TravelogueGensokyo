package io.lemonjuice.tvlgensokyo;

import io.lemonjuice.tvlgensokyo.client.ClientProxy;
import io.lemonjuice.tvlgensokyo.common.CommonProxy;
import io.lemonjuice.tvlgensokyo.common.block.TGBlockRegister;
import io.lemonjuice.tvlgensokyo.common.block.tileentity.TGTileEntityRegister;
import io.lemonjuice.tvlgensokyo.common.capability.TGCapabilityRegister;
import io.lemonjuice.tvlgensokyo.common.container.TGContainerRegister;
import io.lemonjuice.tvlgensokyo.common.enchantments.TGEnchantmentRegister;
import io.lemonjuice.tvlgensokyo.common.entity.TGEntityRegister;
import io.lemonjuice.tvlgensokyo.common.item.TGItemRegister;
import io.lemonjuice.tvlgensokyo.common.item.crafting.TGRecipeRegister;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.common.world.dimension.TGDimensionRegister;
import io.lemonjuice.tvlgensokyo.common.world.feature.TGFeatureRegister;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.decorator.TGTreeDecoratorTypes;
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.placer.TGTreePlacerRegister;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.SlotTypeMessage;

@Mod("tvlgensokyo")
public class TravelogueGensokyo {
    public static final String MODID = "tvlgensokyo";
    public static final Logger LOGGER = LogManager.getLogger();

    public static final CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public TravelogueGensokyo() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::enqueueIMC);
        bus.addListener(this::processIMC);

        TGBlockRegister.BLOCKS.register(bus);
        TGItemRegister.ITEMS.register(bus);
        TGEntityRegister.ENTITIES.register(bus);
        TGEnchantmentRegister.ENCHANTMENTS.register(bus);
        TGContainerRegister.CONTAINERS.register(bus);
        TGBiomeRegister.BIOMES.register(bus);
        TGTreePlacerRegister.FOLIAGE_PLACER_TYPES.register(bus);
        TGTileEntityRegister.TILE_ENTITIES.register(bus);
        TGRecipeRegister.RECIPE_SERIALIZERS.register(bus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        TGCapabilityRegister.capabilitiesRegistry();
        TGNetworkHandler.packetsRegistry();
        TGItemRegister.registerCompostable();
        TGDimensionRegister.register(event);
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("tg_wings").size(1).priority(1).build());
        InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, () -> new SlotTypeMessage.Builder("tg_accessories").priority(2).size(5).build());
    }

    public void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onFeatureRegistry(final RegistryEvent.Register<Feature<?>> event) {
            TGFeatureRegister.registerFeatures();
        }

        @SubscribeEvent
        public static void onTreeDecoratorTypeRegistry(final RegistryEvent.Register<TreeDecoratorType<?>> event) {
            TGTreeDecoratorTypes.registerDecorators();
        }
    }
}
