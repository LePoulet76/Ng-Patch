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

import acs.tabbychat.ChatChannel;
import acs.tabbychat.TabbyChat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class RefreshChannelPacket
implements IPacket,
IClientPacket {
    ArrayList<String> authorizedChannels = new ArrayList();

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.authorizedChannels = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<String>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(new Gson().toJson(this.authorizedChannels));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        if (ClientProxy.serverType.equals("ng")) {
            Iterator<String> iterator = this.authorizedChannels.iterator();
            while (iterator.hasNext()) {
                String channel;
                switch (channel = iterator.next()) {
                    case "admin": {
                        TabbyChat.instance.channelMap.put("ADMIN", new ChatChannel("ADMIN"));
                        break;
                    }
                    case "modo": {
                        TabbyChat.instance.channelMap.put("MODO", new ChatChannel("MODO"));
                        break;
                    }
                    case "journal": {
                        TabbyChat.instance.channelMap.put("Journal", new ChatChannel("Journal"));
                        break;
                    }
                    case "avocat": {
                        TabbyChat.instance.channelMap.put("Avocat", new ChatChannel("Avocat"));
                        break;
                    }
                    case "mafia": {
                        TabbyChat.instance.channelMap.put("Mafia", new ChatChannel("Mafia"));
                        break;
                    }
                    case "police": {
                        TabbyChat.instance.channelMap.put("Police", new ChatChannel("Police"));
                        break;
                    }
                    case "guide": {
                        TabbyChat.instance.channelMap.put("Guide", new ChatChannel("Guide"));
                        break;
                    }
                    case "rp": {
                        TabbyChat.instance.channelMap.put("RP", new ChatChannel("RP"));
                        break;
                    }
                    case "logs": {
                        TabbyChat.instance.channelMap.put("Logs", new ChatChannel("Logs"));
                    }
                }
            }
        } else {
            String channel = this.authorizedChannels.get(this.authorizedChannels.size() - 1);
            Iterator<Map.Entry<String, ChatChannel>> it = TabbyChat.instance.channelMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, ChatChannel> pair = it.next();
                if (pair.getKey().contains("Global")) continue;
                it.remove();
            }
            if (channel.matches("i[0-9]+")) {
                TabbyChat.instance.channelMap.put("Ile " + channel.replace("i", ""), new ChatChannel("Ile " + channel.replace("i", "")));
            }
        }
    }
}

