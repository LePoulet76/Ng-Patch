package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class IslandDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, String> islandInfos = new HashMap();

    public IslandDataPacket(HashMap<String, String> islandInfos)
    {
        this.islandInfos = islandInfos;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.islandInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new IslandDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.islandInfos.isEmpty())
        {
            if (!ClientData.currentIsland.containsKey("id") || !this.islandInfos.containsKey("id") || !((String)ClientData.currentIsland.get("id")).equals(this.islandInfos.get("id")))
            {
                ClientData.currentJumpStartTime = Long.valueOf(-1L);
                ClientData.currentJumpLocation = "";
                ClientData.currentJumpRecord = "";
            }

            ClientData.setCurrentIsland(this.islandInfos);
        }
        else
        {
            ClientData.setCurrentIsland(new HashMap());
        }
    }
}
