package io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class DialogueButtonForScript {
    public static final IPressable NO_ACTION = (button) -> {};

    public final ITextComponent message;
    private final IPressable onPress;
    private String turnTo;

    public DialogueButtonForScript(ITextComponent message) {
        this(message, NO_ACTION, "");
    }

    public DialogueButtonForScript(ITextComponent message, String turnTo) {
        this(message, NO_ACTION, turnTo);
    }

    public DialogueButtonForScript(ITextComponent message, IPressable onPress) {
        this(message, onPress, "");
    }

    public DialogueButtonForScript(ITextComponent message, IPressable onPress, String turnTo) {
        this.message = message;
        this.onPress = onPress;
        this.turnTo = turnTo;
    }

    public String getTurnTo() {
        return this.turnTo;
    }

    public void setTurnTo(String turnTo) {
        this.turnTo = turnTo;
    }

    public void onPress() {
        this.onPress.onPress(this);
    }

    @OnlyIn(Dist.CLIENT)
    public interface IPressable {
        public void onPress(DialogueButtonForScript button);
    }
}
