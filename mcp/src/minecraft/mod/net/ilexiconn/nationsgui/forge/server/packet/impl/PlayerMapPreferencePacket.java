package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;

public class PlayerMapPreferencePacket implements IPacket
{
    String name = "";
    String displayName = "";

    public PlayerMapPreferencePacket(String name, String displayName)
    {
        this.name = name != null ? name : "null";
        this.displayName = displayName != null ? displayName : "null";
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.name = data.readUTF();
        this.displayName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.name);
        data.writeUTF(this.displayName);
    }
}
