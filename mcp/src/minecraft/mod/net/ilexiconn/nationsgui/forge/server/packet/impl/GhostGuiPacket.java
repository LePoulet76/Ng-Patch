package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.io.IOException;
import java.util.LinkedHashMap;
import net.ilexiconn.nationsgui.forge.client.gui.ghost.GhostGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GhostGuiPacket implements IPacket, IClientPacket
{
    NBTTagCompound compound;

    public void fromBytes(ByteArrayDataInput data)
    {
        try
        {
            this.compound = CompressedStreamTools.read(data);
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
            CompressedStreamTools.write(this.compound, data);
        }
        catch (IOException var3)
        {
            var3.printStackTrace();
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        NBTTagList items = this.compound.getTagList("Items");
        ItemStack[] itemStacks = new ItemStack[15];

        for (int map = 0; map < items.tagCount(); ++map)
        {
            itemStacks[map] = ItemStack.loadItemStackFromNBT((NBTTagCompound)items.tagAt(map));
        }

        LinkedHashMap var8 = new LinkedHashMap();
        NBTTagList playerRank = this.compound.getTagList("PlayerRanks");

        for (int i = 0; i < playerRank.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = (NBTTagCompound)playerRank.tagAt(i);
            var8.put(tagCompound.getString("Pseudo"), Integer.valueOf(tagCompound.getInteger("Points")));
        }

        Minecraft.getMinecraft().displayGuiScreen(new GhostGUI(itemStacks, var8, this.compound.getInteger("Points")));
    }
}
