package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SkinPacket implements IPacket, IClientPacket
{
    private String pseudo;
    private List<String> activesSkins;

    public SkinPacket(String pseudo, List<String> activesSkins)
    {
        this.pseudo = pseudo;
        this.activesSkins = activesSkins;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            NBTTagCompound e = CompressedStreamTools.read(data);
            this.pseudo = e.getString("pseudo");
            this.activesSkins = new ArrayList();
            NBTTagList tagList = e.getTagList("activeSkins");

            for (int i = 0; i < tagList.tagCount(); ++i)
            {
                this.activesSkins.add(((NBTTagCompound)tagList.tagAt(i)).getString("skinID"));
            }
        }
        catch (IOException var5)
        {
            throw new RuntimeException(var5);
        }
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.setString("pseudo", this.pseudo);
        NBTTagList tagList = new NBTTagList();
        Iterator e = this.activesSkins.iterator();

        while (e.hasNext())
        {
            String activeSkin = (String)e.next();
            NBTTagCompound obj = new NBTTagCompound();
            obj.setString("skinID", activeSkin);
            tagList.appendTag(obj);
        }

        tagCompound.setTag("activeSkins", tagList);

        try
        {
            CompressedStreamTools.write(tagCompound, data);
        }
        catch (IOException var7)
        {
            throw new RuntimeException(var7);
        }
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.SKIN_MANAGER.setPlayerSkins(this.pseudo, this.activesSkins);
    }
}
