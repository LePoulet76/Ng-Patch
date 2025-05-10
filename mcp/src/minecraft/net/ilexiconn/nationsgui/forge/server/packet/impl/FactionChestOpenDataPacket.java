package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FactionChestOpenDataPacket implements IPacket, IServerPacket, IClientPacket
{
    public String factionName;

    public FactionChestOpenDataPacket(String factionName)
    {
        this.factionName = factionName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.factionName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.factionName);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
    }

    public void handleServerPacket(EntityPlayer player)
    {
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("FactionChest");
        boolean hasT4 = false;
        boolean hasRed = false;
        boolean hasFusee = false;
        boolean hasT5 = false;
        NBTTagList itemsTag = compound.getTagList(this.factionName);

        for (int i = 0; i < itemsTag.tagCount(); ++i)
        {
            NBTTagCompound itemTag = (NBTTagCompound)itemsTag.tagAt(i);
            byte slot = itemTag.getByte("Slot");
            ItemStack item = ItemStack.loadItemStackFromNBT(itemTag);

            if (item.itemID == 19483 && item.getItemDamage() == 22)
            {
                hasT4 = true;
            }
            else if (item.itemID == 19483 && item.getItemDamage() == 23)
            {
                hasRed = true;
            }
            else if (item.itemID != 10111 && item.itemID != 10162)
            {
                if (item.itemID == 19483 && item.getItemDamage() == 32)
                {
                    hasT5 = true;
                }
            }
            else
            {
                hasFusee = true;
            }
        }

        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new FactionChestNuclearPacket(this.factionName, hasT4, hasRed, hasFusee, hasT5)), (Player)player);
    }
}
