package io.lemonjuice.tvlgensokyo.client.gui.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.utils.TGCapabilityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class PowerHUD extends AbstractGui {
    private final int windowWidth;
    private final int windowHeight;
    private final Minecraft MC;
    private static final ResourceLocation HUD = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/overlay_bars.png");

    private final int color;
    private final int filledPart;

    public PowerHUD(int power, boolean color) { //color: true to render green bar, and false to render red bar
        this.MC = Minecraft.getInstance();
        this.windowWidth = this.MC.getMainWindow().getScaledWidth();
        this.windowHeight = this.MC.getMainWindow().getScaledHeight();
        this.color = color ? 28 : 14;
        this.filledPart = (int) ((power * 1.0 / TGCapabilityUtils.getMaxPower(this.MC.player)) * 111) + 2;
    }

    @SuppressWarnings("deprecation")
    public void render(MatrixStack stack) {
        stack.push();
        RenderSystem.enableBlend();
        stack.scale(0.75F, 0.75F, 1.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.75F);

        this.MC.getTextureManager().bindTexture(HUD);

        this.blit(stack, this.windowWidth / 3 * 2 - 73, this.windowHeight / 3 * 2 + 14, 114, 0, 15, 15);
        this.blit(stack, this.windowWidth / 3 * 2 - 56 + this.filledPart, this.windowHeight / 3 * 2 + 15, this.filledPart, 0, 113 - this.filledPart, 13);
        this.blit(stack, this.windowWidth / 3 * 2 - 56, this.windowHeight / 3 * 2 + 15, 0, this.color, this.filledPart, 13);


        RenderSystem.disableBlend();
        stack.pop();
    }
}