package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.LotoAdminGui;
import net.ilexiconn.nationsgui.forge.client.gui.LotoGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.LotoDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class LotoDataPacket implements IPacket, IClientPacket
{
    public boolean isAdmin = false;
    public HashMap<String, Object> data = new HashMap();

    public LotoDataPacket(boolean isAdmin)
    {
        this.isAdmin = isAdmin;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new LotoDataPacket$1(this)).getType());
        this.isAdmin = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeBoolean(this.isAdmin);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        if (this.isAdmin)
        {
            LotoAdminGui.data = this.data;
            LotoAdminGui.loaded = true;
        }
        else
        {
            LotoGui.data = this.data;
            LotoGui.loaded = true;
        }
    }
}
