package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.WarRequestListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionEnemyListPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionEnemyListPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, Object>> warsInfos = new ArrayList();
    public String factionId;

    public FactionEnemyListPacket(String targetName)
    {
        this.factionId = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.warsInfos = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionEnemyListPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.factionId);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        WarRequestListGUI.warsInfos = this.warsInfos;
        WarRequestListGUI.loaded = true;
    }
}
