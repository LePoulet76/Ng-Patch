package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCreateGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCreateDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseCreateDataPacket$2;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseCreateDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> createInfos = new HashMap();
    public List<String> availableTypes = new ArrayList();

    public void fromBytes(ByteArrayDataInput data)
    {
        this.createInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseCreateDataPacket$1(this)).getType());
        this.availableTypes = (List)(new Gson()).fromJson(data.readUTF(), (new EnterpriseCreateDataPacket$2(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        EnterpriseCreateGui.loaded = true;
        EnterpriseCreateGui.createInfos = this.createInfos;
        EnterpriseGui.availableTypes.clear();
        EnterpriseGui.availableTypes.addAll(this.availableTypes);
    }
}
