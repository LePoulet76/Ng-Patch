/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.Cancelable
 *  net.minecraftforge.event.Event
 */
package net.ilexiconn.nationsgui.forge.server.event;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class PrintChatMessageEvent
extends Event {
    private String message;
    private int chatLineId;

    public PrintChatMessageEvent(String message, int chatLineId) {
        this.message = message;
        this.chatLineId = chatLineId;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getChatLineId() {
        return this.chatLineId;
    }

    public void setChatLineId(int chatLineId) {
        this.chatLineId = chatLineId;
    }
}

