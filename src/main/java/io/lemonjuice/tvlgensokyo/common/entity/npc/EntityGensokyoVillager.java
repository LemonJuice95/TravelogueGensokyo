package io.lemonjuice.tvlgensokyo.common.entity.npc;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.World;

public class EntityGensokyoVillager extends VillagerEntity {
    public EntityGensokyoVillager(EntityType<? extends EntityGensokyoVillager> entityType, World world) {
        super(entityType, world);
    }


    //TODO Overwrite the trade data
    @Override
    protected void populateTradeData() {
        super.populateTradeData();
    }
}
