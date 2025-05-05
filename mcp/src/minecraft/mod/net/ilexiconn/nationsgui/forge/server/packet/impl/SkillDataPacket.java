package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map.Entry;
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class SkillDataPacket implements IPacket, IClientPacket
{
    private String playerName;
    public HashMap<String, HashMap<String, Object>> skills = new HashMap();

    public SkillDataPacket(String playerName)
    {
        this.playerName = playerName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.skills = (HashMap)(new Gson()).fromJson(data.readUTF(), (new SkillDataPacket$1(this)).getType());
        this.playerName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.skills));
        data.writeUTF(this.playerName);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        SkillsGui.skillsData = this.skills;
        Entry entry = (Entry)this.skills.entrySet().iterator().next();
        SkillsGui.selectedSkill = (String)entry.getKey();
        SkillsGui.loaded = true;
    }
}
