package io.lemonjuice.tvlgensokyo.common;

import io.lemonjuice.tvlgensokyo.client.ClientProxy;
import io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue.DialogueScript;
import net.minecraft.entity.player.PlayerEntity;

public class CommonProxy {

    public PlayerEntity getPlayer() {
        return null;
    }

    /**Animate power bar at client side
     * {@link ClientProxy#animPowerBar()}
     */
    public void animPowerBar() {

    }

    /**This method should never be called at Server Side
     * {@link ClientProxy#getPowerBarLength()}
     */
    public int getPowerBarLength() {
        return 0;
    }

    /**This method should never be called at Server Side
     * {@link ClientProxy#getChantingProgress()}
     */
    public float getChantingProgress() {
        return 0.0F;
    }

    public float getFogAmount() {
        return 0.0F;
    }

    public void setChantingProgress(float chantingProgress) {

    }

    public void resetPowerBarCache() {

    }

    public void openDialogueGui(DialogueScript script) {

    }
}
