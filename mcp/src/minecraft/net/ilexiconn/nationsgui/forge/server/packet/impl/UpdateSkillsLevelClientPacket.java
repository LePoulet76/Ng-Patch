package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UpdateSkillsLevelClientPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.UpdateSkillsLevelClientPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class UpdateSkillsLevelClientPacket implements IPacket, IClientPacket
{
    public HashMap<String, Integer> skillsLevel;
    public Map<String, String> topSkills;

    public UpdateSkillsLevelClientPacket(HashMap<String, Integer> skillsLevel, Map<String, String> topSkills)
    {
        this.skillsLevel = skillsLevel;
        this.topSkills = topSkills;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.skillsLevel = (HashMap)(new Gson()).fromJson(data.readUTF(), (new UpdateSkillsLevelClientPacket$1(this)).getType());
        this.topSkills = (Map)(new Gson()).fromJson(data.readUTF(), (new UpdateSkillsLevelClientPacket$2(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.skillsLevel));
        data.writeUTF((new Gson()).toJson(this.topSkills));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.skillsLevel.isEmpty())
        {
            ClientData.skillsLevel = this.skillsLevel;
        }

        if (!this.topSkills.isEmpty())
        {
            Iterator var2 = this.topSkills.entrySet().iterator();

            while (var2.hasNext())
            {
                Entry entry = (Entry)var2.next();
                ClientData.topPlayersSkills.put(((String)entry.getValue()).split("#")[0], entry.getKey());
            }
        }
    }
}
