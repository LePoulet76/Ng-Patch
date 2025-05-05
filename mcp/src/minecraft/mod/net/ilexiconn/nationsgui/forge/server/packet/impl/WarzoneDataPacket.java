package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzoneDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class WarzoneDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, String> warzoneInfos = new HashMap();

    public WarzoneDataPacket(HashMap<String, String> warzoneInfos)
    {
        this.warzoneInfos = warzoneInfos;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.warzoneInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new WarzoneDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.warzoneInfos.isEmpty())
        {
            ClientData.setCurrentWarzone(this.warzoneInfos);
        }
        else
        {
            ClientData.setCurrentWarzone(new HashMap());
        }
    }
}
