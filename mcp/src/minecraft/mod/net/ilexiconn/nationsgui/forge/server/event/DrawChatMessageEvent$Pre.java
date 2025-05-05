package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.gui.ChatLine;
import net.minecraftforge.event.Cancelable;

@Cancelable
public class DrawChatMessageEvent$Pre extends DrawChatMessageEvent
{
    public DrawChatMessageEvent$Pre(ChatLine chatLine, String formattedText, int x, int y, int alpha)
    {
        super(chatLine, formattedText, x, y, alpha);
    }
}
