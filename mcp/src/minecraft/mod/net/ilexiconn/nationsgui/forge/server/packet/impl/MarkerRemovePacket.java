package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class MarkerRemovePacket implements IPacket, IClientPacket
{
    private String markerName;

    public MarkerRemovePacket(String markerName)
    {
        this.markerName = markerName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.markerName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.markerName);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        Iterator var2 = ClientData.markers.iterator();

        while (var2.hasNext())
        {
            Map marker = (Map)var2.next();

            if (marker.get("name").equals(this.markerName))
            {
                ClientData.markers.remove(marker);
                break;
            }
        }
    }
}
