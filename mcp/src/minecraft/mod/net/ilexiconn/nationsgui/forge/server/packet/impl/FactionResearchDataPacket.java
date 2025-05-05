package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionResearchGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionResearchDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionResearchDataPacket implements IPacket, IClientPacket
{
    private String targetFaction;
    private HashMap<String, Object> data = new HashMap();

    public FactionResearchDataPacket(String targetFaction)
    {
        this.targetFaction = targetFaction;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.targetFaction = data.readUTF();
        this.data = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionResearchDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.targetFaction);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        System.out.println(this.data.toString());
        FactionResearchGUI.data = this.data;
        FactionResearchGUI.loaded = true;
    }
}
