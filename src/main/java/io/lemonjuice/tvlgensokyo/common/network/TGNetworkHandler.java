package io.lemonjuice.tvlgensokyo.common.network;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.network.toclient.IntCapPacketToClient;
import io.lemonjuice.tvlgensokyo.common.network.toserver.ClickButtonPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.ItemSpecialScrollPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TGNetworkHandler {
    private static final ResourceLocation CHANNEL_NAME = new ResourceLocation(TravelogueGensokyo.MODID + ":networking");
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            CHANNEL_NAME,
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void packetsRegistry() {
        int id = 0;
        CHANNEL.registerMessage(id++, IntCapPacketToClient.class, IntCapPacketToClient::encode, IntCapPacketToClient::new, IntCapPacketToClient.Handler::onMessage);
        CHANNEL.registerMessage(id++, ClickButtonPacket.class, ClickButtonPacket::encode, ClickButtonPacket::new, ClickButtonPacket.Handler::onMessage);
        CHANNEL.registerMessage(id++, ItemSpecialScrollPacket.class, ItemSpecialScrollPacket::encode, ItemSpecialScrollPacket::new, ItemSpecialScrollPacket.Handler::onMessage);
    }
}
