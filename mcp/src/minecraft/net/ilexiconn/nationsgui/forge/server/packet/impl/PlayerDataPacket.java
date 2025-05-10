package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.network.packet.Packet;

public class PlayerDataPacket implements IServerPacket, IClientPacket, IPacket
{
    private List<String> emotes;
    private List<String> currentEmotes;
    private List<String> capes;
    private String currentCape;

    public PlayerDataPacket(List<String> emotes, List<String> currentEmotes, List<String> capes, String currentCape)
    {
        this.emotes = emotes;
        this.currentEmotes = currentEmotes;
        this.capes = capes;
        this.currentCape = currentCape;
    }

    public PlayerDataPacket(List<String> currentEmotes, String currentCape)
    {
        this.emotes = new ArrayList();
        this.currentEmotes = currentEmotes;
        this.capes = new ArrayList();
        this.currentCape = currentCape;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            this.emotes = NGPlayerData.nbtToList(Packet.readNBTTagCompound(data));
            this.currentEmotes = NGPlayerData.nbtToList(Packet.readNBTTagCompound(data));
            this.capes = NGPlayerData.nbtToList(Packet.readNBTTagCompound(data));
            this.currentCape = data.readUTF();
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        try
        {
            byte[] e = CompressedStreamTools.compress(NGPlayerData.listToNBT(this.emotes));
            data.writeShort(e.length);
            data.write(e);
            byte[] currentEmotesData = CompressedStreamTools.compress(NGPlayerData.listToNBT(this.currentEmotes));
            data.writeShort(currentEmotesData.length);
            data.write(currentEmotesData);
            byte[] capesData = CompressedStreamTools.compress(NGPlayerData.listToNBT(this.capes));
            data.writeShort(capesData.length);
            data.write(capesData);
            data.writeUTF(this.currentCape != null ? this.currentCape : "");
        }
        catch (IOException var5)
        {
            var5.printStackTrace();
        }
    }

    public void handleClientPacket(EntityPlayer player)
    {
        NGPlayerData props = NGPlayerData.get(player);
        props.emotes = this.emotes;
        props.currentEmotes = this.currentEmotes;
        props.capes = this.capes;
        props.currentCape = this.currentCape;
    }

    public void handleServerPacket(EntityPlayer player)
    {
        NGPlayerData props = NGPlayerData.get(player);
        Iterator var3 = this.currentEmotes.iterator();
        String emote;

        do
        {
            if (!var3.hasNext())
            {
                props.currentEmotes = this.currentEmotes;

                if (props.hasCape(this.currentCape))
                {
                    props.currentCape = this.currentCape;
                }

                return;
            }

            emote = (String)var3.next();
        }
        while (props.hasEmote(emote));
    }
}
