package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.faction.MembersGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMembersDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionMembersDataPacket implements IPacket, IClientPacket
{
    public TreeMap<String, Object> data = new TreeMap();
    public String targetFactionId;

    public FactionMembersDataPacket(String targetName)
    {
        MembersGUI.factionMembersInfos.clear();
        this.targetFactionId = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.data = (TreeMap)(new Gson()).fromJson(data.readUTF(), (new FactionMembersDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.targetFactionId);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        Iterator var2 = this.data.entrySet().iterator();

        while (var2.hasNext())
        {
            Entry entry = (Entry)var2.next();
            MembersGUI.factionMembersInfos.put(entry.getKey(), entry.getValue());
        }

        MembersGUI.loaded = true;
    }
}
