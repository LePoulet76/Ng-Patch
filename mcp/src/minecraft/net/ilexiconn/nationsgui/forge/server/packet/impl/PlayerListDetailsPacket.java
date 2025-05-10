package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.PlayerListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDetailsPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerListDetailsPacket implements IPacket, IClientPacket
{
    public String targetPlayer;
    public HashMap<String, String> playerData = new HashMap();

    public PlayerListDetailsPacket(String targetPlayer)
    {
        this.targetPlayer = targetPlayer;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.targetPlayer = data.readUTF();
        this.playerData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new PlayerListDetailsPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.targetPlayer);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PlayerListGUI.playersExtraData.put(this.targetPlayer, this.playerData);
        System.out.println(this.playerData.toString());
    }
}
