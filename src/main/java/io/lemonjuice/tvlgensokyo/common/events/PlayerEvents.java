package io.lemonjuice.tvlgensokyo.common.events;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.capability.TGCapabilityList;
import io.lemonjuice.tvlgensokyo.common.capability.IPlayerDataCapability;
import io.lemonjuice.tvlgensokyo.common.capability.PlayerDataCapabilityProvider;
import io.lemonjuice.tvlgensokyo.common.world.dimension.TGDimensionRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBlockUtils;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.utils.TGPlayerUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents {

    @SubscribeEvent
    public static void onCapabilityAttach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(TravelogueGensokyo.MODID, "player_data"), new PlayerDataCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if(!event.isWasDeath() || event.getOriginal().world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            LazyOptional<IPlayerDataCapability> oldPowerCap = event.getOriginal().getCapability(TGCapabilityList.PLAYER_DATA);
            LazyOptional<IPlayerDataCapability> newPowerCap = event.getPlayer().getCapability(TGCapabilityList.PLAYER_DATA);
            if (oldPowerCap.isPresent() && newPowerCap.isPresent()) {
                newPowerCap.ifPresent((newCap) -> {
                    oldPowerCap.ifPresent((oldCap) -> {
                        newCap.deserializeNBT(oldCap.serializeNBT());
                    });
                });
                TGCapabilityUtils.refreshPowerRecovery(event.getPlayer());
            }
        }

        TravelogueGensokyo.PROXY.resetPowerBarCache();
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if(!event.getPlayer().world.isRemote) {
            TGCapabilityUtils.syncCapabilities((ServerPlayerEntity) event.getPlayer());
            TGCapabilityUtils.refreshPowerRecovery(event.getPlayer());
        }

        TravelogueGensokyo.PROXY.resetPowerBarCache();
    }

    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if(!event.getPlayer().world.isRemote) {
            TGCapabilityUtils.syncCapabilities((ServerPlayerEntity) event.getPlayer());
            TGCapabilityUtils.refreshPowerRecovery(event.getPlayer());
        }


        TravelogueGensokyo.PROXY.resetPowerBarCache();
    }

    /*@SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if(player instanceof ServerPlayerEntity) {
            TGCapabilityUtils.syncCapabilities((ServerPlayerEntity) event.getPlayer());
            TGCapabilityUtils.refreshPowerRecovery(event.getPlayer());
        }
    }*/

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if(!player.world.isRemote) {
            TGPlayerUtils.handlePowerRecovery(player);
        }
        if(player.world.getDimensionKey() == TGDimensionRegister.DREAM_WORLD) {
            if(Math.abs(player.getPosX()) >= 150) {
                player.setPosition(-(player.getPosX() / Math.abs(player.getPosX())) * 149, player.getPosY(), player.getPosZ());
            }
            if(Math.abs(player.getPosZ()) >= 150) {
                player.setPosition(player.getPosX(), player.getPosY(), -(player.getPosZ() / Math.abs(player.getPosZ())) * 149);
            }
        }

        TravelogueGensokyo.PROXY.animPowerBar();
    }
    
    @SubscribeEvent
    public static void onPlayerUseTool(BlockEvent.BlockToolInteractEvent event) {
        if(event.getHeldItemStack().getItem() instanceof AxeItem) {
            BlockState newState = TGBlockUtils.getLogStripped(event.getState());
            if(newState != null) {
                event.setFinalState(newState);
            }
        }
    }

}
