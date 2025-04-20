package io.lemonjuice.tvlgensokyo;

import io.lemonjuice.tvlgensokyo.client.ClientProxy;
import io.lemonjuice.tvlgensokyo.client.TGItemModelsProperties;
import io.lemonjuice.tvlgensokyo.client.input.TGInputRegister;
import io.lemonjuice.tvlgensokyo.client.renderer.block.TGBlockRenderHandler;
import io.lemonjuice.tvlgensokyo.client.renderer.entity.TGEntityRendererRegister;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.DreamWorldRenderInfo;
import io.lemonjuice.tvlgensokyo.client.renderer.environment.GensokyoRenderInfo;
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
import io.lemonjuice.tvlgensokyo.common.world.feature.tree.TGTreeDecoratorTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("tvlgensokyo")
public class TravelogueGensokyo {
    public static final String MODID = "tvlgensokyo";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Minecraft MC = Minecraft.getInstance();

    public static final CommonProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    public TravelogueGensokyo() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the setup method for modloading
        bus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        bus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        bus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        bus.addListener(this::doClientStuff);

        TGItemRegister.ITEMS.register(bus);
        TGEntityRegister.ENTITIES.register(bus);
        TGEnchantmentRegister.ENCHANTMENTS.register(bus);
        TGContainerRegister.CONTAINERS.register(bus);
        TGBlockRegister.BLOCKS.register(bus);
        TGBiomeRegister.BIOMES.register(bus);
        TGTileEntityRegister.TILE_ENTITIES.register(bus);
        TGRecipeRegister.RECIPE_SERIALIZERS.register(bus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        TGCapabilityRegister.capabilitiesRegistry();
        TGNetworkHandler.packetsRegistry();
        TGDimensionRegister.register(event);
        TGItemRegister.registerCompostable();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        TGContainerRegister.registerContainerScreens();
        TGItemModelsProperties.registerProperties();
        TGEntityRendererRegister.entityRenderersRegistry();
        TGBlockRenderHandler.registerBlockRenderType();
        TGBlockRenderHandler.registerTileEntityRenderer();
        TGInputRegister.registerInputs();

        DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(MODID, "dream_world"), new DreamWorldRenderInfo());
        DimensionRenderInfo.field_239208_a_.put(new ResourceLocation(MODID, "gensokyo"), new GensokyoRenderInfo());
    }



    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
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
