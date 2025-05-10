package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.override.HotbarOverride;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class HotbarMessagePacket implements IPacket, IClientPacket
{
    private String message;

    public HotbarMessagePacket(String message)
    {
        this.message = message;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.message = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.message);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.sendClientHotBarMessage(this.message, HotbarOverride.defaultDelayHotbarMessage.longValue());
    }
}
