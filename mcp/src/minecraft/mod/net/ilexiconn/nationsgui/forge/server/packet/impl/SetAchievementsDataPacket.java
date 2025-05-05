package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.SetAchievementsDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class SetAchievementsDataPacket implements IPacket, IServerPacket
{
    public ArrayList<HashMap<String, String>> achievements = new ArrayList();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.achievements = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new SetAchievementsDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF((new Gson()).toJson(this.achievements));
    }

    public void handleServerPacket(EntityPlayer player)
    {
        if (this.achievements != null)
        {
            Iterator var2 = this.achievements.iterator();

            while (var2.hasNext())
            {
                HashMap achievementInfos = (HashMap)var2.next();

                if (((String)achievementInfos.get("progress")).equalsIgnoreCase("100"))
                {
                    NationsGUI.addSkinToPlayer(player.username, "badges_" + ((String)achievementInfos.get("badge")).replaceAll("badges", ""));
                }
                else if (((String)achievementInfos.get("badge")).split("_").length > 1 && !((String)achievementInfos.get("badge")).split("_")[1].equals("1"))
                {
                    int level = Integer.parseInt(((String)achievementInfos.get("badge")).split("_")[1]);
                    String partName = ((String)achievementInfos.get("badge")).split("_")[0];

                    for (int i = 1; i < level; ++i)
                    {
                        NationsGUI.addSkinToPlayer(player.username, "badges_" + partName + "_" + i);
                    }
                }
            }
        }
    }
}
