package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import io.lemonjuice.tvlgensokyo.common.CommonProxy;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ClientProxy extends CommonProxy {
    private int powerBarCache;
    private float chantingProgress;
    private static final Minecraft MC = Minecraft.getInstance();

    @Override
    public PlayerEntity getPlayer() {
        return MC.player;
    }

    @Override
    public void animPowerBar() {
        int targetPower = TGCapabilityUtils.getPower(MC.player);
        if(this.powerBarCache != targetPower) {
            int frame = MathHelper.ceil(TGCapabilityUtils.getMaxPower(MC.player) / 20.0F);
            if(this.powerBarCache >= targetPower)
                this.powerBarCache = MathHelper.clamp(this.powerBarCache - frame, targetPower, Integer.MAX_VALUE);
            else if (this.powerBarCache <= targetPower - frame)
                this.powerBarCache = MathHelper.clamp(this.powerBarCache + frame, 0, targetPower);
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
        this.powerBarCache = TGCapabilityUtils.getPower(MC.player);
    }

    @Override
    public void openDialogueGui(DialogueScript script) {
        MC.displayGuiScreen(new DialogueScreen(script));
    }
}
