package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsDashboardDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSkillsDashboardDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> skillsData = new HashMap();
    public String target;

    public FactionSkillsDashboardDataPacket(String targetName)
    {
        this.target = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.skillsData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsDashboardDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        FactionSkillsGUI.dashboardData = this.skillsData;
        FactionSkillsGUI.loaded_dashboard = true;
    }
}
