package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.common.container.BankInGapContainer;
import io.lemonjuice.tvlgensokyo.common.misc.CurrencyType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CWithdrawMoneyPacket {
    private final CurrencyType currencyType;
    private final int count;

    public CWithdrawMoneyPacket(CurrencyType currencyType, int count) {
        this.currencyType = currencyType;
        this.count = count;
    }

    public CWithdrawMoneyPacket(PacketBuffer buffer) {
        this.currencyType = CurrencyType.values()[buffer.readInt()];
        this.count = buffer.readInt();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.currencyType.ordinal());
        buffer.writeInt(this.count);
    }

    public static void handle(CWithdrawMoneyPacket packet, Supplier<NetworkEvent.Context> ctx) {
        final PlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            if(player.openContainer instanceof BankInGapContainer) {
                BankInGapContainer container = (BankInGapContainer) player.openContainer;
                container.withdrawCurrency(player, packet.currencyType, packet.count);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
