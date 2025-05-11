/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaderConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.ProfilGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMembersDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionNewMembersDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionPlayerDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionProfilActionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MembersGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static boolean loaded_new_player = false;
    public static TreeMap<String, Object> factionMembersInfos;
    public static LinkedHashMap<String, Object> factionNewMembersInfos;
    private GuiScrollBarGeneric scrollBarMembers;
    private GuiTextField searchInput;
    private String displayMode = "members";
    private String hoveredPlayer;
    public static Long lastPromotePlayer;
    private HashMap<String, EntityOtherPlayerMP> entities = new HashMap();
    public static ArrayList<Integer> OFFICERS_POSITION_X;
    public static ArrayList<Integer> OFFICERS_POSITION_Y;
    public int countMembersBySearch = -1;
    public int countNewMembersBySearch = -1;
    public static HashMap<String, Integer> blockMembers;

    public MembersGUI() {
        loaded = false;
        factionMembersInfos = new TreeMap();
        factionNewMembersInfos = new LinkedHashMap();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionNewMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
    }

    public void func_73876_c() {
        this.searchInput.func_73780_a();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBarMembers = new GuiScrollBarGeneric(this.guiLeft + 250, this.guiTop + 101, 119, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.searchInput = new GuiTextField(this.field_73886_k, this.guiLeft + 60, this.guiTop + 63, 97, 10);
        this.searchInput.func_73786_a(false);
        this.searchInput.func_73804_f(15);
        this.countMembersBySearch = -1;
        this.countNewMembersBySearch = -1;
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.hoveredPlayer = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (loaded) {
            int index;
            if (FactionGUI.factionInfos.get("banners") != null && ((Map)FactionGUI.factionInfos.get("banners")).containsKey("members")) {
                ModernGui.bindRemoteTexture((String)((Map)FactionGUI.factionInfos.get("banners")).get("members"));
            } else {
                ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N3255yGyNN.png");
            }
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 89, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
            ClientEventHandler.STYLE.bindTexture("faction_global");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 33 * GUI_SCALE, 280 * GUI_SCALE, 433 * GUI_SCALE, 89 * GUI_SCALE, 433, 89, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
            ModernGui.drawSectionStringCustomFont(((String)FactionGUI.factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), this.guiLeft + 43, this.guiTop + 30, 10395075, 0.5f, "left", false, "georamaMedium", 22, 7, 360);
            ClientEventHandler.STYLE.bindTexture("faction_members");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, this.guiTop + 61, 177 * GUI_SCALE, 123 * GUI_SCALE, 113 * GUI_SCALE, 12 * GUI_SCALE, 113, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 46, this.guiTop + 63, 177 * GUI_SCALE, 24 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            this.searchInput.func_73795_f();
            ArrayList<String> officers = new ArrayList<String>();
            ArrayList<String> members = new ArrayList<String>();
            for (Map.Entry<String, Object> pair : factionMembersInfos.entrySet()) {
                String playerName = pair.getKey().split(" ")[1];
                LinkedTreeMap playerData = (LinkedTreeMap)pair.getValue();
                if (playerData.get((Object)"rank").equals("LEADER") || playerData.get((Object)"rank").equals("OFFICER")) {
                    if (!this.entities.containsKey(playerName)) {
                        try {
                            this.entities.put(playerName, new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, playerName));
                        }
                        catch (Exception e) {
                            this.entities.put(playerName, null);
                        }
                    }
                    if (!playerData.get((Object)"rank").equals("OFFICER")) continue;
                    officers.add(playerName);
                    continue;
                }
                if (!playerData.get((Object)"rank").equals("MEMBER")) continue;
                members.add(playerName);
            }
            ArrayList<Integer> officersPositionX = OFFICERS_POSITION_X;
            ArrayList<Integer> officersPositionY = OFFICERS_POSITION_Y;
            if (officers.size() <= 2) {
                officersPositionX = new ArrayList<Integer>(OFFICERS_POSITION_X.subList(4, 6));
                officersPositionY = new ArrayList<Integer>(OFFICERS_POSITION_Y.subList(4, 6));
            } else if (officers.size() == 3) {
                officersPositionX = new ArrayList<Integer>(Arrays.asList(OFFICERS_POSITION_X.get(2), OFFICERS_POSITION_X.get(4), OFFICERS_POSITION_X.get(5)));
                officersPositionY = new ArrayList<Integer>(Arrays.asList(OFFICERS_POSITION_Y.get(2), OFFICERS_POSITION_Y.get(4), OFFICERS_POSITION_Y.get(5)));
            } else if (officers.size() == 4) {
                officersPositionX = new ArrayList<Integer>(OFFICERS_POSITION_X.subList(2, 6));
                officersPositionY = new ArrayList<Integer>(OFFICERS_POSITION_Y.subList(2, 6));
            } else if (officers.size() == 5) {
                officersPositionX = new ArrayList<Integer>(Arrays.asList(OFFICERS_POSITION_X.get(0), OFFICERS_POSITION_X.get(2), OFFICERS_POSITION_X.get(3), OFFICERS_POSITION_X.get(4), OFFICERS_POSITION_X.get(5)));
                officersPositionY = new ArrayList<Integer>(Arrays.asList(OFFICERS_POSITION_Y.get(0), OFFICERS_POSITION_Y.get(2), OFFICERS_POSITION_Y.get(3), OFFICERS_POSITION_Y.get(4), OFFICERS_POSITION_Y.get(5)));
            }
            for (int i = 0; i < Math.min(6, officers.size()); ++i) {
                if (!this.entities.containsKey(officers.get(i)) || this.entities.get(officers.get(i)) == null) continue;
                GUIUtils.startGLScissor(this.guiLeft + officersPositionX.get(i) - 80, this.guiTop + officersPositionY.get(i) - 83, 500, 89 - (officersPositionY.get(i) - 83));
                GuiInventory.func_110423_a((int)(this.guiLeft + officersPositionX.get(i)), (int)(this.guiTop + officersPositionY.get(i)), (int)40, (float)0.0f, (float)0.0f, (EntityLivingBase)((EntityLivingBase)this.entities.get(officers.get(i))));
                GUIUtils.endGLScissor();
            }
            if (this.entities.containsKey((String)FactionGUI.factionInfos.get("leader")) && this.entities.get((String)FactionGUI.factionInfos.get("leader")) != null) {
                GUIUtils.startGLScissor(this.guiLeft + 330 - 50, this.guiTop + 135 - 135, 160, 89);
                GuiInventory.func_110423_a((int)(this.guiLeft + 330), (int)(this.guiTop + 135), (int)65, (float)0.0f, (float)0.0f, (EntityLivingBase)((EntityLivingBase)this.entities.get((String)FactionGUI.factionInfos.get("leader"))));
                GUIUtils.endGLScissor();
            }
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, this.guiTop + 77, 375 * GUI_SCALE, (this.displayMode.equals("members") ? 0 : 15) * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.button.members"), this.guiLeft + 42 + 33, this.guiTop + 80, this.displayMode.equals("members") ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
            if (mouseX > this.guiLeft + 42 && mouseX < this.guiLeft + 42 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                this.hoveredAction = "members";
            }
            ClientEventHandler.STYLE.bindTexture("faction_skills");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 108, this.guiTop + 77, 375 * GUI_SCALE, (this.displayMode.equals("new_members") ? 0 : 15) * GUI_SCALE, 65 * GUI_SCALE, 12 * GUI_SCALE, 65, 12, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.button.new_members"), this.guiLeft + 108 + 33, this.guiTop + 80, this.displayMode.equals("new_members") ? 0x6E76EE : 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 24);
            if (!factionNewMembersInfos.isEmpty()) {
                ClientEventHandler.STYLE.bindTexture("faction_members");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 170, this.guiTop + 76, 450 * GUI_SCALE, 278 * GUI_SCALE, 5 * GUI_SCALE, 5 * GUI_SCALE, 5, 5, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
            }
            if (mouseX > this.guiLeft + 108 && mouseX < this.guiLeft + 108 + 65 && mouseY >= this.guiTop + 77 && mouseY <= this.guiTop + 77 + 12) {
                this.hoveredAction = "new_members";
            }
            ClientEventHandler.STYLE.bindTexture("faction_members");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 250, this.guiTop + 101, 237 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 119 * GUI_SCALE, 2, 119, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            if (this.displayMode.equals("members")) {
                GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 103, 213, 115);
                index = 0;
                for (Map.Entry<String, Object> pair : factionMembersInfos.entrySet()) {
                    int offsetX = this.guiLeft + 45;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 103 + index * 13) + this.getSlideMembers());
                    String playerNamePrefix = pair.getKey().split(" ")[0];
                    String playerName = pair.getKey().split(" ")[1];
                    if (!playerName.toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) continue;
                    LinkedTreeMap playerData = (LinkedTreeMap)pair.getValue();
                    if (offsetY.intValue() < this.guiTop + 103 + 115) {
                        if (!ClientProxy.cacheHeadPlayer.containsKey(playerName)) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                                ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10, offsetY.intValue() + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                    }
                    String title = ((String)playerData.get((Object)"title")).length() <= 12 ? (String)playerData.get((Object)"title") : ((String)playerData.get((Object)"title")).substring(0, 12);
                    ModernGui.drawScaledStringCustomFont(title, offsetX + 13, offsetY.intValue() + 2, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_global");
                    if (playerNamePrefix.contains("**")) {
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 62, offsetY.intValue() + 2, 345 * GUI_SCALE, ((Boolean)playerData.get((Object)"isOnline") != false ? 96 : 136) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else if (playerNamePrefix.contains("*")) {
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 62, offsetY.intValue() + 2, 305 * GUI_SCALE, ((Boolean)playerData.get((Object)"isOnline") != false ? 96 : 136) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else if (playerNamePrefix.contains("-")) {
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 62, offsetY.intValue() + 2, 265 * GUI_SCALE, ((Boolean)playerData.get((Object)"isOnline") != false ? 96 : 136) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 62, offsetY.intValue() + 2, 225 * GUI_SCALE, ((Boolean)playerData.get((Object)"isOnline") != false ? 96 : 136) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                    ModernGui.drawScaledStringCustomFont(playerName, offsetX + 70, offsetY.intValue() + 2, (Boolean)playerData.get((Object)"isOnline") != false ? 0xFFFFFF : 10395075, 0.5f, "left", false, "georamaMedium", 28);
                    if (offsetY.floatValue() >= (float)(this.guiTop + 95) && offsetY.floatValue() <= (float)(this.guiTop + 103 + 115)) {
                        if (mouseX >= offsetX + 62 && mouseX <= offsetX + 62 + 50 && (float)mouseY >= offsetY.floatValue() + 1.0f && (float)mouseY <= offsetY.floatValue() + 2.0f + 8.0f) {
                            if (FactionGUI.playerTooltip.containsKey(playerName)) {
                                this.hoveredPlayer = playerName;
                                this.hoveredAction = "see_player";
                                tooltipToDraw = FactionGUI.getPlayerTooltip(playerName);
                            } else {
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(playerName)));
                                FactionGUI.playerTooltip.put(playerName, null);
                            }
                        }
                        if (((Boolean)FactionGUI.factionInfos.get("isInCountry")).booleanValue()) {
                            ClientEventHandler.STYLE.bindTexture("faction_members");
                            if (!playerName.equals((String)FactionGUI.factionInfos.get("leader"))) {
                                if (mouseX >= offsetX + 153 && mouseX <= offsetX + 153 + 8 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 8) {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 153, offsetY.intValue() + 1, 177 * GUI_SCALE, 13 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    if (playerData.get((Object)"rank").equals("MEMBER")) {
                                        this.hoveredAction = "demote_recruit";
                                    } else if (playerData.get((Object)"rank").equals("OFFICER")) {
                                        this.hoveredAction = "demote_member";
                                    }
                                    this.hoveredPlayer = playerName;
                                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.members.action.demote").replaceAll("#rank#", FactionGUI.getRoleName(this.hoveredAction.replace("demote_", ""))));
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 153, offsetY.intValue() + 1, 177 * GUI_SCALE, 2 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                                if (mouseX >= offsetX + 164 && mouseX <= offsetX + 164 + 8 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 8) {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 164, offsetY.intValue() + 1, 188 * GUI_SCALE, 13 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    if (playerData.get((Object)"rank").equals("RECRUIT")) {
                                        this.hoveredAction = "promote_member";
                                    } else if (playerData.get((Object)"rank").equals("MEMBER")) {
                                        this.hoveredAction = "promote_officer";
                                    } else if (playerData.get((Object)"rank").equals("OFFICER")) {
                                        this.hoveredAction = "promote_leader";
                                    }
                                    this.hoveredPlayer = playerName;
                                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.members.action.promote").replaceAll("#rank#", FactionGUI.getRoleName(this.hoveredAction.replace("promote_", ""))));
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 164, offsetY.intValue() + 1, 188 * GUI_SCALE, 2 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                                if (mouseX >= offsetX + 175 && mouseX <= offsetX + 175 + 8 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 8) {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 175, offsetY.intValue() + 1, 201 * GUI_SCALE, ((Boolean)playerData.get((Object)"bankMember") != false ? 13 : 2) * GUI_SCALE, 10 * GUI_SCALE, 8 * GUI_SCALE, 10, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    this.hoveredAction = (Boolean)playerData.get((Object)"bankMember") != false ? "remove_bank" : "add_bank";
                                    this.hoveredPlayer = playerName;
                                    tooltipToDraw.add(I18n.func_135053_a((String)("faction.members.action." + this.hoveredAction)));
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 175, offsetY.intValue() + 1, 201 * GUI_SCALE, ((Boolean)playerData.get((Object)"bankMember") != false ? 2 : 13) * GUI_SCALE, 10 * GUI_SCALE, 8 * GUI_SCALE, 10, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                                if (mouseX >= offsetX + 186 && mouseX <= offsetX + 186 + 8 && mouseY >= offsetY.intValue() + 1 && mouseY <= offsetY.intValue() + 1 + 8) {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 186, offsetY.intValue() + 1, 214 * GUI_SCALE, 13 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                    this.hoveredAction = "exclude";
                                    this.hoveredPlayer = playerName;
                                    tooltipToDraw.add(I18n.func_135053_a((String)"faction.members.action.exclude"));
                                } else {
                                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 186, offsetY.intValue() + 1, 214 * GUI_SCALE, 2 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                                }
                            }
                        }
                    }
                    ++index;
                }
                if (this.countMembersBySearch == -1) {
                    this.countMembersBySearch = index + 1;
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 45 && mouseX < this.guiLeft + 45 + 215 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 127) {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }
                ClientEventHandler.STYLE.bindTexture("faction_members");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 276, this.guiTop + 101, 0 * GUI_SCALE, blockMembers.get((String)FactionGUI.factionInfos.get("actualRelation")) * GUI_SCALE, 172 * GUI_SCALE, 119 * GUI_SCALE, 172, 119, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(FactionGUI.getRoleName("leader"), this.guiLeft + 276 + 86, this.guiTop + 106, 0xFFFFFF, 0.5f, "center", false, "georamaBold", 28);
                if (!ClientProxy.cacheHeadPlayer.containsKey((String)FactionGUI.factionInfos.get("leader"))) {
                    try {
                        ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                        resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)FactionGUI.factionInfos.get("leader")));
                        AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)FactionGUI.factionInfos.get("leader")));
                        ClientProxy.cacheHeadPlayer.put((String)FactionGUI.factionInfos.get("leader"), resourceLocation);
                    }
                    catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get((String)FactionGUI.factionInfos.get("leader")));
                    this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get((String)FactionGUI.factionInfos.get("leader")));
                    GUIUtils.drawScaledCustomSizeModalRect(this.guiLeft + 276 + 86 + 6, this.guiTop + 115 + 11, 8.0f, 16.0f, 8, -8, -12, -12, 64.0f, 64.0f);
                    if (mouseX >= this.guiLeft + 276 + 86 + 6 - 12 && mouseX <= this.guiLeft + 276 + 86 + 6 && mouseY >= this.guiTop + 115 + 11 - 12 && mouseY <= this.guiTop + 115 + 11) {
                        tooltipToDraw.add((String)FactionGUI.factionInfos.get("leader"));
                    }
                }
                ModernGui.drawScaledStringCustomFont(FactionGUI.getRoleName("officer"), this.guiLeft + 276 + 86, this.guiTop + 132, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 28);
                int offsetX = this.guiLeft + 276 + 86;
                if (officers.size() > 1) {
                    offsetX = offsetX - 2 - (Math.min(10, officers.size()) - 2) * 8;
                }
                for (int i = 0; i < Math.min(10, officers.size()); ++i) {
                    if (!ClientProxy.cacheHeadPlayer.containsKey(officers.get(i))) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)officers.get(i)));
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)officers.get(i)));
                            ClientProxy.cacheHeadPlayer.put((String)officers.get(i), resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        continue;
                    }
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(officers.get(i)));
                    this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(officers.get(i)));
                    GUIUtils.drawScaledCustomSizeModalRect(offsetX + i * 16, this.guiTop + 153, 8.0f, 16.0f, 8, -8, -12, -12, 64.0f, 64.0f);
                    if (mouseX < offsetX + i * 16 - 12 || mouseX > offsetX + i * 16 || mouseY < this.guiTop + 153 - 12 || mouseY > this.guiTop + 153) continue;
                    tooltipToDraw.add(officers.get(i));
                }
                ModernGui.drawScaledStringCustomFont(FactionGUI.getRoleName("member"), this.guiLeft + 276 + 86, this.guiTop + 162, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 28);
                offsetX = this.guiLeft + 276 + 86;
                int offsetY = this.guiTop + 183;
                if (members.size() > 1) {
                    offsetX = offsetX - 2 - (Math.min(10, members.size()) - 2) * 8;
                }
                for (int i = 0; i < Math.min(30, members.size()); ++i) {
                    if (!ClientProxy.cacheHeadPlayer.containsKey(members.get(i))) {
                        try {
                            ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                            resourceLocation = AbstractClientPlayer.func_110311_f((String)((String)members.get(i)));
                            AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)((String)members.get(i)));
                            ClientProxy.cacheHeadPlayer.put((String)members.get(i), resourceLocation);
                        }
                        catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        continue;
                    }
                    Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(members.get(i)));
                    this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(members.get(i)));
                    GUIUtils.drawScaledCustomSizeModalRect(offsetX + i % 10 * 16, offsetY + 16 * (i / 10), 8.0f, 16.0f, 8, -8, -12, -12, 64.0f, 64.0f);
                    if (mouseX < offsetX + i % 10 * 16 - 12 || mouseX > offsetX + i % 10 * 16 || mouseY < offsetY + 16 * (i / 10) - 12 || mouseY > offsetY + 16 * (i / 10)) continue;
                    tooltipToDraw.add(members.get(i));
                }
            } else {
                GUIUtils.startGLScissor(this.guiLeft + 45, this.guiTop + 103, 213, 115);
                index = 0;
                int multiplier = 1;
                if (((Boolean)FactionGUI.factionInfos.get("isReferent")).booleanValue()) {
                    multiplier = 2;
                }
                int rewardPossible = 0;
                int rewardPossiblePower = 0;
                int rewardGiven = 0;
                int rewardGivenPower = 0;
                for (Map.Entry<String, Object> entry : factionNewMembersInfos.entrySet()) {
                    Map rawPlayerData = (Map)entry.getValue();
                    LinkedHashMap playerData = new LinkedHashMap(rawPlayerData);
                    double amountPlayTime = ((Number)playerData.get("time")).doubleValue() / 3600.0;
                    if (amountPlayTime > 4.0) {
                        rewardGiven += 500 * multiplier;
                    } else {
                        rewardPossible += 500 * multiplier;
                    }
                    if (amountPlayTime > 8.0) {
                        rewardGiven += 1000 * multiplier;
                        rewardGivenPower += 2 * multiplier;
                    } else {
                        rewardPossible += 1000 * multiplier;
                        rewardPossiblePower += 2 * multiplier;
                    }
                    if (amountPlayTime > 24.0) {
                        rewardGiven += 1500 * multiplier;
                        rewardGivenPower += 5 * multiplier;
                    } else {
                        rewardPossible += 1500 * multiplier;
                        rewardPossiblePower += 5 * multiplier;
                    }
                    if (amountPlayTime > 48.0) {
                        rewardGiven += 2000 * multiplier;
                        rewardGivenPower += 10 * multiplier;
                        continue;
                    }
                    rewardPossible += 2000 * multiplier;
                    rewardPossiblePower += 10 * multiplier;
                }
                for (Map.Entry<String, Object> pair : factionNewMembersInfos.entrySet()) {
                    Map value = (Map)pair.getValue();
                    int offsetX = this.guiLeft + 45;
                    Float offsetY = Float.valueOf((float)(this.guiTop + 103 + index * 13) + this.getSlideMembers());
                    double amountPlayTime = (Double)value.get("time");
                    boolean isOnline = (Boolean)value.get("online");
                    String playerName = pair.getKey();
                    if (!playerName.toLowerCase().contains(this.searchInput.func_73781_b().toLowerCase())) continue;
                    amountPlayTime = (int)(amountPlayTime / 60.0 / 60.0);
                    LinkedTreeMap playerData = (LinkedTreeMap)pair.getValue();
                    if (offsetY.intValue() < this.guiTop + 103 + 115) {
                        if (!ClientProxy.cacheHeadPlayer.containsKey(playerName)) {
                            try {
                                ResourceLocation resourceLocation = AbstractClientPlayer.field_110314_b;
                                resourceLocation = AbstractClientPlayer.func_110311_f((String)playerName);
                                AbstractClientPlayer.func_110304_a((ResourceLocation)resourceLocation, (String)playerName);
                                ClientProxy.cacheHeadPlayer.put(playerName, resourceLocation);
                            }
                            catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            Minecraft.func_71410_x().field_71446_o.func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            this.field_73882_e.func_110434_K().func_110577_a(ClientProxy.cacheHeadPlayer.get(playerName));
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 10, offsetY.intValue() + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                    }
                    ModernGui.drawScaledStringCustomFont(playerName, offsetX + 14, offsetY.intValue() + 2, isOnline ? 0xFFFFFF : 10395075, 0.5f, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont(String.valueOf(amountPlayTime) + "h", offsetX + 110, offsetY.intValue() + 2, 10395075, 0.5f, "center", false, "georamaMedium", 28);
                    if (offsetY.floatValue() >= (float)(this.guiTop + 95) && offsetY.floatValue() <= (float)(this.guiTop + 103 + 115)) {
                        if (mouseX >= offsetX + 14 && mouseX <= offsetX + 44 + 50 && (float)mouseY >= offsetY.floatValue() + 1.0f && (float)mouseY <= offsetY.floatValue() + 2.0f + 8.0f) {
                            if (FactionGUI.playerTooltip.containsKey(playerName)) {
                                this.hoveredPlayer = playerName;
                                this.hoveredAction = "see_player";
                                tooltipToDraw = FactionGUI.getPlayerTooltip(playerName);
                            } else {
                                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionPlayerDataPacket(playerName)));
                                FactionGUI.playerTooltip.put(playerName, null);
                            }
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_members");
                        ModernGui.drawScaledCustomSizeModalRect(offsetX + 154, offsetY.intValue() + 1, 462 * GUI_SCALE, 287 * GUI_SCALE, 44 * GUI_SCALE, 9 * GUI_SCALE, 44, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        if (amountPlayTime > 4.0) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 154, offsetY.intValue() + 1, 462 * GUI_SCALE, 275 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        if (amountPlayTime > 8.0) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 166, offsetY.intValue() + 1, 462 * GUI_SCALE, 275 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        if (amountPlayTime > 24.0) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 178, offsetY.intValue() + 1, 462 * GUI_SCALE, 275 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        if (amountPlayTime > 48.0) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 190, offsetY.intValue() + 1, 462 * GUI_SCALE, 275 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"1"), offsetX + 160, (float)offsetY.intValue() + 2.5f, amountPlayTime < 4.0 ? 10395075 : 7815169, 0.4f, "right", false, "georamaMedium", 31);
                        if (mouseX >= offsetX + 154 && mouseX <= offsetX + 154 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                            tooltipToDraw.add("Apr\u00e8s 4h de temps de jeux inter-serveur");
                            tooltipToDraw.add("\u00a7a+" + 500 * multiplier + " $");
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"2"), offsetX + 172, (float)offsetY.intValue() + 2.5f, amountPlayTime < 8.0 ? 10395075 : 7815169, 0.4f, "right", false, "georamaMedium", 31);
                        if (mouseX >= offsetX + 166 && mouseX <= offsetX + 166 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                            tooltipToDraw.add("Apr\u00e8s 8h de temps de jeux inter-serveur");
                            tooltipToDraw.add("\u00a7a+" + 1000 * multiplier + " $");
                            tooltipToDraw.add("\u00a7e+" + 2 * multiplier + " Power (30jours)");
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"3"), offsetX + 184, (float)offsetY.intValue() + 2.5f, amountPlayTime < 24.0 ? 10395075 : 7815169, 0.4f, "right", false, "georamaMedium", 31);
                        if (mouseX >= offsetX + 178 && mouseX <= offsetX + 178 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                            tooltipToDraw.add("Apr\u00e8s 24h de temps de jeux inter-serveur");
                            tooltipToDraw.add("\u00a7a+" + 1500 * multiplier + " $");
                            tooltipToDraw.add("\u00a7e+" + 5 * multiplier + " Power (30jours)");
                        }
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"4"), offsetX + 196, (float)offsetY.intValue() + 2.5f, amountPlayTime < 48.0 ? 10395075 : 7815169, 0.4f, "right", false, "georamaMedium", 31);
                        if (mouseX >= offsetX + 190 && mouseX <= offsetX + 190 + 8 && mouseY >= offsetY.intValue() + 2 && mouseY <= offsetY.intValue() + 2 + 8) {
                            tooltipToDraw.add("Apr\u00e8s 48h de temps de jeux inter-serveur");
                            tooltipToDraw.add("\u00a7a+" + 2000 * multiplier + " $");
                            tooltipToDraw.add("\u00a7e+" + 10 * multiplier + " Power (30jours)");
                        }
                    }
                    ++index;
                }
                this.countNewMembersBySearch = index + 1;
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 45 && mouseX < this.guiLeft + 45 + 215 && mouseY >= this.guiTop + 100 && mouseY <= this.guiTop + 100 + 127) {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }
                ClientEventHandler.STYLE.bindTexture("faction_members");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 269, this.guiTop + 89, 282 * GUI_SCALE, 305 * GUI_SCALE, 194 * GUI_SCALE, 146 * GUI_SCALE, 194, 146, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.data.info.title"), this.guiLeft + 293, this.guiTop + 108, 15463162, 0.5f, "left", false, "georamaMedium", 32);
                ModernGui.drawSectionStringCustomFont(I18n.func_135053_a((String)"faction.members.data.info.desc"), this.guiLeft + 293, this.guiTop + 120, 10395075, 0.5f, "left", false, "georamaMedium", 20, 6, 300);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.data.stats.title"), this.guiLeft + 293, this.guiTop + 164, 15463162, 0.5f, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.data.stats.forma_progress"), this.guiLeft + 293, this.guiTop + 177, -856952070, 0.4f, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"10"), this.guiLeft + 439, this.guiTop + 177, -2132020486, 0.4f, "right", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.data.stats.forma_finish"), this.guiLeft + 293, this.guiTop + 187, -856952070, 0.4f, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"10"), this.guiLeft + 439, this.guiTop + 187, -2132020486, 0.4f, "right", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.data.stats.reward_possible"), this.guiLeft + 293, this.guiTop + 197, -856952070, 0.4f, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(rewardPossible + " $ / " + rewardPossiblePower + " power")), this.guiLeft + 439, this.guiTop + 197, 16171012, 0.4f, "right", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.members.data.stats.reward_finish"), this.guiLeft + 293, this.guiTop + 207, -856952070, 0.4f, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)(rewardGiven + " $ / " + rewardGivenPower + " power")), this.guiLeft + 439, this.guiTop + 207, 16171012, 0.4f, "right", false, "georamaMedium", 32);
            }
        }
        if (tooltipToDraw != null && !tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    private float getSlideMembers() {
        if (this.displayMode.equals("members")) {
            return this.countMembersBySearch > 10 ? (float)(-(this.countMembersBySearch - 10) * 13) * this.scrollBarMembers.getSliderValue() : 0.0f;
        }
        return this.countNewMembersBySearch > 10 ? (float)(-(this.countNewMembersBySearch - 10) * 13) * this.scrollBarMembers.getSliderValue() : 0.0f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.searchInput.func_73802_a(typedChar, keyCode);
        this.countMembersBySearch = -1;
        this.countNewMembersBySearch = -1;
        super.func_73869_a(typedChar, keyCode);
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            if (this.hoveredAction.equals("new_members")) {
                loaded_new_player = false;
                this.scrollBarMembers.setSliderValue(0.0f);
                this.displayMode = this.hoveredAction;
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionNewMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
                return;
            }
            if (this.hoveredAction.equals("members")) {
                this.scrollBarMembers.setSliderValue(0.0f);
                this.displayMode = this.hoveredAction;
                return;
            }
            if (this.hoveredAction.equals("edit_photo")) {
                ClientData.lastCaptureScreenshot.put("members", System.currentTimeMillis());
                Minecraft.func_71410_x().func_71373_a(null);
                Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture")));
            }
            if (this.hoveredAction.equals("see_player")) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ProfilGui(this.hoveredPlayer, ""));
            }
            if (this.hoveredAction.contains("promote")) {
                if (System.currentTimeMillis() - lastPromotePlayer < 2000L) {
                    return;
                }
                lastPromotePlayer = System.currentTimeMillis();
            }
            if (this.hoveredAction.equals("promote_leader")) {
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new LeaderConfirmGui(this, this.hoveredPlayer));
            } else {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionProfilActionPacket(this.hoveredPlayer, "", this.hoveredAction)));
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionNewMembersDataPacket((String)FactionGUI.factionInfos.get("id"))));
            }
            this.hoveredAction = "";
        }
        this.searchInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean isNumeric(String str) {
        try {
            return Double.parseDouble(str) > 0.0;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    static {
        lastPromotePlayer = 0L;
        OFFICERS_POSITION_X = new ArrayList<Integer>(Arrays.asList(225, 435, 255, 405, 285, 375));
        OFFICERS_POSITION_Y = new ArrayList<Integer>(Arrays.asList(127, 127, 121, 121, 115, 115));
        blockMembers = new HashMap<String, Integer>(){
            {
                this.put("neutral", 0);
                this.put("enemy", 123);
                this.put("ally", 246);
                this.put("colony", 369);
            }
        };
    }
}

