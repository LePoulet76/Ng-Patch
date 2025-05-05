package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDataPacket$3;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> players = new ArrayList();
    public LinkedHashMap<String, ArrayList<String>> staffPlayers = new LinkedHashMap();
    public LinkedHashMap<String, ArrayList<String>> staffInterServerPlayers = new LinkedHashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.players = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new PlayerListDataPacket$1(this)).getType());
        this.staffPlayers = (LinkedHashMap)(new Gson()).fromJson(data.readUTF(), (new PlayerListDataPacket$2(this)).getType());
        this.staffInterServerPlayers = (LinkedHashMap)(new Gson()).fromJson(data.readUTF(), (new PlayerListDataPacket$3(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        if (!this.staffPlayers.isEmpty())
        {
            PlayerListGUI.staffPlayers = this.staffPlayers;
        }

        if (!this.staffInterServerPlayers.isEmpty())
        {
            PlayerListGUI.staffInterServerPlayers = this.staffInterServerPlayers;
        }

        if (!this.players.isEmpty())
        {
            PlayerListGUI.playersData.addAll(this.players);
        }

        if (!PlayerListGUI.loaded && this.players.size() > 0)
        {
            PlayerListGUI.selectedPlayer = (HashMap)this.players.get(0);
        }

        PlayerListGUI.loaded = true;
    }
}
