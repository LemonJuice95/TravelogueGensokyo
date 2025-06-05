package io.lemonjuice.tvlgensokyo.client.gui.screen.container;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import io.lemonjuice.tvlgensokyo.common.container.BankInGapContainer;
import io.lemonjuice.tvlgensokyo.common.misc.CurrencyType;
import io.lemonjuice.tvlgensokyo.common.network.TGNetworkHandler;
import io.lemonjuice.tvlgensokyo.common.network.toserver.CWithdrawMoneyPacket;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BankInGapContainerScreen extends ContainerScreen<BankInGapContainer> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/container/bank_in_gap.png");

    public BankInGapContainerScreen(BankInGapContainer container, PlayerInventory inventory, ITextComponent title) {
        super(container, inventory, title);
        this.xSize = 176;
        this.ySize = 181;
        this.playerInventoryTitleY = this.ySize - 94;
    }

    @Override
    protected void init() {
        super.init();

        this.addButton(new WithDrawButton(this.guiLeft + 82, this.guiTop + 16, CurrencyType.COPPER_COIN));
        this.addButton(new WithDrawButton(this.guiLeft + 82, this.guiTop + 39, CurrencyType.SILVER_PIECE));
        this.addButton(new WithDrawButton(this.guiLeft + 82, this.guiTop + 62, CurrencyType.SILVER_INGOT));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    private int getWithdrawingCount() {
        if (hasShiftDown()) {
            return 10;
        }
        if(hasControlDown()) {
            return 64;
        }

        return 1;
    }

    @Override
    protected void renderHoveredTooltip(MatrixStack matrixStack, int x, int y) {
        if(this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot == this.container.getSlot(0))
            this.renderWrappedToolTip(matrixStack, ImmutableList.of(new TranslationTextComponent("gui.tvlgensokyo.deposit")), x, y, this.minecraft.fontRenderer);
        else
            super.renderHoveredTooltip(matrixStack, x, y);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        matrixStack.push();
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        blit(matrixStack, this.guiLeft, this.guiTop,0, 0, this.xSize, this.ySize);
        drawCenteredString(matrixStack, this.minecraft.fontRenderer, new TranslationTextComponent("gui.tvlgensokyo.balance", this.container.getBalance()), this.guiLeft + 36, this.guiTop + 68, 0xffffff);
        matrixStack.pop();
    }

    public class WithDrawButton extends Button {
        private final CurrencyType currencyType;
        private final int value;

        public WithDrawButton(int x, int y, CurrencyType currencyType) {
            super(x, y, 75, 20, new TranslationTextComponent("gui.tvlgensokyo.withdraw_" + currencyType.toString().toLowerCase(), getWithdrawingCount()), (button) -> {
                TGNetworkHandler.CHANNEL.sendToServer(new CWithdrawMoneyPacket(currencyType, getWithdrawingCount()));
            });

            this.currencyType = currencyType;
            this.value = CurrencyType.CURRENCY_MAP.get(currencyType).value;
        }

        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            this.setMessage(new TranslationTextComponent("gui.tvlgensokyo.withdraw_" + currencyType.toString().toLowerCase(), getWithdrawingCount()));
            this.active = getWithdrawingCount() * this.value <= container.getBalance();
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        }
    }
}
