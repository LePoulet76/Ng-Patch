package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class TVGStreamersSetPacket implements IPacket
{
    private String streamer;

    public TVGStreamersSetPacket(String streamer)
    {
        this.streamer = streamer;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.streamer = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.streamer);
    }
}
