package io.lemonjuice.tvlgensokyo.common.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;

public abstract class TGContainerBase extends Container {
    public TGContainerBase(ContainerType<?> containerType, int id) {
        super(containerType, id);
    }

    public void addPlayersInventory(PlayerEntity player, int leftX, int upY) {
        //add Inventory
        for(int i = 0; i < 3; ++ i) {
            for(int j = 0; j < 9; ++ j) {
                this.addSlot(new Slot(player.inventory, j + i * 9 + 9, leftX + 18 * j, upY + 18 * i));
            }
        }

        //add HotBar
        for(int i = 0; i < 9; ++ i) {
            this.addSlot(new Slot(player.inventory, i, leftX + 18 * i, upY + 58));
        }
    }
}
