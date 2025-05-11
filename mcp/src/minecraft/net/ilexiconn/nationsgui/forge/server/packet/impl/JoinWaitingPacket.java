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
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class JoinWaitingPacket
implements IPacket,
IClientPacket {
    private String serverName;
    private String address;
    private int port;

    public JoinWaitingPacket(String serverName, String address, int port) {
        this.serverName = serverName;
        this.address = address;
        this.port = port;
    }

    public JoinWaitingPacket() {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        System.out.println("DEBUG: JoinWaitingPacket.handleClientPacket");
        System.out.println("DEBUG: serverName = " + this.serverName);
        System.out.println("DEBUG: address = " + this.address);
        System.out.println("DEBUG: port = " + this.port);
        ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + this.serverName);
        ClientData.waitingJoinTime = System.currentTimeMillis();
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.serverName = data.readUTF();
        this.address = data.readUTF();
        this.port = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.serverName);
        data.writeUTF(this.address);
        data.writeInt(this.port);
    }
}

