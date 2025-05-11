/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.minecraft.entity.player.EntityPlayer;

public class SetAchievementsDataPacket
implements IPacket,
IServerPacket {
    public ArrayList<HashMap<String, String>> achievements = new ArrayList();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.achievements = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.achievements));
    }

    @Override
    public void handleServerPacket(EntityPlayer player) {
        if (this.achievements != null) {
            for (HashMap<String, String> achievementInfos : this.achievements) {
                if (achievementInfos.get("progress").equalsIgnoreCase("100")) {
                    NationsGUI.addSkinToPlayer(player.field_71092_bJ, "badges_" + achievementInfos.get("badge").replaceAll("badges", ""));
                    continue;
                }
                if (achievementInfos.get("badge").split("_").length <= 1 || achievementInfos.get("badge").split("_")[1].equals("1")) continue;
                int level = Integer.parseInt(achievementInfos.get("badge").split("_")[1]);
                String partName = achievementInfos.get("badge").split("_")[0];
                for (int i = 1; i < level; ++i) {
                    NationsGUI.addSkinToPlayer(player.field_71092_bJ, "badges_" + partName + "_" + i);
                }
            }
        }
    }
}

