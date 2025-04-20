package io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue;

import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class DialoguePage {
    public final List<DialogueButtonForScript> buttonList = new ArrayList<>();
    public final ITextComponent message;

    public DialoguePage(ITextComponent message) {
        this.message = message;
    }

    public void addButton(DialogueButtonForScript button) {
        this.buttonList.add(button);
    }
}
