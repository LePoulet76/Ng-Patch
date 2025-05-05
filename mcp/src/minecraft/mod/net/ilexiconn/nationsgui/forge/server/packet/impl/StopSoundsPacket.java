package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import java.util.ArrayList;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.entity.player.EntityPlayer;

public class StopSoundsPacket implements IPacket, IClientPacket
{
    public void handleClientPacket(EntityPlayer player)
    {
        Iterator var2 = (new ArrayList(ClientProxy.STREAMER_LIST)).iterator();

        while (var2.hasNext())
        {
            SoundStreamer streamer = (SoundStreamer)var2.next();
            streamer.forceClose();
        }

        ClientProxy.STREAMER_LIST.clear();
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data) {}
}
