package io.lemonjuice.tvlgensokyo.client.gui.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;

public class SpellChantHUD extends AbstractGui {
    private final int windowWidth;
    private final int windowHeight;
    private final Minecraft MC;
    private final ResourceLocation HUD = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/overlay_bars.png");
    private final int yOffset;
    private final ITextComponent spellName;

    private final int filledPart;

    public SpellChantHUD(float chantProgress, boolean isCreative, ITextComponent spellName) {
        this.MC = Minecraft.getInstance();
        this.windowWidth = this.MC.getMainWindow().getScaledWidth();
        this.windowHeight = this.MC.getMainWindow().getScaledHeight();
        this.filledPart = (int) (chantProgress * 111) + 2;
        this.yOffset = isCreative ? -17 : 0;
        this.spellName = spellName;
    }

    @SuppressWarnings("deprecation")
    public void render(MatrixStack stack) {
        stack.push();
        RenderSystem.enableBlend();
        stack.scale(0.75F, 0.75F, 1.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.75F);

        this.MC.getTextureManager().bindTexture(HUD);

        this.blit(stack, this.windowWidth / 3 * 2 - 73, this.windowHeight / 3 * 2 + 31 + this.yOffset, 130, 0, 15, 15);
        this.blit(stack, this.windowWidth / 3 * 2 - 56 + this.filledPart, this.windowHeight / 3 * 2 + 32 + this.yOffset, this.filledPart, 0, 113 - this.filledPart, 13);
        this.blit(stack, this.windowWidth / 3 * 2 - 56, this.windowHeight / 3 * 2 + 32 + this.yOffset, 0, 42, this.filledPart, 13);

        this.MC.fontRenderer.func_238422_b_(stack, this.spellName.func_241878_f(), this.windowWidth / 3 * 2 - this.MC.fontRenderer.func_243245_a(spellName.func_241878_f()) / 2, this. windowHeight / 3 * 2 + 34.5F + this.yOffset, Color.MAGENTA.getRGB());

        RenderSystem.disableBlend();
        stack.pop();
    }
}
