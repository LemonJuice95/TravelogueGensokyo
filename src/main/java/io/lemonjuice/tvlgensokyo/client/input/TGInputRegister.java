package io.lemonjuice.tvlgensokyo.client.input;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TGInputRegister {
    public static void registerInputs() {
        ClientRegistry.registerKeyBinding(TGInputs.SPECIAL_SCROLL_SWITCH);
    }
}
