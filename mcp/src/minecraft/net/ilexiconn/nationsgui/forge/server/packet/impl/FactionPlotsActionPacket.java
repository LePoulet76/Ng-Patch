/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.network.packet.Packet
 */
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
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlotsDataPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet;

public class FactionPlotsActionPacket
implements IPacket,
IClientPacket {
    public int targetPlot;
    public String action;
    public HashMap<String, Object> data;

    public FactionPlotsActionPacket(int targetPlot, String action, HashMap<String, Object> data) {
        this.targetPlot = targetPlot;
        this.action = action;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeInt(this.targetPlot);
        data.writeUTF(this.action);
        data.writeUTF(new Gson().toJson(this.data));
    }

    @Override
    public void handleClientPacket(EntityPlayer player) {
        FactionPlotsGUI.loaded = false;
        FactionPlotsGUI.plots.clear();
        FactionPlotsGUI.selectedPlot = new HashMap();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlotsDataPacket((String)FactionGUI.factionInfos.get("id"))));
    }
}

