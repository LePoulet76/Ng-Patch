package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionCreateEditPlotsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotDataPacket$1;
import net.minecraft.entity.player.EntityPlayer;

public class FactionPlotDataPacket implements IPacket, IClientPacket
{
    public HashMap<String, Object> plotData = new HashMap();
    public int plotId;

    public FactionPlotDataPacket(int plotId)
    {
        this.plotId = plotId;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.plotData = (HashMap)(new Gson()).fromJson(data.readUTF(), (new FactionPlotDataPacket$1(this)).getType());
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.plotId);
    }

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        FactionCreateEditPlotsGUI.editedPlot = this.plotData;
        FactionCreateEditPlotsGUI.loaded = true;
        System.out.println("Received plot data for " + this.plotId);
        System.out.println(this.plotData.toString());
    }
}
