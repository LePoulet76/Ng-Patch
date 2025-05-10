package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ActionsListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsListDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionActionsListDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> actions = new ArrayList();
    public String factionTarget;

    public FactionActionsListDataPacket(String factionTarget)
    {
        this.factionTarget = factionTarget;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.actions = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionActionsListDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.factionTarget);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ActionsListGui.actions = this.actions;
        ActionsListGui.loaded = true;
    }
}
