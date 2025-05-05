package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DiplomatieGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionDiplomatieDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionDiplomatieDataPacket implements IPacket, IClientPacket
{
    public TreeMap<String, Object> diplomatieInfos = new TreeMap();
    public String target;

    public FactionDiplomatieDataPacket(String targetName)
    {
        this.target = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.diplomatieInfos = (TreeMap)(new Gson()).fromJson(data.readUTF(), (new FactionDiplomatieDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        DiplomatieGUI.factionDiplomatieInfos = this.diplomatieInfos;
        DiplomatieGUI.loaded = true;
    }
}
