/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import acs.tabbychat.GuiChatTC;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerListDetailsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class PlayerListGUI
extends TabbedFactionListGUI {
    public static HashMap<String, HashMap<String, String>> playersExtraData = new HashMap();
    public static ArrayList<HashMap<String, String>> playersData = new ArrayList();
    public static LinkedHashMap<String, ArrayList<String>> staffPlayers = new LinkedHashMap();
    public static LinkedHashMap<String, ArrayList<String>> staffInterServerPlayers = new LinkedHashMap();
    public static HashMap<String, String> selectedPlayer = new HashMap();
    public static HashMap<String, String> hoveredPlayer = new HashMap();
    public static String hoveredStaff = "";
    private HashMap<String, EntityOtherPlayerMP> playersEntity = new HashMap();
    public static boolean loaded = false;
    private GuiScrollBarGeneric scrollBarPlayer;
    private GuiScrollBarGeneric scrollBarStaff;
    public static int staffContentHeight = 0;
    private GuiTextField searchInput;
    public int countPlayersBySearch = -1;
    public static Long lastExtraDataPacket = 0L;
    private CFontRenderer cFontSemiBold28;
    private TAB activeTab = TAB.LIST_PLAYERS;

    public PlayerListGUI() {
        playersData.clear();
        loaded = false;
        PlayerListGUI.initTabs();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        if (playersData.isEmpty()) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerListDataPacket()));
        }
        this.scrollBarPlayer = new GuiScrollBarGeneric(this.guiLeft + 297, this.guiTop + 70, 144, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarPlayer.setScrollIncrement(5.0E-4f);
        this.scrollBarStaff = new GuiScrollBarGeneric(this.guiLeft + 297, this.guiTop + 70, 144, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.searchInput = new CustomInputFieldGUI(this.guiLeft + 343, this.guiTop + 54, 94, 12, "georamaSemiBold", 28);
        this.searchInput.func_73786_a(false);
        this.searchInput.func_73804_f(15);
        this.countPlayersBySearch = -1;
        this.cFontSemiBold28 = ModernGui.getCustomFont("georamaSemiBold", 28);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        String groupName;
        ModernGui.drawDefaultBackground(this, this.field_73880_f, this.field_73881_g, mouseX, mouseY);
        tooltipToDraw.clear();
        this.hoveredAction = "";
        hoveredStaff = "";
        hoveredPlayer = new HashMap();
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.manage_countries"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.list_players"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 324, this.guiTop + 48, 276 * GUI_SCALE, 157 * GUI_SCALE, 129 * GUI_SCALE, 27 * GUI_SCALE, 129, 27, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        this.searchInput.func_73795_f();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        int index = 0;
        for (TAB tab : TAB.values()) {
            ClientEventHandler.STYLE.bindTexture("faction_list");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42 + 55 * index, this.guiTop + 36, 409 * GUI_SCALE, (this.activeTab.equals((Object)tab) ? 103 : 89) * GUI_SCALE, 52 * GUI_SCALE, 12 * GUI_SCALE, 52, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.tab." + tab.name().toLowerCase())), this.guiLeft + 42 + 55 * index + 26, this.guiTop + 39, this.activeTab.equals((Object)tab) ? 0x6E76EE : 0xDADAED, 0.5f, "center", false, "georamaSemiBold", 26);
            if (mouseX >= this.guiLeft + 42 + 55 * index && mouseX <= this.guiLeft + 42 + 55 * index + 52 && mouseY >= this.guiTop + 36 && mouseY <= this.guiTop + 36 + 12) {
                this.hoveredAction = "switch_tab#" + tab.name();
            }
            ++index;
        }
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 48, 0 * GUI_SCALE, 191 * GUI_SCALE, 266 * GUI_SCALE, 173 * GUI_SCALE, 266, 173, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        if (this.activeTab.equals((Object)TAB.LIST_PLAYERS) || this.activeTab.equals((Object)TAB.AVAILABLE)) {
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.skin"), this.guiLeft + 50, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.pseudo"), this.guiLeft + 93, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.countries"), this.guiLeft + 169, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
            ModernGui.drawScaledStringCustomFont("\u00a7o" + I18n.func_135053_a((String)"faction.list.label.power"), this.guiLeft + 243, this.guiTop + 57, 10395075, 0.5f, "left", false, "georamaMedium", 28);
            GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 67, 247, 154);
            if (playersData.size() > 0) {
                index = 0;
                for (HashMap hashMap : playersData) {
                    int offsetX = this.guiLeft + 50;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 70 + index * 16) + this.getSlidePlayers());
                    if (!this.searchInput.func_73781_b().isEmpty() && !((String)hashMap.get("name")).toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase()) || this.activeTab.equals((Object)TAB.AVAILABLE) && (!((String)hashMap.get("country")).equals("") || !((String)hashMap.get("isOnline")).equals("true"))) continue;
                    if (offsetY.intValue() >= this.guiTop + 60 && offsetY.intValue() <= this.guiTop + 70 + 170) {
                        if (!ClientProxy.cacheHeadPlayer.containsKey(hashMap.get("name"))) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)hashMap.get("name")));
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)hashMap.get("name")));
                                ClientProxy.cacheHeadPlayer.put((String)hashMap.get("name"), resourceLocation);
                            }
                            catch (Exception resourceLocation) {}
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(hashMap.get("name")));
                            this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(hashMap.get("name")));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10, offsetY.intValue() + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                            ClientEventHandler.STYLE.bindTexture("faction_list");
                            if (((String)hashMap.get("isOnline")).equals("true")) {
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 7, offsetY.intValue() - 3, 481 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            } else {
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 7, offsetY.intValue() - 3, 491 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            }
                        }
                        if (!((String)hashMap.get("country")).isEmpty()) {
                            ClientProxy.loadCountryFlag((String)hashMap.get("country"));
                            if (ClientProxy.flagsTexture.containsKey(hashMap.get("country"))) {
                                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get(hashMap.get("country")).func_110552_b());
                                ModernGui.drawScaledCustomSizeModalRect(offsetX + 15, offsetY.intValue() + 0, 0.0f, 0.0f, 156, 78, 17, 10, 156.0f, 78.0f, false);
                            }
                        }
                    }
                    ModernGui.drawScaledStringCustomFont((String)hashMap.get("name"), offsetX + 43, offsetY.intValue() + 2, ((String)hashMap.get("name")).equals(selectedPlayer.get("name")) ? 0x6E76EE : (((String)hashMap.get("isOnline")).equals("true") ? 16514302 : 10395075), 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont((String)hashMap.get("country"), offsetX + 120, offsetY.intValue() + 2, ((String)hashMap.get("name")).equals(selectedPlayer.get("name")) ? 0x6E76EE : (((String)hashMap.get("isOnline")).equals("true") ? 16514302 : 10395075), 0.5f, "left", false, "georamaSemiBold", 28);
                    ModernGui.drawScaledStringCustomFont((String)hashMap.get("power"), offsetX + 194, offsetY.intValue() + 2, ((String)hashMap.get("name")).equals(selectedPlayer.get("name")) ? 0x6E76EE : (((String)hashMap.get("isOnline")).equals("true") ? 16514302 : 10395075), 0.5f, "left", false, "georamaSemiBold", 28);
                    if (mouseX >= offsetX && mouseX <= offsetX + 247 && (float)mouseY >= offsetY.floatValue() && (float)mouseY <= offsetY.floatValue() + 16.0f) {
                        hoveredPlayer = hashMap;
                    }
                    ++index;
                }
                if (this.countPlayersBySearch == -1) {
                    this.countPlayersBySearch = index + 1;
                    if (this.countPlayersBySearch > 2500) {
                        this.scrollBarPlayer.setScrollIncrement(5.0E-4f);
                    } else if (this.countPlayersBySearch > 1000) {
                        this.scrollBarPlayer.setScrollIncrement(0.005f);
                    } else if (this.countPlayersBySearch > 200) {
                        this.scrollBarPlayer.setScrollIncrement(0.01f);
                    }
                }
            }
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173) {
                this.scrollBarPlayer.draw(mouseX, mouseY);
            }
        } else if (this.activeTab.equals((Object)TAB.STAFF)) {
            GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 55, 247, 154);
            int offsetYFix = 0;
            for (Map.Entry<String, ArrayList<String>> pair : staffPlayers.entrySet()) {
                if (pair.getValue().size() <= 0) continue;
                groupName = pair.getKey();
                int offsetX = this.guiLeft + 50;
                Float offsetY = Float.valueOf((float)(this.guiTop + 58 + offsetYFix) + this.getSlideStaff());
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("badge.title." + groupName)), offsetX, offsetY.intValue(), 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                offsetY = Float.valueOf((float)(this.guiTop + 58 + (offsetYFix += 13)) + this.getSlideStaff());
                int playerIndex = 0;
                int x = 0;
                int y = 0;
                for (String playerName : pair.getValue()) {
                    if (!this.searchInput.func_73781_b().isEmpty() && !playerName.toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) continue;
                    x = playerIndex % 8;
                    y = playerIndex / 8;
                    String online = playerName.split("##")[1];
                    if (!ClientProxy.cacheHeadPlayer.containsKey(playerName = playerName.split("##")[0])) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                            ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                        }
                        catch (Exception resourceLocation) {}
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                        this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 21 + 30 * x, offsetY.intValue() + 21 + 25 * y, 8.0f, 16.0f, 8, -8, -21, -21, 64.0f, 64.0f);
                        ClientEventHandler.STYLE.bindTexture("faction_list");
                        if (online.equals("online")) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 30 * x + 18, offsetY.intValue() + 25 * y - 3, 481 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 30 * x + 18, offsetY.intValue() + 25 * y - 3, 491 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        }
                    }
                    if (mouseX >= offsetX + 30 * x && mouseX <= offsetX + 30 * x + 21 && mouseY >= offsetY.intValue() + 25 * y && mouseY <= offsetY.intValue() + 25 * y + 21) {
                        tooltipToDraw.add(playerName);
                        hoveredStaff = playerName;
                    }
                    ++playerIndex;
                }
                offsetYFix += (y + 1) * 25 + 3;
            }
            staffContentHeight = offsetYFix;
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173) {
                this.scrollBarStaff.draw(mouseX, mouseY);
            }
        } else if (this.activeTab.equals((Object)TAB.STAFF_INTER_SERVER)) {
            GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 55, 247, 154);
            int offsetYFix = 0;
            for (Map.Entry<String, ArrayList<String>> pair : staffInterServerPlayers.entrySet()) {
                if (pair.getValue().size() <= 0) continue;
                groupName = pair.getKey();
                int offsetX = this.guiLeft + 50;
                Float offsetY = Float.valueOf((float)(this.guiTop + 58 + offsetYFix) + this.getSlideStaff());
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("staff.interserver." + groupName)), offsetX, offsetY.intValue(), 16514302, 0.5f, "left", false, "georamaSemiBold", 32);
                offsetY = Float.valueOf((float)(this.guiTop + 58 + (offsetYFix += 13)) + this.getSlideStaff());
                int playerIndex = 0;
                int x = 0;
                int y = 0;
                for (String playerName : pair.getValue()) {
                    if (!this.searchInput.func_73781_b().isEmpty() && !playerName.toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) continue;
                    x = playerIndex % 8;
                    y = playerIndex / 8;
                    String online = playerName.split("##")[1];
                    if (!ClientProxy.cacheHeadPlayer.containsKey(playerName = playerName.split("##")[0])) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                            ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                        }
                        catch (Exception exception) {}
                    } else {
                        Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                        this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                        GUIUtils.drawScaledCustomSizeModalRect(offsetX + 21 + 30 * x, offsetY.intValue() + 21 + 25 * y, 8.0f, 16.0f, 8, -8, -21, -21, 64.0f, 64.0f);
                        ClientEventHandler.STYLE.bindTexture("faction_list");
                        if (online.equals("online")) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 30 * x + 18, offsetY.intValue() + 25 * y - 3, 481 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 30 * x + 18, offsetY.intValue() + 25 * y - 3, 491 * GUI_SCALE, 2 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        }
                    }
                    if (mouseX >= offsetX + 30 * x && mouseX <= offsetX + 30 * x + 21 && mouseY >= offsetY.intValue() + 25 * y && mouseY <= offsetY.intValue() + 25 * y + 21) {
                        tooltipToDraw.add(playerName);
                        hoveredStaff = playerName;
                    }
                    ++playerIndex;
                }
                offsetYFix += (y + 1) * 25 + 3;
            }
            staffContentHeight = offsetYFix;
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 42 && mouseX <= this.guiLeft + 42 + 266 && mouseY >= this.guiTop + 48 && mouseY <= this.guiTop + 48 + 173) {
                this.scrollBarStaff.draw(mouseX, mouseY);
            }
        }
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 324, this.guiTop + 81, 0 * GUI_SCALE, 372 * GUI_SCALE, 129 * GUI_SCALE, 140 * GUI_SCALE, 129, 140, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        if (selectedPlayer != null && !selectedPlayer.isEmpty()) {
            if (!playersExtraData.containsKey(selectedPlayer.get("name"))) {
                if (System.currentTimeMillis() - lastExtraDataPacket > 500L) {
                    lastExtraDataPacket = System.currentTimeMillis();
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new PlayerListDetailsPacket(selectedPlayer.get("name"))));
                }
            } else {
                void var6_18;
                HashMap<String, String> playerExtraData = playersExtraData.get(selectedPlayer.get("name"));
                ClientEventHandler.STYLE.bindTexture("faction_list");
                if (selectedPlayer.get("isOnline").equals("true")) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 448, this.guiTop + 77, 480 * GUI_SCALE, 11 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 448, this.guiTop + 77, 490 * GUI_SCALE, 11 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(selectedPlayer.get("name"), this.guiLeft + 378, this.guiTop + 88, 16514302, 0.75f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.label." + (selectedPlayer.get("isOnline").equals("true") ? "online" : "offline"))).replaceAll("#time#", !selectedPlayer.get("lastLogin").isEmpty() ? ModernGui.formatDelayTime(Long.parseLong(selectedPlayer.get("lastLogin"))) : "-"), this.guiLeft + 378, this.guiTop + 100, 10395075, 0.5f, "left", false, "georamaMedium", 26);
                if (NationsGUI.BADGES_RESOURCES.containsKey(playerExtraData.get("group").toLowerCase())) {
                    Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUI.BADGES_RESOURCES.get(playerExtraData.get("group").toLowerCase()));
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 378, this.guiTop + 113, 0.0f, 0.0f, 18, 18, 8, 8, 18.0f, 18.0f, false);
                }
                ModernGui.drawScaledStringCustomFont(ModernGui.getRankColor(playerExtraData.get("group").toLowerCase()) + playerExtraData.get("group"), this.guiLeft + 388, this.guiTop + 113, 10395075, 0.5f, "left", false, "georamaMedium", 26);
                if (!this.playersEntity.containsKey(selectedPlayer.get("name"))) {
                    try {
                        this.playersEntity.put(selectedPlayer.get("name"), new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, selectedPlayer.get("name")));
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                if (this.playersEntity.containsKey(selectedPlayer.get("name")) && this.playersEntity.get(selectedPlayer.get("name")) != null) {
                    GUIUtils.startGLScissor(this.guiLeft + 324, this.guiTop + 67, 50, 57);
                    GuiInventory.func_110423_a((int)(this.guiLeft + 350), (int)(this.guiTop + 145), (int)35, (float)0.0f, (float)0.0f, (EntityLivingBase)((EntityLivingBase)this.playersEntity.get(selectedPlayer.get("name"))));
                    GUIUtils.endGLScissor();
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.playtime_interserv"), this.guiLeft + 353, this.guiTop + 131, FactionGUI.textColor.get("neutral"), 0.5f, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(ModernGui.formatDuration(Long.parseLong(playerExtraData.get("playtimeInterServ"))), this.guiLeft + 353, this.guiTop + 137, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 124, FactionListGUI.iconsXPerRelation.get("neutral") * GUI_SCALE, FactionListGUI.iconsYByName.get("stats") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.playtime").replaceAll("#server#", ClientProxy.currentServerName), this.guiLeft + 353, this.guiTop + 156, FactionGUI.textColor.get("neutral"), 0.5f, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(!selectedPlayer.get("playtime").isEmpty() ? ModernGui.formatDuration(Long.parseLong(selectedPlayer.get("playtime"))) : "0", this.guiLeft + 353, this.guiTop + 162, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 149, FactionListGUI.iconsXPerRelation.get("neutral") * GUI_SCALE, FactionListGUI.iconsYByName.get("timer") * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.list.label.top_skill"), this.guiLeft + 353, this.guiTop + 181, FactionGUI.textColor.get("neutral"), 0.5f, "left", false, "georamaMedium", 22);
                ModernGui.drawScaledStringCustomFont(!playerExtraData.get("topSkill").isEmpty() ? I18n.func_135053_a((String)("skills.skill." + playerExtraData.get("topSkill"))) : "-", this.guiLeft + 353, this.guiTop + 187, 16514302, 0.5f, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 325, this.guiTop + 174, FactionListGUI.iconsXPerRelation.get("neutral") * GUI_SCALE, FactionListGUI.iconsYByName.get(playerExtraData.get("topSkill")) * GUI_SCALE, 30 * GUI_SCALE, 29 * GUI_SCALE, 30, 29, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("faction_list");
                String string = "send_message";
                if (selectedPlayer.get("name").equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) || selectedPlayer.get("isOnline").equals("false")) {
                    String string2 = "see_player";
                } else if (this.activeTab.equals((Object)TAB.LIST_PLAYERS)) {
                    String string3 = "send_message";
                } else if (this.activeTab.equals((Object)TAB.AVAILABLE) && playerExtraData.containsKey("viewerCanInvite") && playerExtraData.get("viewerCanInvite").equals("true")) {
                    String string4 = "invite_country";
                }
                if (mouseX >= this.guiLeft + 337 && mouseX <= this.guiLeft + 337 + 103 && mouseY >= this.guiTop + 204 && mouseY <= this.guiTop + 204 + 13) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 204, 276 * GUI_SCALE, 222 * GUI_SCALE, 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    this.hoveredAction = var6_18;
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 204, 276 * GUI_SCALE, 204 * GUI_SCALE, 103 * GUI_SCALE, 13 * GUI_SCALE, 103, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.list.label." + (String)var6_18)), this.guiLeft + 337 + 52, this.guiTop + 207, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    private float getSlidePlayers() {
        return this.countPlayersBySearch > 10 ? (float)(-(this.countPlayersBySearch - 10) * 12) * this.scrollBarPlayer.getSliderValue() : 0.0f;
    }

    private float getSlideStaff() {
        return (float)(-(staffContentHeight - 154)) * this.scrollBarStaff.getSliderValue();
    }

    public void func_73876_c() {
        this.searchInput.func_73780_a();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.searchInput.func_73802_a(typedChar, keyCode);
        this.countPlayersBySearch = -1;
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        block4: {
            block5: {
                block8: {
                    block7: {
                        block6: {
                            if (mouseButton != 0) break block4;
                            if (this.hoveredAction.isEmpty()) break block5;
                            if (!this.hoveredAction.equals("send_message")) break block6;
                            Minecraft.func_71410_x().func_71373_a((GuiScreen)new GuiChatTC("/msg " + selectedPlayer.get("name") + ""));
                            break block4;
                        }
                        if (!this.hoveredAction.equals("invite_country")) break block7;
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket(selectedPlayer.get("name"), "", "invite_start")));
                        break block4;
                    }
                    if (!this.hoveredAction.contains("switch_tab")) break block8;
                    this.activeTab = TAB.valueOf(this.hoveredAction.replace("switch_tab#", ""));
                    this.countPlayersBySearch = -1;
                    break block4;
                }
                if (!this.hoveredAction.contains("see_player")) break block4;
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(selectedPlayer.get("name"), ""));
                break block4;
            }
            if (hoveredPlayer != null && !hoveredPlayer.isEmpty()) {
                selectedPlayer = hoveredPlayer;
            } else if (!hoveredStaff.isEmpty()) {
                for (HashMap<String, String> player : playersData) {
                    if (!player.get("name").equalsIgnoreCase(hoveredStaff)) continue;
                    selectedPlayer = player;
                    break;
                }
            }
        }
        this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    static enum TAB {
        LIST_PLAYERS,
        AVAILABLE,
        STAFF,
        STAFF_INTER_SERVER;

    }
}

