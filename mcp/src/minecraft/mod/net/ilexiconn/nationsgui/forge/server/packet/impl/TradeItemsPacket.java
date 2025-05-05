package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.gui.trade.GuiTrade;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class TradeItemsPacket implements IPacket, IClientPacket
{
    private NBTTagCompound comp;

    public TradeItemsPacket(NBTTagCompound comp, String username)
    {
        this.comp = comp;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            this.comp = CompressedStreamTools.read(data);
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
            CompressedStreamTools.write(this.comp, data);
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (mc.currentScreen instanceof GuiTrade)
        {
            GuiTrade gui = (GuiTrade)mc.currentScreen;
            gui.items = ContainerTrade.CompToItem(this.comp);
        }
    }
}
