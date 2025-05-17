package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.common.item.weapon.SpellBookItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CSetBookOpenStatePacket {
    private boolean isOpened;

    public CSetBookOpenStatePacket(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public CSetBookOpenStatePacket(PacketBuffer buffer) {
        this.isOpened = buffer.readBoolean();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(this.isOpened);
    }

    public static void handle(CSetBookOpenStatePacket packet, Supplier<NetworkEvent.Context> ctx) {
        ServerPlayerEntity player = ctx.get().getSender();
        ctx.get().enqueueWork(() -> {
            if (player != null) {
                ItemStack stack = player.getHeldItemMainhand();
                if (!(stack.getItem() instanceof SpellBookItem))
                    stack = player.getHeldItemOffhand();
                if (stack.getItem() instanceof SpellBookItem) {
                    SpellBookItem.setOpened(stack, packet.isOpened);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
