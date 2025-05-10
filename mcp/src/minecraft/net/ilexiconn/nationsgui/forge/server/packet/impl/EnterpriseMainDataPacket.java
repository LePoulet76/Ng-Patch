package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$1;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$10;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$2;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$3;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$4;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$5;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$6;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$7;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$8;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseMainDataPacket$9;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseMainDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> enterpriseInfos = new HashMap();
    public String target;

    public EnterpriseMainDataPacket(String targetName)
    {
        this.target = targetName;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.enterpriseInfos = (HashMap)(new Gson()).fromJson(data.readUTF(), (new EnterpriseMainDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        EnterpriseGui.initTabs();
        EnterpriseGui.enterpriseInfos = this.enterpriseInfos;
        EnterpriseGui.loaded = true;
        EnterpriseGui.mapLoaded = false;
        EnterpriseGui.availableTypes.clear();
        EnterpriseGui.availableTypes.add("all");
        EnterpriseGui.availableTypes.addAll((List)this.enterpriseInfos.get("allTypes"));

        if (this.enterpriseInfos.get("type").equals("realestate"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$2(this));
        }
        else if (!this.enterpriseInfos.get("type").equals("casino") && !this.enterpriseInfos.get("type").equals("bet") && !this.enterpriseInfos.get("type").equals("electric") && !this.enterpriseInfos.get("type").equals("petrol") && !this.enterpriseInfos.get("type").equals("farm"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$3(this));
        }

        if (this.enterpriseInfos.get("type").equals("trader"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$4(this));
        }

        if (this.enterpriseInfos.get("type").equals("casino"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$5(this));
        }

        if (this.enterpriseInfos.get("type").equals("electric"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$6(this));
        }

        if (this.enterpriseInfos.get("type").equals("petrol"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$7(this));
        }

        if (this.enterpriseInfos.get("type").equals("farm"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$8(this));
        }

        if (this.enterpriseInfos.get("type").equals("bet"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$9(this));
        }

        if (EnterpriseGui.hasPermission("settings"))
        {
            EnterpriseGui.TABS.add(new EnterpriseMainDataPacket$10(this));
        }
    }
}
