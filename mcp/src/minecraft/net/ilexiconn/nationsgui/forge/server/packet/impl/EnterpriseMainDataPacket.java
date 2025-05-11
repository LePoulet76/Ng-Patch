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
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseBetGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseCasinoGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseContractGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseElectricGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseFarmGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseParcelleGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterprisePetrolGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseSettingsGUI;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseTraderGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseMainDataPacket
implements IPacket,
IClientPacket {
    public HashMap<String, Object> enterpriseInfos = new HashMap();
    public String target;

    public EnterpriseMainDataPacket(String targetName) {
        this.target = targetName;
    }

    @Override
    public void fromBytes(ByteArrayDataInput data) {
        this.enterpriseInfos = (HashMap)new Gson().fromJson(data.readUTF(), new TypeToken<HashMap<String, Object>>(){}.getType());
    }

    @Override
    public void toBytes(ByteArrayDataOutput data) {
        data.writeUTF(this.target);
    }

    @Override
    @SideOnly(value=Side.CLIENT)
    public void handleClientPacket(EntityPlayer player) {
        EnterpriseGui.initTabs();
        EnterpriseGui.enterpriseInfos = this.enterpriseInfos;
        EnterpriseGui.loaded = true;
        EnterpriseGui.mapLoaded = false;
        EnterpriseGui.availableTypes.clear();
        EnterpriseGui.availableTypes.add("all");
        EnterpriseGui.availableTypes.addAll((List)this.enterpriseInfos.get("allTypes"));
        if (this.enterpriseInfos.get("type").equals("realestate")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseParcelleGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseParcelleGUI());
                }
            });
        } else if (!(this.enterpriseInfos.get("type").equals("casino") || this.enterpriseInfos.get("type").equals("bet") || this.enterpriseInfos.get("type").equals("electric") || this.enterpriseInfos.get("type").equals("petrol") || this.enterpriseInfos.get("type").equals("farm"))) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseContractGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseContractGUI());
                }
            });
        }
        if (this.enterpriseInfos.get("type").equals("trader")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseTraderGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseTraderGUI());
                }
            });
        }
        if (this.enterpriseInfos.get("type").equals("casino")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseCasinoGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseCasinoGUI());
                }
            });
        }
        if (this.enterpriseInfos.get("type").equals("electric")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseElectricGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseElectricGUI());
                }
            });
        }
        if (this.enterpriseInfos.get("type").equals("petrol")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterprisePetrolGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterprisePetrolGUI());
                }
            });
        }
        if (this.enterpriseInfos.get("type").equals("farm")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseFarmGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseFarmGUI());
                }
            });
        }
        if (this.enterpriseInfos.get("type").equals("bet")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseBetGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseBetGUI());
                }
            });
        }
        if (EnterpriseGui.hasPermission("settings")) {
            EnterpriseGui.TABS.add(new GuiScreenTab(){

                @Override
                public Class<? extends GuiScreen> getClassReferent() {
                    return EnterpriseSettingsGUI.class;
                }

                @Override
                public void call() {
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseSettingsGUI());
                }
            });
        }
    }
}

