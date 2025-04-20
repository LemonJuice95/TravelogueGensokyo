package io.lemonjuice.tvlgensokyo.common.capability;

import io.lemonjuice.tvlgensokyo.common.misc.TGGameEvent;
import net.minecraft.nbt.CompoundNBT;

public class PlayerDataCapability implements IPlayerDataCapability {
    private final PlayerDataManager dataManager;

    public PlayerDataCapability() {
        this.dataManager = new PlayerDataManager();
    }

    @Override
    public PlayerDataManager getManager() {
        return this.dataManager;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("Power", this.dataManager.getPower());
        nbt.putInt("PowerRecovery", this.dataManager.getPowerRecovery());
        CompoundNBT data = new CompoundNBT();
        for(TGGameEvent i : TGGameEvent.values()) {
            data.putInt(i.toString(), this.dataManager.getEventProgress(i));
        }
        nbt.put("EventProgress", data);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if(nbt.contains("Power")) {
            this.dataManager.setPower(nbt.getInt("Power"));
        }
        if(nbt.contains("PowerRecovery")) {
            this.dataManager.setPowerRecovery(nbt.getInt("PowerRecovery"));
        }
        if(nbt.contains("EventProgress")) {
            CompoundNBT data = nbt.getCompound("EventProgress");
            for(TGGameEvent i : TGGameEvent.values()) {
                if(data.contains(i.toString())) {
                    this.dataManager.setEventProgress(i, data.getInt(i.toString()));
                }
            }
        }
    }
}
