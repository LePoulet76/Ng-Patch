/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class NotificationActionPacket
implements IPacket,
IClientPacket {
    private String notificationAction;
    private String args;

    public NotificationActionPacket(String notificationAction, String args) {
        this.notificationAction = notificationAction;
        this.args = args;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.notificationAction = data.readUTF();
        this.args = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.notificationAction);
        data.writeUTF(this.args);
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (this.notificationAction.equals("screen.uploader.allow") && this.args != null && !this.args.isEmpty()) {
            NationsGUIClientHooks.uploadToForum(NationsGUIClientHooks.screenMap.remove(this.args));
        }
    }
}

