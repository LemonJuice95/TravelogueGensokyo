package io.lemonjuice.tvlgensokyo.client.gui.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.container.SpellBookBindingTableContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class SpellBookBindingTableContainerScreen extends ContainerScreen<SpellBookBindingTableContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/container/spell_book_binding_table.png");

    public SpellBookBindingTableContainerScreen(SpellBookBindingTableContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.xSize = 176;
        this.ySize = 170;
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
        int slotCount = this.container.getSpellPageSlotCount();
        if(slotCount > 0) {
            if(slotCount <= 4) {
                blit(matrixStack, this.guiLeft + 78, this.guiTop + 21, 99, 190, 61, 46);
                for(int i = 0; i < slotCount; i++) {
                    blit(matrixStack, this.guiLeft + 86 + i / 2 * 27, this.guiTop + (i % 2 == 0 ? 26 : 44), 0, 171, 18, 18);
                }
            } else {
                blit(matrixStack, this.guiLeft + 60, this.guiTop + 21, 0, 190, 97, 46);
                for(int i = 0; i < slotCount - 4; i++) {
                    blit(matrixStack, this.guiLeft + 113 + i / 2 * 18, this.guiTop + (i % 2 == 0 ? 26 :44), 0, 171, 18, 18);
                }
            }
        }
        matrixStack.pop();
    }
}
