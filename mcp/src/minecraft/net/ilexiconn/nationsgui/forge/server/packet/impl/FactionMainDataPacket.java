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
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.entity.player.EntityPlayer
 */
package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionPlotsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class FactionMainDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> factionInfos = new HashMap();
    public String target;
    private boolean force;

    public FactionMainDataPacket(String targetName, boolean force) {
        this.target = targetName;
        this.force = force;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.factionInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
        this.force = data.readBoolean();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
        data.writeBoolean(this.force);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        FactionGUI.initTabs();
        FactionGUI.factionInfos = this.factionInfos;
        FactionGUI.loaded = true;
        if (((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue() || ((Boolean)FactionGUI.factionInfos.get("isAdmin")).booleanValue()) {
            FactionGUI.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return FactionPlotsGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionPlotsGUI());
                }
            });
        }
        if (FactionGUI.hasPermissions("settings")) {
            FactionGUI.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return SettingsGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new SettingsGUI());
                }
            });
        }
    }
}

