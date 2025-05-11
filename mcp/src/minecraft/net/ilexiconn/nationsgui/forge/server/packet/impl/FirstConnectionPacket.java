/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FirstConnectionPacket
implements IPacket,
IClientPacket {
    public boolean waitAuthMe = false;
    public boolean forceOpen = false;
    public String serverName = "";
    public String serverType = "ng";
    public String serverIp = "127.0.0.1";
    public String serverPort = "25565";

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        ClientProxy.currentServerName = this.serverName;
        ClientProxy.serverType = this.serverType;
        ClientProxy.serverIp = this.serverIp;
        ClientProxy.serverPort = this.serverPort;
        if (this.serverName.equals(ClientData.waitingServerName)) {
            ClientData.waitingServerName = null;
            ClientData.waitingPosition = 0;
            ClientData.waitingJoinTime = 0L;
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.waitAuthMe = data.readBoolean();
        this.serverName = data.readUTF();
        this.forceOpen = data.readBoolean();
        this.serverType = data.readUTF();
        this.serverIp = data.readUTF();
        this.serverPort = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeBoolean(this.waitAuthMe);
        data.writeUTF(this.serverName);
        data.writeBoolean(this.forceOpen);
        data.writeUTF(this.serverType);
        data.writeUTF(this.serverIp);
        data.writeUTF(this.serverPort);
    }
}

