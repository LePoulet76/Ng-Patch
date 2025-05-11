/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPasswordGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTPPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class IslandPasswordPacket
implements IPacket,
IClientPacket {
    public String id;
    public String password;
    public String passwordValue;
    public String serverNumber;
    public boolean response;

    public IslandPasswordPacket(String id, String password, String passwordValue, String serverNumber) {
        this.id = id;
        this.password = password;
        this.passwordValue = passwordValue;
        this.serverNumber = serverNumber;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.id = data.readUTF();
        this.response = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.id);
        data.writeUTF(this.password);
        data.writeUTF(this.passwordValue);
        data.writeUTF(this.serverNumber);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        if (!this.response) {
            IslandPasswordGui.hasError = true;
        } else {
            Minecraft.func_71410_x().func_71373_a(null);
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTPPacket(this.id, this.serverNumber)));
        }
    }
}

