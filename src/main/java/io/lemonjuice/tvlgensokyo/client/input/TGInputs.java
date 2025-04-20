package io.lemonjuice.tvlgensokyo.client.input;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class TGInputs {
    public static final KeyBinding SPECIAL_SCROLL_SWITCH = new KeyBinding("key.tvlgensokyo.special_scroll",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_ALT,
            "key.category.tvlgensokyo");
}
