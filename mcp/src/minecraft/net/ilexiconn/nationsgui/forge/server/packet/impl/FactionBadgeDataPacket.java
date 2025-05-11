/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.common.network.Player
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BadgesGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;

public class FactionBadgeDataPacket
implements IPacket,
IServerPacket,
IClientPacket {
    private NBTTagCompound badges;
    private String playerName;

    public FactionBadgeDataPacket(String playerName) {
        this.playerName = playerName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        boolean client;
        this.playerName = data.readUTF();
        boolean bl = client = data.readByte() == 1;
        if (client) {
            try {
                this.badges = CompressedStreamTools.func_74794_a((DataInput)data);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.playerName);
        data.writeByte(this.badges == null ? 0 : 1);
        if (this.badges != null) {
            try {
                CompressedStreamTools.func_74800_a((NBTTagCompound)this.badges, (DataOutput)data);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        this.badges = ((NBTTagCompound)NBTConfig.CONFIG.getCompound().func_74781_a("Badges")).func_74775_l(this.playerName);
        PacketDispatcher.sendPacketToPlayer((Packet)PacketRegistry.INSTANCE.generatePacket(this), (Player)((Player)player));
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        BadgesGUI.CLIENT_BADGES = this.badges;
        BadgesGUI.loaded = true;
    }
}

