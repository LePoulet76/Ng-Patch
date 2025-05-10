package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionSkillsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsPlayersLeaderboardDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsPlayersLeaderboardDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSkillsPlayersLeaderboardDataPacket$3;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSkillsPlayersLeaderboardDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, List<String>> playersLeaderboard = new HashMap();
    public HashMap<String, Double> playerPositionPerSkill = new HashMap();
    public HashMap<String, Double> playerTotalLevelPerSkill = new HashMap();
    public String target;
    private boolean onlyCountry;

    public FactionSkillsPlayersLeaderboardDataPacket(String target, boolean onlyCountry)
    {
        this.target = target;
        this.onlyCountry = onlyCountry;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.playersLeaderboard = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsPlayersLeaderboardDataPacket$1(this)).getType());
        this.playerPositionPerSkill = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsPlayersLeaderboardDataPacket$2(this)).getType());
        this.playerTotalLevelPerSkill = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionSkillsPlayersLeaderboardDataPacket$3(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
        data.writeBoolean(this.onlyCountry);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        FactionSkillsGUI.playersLeaderboard = this.playersLeaderboard;
        FactionSkillsGUI.playerPositionPerSkill = this.playerPositionPerSkill;
        FactionSkillsGUI.playerTotalLevelPerSkill = this.playerTotalLevelPerSkill;
        FactionSkillsGUI.loaded_players_leaderboard = true;
    }
}
