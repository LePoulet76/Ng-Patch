package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.capes.Capes;
import net.ilexiconn.nationsgui.forge.server.entity.data.NGPlayerData;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;

public class CapeSetCurrentPacket implements IPacket, IServerPacket
{
    private String capeIdentifier;

    public CapeSetCurrentPacket(String capeIdentifier)
    {
        this.capeIdentifier = capeIdentifier;
    }

    public void handleServerPacket(EntityPlayer player)
    {
        NGPlayerData ngPlayerData = NGPlayerData.get(player);

        if (!this.capeIdentifier.isEmpty() && Capes.getCapeFromIdentifier(this.capeIdentifier) != null && ngPlayerData.hasCape(this.capeIdentifier) && (ngPlayerData.getCurrentCape() == null || !ngPlayerData.getCurrentCape().getIdentifier().equalsIgnoreCase(this.capeIdentifier)))
        {
            ngPlayerData.setCurrentCape(Capes.getCapeFromIdentifier(this.capeIdentifier));
        }
        else if (this.capeIdentifier.isEmpty())
        {
            ngPlayerData.resetCurrentCape();
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.capeIdentifier = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.capeIdentifier);
    }
}
