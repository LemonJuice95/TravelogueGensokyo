package io.lemonjuice.tvlgensokyo.client.gui.screen.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.container.ContainerPowerProvider;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.ClickButtonPacket;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PowerProviderContainerScreen extends ContainerScreen<ContainerPowerProvider> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/container/power_provider.png");

    public PowerProviderContainerScreen(ContainerPowerProvider container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.xSize = 176;
        this.ySize = 159;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    @Override
    protected void init() {
        this.addButton(new Button(this.width / 2 - 25, this.height / 2 - 35, 50, 20, new TranslationTextComponent("gui.tvlgensokyo.inject_power"), (button) -> {
            TGNetworkHandler.CHANNEL.sendToServer(new ClickButtonPacket(ClickButtonPacket.Operations.INJECT_POWER));
        }));
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack stack, float partialTicks, int mouseX, int mouseY) {
        stack.push();
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(stack, this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
        stack.pop();
    }
}
