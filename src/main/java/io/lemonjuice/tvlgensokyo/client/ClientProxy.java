package io.lemonjuice.tvlgensokyo.client;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScreen;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import io.lemonjuice.tvlgensokyo.common.CommonProxy;
import io.lemonjuice.tvlgensokyo.common.world.biome.TGBiomeRegister;
import io.lemonjuice.tvlgensokyo.utils.TGBiomeUtils;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class ClientProxy extends CommonProxy {
    private int powerBarCache;
    private float fogAmount;
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
                this.powerBarCache = Math.max(this.powerBarCache - frame, targetPower);
            else if (this.powerBarCache <= targetPower - frame)
                this.powerBarCache = MathHelper.clamp(this.powerBarCache + frame, 0, targetPower);
            else
                this.resetPowerBarCache();

        }
    }

    @Override
    public float getFogAmount() {
        Entity entity = MC.getRenderViewEntity();
        boolean flag = TGBiomeUtils.isSameBiome(entity.world.getBiome(entity.getPosition()), TGBiomeRegister.MISTY_LAKE);
        if(flag) {
            this.fogAmount = Math.min(this.fogAmount + (1.0F - this.fogAmount) / 400.0F, 1.0F);
        } else {
            this.fogAmount = Math.max(this.fogAmount - this.fogAmount / 400.0F, 0.0F);
            if(this.fogAmount < 0.01F)
                this.fogAmount = 0.0F;
        }
        return this.fogAmount;
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
