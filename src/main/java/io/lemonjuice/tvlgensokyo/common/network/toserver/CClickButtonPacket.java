package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.common.container.PowerProviderContainer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CClickButtonPacket {
    private int operation;

    public CClickButtonPacket(Operations operation) {
        this.operation = operation.ordinal();
    }

    public CClickButtonPacket(PacketBuffer buffer) {
        this.operation = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.operation);
    }

    public static void handle(CClickButtonPacket packet, Supplier<NetworkEvent.Context> ctx) {
        final ServerPlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            Operations operation = Operations.values()[packet.operation];
            if (operation == Operations.INJECT_POWER) {
                if (player != null && player.openContainer instanceof PowerProviderContainer) {
                    ((PowerProviderContainer) player.openContainer).onPowerInject();
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public enum Operations {
        INJECT_POWER
    }
}
