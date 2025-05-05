package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WildernessGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionWildernessDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionWildernessDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> players = new ArrayList();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.players = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionWildernessDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        WildernessGui.players = this.players;
        WildernessGui.loaded = true;
    }
}
