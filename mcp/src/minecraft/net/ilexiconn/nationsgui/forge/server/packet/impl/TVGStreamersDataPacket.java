package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.TVGStreamersGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TVGStreamersDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class TVGStreamersDataPacket implements IPacket, IClientPacket
{
    private Integer totalCagnotte;
    private String targetStreamer;
    private String playerStreamer;
    private HashMap<String, String> streamerData;
    private Long playerTime;
    private Integer playerPosition;

    public TVGStreamersDataPacket(String targetStreamer)
    {
        this.targetStreamer = targetStreamer;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.playerStreamer = data.readUTF();
        this.streamerData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new TVGStreamersDataPacket$1(this)).getType());
        this.totalCagnotte = Integer.valueOf(data.readInt());
        this.playerTime = Long.valueOf(data.readLong());
        this.playerPosition = Integer.valueOf(data.readInt());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.targetStreamer);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        TVGStreamersGui.playerStreamer = this.playerStreamer;

        if (TVGStreamersGui.selectedStreamer.isEmpty())
        {
            TVGStreamersGui.selectedStreamer = this.playerStreamer;
        }

        TVGStreamersGui.streamerData = this.streamerData;
        TVGStreamersGui.totalCagnotte = this.totalCagnotte;
        TVGStreamersGui.playerPosition = this.playerPosition;
        TVGStreamersGui.playerTime = this.playerTime;
        TVGStreamersGui.loaded = true;
    }
}
