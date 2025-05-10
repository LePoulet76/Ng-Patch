package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.warzone.WarzonesGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesLeaderboardDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class WarzonesLeaderboardDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> infos = new HashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.infos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new WarzonesLeaderboardDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        WarzonesGui.rankingAllInfos = this.infos;
        WarzonesGui.loadedRanking = true;
    }
}
