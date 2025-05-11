/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AssistanceNewTicketGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ScreenAuthorizationPacket
implements IPacket,
IClientPacket {
    private String token;
    private String location;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.token = data.readUTF();
        this.location = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (Minecraft.func_71410_x().field_71462_r instanceof AssistanceNewTicketGUI) {
            AssistanceNewTicketGUI gui = (AssistanceNewTicketGUI)Minecraft.func_71410_x().field_71462_r;
            gui.screenUrl = this.location;
            gui.screenToken = this.token;
        }
    }
}

