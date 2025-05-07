package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.utils.TGPlayerUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CChangePlayerDimPacket {
    private RegistryKey<World> worldKey;
    private BlockPos targetPos;

    public CChangePlayerDimPacket(RegistryKey<World> worldKey, BlockPos targetPos) {
        this.worldKey = worldKey;
        this.targetPos = targetPos;
    }

    public CChangePlayerDimPacket(RegistryKey<World> worldKey, double targetX, double targetY, double targetZ) {
        this.worldKey = worldKey;
        this.targetPos = new BlockPos(targetX, targetY, targetZ);
    }

    public CChangePlayerDimPacket(PacketBuffer buffer) {
        this.worldKey = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, buffer.readResourceLocation());
        this.targetPos = buffer.readBlockPos();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeResourceLocation(this.worldKey.getLocation());
        buffer.writeBlockPos(this.targetPos);
    }

    public static void handle(CChangePlayerDimPacket packet, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            TGPlayerUtils.changePlayerDimension(player, packet.worldKey, packet.targetPos.getX(), packet.targetPos.getY(), packet.targetPos.getZ());
        });
        ctx.get().setPacketHandled(true);
    }
}
