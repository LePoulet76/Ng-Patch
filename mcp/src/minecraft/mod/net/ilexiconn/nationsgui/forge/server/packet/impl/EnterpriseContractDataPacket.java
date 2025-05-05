package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseContractDataPacket implements IPacket, IClientPacket
{
    public ArrayList<HashMap<String, Object>> contractsInfos = new ArrayList();
    public String target;

    public EnterpriseContractDataPacket(String targetName)
    {
        this.target = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.contractsInfos = (ArrayList)(new Gson()).fromJson(data.readUTF(), (new EnterpriseContractDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        EnterpriseContractGUI.contractsInfos = this.contractsInfos;
        EnterpriseContractGUI.loaded = true;
    }
}
