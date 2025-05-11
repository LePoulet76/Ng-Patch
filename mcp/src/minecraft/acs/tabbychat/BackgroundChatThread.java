/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package acs.tabbychat;

import net.minecraft.client.Minecraft;

public class BackgroundChatThread
extends Thread {
    String sendChat = "";

    BackgroundChatThread(String _send) {
        this.sendChat = _send;
    }

    @Override
    public synchronized void run() {
        Minecraft.func_71410_x().field_71456_v.func_73827_b().func_73767_b(this.sendChat);
        if (this.sendChat.toLowerCase().matches("^/msg \\*\\*.*") || this.sendChat.toLowerCase().matches("^/m \\*\\*.*")) {
            return;
        }
        Minecraft.func_71410_x().field_71439_g.func_71165_d(this.sendChat);
    }
}

