package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.util.HashMap;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.util.ResourceLocation;

public class BadgeChatTag extends EmoteChatTag
{
    public BadgeChatTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
    }

    protected HashMap<String, ResourceLocation> getResourceList()
    {
        return NationsGUI.BADGES_RESOURCES;
    }
}
