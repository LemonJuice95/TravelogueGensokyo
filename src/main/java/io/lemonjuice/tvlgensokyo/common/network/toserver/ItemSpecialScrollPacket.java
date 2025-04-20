package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.common.item.interfaces.IScrollable;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ItemSpecialScrollPacket {
    private final double direction;

    public ItemSpecialScrollPacket(double direction) {
        this.direction = direction;
    }

    public ItemSpecialScrollPacket(PacketBuffer buffer) {
        this.direction = buffer.readDouble();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeDouble(this.direction);
    }

    public static class Handler {
        public static void onMessage(ItemSpecialScrollPacket packet, Supplier<NetworkEvent.Context> ctx) {
            final ServerPlayerEntity player = ctx.get().getSender();
            if(player != null) {
                ItemStack stack = player.getHeldItemMainhand();
                if(stack.getItem() instanceof IScrollable) {
                    ((IScrollable) stack.getItem()).specialScroll(stack, packet.direction);
                }
            }
        }
    }
}
