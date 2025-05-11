/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.io.ByteArrayDataInput
 *  com.google.common.io.ByteArrayDataOutput
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionPlotsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionPlotsDataPacket
implements IPacket,
IClientPacket {
    public ArrayList<HashMap<String, Object>> plots = new ArrayList();
    public String targetFaction;
    public int countSell;
    public int countRent;
    public int countAvailable;
    public boolean canCreateNewPlot;

    public FactionPlotsDataPacket(String targetFaction) {
        this.targetFaction = targetFaction;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.targetFaction = data.readUTF();
        this.plots = (ArrayList)new Gson().fromJson(data.readUTF(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
        this.countSell = data.readInt();
        this.countRent = data.readInt();
        this.countAvailable = data.readInt();
        this.canCreateNewPlot = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.targetFaction);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        FactionPlotsGUI.plots.addAll(this.plots);
        FactionPlotsGUI.loaded = true;
        FactionPlotsGUI.selectedPlot = new HashMap();
        FactionPlotsGUI.countSell = this.countSell;
        FactionPlotsGUI.countRent = this.countRent;
        FactionPlotsGUI.countAvailable = this.countAvailable;
        FactionPlotsGUI.canCreateNewPlot = this.canCreateNewPlot;
    }
}

