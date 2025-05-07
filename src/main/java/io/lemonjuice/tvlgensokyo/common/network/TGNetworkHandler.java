package io.lemonjuice.tvlgensokyo.common.network;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.network.toclient.SIntCapSyncPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CClickButtonPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CItemSpecialScrollPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CSetBookOpenStatePacket;
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
        CHANNEL.registerMessage(id++, SIntCapSyncPacket.class, SIntCapSyncPacket::encode, SIntCapSyncPacket::new, SIntCapSyncPacket::handle);
        CHANNEL.registerMessage(id++, CClickButtonPacket.class, CClickButtonPacket::encode, CClickButtonPacket::new, CClickButtonPacket::handle);
        CHANNEL.registerMessage(id++, CItemSpecialScrollPacket.class, CItemSpecialScrollPacket::encode, CItemSpecialScrollPacket::new, CItemSpecialScrollPacket::handle);
        CHANNEL.registerMessage(id++, CSetBookOpenStatePacket.class, CSetBookOpenStatePacket::encode, CSetBookOpenStatePacket::new, CSetBookOpenStatePacket::handle);
    }
}
