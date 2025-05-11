/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.nbt.CompressedStreamTools
 *  net.minecraft.nbt.NBTTagCompound
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;

public class PlayerDataPacket
implements IServerPacket,
IClientPacket,
IPacket {
    private List<String> emotes;
    private List<String> currentEmotes;
    private List<String> capes;
    private String currentCape;

    public PlayerDataPacket(List<String> emotes, List<String> currentEmotes, List<String> capes, String currentCape) {
        this.emotes = emotes;
        this.currentEmotes = currentEmotes;
        this.capes = capes;
        this.currentCape = currentCape;
    }

    public PlayerDataPacket(List<String> currentEmotes, String currentCape) {
        this.emotes = new ArrayList<String>();
        this.currentEmotes = currentEmotes;
        this.capes = new ArrayList<String>();
        this.currentCape = currentCape;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        try {
            this.emotes = NGPlayerData.nbtToList(Packet.func_73283_d((DataInput)data));
            this.currentEmotes = NGPlayerData.nbtToList(Packet.func_73283_d((DataInput)data));
            this.capes = NGPlayerData.nbtToList(Packet.func_73283_d((DataInput)data));
            this.currentCape = data.readUTF();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        try {
            byte[] emotesData = CompressedStreamTools.func_74798_a((NBTTagCompound)NGPlayerData.listToNBT(this.emotes));
            data.writeShort(emotesData.length);
            data.write(emotesData);
            byte[] currentEmotesData = CompressedStreamTools.func_74798_a((NBTTagCompound)NGPlayerData.listToNBT(this.currentEmotes));
            data.writeShort(currentEmotesData.length);
            data.write(currentEmotesData);
            byte[] capesData = CompressedStreamTools.func_74798_a((NBTTagCompound)NGPlayerData.listToNBT(this.capes));
            data.writeShort(capesData.length);
            data.write(capesData);
            data.writeUTF(this.currentCape != null ? this.currentCape : "");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        NGPlayerData props = NGPlayerData.get(player);
        props.emotes = this.emotes;
        props.currentEmotes = this.currentEmotes;
        props.capes = this.capes;
        props.currentCape = this.currentCape;
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        NGPlayerData props = NGPlayerData.get(player);
        for (String emote : this.currentEmotes) {
            if (props.hasEmote(emote)) continue;
            return;
        }
        props.currentEmotes = this.currentEmotes;
        if (props.hasCape(this.currentCape)) {
            props.currentCape = this.currentCape;
        }
    }
}

