package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionChestSaveDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class FactionChestSaveDataPacket implements IPacket, IClientPacket, IServerPacket
{
    public HashMap<String, Object> logs;

    public FactionChestSaveDataPacket(HashMap<String, Object> logs)
    {
        this.logs = logs;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.logs = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionChestSaveDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.logs));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(this));
    }

    public void handleServerPacket(EntityPlayer player)
    {
        String factionName = NationsGUI.getFactionNameFromId((String)this.logs.get("factionName"));
        System.out.println("LOG TRANSACTION CHEST " + factionName);
        System.out.println(this.logs.get("logs").toString());
        NBTTagCompound compound = (NBTTagCompound)NBTConfig.CONFIG.getCompound().getTag("FactionChest");
        boolean hasT4 = false;
        boolean hasRed = false;
        boolean hasFusee = false;
        boolean hasT5 = false;
        NBTTagList itemsTag = compound.getTagList((String)this.logs.get("factionName"));

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
            else if (item.itemID == 19483 && item.getItemDamage() == 32)
            {
                hasT5 = true;
            }
            else if (item.itemID == 10111 || item.itemID == 10162)
            {
                hasFusee = true;
            }
        }

        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new FactionChestNuclearPacket((String)this.logs.get("factionName"), hasT4, hasRed, hasFusee, hasT5)), (Player)player);
    }
}
