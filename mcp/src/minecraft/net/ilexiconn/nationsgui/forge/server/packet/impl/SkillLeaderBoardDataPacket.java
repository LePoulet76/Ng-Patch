package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.SkillsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillLeaderBoardDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SkillLeaderBoardDataPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class SkillLeaderBoardDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, List<String>> skillsLeaderBoard = new HashMap();
    public HashMap<String, String> skillsTopInterServ = new HashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.skillsLeaderBoard = (HashMap)(new Gson()).fromJson(data.readUTF(), (new SkillLeaderBoardDataPacket$1(this)).getType());
        this.skillsTopInterServ = (HashMap)(new Gson()).fromJson(data.readUTF(), (new SkillLeaderBoardDataPacket$2(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.skillsLeaderBoard));
        data.writeUTF((new Gson()).toJson(this.skillsTopInterServ));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        SkillsGui.skillsLeaderBoard = this.skillsLeaderBoard;
        SkillsGui.skillsTopInterServ = this.skillsTopInterServ;
        SkillsGui.displayMode = "leaderboard";
    }
}
