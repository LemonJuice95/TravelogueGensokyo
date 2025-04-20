package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.common.container.ContainerPowerProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ClickButtonPacket {
    private final int operation;

    public ClickButtonPacket(Operations operation) {
        this.operation = operation.ordinal();
    }

    public ClickButtonPacket(PacketBuffer buffer) {
        this.operation = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.operation);
    }

    public static class Handler {
        public static void onMessage(ClickButtonPacket packet, Supplier<NetworkEvent.Context> ctx) {
            final ServerPlayerEntity player = ctx.get().getSender();
            Operations operation = Operations.values()[packet.operation];
            if(operation == Operations.INJECT_POWER) {
                if (player != null && player.openContainer instanceof ContainerPowerProvider) {
                    ((ContainerPowerProvider) player.openContainer).onPowerInject();
                }
            }
            ctx.get().setPacketHandled(true);
        }
    }

    public enum Operations {
        INJECT_POWER
    }
}
