package io.lemonjuice.tvlgensokyo.client.gui.screen.dialogue;

import java.util.HashMap;
import java.util.Map;

public class DialogueScript {
    private final Map<String, DialoguePage> pageMap;
    public final String startPage;

    public DialogueScript(Map<String, DialoguePage> pageMap, String startPage) {
        this.pageMap = pageMap;
        this.startPage = startPage;
    }

    public DialoguePage getPage(String key) {
        if(pageMap.containsKey(key))
            return this.pageMap.get(key);
        else
            return null;
    }
}
