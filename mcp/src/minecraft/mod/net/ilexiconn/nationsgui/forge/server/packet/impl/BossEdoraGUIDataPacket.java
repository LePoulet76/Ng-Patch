package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.BossEdoraGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BossEdoraGUIDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class BossEdoraGUIDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> data = new HashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new BossEdoraGUIDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        System.out.println(this.data.toString());
        BossEdoraGui.data = this.data;
    }
}
