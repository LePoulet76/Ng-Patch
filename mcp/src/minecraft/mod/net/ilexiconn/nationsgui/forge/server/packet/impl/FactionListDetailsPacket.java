package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionListDetailsPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionListDetailsPacket implements IPacket, IClientPacket
{
    public String targetFaction;
    public HashMap<String, String> countryData = new HashMap();

    public FactionListDetailsPacket(String targetFaction)
    {
        this.targetFaction = targetFaction;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.targetFaction = data.readUTF();
        this.countryData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionListDetailsPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.targetFaction);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        FactionListGUI.countriesExtraData.put(this.targetFaction, this.countryData);
    }
}
