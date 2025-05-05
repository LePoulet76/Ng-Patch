package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionPlotsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionPlotsActionPacket implements IPacket, IClientPacket
{
    public int targetPlot;
    public String action;
    public HashMap<String, Object> data;

    public FactionPlotsActionPacket(int targetPlot, String action, HashMap<String, Object> data)
    {
        this.targetPlot = targetPlot;
        this.action = action;
        this.data = data;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeInt(this.targetPlot);
        data.writeUTF(this.action);
        data.writeUTF((new Gson()).toJson(this.data));
    }

    public void handleClientPacket(EntityPlayer player)
    {
        FactionPlotsGUI.loaded = false;
        FactionPlotsGUI.plots.clear();
        FactionPlotsGUI.selectedPlot = new HashMap();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionPlotsDataPacket((String)FactionGUI.factionInfos.get("id"))));
    }
}
