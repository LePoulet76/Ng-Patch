package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseParcelleGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseParcelleDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseParcelleDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, Object>> parcellesInfos = new ArrayList();
    public String target;
    public boolean canSeeCoords;

    public EnterpriseParcelleDataPacket(String targetName)
    {
        this.target = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.parcellesInfos = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new EnterpriseParcelleDataPacket$1(this)).getType());
        this.canSeeCoords = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        EnterpriseParcelleGUI.parcellesInfos = this.parcellesInfos;
        EnterpriseParcelleGUI.loaded = true;
        EnterpriseParcelleGUI.canSeeCoords = this.canSeeCoords;
    }
}
