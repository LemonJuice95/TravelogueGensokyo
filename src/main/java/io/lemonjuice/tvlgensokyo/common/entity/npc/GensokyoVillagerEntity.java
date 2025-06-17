package io.lemonjuice.tvlgensokyo.common.entity.npc;

import io.lemonjuice.tvlgensokyo.common.misc.GensokyoVillagerTrades;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerData;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.item.MerchantOffers;
import net.minecraft.world.World;

import java.util.Optional;

//TODO 农业方面基本完成后添加修改过后的FarmTask
public class GensokyoVillagerEntity extends VillagerEntity {
    public GensokyoVillagerEntity(EntityType<? extends GensokyoVillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void populateTradeData() {
        VillagerData villagerData = this.getVillagerData();
        Optional<Int2ObjectMap<VillagerTrades.ITrade[]>> optTradeMap = Optional.ofNullable(GensokyoVillagerTrades.GENSOKYO_VILLAGER_DEFAULT_TRADES.get(villagerData.getProfession()));
        if(!optTradeMap.isPresent()) {
            super.populateTradeData();
        } else {
            Int2ObjectMap<VillagerTrades.ITrade[]> tradeMap = optTradeMap.get();
            if (!tradeMap.isEmpty()) {
                VillagerTrades.ITrade[] avillagertrades$itrade = tradeMap.get(villagerData.getLevel());
                if (avillagertrades$itrade != null) {
                    MerchantOffers merchantoffers = this.getOffers();
                    this.addTrades(merchantoffers, avillagertrades$itrade, 2);
                }
            }
        }
    }
}
