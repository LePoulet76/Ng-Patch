/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.ChatLine
 *  net.minecraftforge.event.Cancelable
 *  net.minecraftforge.event.Event
 */
package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraft.client.gui.ChatLine;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

public abstract class DrawChatMessageEvent
extends Event {
    private final ChatLine chatLine;
    private final String formattedText;
    private final int x;
    private final int y;
    private final int alpha;

    public DrawChatMessageEvent(ChatLine chatLine, String formattedText, int x, int y, int alpha) {
        this.chatLine = chatLine;
        this.formattedText = formattedText;
        this.x = x;
        this.y = y;
        this.alpha = alpha;
    }

    public ChatLine getChatLine() {
        return this.chatLine;
    }

    public String getFormattedText() {
        return this.formattedText;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public static class Post
    extends DrawChatMessageEvent {
        public Post(ChatLine chatLine, String formattedText, int x, int y, int alpha) {
            super(chatLine, formattedText, x, y, alpha);
        }
    }

    @Cancelable
    public static class Pre
    extends DrawChatMessageEvent {
        public Pre(ChatLine chatLine, String formattedText, int x, int y, int alpha) {
            super(chatLine, formattedText, x, y, alpha);
        }
    }
}

