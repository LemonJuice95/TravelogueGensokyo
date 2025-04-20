package io.lemonjuice.tvlgensokyo.client.gui.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.container.ContainerSpellWritingTable;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SpellWritingTableContainerScreen extends ContainerScreen<ContainerSpellWritingTable> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/container/spell_writing_table.png");

    public SpellWritingTableContainerScreen(ContainerSpellWritingTable container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.xSize = 176;
        this.ySize = 215;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        matrixStack.push();

        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(matrixStack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        if(!this.container.getSlot(1).getHasStack()) {
            blit(matrixStack, this.guiLeft + 55, this.guiTop + 59, 0, 217, 18, 18);
        }

        matrixStack.pop();
    }
}
