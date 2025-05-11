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
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandBackupGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMembersGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPermsGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandPropertiesGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandSettingsGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class IslandMainDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> islandInfos = new HashMap();
    public boolean isPremium;
    public boolean isOp;
    public String serverNumber;

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.islandInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
        this.isPremium = data.readBoolean();
        this.isOp = data.readBoolean();
        this.serverNumber = data.readUTF();
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        IslandMainGui.islandInfos = this.islandInfos;
        IslandMainGui.isPremium = this.isPremium;
        IslandMainGui.isOp = this.isOp;
        IslandMainGui.serverNumber = this.serverNumber;
        IslandMainGui.membersPerms = (HashMap)new Gson().fromJson((String)this.islandInfos.get("memberPermissions"), new TypeToken<HashMap<String, Boolean>>(){}.getType());
        IslandMainGui.visitorsPerms = (HashMap)new Gson().fromJson((String)this.islandInfos.get("visitorPermissions"), new TypeToken<HashMap<String, Boolean>>(){}.getType());
        IslandMainGui.islandFlags = (HashMap)new Gson().fromJson((String)this.islandInfos.get("islandFlags"), new TypeToken<HashMap<String, Boolean>>(){}.getType());
        IslandPermsGui.editedPerms = new HashMap();
        IslandMainGui.TABS.clear();
        IslandMainGui.TABS.add(new GuiScreenTab(){

            @Override
            public Class<? extends GuiScreen> getClassReferent() {
                return IslandMainGui.class;
            }

            @Override
            public void call() {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandMainGui());
            }
        });
        if (this.islandInfos.get("isCreator").equals("true") || this.isOp) {
            IslandMainGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return IslandMembersGui.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandMembersGui());
                }
            });
            IslandMainGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return IslandPermsGui.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandPermsGui());
                }
            });
            IslandMainGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return IslandSettingsGui.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandSettingsGui());
                }
            });
        }
        if (this.islandInfos.get("isCreator").equals("true") || ((ArrayList)this.islandInfos.get("playerPermissions")).contains("tab_properties") || this.isOp) {
            IslandMainGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return IslandPropertiesGui.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandPropertiesGui());
                }
            });
        }
        if (this.islandInfos.get("isCreator").equals("true") || this.isOp) {
            IslandMainGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return IslandBackupGui.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new IslandBackupGui());
                }
            });
        }
        IslandMainGui.loaded = true;
    }
}

