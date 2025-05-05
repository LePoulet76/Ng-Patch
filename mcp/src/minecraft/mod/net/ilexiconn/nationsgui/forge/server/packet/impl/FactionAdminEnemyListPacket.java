package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.AdminWarRequestListGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionAdminEnemyListPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionAdminEnemyListPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, Object>> warsInfos = new ArrayList();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.warsInfos = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new FactionAdminEnemyListPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        AdminWarRequestListGUI.warsInfos.addAll(this.warsInfos);
        AdminWarRequestListGUI.loaded = true;
    }
}
