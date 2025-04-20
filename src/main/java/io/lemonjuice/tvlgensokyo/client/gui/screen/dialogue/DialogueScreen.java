package io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

public class DialogueScreen extends Screen {
    private static final ResourceLocation SCREEN_TEXTURE = new ResourceLocation(TravelogueGensokyo.MODID, "textures/gui/dialogue.png");

    private final DialogueScript script;
    private String currentPage;
    private int buttonPage;

    public DialogueScreen(DialogueScript script) {
        super(new StringTextComponent(""));
        this.script = script;
        this.currentPage = script.startPage;
        this.buttonPage = 1;
    }

    @Override
    protected void init() {
        this.refreshButtons();
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(stack);

        stack.push();
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 0.75f);
        this.minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
        stack.scale(1.5f, 1.5f, 1.0f);
        blit(stack, this.width / 3 - 88, this.height / 3 - 70, 0, 0, 176, 98);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        stack.pop();
        stack.push();
        stack.scale(0.8f, 0.8f, 1.0f);
        List<IReorderingProcessor> messageLines = this.font.trimStringToWidth(this.script.getPage(this.currentPage).message, 300);
        int n = messageLines.size();
        for(int i = 0; i < n; i++) {
            IReorderingProcessor line = messageLines.get(i);
            this.font.func_238422_b_(stack, line, (float) this.width / 8 * 5 - 153, (float) this.height / 8 * 5 - 120 + i * 9, 0);
        }
        RenderSystem.disableBlend();
        stack.pop();

        super.render(stack, mouseX, mouseY, partialTicks);
    }

    private void refreshButtons() {
        this.buttons.clear();
        this.children.clear();

        //Add Dialogue Buttons
        int[][] buttonPos = {{this.width / 2 - 154, this.height - 65}, {this.width / 2 + 3, this.height - 65},
                             {this.width / 2 - 154, this.height - 39}, {this.width / 2 + 3, this.height - 39}};
        List<DialogueButtonForScript> buttonList = this.script.getPage(this.currentPage).buttonList;
        int start = (this.buttonPage - 1) * 4;
        int end = Math.min(this.buttonPage * 4, buttonList.size());
        for(int i = start; i < end; i++) {
            DialogueButtonForScript button = buttonList.get(i);
            if(!button.getTurnTo().isEmpty()) {
                this.addButton(new DialogueButton(buttonPos[i % 4][0], buttonPos[i % 4][1], button.message, (b) -> {
                    button.onPress();
                    this.turnTo(button.getTurnTo());
                    this.refreshButtons();
                }));
            } else if(button.getTurnTo().equals("close")) {
                this.addButton(new DialogueButton(buttonPos[i % 4][0], buttonPos[i % 4][1], button.message, (b) -> {
                    button.onPress();
                    this.closeScreen();
                }));
            } else {
                this.addButton(new DialogueButton(buttonPos[i % 4][0], buttonPos[i % 4][1], button.message, (b) -> {
                    button.onPress();
                    this.refreshButtons();
                }));
            }
        }

        //Add Turn-to-right Button
        if(end < buttonList.size()) {
            this.addButton(new ChangeButtonPageButton(this.width / 2 + 164, this.height - 48, (b) -> {
                if(this.buttonPage * 4 < buttonList.size())
                    this.buttonPage++;
                this.refreshButtons();
            }, true));
        }

        //Add Turn-to-left Button
        if(this.buttonPage > 1) {
            this.addButton(new ChangeButtonPageButton(this.width / 2 - 175, this.height - 48, (b) -> {
                if(this.buttonPage > 1)
                    this.buttonPage--;
                this.refreshButtons();
            }, false));
        }
    }

    public void turnTo(String page) {
        if(this.script.getPage(page) != null) {
            this.currentPage = page;
            this.buttonPage = 1;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public class DialogueButton extends Button {
        public DialogueButton(int x, int y, ITextComponent title, IPressable onPress) {
            super(x, y, 151, 24, title, onPress);
        }

        @Override
        public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft minecraft = Minecraft.getInstance();
            FontRenderer fontrenderer = minecraft.fontRenderer;
            minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            this.blit(matrixStack, this.x, this.y, 0, this.isHovered ? 124 : 99, this.width, this.height);
            IReorderingProcessor text = this.getMessage().func_241878_f();
            fontrenderer.func_238422_b_(matrixStack, text, (float)(this.x + this.width / 2 - fontrenderer.func_243245_a(text) / 2), this.y + (this.height - 8) / 2, 0);
        }
    }

    public class ChangeButtonPageButton extends Button {
        private final boolean leftOrRight; //false: left, ture: right

        public ChangeButtonPageButton(int x, int y, IPressable onPress, boolean leftOrRight) {
            super(x, y, 11, 17, StringTextComponent.EMPTY, onPress);
            this.leftOrRight = leftOrRight;
        }

        @Override
        public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.getTextureManager().bindTexture(SCREEN_TEXTURE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.enableDepthTest();
            this.blit(matrixStack, this.x, this.y, this.leftOrRight ? 1 : 15, this.isHovered ? 167 : 149, this.width, this.height);
        }
    }
}
