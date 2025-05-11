/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Timer;
import java.util.TimerTask;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.gui.ServerSwitchExpressGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class ChangeServerPacket
implements IPacket,
IClientPacket {
    private String address;
    private int port;

    public ChangeServerPacket(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public ChangeServerPacket() {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        final String serverName = ClientProxy.getServerNameByIpAndPort(this.address + ":" + this.port);
        if (serverName != null) {
            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + serverName);
            ClientData.waitingJoinTime = System.currentTimeMillis();
            new Timer().schedule(new TimerTask(){

                @Override
                public void run() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new ServerSwitchExpressGui(ChangeServerPacket.this.address, ChangeServerPacket.this.port, serverName));
                }
            }, 1500L);
        } else {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new ServerSwitchExpressGui(this.address, this.port, null));
        }
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.address = data.readUTF();
        this.port = data.readInt();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.address);
        data.writeInt(this.port);
    }
}

