package net.ilexiconn.nationsgui.forge.server.packet.impl;

import acs.tabbychat.ChatChannel;
import acs.tabbychat.TabbyChat;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.InventoryGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionNamePacket implements IPacket, IClientPacket
{
    String name = "";
    boolean isInAssault = false;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.name = data.readUTF();
        this.isInAssault = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.name);
        data.writeBoolean(this.isInAssault);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (!TabbyChat.defaultChannelsInit && ClientProxy.serverType.equals("ng"))
        {
            if (this.name.contains("Wilderness"))
            {
                TabbyChat.instance.channelMap.remove("Mon pays");
                TabbyChat.instance.channelMap.remove("ALL");
                TabbyChat.instance.channelMap.remove("ENE");
            }
            else
            {
                if (!TabbyChat.instance.channelMap.containsKey("Mon pays"))
                {
                    TabbyChat.instance.channelMap.put("Mon pays", new ChatChannel("Mon pays"));
                }

                if (!TabbyChat.instance.channelMap.containsKey("ALL"))
                {
                    TabbyChat.instance.channelMap.put("ALL", new ChatChannel("ALL"));
                }

                if (!TabbyChat.instance.channelMap.containsKey("ENE"))
                {
                    TabbyChat.instance.channelMap.put("ENE", new ChatChannel("ENE"));
                }
            }

            TabbyChat.defaultChannelsInit = true;
        }

        ClientData.currentFaction = this.name;
        InventoryGUI.isInAssault = this.isInAssault;
    }
}
