package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMembersGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamsDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamsDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamsDataPacket$3;
import net.minecraft.entity.player.EntityPlayer;

public class IslandTeamsDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, Object>> teamsInfos = new ArrayList();
    public ArrayList<String> teamFlags = new ArrayList();
    public boolean isCreation;

    public IslandTeamsDataPacket(boolean isCreation)
    {
        this.isCreation = isCreation;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.teamsInfos = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new IslandTeamsDataPacket$1(this)).getType());
        this.teamFlags = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new IslandTeamsDataPacket$2(this)).getType());
        this.isCreation = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeBoolean(this.isCreation);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        IslandMembersGui.teamsInfos = new ArrayList();
        IslandMembersGui.teamFlags = this.teamFlags;
        IslandMembersGui.needUpdate = true;

        if (this.teamsInfos.size() > 0)
        {
            Iterator var2 = this.teamsInfos.iterator();

            while (var2.hasNext())
            {
                HashMap teamInfos = (HashMap)var2.next();
                teamInfos.put("flags", (new Gson()).fromJson((String)teamInfos.get("flags"), (new IslandTeamsDataPacket$3(this)).getType()));
                IslandMembersGui.teamsInfos.add(teamInfos);
            }

            if (!this.isCreation)
            {
                IslandMembersGui.selectedTeamInfos = (HashMap)this.teamsInfos.get(0);
            }
            else
            {
                IslandMembersGui.selectedTeamInfos = (HashMap)this.teamsInfos.get(this.teamsInfos.size() - 1);
            }
        }
    }
}
