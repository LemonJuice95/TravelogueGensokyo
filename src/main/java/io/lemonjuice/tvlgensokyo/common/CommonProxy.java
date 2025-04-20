package io.lemonjuice.tvlgensokyo.common;

import io.lemonjuice.tvlgensokyo.client.ClientProxy;

public class CommonProxy {
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

    public void setChantingProgress(float chantingProgress) {

    }

    public void resetPowerBarCache() {

    }
}
