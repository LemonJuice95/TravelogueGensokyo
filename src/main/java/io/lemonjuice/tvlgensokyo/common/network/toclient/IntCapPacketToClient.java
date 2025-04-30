package io.lemonjuice.tvlgensokyo.common.network.toclient;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.api.interfaces.ITGCapabilityPacket;
import io.lemonjuice.tvlgensokyo.common.capability.PlayerDataManager;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class IntCapPacketToClient implements ITGCapabilityPacket {
    private Capability cap;
    private int num;

    public IntCapPacketToClient(Capability cap ,int num) {
        this.cap = cap;
        this.num = num;
    }

    public IntCapPacketToClient(PacketBuffer buffer) {
        this.cap = Capability.values()[buffer.readInt()];
        this.num = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.cap.ordinal());
        buffer.writeInt(this.num);
    }

    public static class Handler {
        public static void onMessage(IntCapPacketToClient packetToClient, Supplier<NetworkEvent.Context> ctx) {
            PlayerDataManager manager = TGCapabilityUtils.getManager(TravelogueGensokyo.PROXY.getPlayer());
            ctx.get().enqueueWork(() -> {
                if(packetToClient.cap == Capability.POWER)
                    manager.setPower(packetToClient.num);
                if(packetToClient.cap == Capability.MAX_POWER)
                    manager.setMaxPower(packetToClient.num);
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public enum Capability {
        POWER,
        MAX_POWER
    }
}