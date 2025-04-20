package io.lemonjuice.tvlgensokyo.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class TGPlayerUtils {
    public static void changePlayerDimension(PlayerEntity player, RegistryKey<World> world, double x, double y, double z) {
        if(player.world instanceof ServerWorld && player.canChangeDimension() && player.world.getDimensionKey() != world) {
            ServerWorld dimension = player.getServer().getWorld(world);
            if (dimension != null) {
                player.changeDimension(dimension, new ITeleporter() {
                    @Override
                    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                        entity = repositionEntity.apply(false);
                        entity.teleportKeepLoaded(x, y, z);
                        return entity;
                    }
                });
            }
        }
    }

    public static void handlePowerRecovery(PlayerEntity player) {
        int powerRecovery = TGCapabilityUtils.getPowerRecovery(player);
        int powerRaw = TGCapabilityUtils.getPower(player);
        int maxPower = TGCapabilityUtils.getMaxPower(player);
        if(!player.world.isRemote && powerRaw < maxPower) {
            if(powerRaw + powerRecovery <= maxPower) {
                TGCapabilityUtils.addPower(player, powerRecovery);
            } else {
                TGCapabilityUtils.setPower(player, maxPower);
            }
        }
    }
}
