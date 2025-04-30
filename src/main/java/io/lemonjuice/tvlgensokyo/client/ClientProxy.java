package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.common.CommonProxy;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ClientProxy extends CommonProxy {
    private int powerBarCache;
    private float chantingProgress;
    private final Minecraft MC = Minecraft.getInstance();
    private PlayerEntity player = MC.player;

    @Override
    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public void animPowerBar() {
        int targetPower = TGCapabilityUtils.getPower(this.MC.player);
        if(this.powerBarCache != targetPower) {
            int frame = MathHelper.ceil(TGCapabilityUtils.getMaxPower(this.MC.player) / 20.0F);
            if(this.powerBarCache >= targetPower + frame)
                this.powerBarCache -= frame;
            else if (this.powerBarCache <= targetPower - frame)
                this.powerBarCache += frame;
            else
                this.resetPowerBarCache();

        }
    }

    @Override
    public int getPowerBarLength() {
        return this.powerBarCache;
    }

    @Override
    public float getChantingProgress() {
        return this.chantingProgress;
    }

    @Override
    public void setChantingProgress(float chantingProgress) {
        this.chantingProgress = chantingProgress;
    }

    @Override
    public void resetPowerBarCache() {
        this.powerBarCache = TGCapabilityUtils.getPower(this.MC.player);
    }
}
