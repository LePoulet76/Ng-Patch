package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EdoraBossOverlayDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EdoraBossOverlayDataPacket implements IPacket, IClientPacket
{
    public static long lastPacketReceived = 0L;
    public HashMap<String, Object> data;

    public EdoraBossOverlayDataPacket(HashMap<String, Object> data)
    {
        this.data = data;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EdoraBossOverlayDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.data));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.data.isEmpty())
        {
            ClientData.bossOverlayData = this.data;
            lastPacketReceived = System.currentTimeMillis();
        }
        else
        {
            ClientData.bossOverlayData = new HashMap();
        }
    }
}
