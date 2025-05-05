package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.ATMGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ATMDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class ATMDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> data = new HashMap();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new ATMDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        ATMGUI.data = this.data;
        ATMGUI.loaded = true;
        ATMGUI.lastAmountAnimation = System.currentTimeMillis();
    }
}
