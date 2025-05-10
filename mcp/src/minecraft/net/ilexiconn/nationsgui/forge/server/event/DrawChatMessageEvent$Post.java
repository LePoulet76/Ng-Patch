package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.gui.ChatLine;

public class DrawChatMessageEvent$Post extends DrawChatMessageEvent
{
    public DrawChatMessageEvent$Post(ChatLine chatLine, String formattedText, int x, int y, int alpha)
    {
        super(chatLine, formattedText, x, y, alpha);
    }
}
