package io.lemonjuice.tvlgensokyo.common.capability;

import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import io.lemonjuice.tvlgensokyo.common.misc.TGGameEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {
    public static final PlayerDataManager DUMMY = new PlayerDataManager();

    private int power;
    private int powerRecovery;
    private int maxPower;
    private final Map<TGGameEvent, Integer> eventProgress = new HashMap<>();

    public PlayerDataManager() {
        this.power = 0;
        this.powerRecovery = TGCapabilityUtils.BASE_POWER_RECOVERY;
        this.maxPower = TGCapabilityUtils.MAX_POWER;
        for(TGGameEvent i : TGGameEvent.values()) {
            eventProgress.put(i, 0);
        }
    }

    public int getPowerRecovery() {
        return this.powerRecovery;
    }

    public void setPowerRecovery(int powerRecovery) {
        if(this != DUMMY)
            this.powerRecovery = powerRecovery;
    }

    public int getPower() {
        return this.power;
    }

    public void setPower(int power) {
        if(this != DUMMY)
            this.power = power >= 0 && power <= this.maxPower ? power : this.power;
    }

    public void addPower(int power) {
        int power_ = this.power + power;
        if(power_ > this.maxPower)
            this.power = this.maxPower;
        else if(power_ < 0)
            this.power = 0;
        else
            this.power = power_;
    }

    public void setMaxPower(int maxPower) {
        if(this != DUMMY)
            this.maxPower = maxPower;
    }

    public int getMaxPower() {
        return this.maxPower;
    }

    public int getEventProgress(TGGameEvent event) {
        return eventProgress.get(event);
    }

    public void setEventProgress(TGGameEvent event, int progress) {
        if(this != DUMMY)
            this.eventProgress.put(event, progress);
    }
}
