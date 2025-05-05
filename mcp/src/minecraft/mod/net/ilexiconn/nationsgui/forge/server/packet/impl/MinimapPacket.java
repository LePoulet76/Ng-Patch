package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class MinimapPacket implements IPacket, IClientPacket
{
    private byte[] colors;

    public MinimapPacket(byte[] colors)
    {
        this.colors = colors;
    }

    public MinimapPacket() {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        ClientData.minimapColors = this.colors;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            NBTTagCompound e = CompressedStreamTools.read(data);
            this.colors = e.getByteArray("colors");
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();

        try
        {
            nbtTagCompound.setByteArray("colors", this.colors);
            CompressedStreamTools.write(nbtTagCompound, data);
        }
        catch (IOException var4)
        {
            var4.printStackTrace();
        }
    }
}
