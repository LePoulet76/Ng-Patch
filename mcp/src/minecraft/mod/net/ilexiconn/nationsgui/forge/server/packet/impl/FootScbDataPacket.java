package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FootScbDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FootScbDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, String> footInfos = new HashMap();

    public FootScbDataPacket(HashMap<String, String> footInfos)
    {
        this.footInfos = footInfos;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.footInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FootScbDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.footInfos.isEmpty())
        {
            ClientData.setCurrentFoot(this.footInfos);
        }
        else
        {
            ClientData.setCurrentFoot(new HashMap());
        }
    }
}
