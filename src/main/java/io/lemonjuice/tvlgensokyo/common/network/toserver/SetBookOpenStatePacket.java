package io.lemonjuice.tvlgensokyo.common.network.toserver;

import io.lemonjuice.tvlgensokyo.common.item.weapon.ItemSpellBook;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SetBookOpenStatePacket {
    private final boolean isOpened;

    public SetBookOpenStatePacket(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public SetBookOpenStatePacket(PacketBuffer buffer) {
        this.isOpened = buffer.readBoolean();
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeBoolean(this.isOpened);
    }

    public static class Handler {
        public static void onMessage(SetBookOpenStatePacket packet, Supplier<NetworkEvent.Context> ctx) {
            ServerPlayerEntity player = ctx.get().getSender();
            if(player != null) {
                ItemStack stack = player.getHeldItemMainhand();
                if(!(stack.getItem() instanceof ItemSpellBook))
                    stack = player.getHeldItemOffhand();
                if(stack.getItem() instanceof ItemSpellBook) {
                    ItemSpellBook.setOpened(stack, packet.isOpened);
                }
            }
        }
    }
}
