package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandListGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandListDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class IslandListDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> islands = new ArrayList();
    public String filter;
    public boolean onlyMyIsland;
    public boolean isPremium;
    public boolean isOp;
    public String serverNumber;

    public IslandListDataPacket(String filter, boolean onlyMyIsland)
    {
        this.filter = filter;
        this.onlyMyIsland = onlyMyIsland;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.islands = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new IslandListDataPacket$1(this)).getType());
        this.isPremium = data.readBoolean();
        this.isOp = data.readBoolean();
        this.serverNumber = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.filter);
        data.writeBoolean(this.onlyMyIsland);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (IslandListGui.loaded && !IslandListGui.resetList)
        {
            IslandListGui.islands.addAll(this.islands);
        }
        else
        {
            IslandListGui.islands = this.islands;
            IslandListGui.loaded = true;
            IslandListGui.isPremium = this.isPremium;
            IslandListGui.isOp = this.isOp;
            IslandListGui.serverNumber = this.serverNumber;
            IslandListGui.resetList = false;
        }
    }
}
