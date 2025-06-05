package io.lemonjuice.tvlgensokyo.common.network;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.network.toclient.SIntCapSyncPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CChangePlayerDimPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CItemSpecialScrollPacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CSetBookOpenStatePacket;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CWithdrawMoneyPacket;
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

        //Server Packets
        CHANNEL.registerMessage(id++, SIntCapSyncPacket.class, SIntCapSyncPacket::encode, SIntCapSyncPacket::new, SIntCapSyncPacket::handle);

        //Client Packets
        CHANNEL.registerMessage(id++, CItemSpecialScrollPacket.class, CItemSpecialScrollPacket::encode, CItemSpecialScrollPacket::new, CItemSpecialScrollPacket::handle);
        CHANNEL.registerMessage(id++, CSetBookOpenStatePacket.class, CSetBookOpenStatePacket::encode, CSetBookOpenStatePacket::new, CSetBookOpenStatePacket::handle);
        CHANNEL.registerMessage(id++, CChangePlayerDimPacket.class, CChangePlayerDimPacket::encode, CChangePlayerDimPacket::new, CChangePlayerDimPacket::handle);
        CHANNEL.registerMessage(id++, CWithdrawMoneyPacket.class, CWithdrawMoneyPacket::encode, CWithdrawMoneyPacket::new, CWithdrawMoneyPacket::handle);
    }
}
