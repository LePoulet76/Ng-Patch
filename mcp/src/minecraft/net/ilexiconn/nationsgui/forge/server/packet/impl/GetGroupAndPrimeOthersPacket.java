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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GetGroupAndPrimePacket;
import net.minecraft.entity.player.EntityPlayer;

public class GetGroupAndPrimeOthersPacket
implements IPacket,
IClientPacket {
    public HashMap<String, String> badges = new HashMap();
    public List<String> ngprimePlayers = new ArrayList<String>();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.badges = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, String>>(){}.getType());
        this.ngprimePlayers = (List)new Gson().fromJson(data.readUTF(), new TypeToken<List<String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.badges));
        data.writeUTF(new Gson().toJson(this.ngprimePlayers));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        Iterator<Map.Entry<String, String>> it = this.badges.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> pair = it.next();
            String playerName = pair.getKey();
            String badgeName = pair.getValue();
            if (!badgeName.equals("none") && NationsGUI.BADGES_RESOURCES.containsKey(badgeName.toLowerCase())) {
                GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.put(playerName, badgeName.toLowerCase());
            } else {
                GetGroupAndPrimePacket.GRP_BADGES_PLAYERS.remove(playerName);
            }
            it.remove();
        }
        GetGroupAndPrimePacket.NGPRIME_PLAYERS = this.ngprimePlayers;
    }
}

