package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.achievements.AchievementsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AchievementsDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class AchievementsDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, String>> achievements = new ArrayList();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.achievements = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new AchievementsDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.achievements));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (ClientData.getAchievements() != null)
        {
            ClientData.getAchievements().clear();
        }

        if (this.achievements != null)
        {
            ClientData.setAchievements(this.achievements);
            AchievementsGUI.loaded = true;
            SetAchievementsDataPacket packet = new SetAchievementsDataPacket();
            packet.achievements = this.achievements;
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(packet));
        }
    }
}
