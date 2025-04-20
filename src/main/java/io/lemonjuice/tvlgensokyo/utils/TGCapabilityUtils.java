package io.lemonjuice.tvlgensokyo.utils;

import io.lemonjuice.tvlgensokyo.api.interfaces.ITGCapabilityPacket;
import io.lemonjuice.tvlgensokyo.common.capability.IPlayerDataCapability;
import io.lemonjuice.tvlgensokyo.common.capability.TGCapabilityList;
import io.lemonjuice.tvlgensokyo.common.capability.PlayerDataManager;
import io.lemonjuice.tvlgensokyo.common.misc.TGGameEvent;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toclient.IntCapPacketToClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Optional;

public class TGCapabilityUtils {

    public static final int BASE_POWER_RECOVERY = 5;
    public static final int MAX_POWER = 500;

    public static PlayerDataManager getManager(PlayerEntity player) {
        LazyOptional<IPlayerDataCapability> optCap = player.getCapability(TGCapabilityList.PLAYER_DATA);
        if(optCap.isPresent()) {
            return optCap.orElse(null).getManager();
        }
        return PlayerDataManager.DUMMY;
    }

    public static void addPower(PlayerEntity player, int power) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            manager.addPower(power);
            syncToClient((ServerPlayerEntity) player, new IntCapPacketToClient(IntCapPacketToClient.Capability.POWER, manager.getPower()));
        }
    }

    public static void setPower(PlayerEntity player, int power) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            manager.setPower(power);
            syncToClient((ServerPlayerEntity) player, new IntCapPacketToClient(IntCapPacketToClient.Capability.POWER, power));
        }
    }

    public static int getPower(PlayerEntity player) {
        if(player != null && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            return manager.getPower();
        }

        return 0;
    }

    public static int getPowerRecovery(PlayerEntity player) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            return manager.getPowerRecovery();
        }

        return BASE_POWER_RECOVERY;
    }

    public static void refreshPowerRecovery(PlayerEntity player) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            manager.setPowerRecovery(TGMathUtils.calculatePowerRecovery(player));
        }
    }

    public static int getMaxPower(PlayerEntity player) {
        if(player != null && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            return manager.getMaxPower();
        }

        return MAX_POWER;
    }

    public static void setMaxPower(PlayerEntity player, int maxPower) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            manager.setMaxPower(maxPower);
            syncToClient((ServerPlayerEntity) player, new IntCapPacketToClient(IntCapPacketToClient.Capability.MAX_POWER, maxPower));
        }
    }

    public static void setProgress(PlayerEntity player, TGGameEvent event, int progress) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            manager.setEventProgress(event, progress);
        }
    }

    public static int getProgress(PlayerEntity player, TGGameEvent event) {
        if(player instanceof ServerPlayerEntity && player.isAlive()) {
            PlayerDataManager manager = getManager(player);
            return manager.getEventProgress(event);
        }

        return 0;
    }

    public static void syncCapabilities(ServerPlayerEntity player) {
        syncToClient(player, new IntCapPacketToClient(IntCapPacketToClient.Capability.POWER, getManager(player).getPower()));
        syncToClient(player, new IntCapPacketToClient(IntCapPacketToClient.Capability.MAX_POWER, getManager(player).getMaxPower()));
    }

    public static void syncToClient(ServerPlayerEntity player, ITGCapabilityPacket packet) {
        TGNetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> {
            return player;
        }), packet);
    }
}
