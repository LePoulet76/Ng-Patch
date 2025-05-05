package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EventDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EventDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> eventInfos = new HashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.eventInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EventDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.eventInfos.isEmpty())
        {
            ClientData.addEventInfos(this.eventInfos);
        }
        else
        {
            ClientData.eventsInfos = new ArrayList();
        }
    }
}
