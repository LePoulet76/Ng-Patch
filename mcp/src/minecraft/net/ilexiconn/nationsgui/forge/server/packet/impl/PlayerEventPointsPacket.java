package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerEventPointsPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerEventPointsPacket implements IPacket, IClientPacket
{
    private ArrayList<String> eventPoints;

    public PlayerEventPointsPacket(ArrayList<String> eventPoints)
    {
        this.eventPoints = eventPoints;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.eventPoints = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new PlayerEventPointsPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.eventPoints));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        System.out.println("debug points event");
        System.out.println(this.eventPoints.toString());
        ClientData.eventPointsValidated.addAll(this.eventPoints);
    }
}
