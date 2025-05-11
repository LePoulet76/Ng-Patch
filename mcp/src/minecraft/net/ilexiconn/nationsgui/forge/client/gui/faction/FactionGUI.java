/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.entity.AbstractClientPlayer
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ChatMessageComponent
 *  net.minecraft.util.ResourceLocation
 *  net.minecraft.world.World
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.BuyCountryConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.EmpireConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.LeaveConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.OpenDiscordConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainJoinPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionRemoveDiscordPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class FactionGUI
extends TabbedFactionGUI {
    public static HashMap<String, Object> factionInfos;
    public static boolean loaded;
    public static Long durationLevelAnimation;
    public static Long startLevelAnimation;
    public static boolean displayLevels;
    public static HashMap<String, HashMap<String, Object>> playerTooltip;
    public static boolean achievementDone;
    public static HashMap<String, Integer> textColor;
    public static HashMap<String, Integer> blockLevel;
    private String targetName;
    private RenderItem itemRenderer = new RenderItem();
    protected String hoveredPlayer = "";
    private EntityOtherPlayerMP leaderEntity = null;
    private DynamicTexture flagTexture;
    private DynamicTexture bannerTexture;
    private GuiScrollBarGeneric scrollBar;
    public static HashMap<String, Integer> blockMainPalierX;
    public static HashMap<String, Integer> blockMainPalierY;
    public static HashMap<String, Integer> blockSecondaryPalierX;
    public static HashMap<String, Integer> blockRelation;
    public static HashMap<String, Integer> iconRelations;
    public static HashMap<String, Integer> buttonRelations;
    public static HashMap<String, Integer> badgesPositionTextureX;
    public static HashMap<String, Integer> iconsRewardsMainY;
    public static HashMap<String, Integer> iconsRewardsSecondaryY;
    public static HashMap<String, Integer> progressBarX;
    public static HashMap<String, Integer> progressBarY;
    public static HashMap<String, Integer> numbersY;
    public int currentDisplayPalier = -1;
    private HashMap<Integer, LinkedHashMap<String, String>> rewards = new HashMap();
    private Long levelGUIOpen = 0L;

    public FactionGUI(String targetName) {
        FactionGUI.initTabs();
        this.targetName = targetName;
        displayLevels = false;
        loaded = false;
        factionInfos = null;
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_country", 1)));
        }
        playerTooltip = new HashMap();
        this.rewards.put(0, new LinkedHashMap<String, String>(){
            {
                this.put("members", "40");
                this.put("relations", "15");
                this.put("chest", "9");
                this.put("gallery", "1");
                this.put("warps", "0");
                this.put("missiles", "0");
                this.put("discord", "false");
                this.put("empire", "false");
                this.put("colonies", "0");
            }
        });
        this.rewards.put(10, new LinkedHashMap<String, String>(){
            {
                this.put("members", "70");
                this.put("relations", "15");
                this.put("chest", "9");
                this.put("gallery", "3");
                this.put("warps", "0");
                this.put("missiles", "0");
                this.put("discord", "false");
                this.put("empire", "false");
                this.put("colonies", "0");
            }
        });
        this.rewards.put(20, new LinkedHashMap<String, String>(){
            {
                this.put("members", "100");
                this.put("relations", "20");
                this.put("chest", "18");
                this.put("gallery", "4");
                this.put("warps", "1");
                this.put("missiles", "1");
                this.put("discord", "false");
                this.put("empire", "false");
                this.put("colonies", "0");
            }
        });
        this.rewards.put(30, new LinkedHashMap<String, String>(){
            {
                this.put("members", "140");
                this.put("relations", "25");
                this.put("chest", "18");
                this.put("gallery", "6");
                this.put("warps", "1");
                this.put("missiles", "1");
                this.put("discord", "true");
                this.put("empire", "false");
                this.put("colonies", "0");
            }
        });
        this.rewards.put(40, new LinkedHashMap<String, String>(){
            {
                this.put("members", "180");
                this.put("relations", "30");
                this.put("chest", "27");
                this.put("gallery", "8");
                this.put("warps", "2");
                this.put("missiles", "1");
                this.put("discord", "true");
                this.put("empire", "false");
                this.put("colonies", "0");
            }
        });
        this.rewards.put(50, new LinkedHashMap<String, String>(){
            {
                this.put("members", "220");
                this.put("relations", "35");
                this.put("chest", "36");
                this.put("gallery", "10");
                this.put("warps", "2");
                this.put("missiles", "2");
                this.put("discord", "true");
                this.put("empire", "false");
                this.put("colonies", "0");
            }
        });
        this.rewards.put(60, new LinkedHashMap<String, String>(){
            {
                this.put("members", "280");
                this.put("relations", "40");
                this.put("chest", "45");
                this.put("gallery", "15");
                this.put("warps", "3");
                this.put("missiles", "2");
                this.put("discord", "true");
                this.put("empire", "true");
                this.put("colonies", "3");
            }
        });
        this.rewards.put(70, new LinkedHashMap<String, String>(){
            {
                this.put("members", "340");
                this.put("relations", "45");
                this.put("chest", "54");
                this.put("gallery", "20");
                this.put("warps", "3");
                this.put("missiles", "3");
                this.put("discord", "true");
                this.put("empire", "true");
                this.put("colonies", "5");
            }
        });
        this.rewards.put(80, new LinkedHashMap<String, String>(){
            {
                this.put("members", "inf");
                this.put("relations", "inf");
                this.put("chest", "54");
                this.put("gallery", "25");
                this.put("warps", "4");
                this.put("missiles", "3");
                this.put("discord", "true");
                this.put("empire", "true");
                this.put("colonies", "8");
            }
        });
    }

    public static String getRoleName(String roleName) {
        if (factionInfos != null && ((LinkedTreeMap)factionInfos.get("rolesName")).containsKey((Object)roleName) && !((String)((LinkedTreeMap)factionInfos.get("rolesName")).get((Object)roleName)).isEmpty()) {
            return (String)((LinkedTreeMap)factionInfos.get("rolesName")).get((Object)roleName);
        }
        return roleName;
    }

    public static int getResearchLevel(String domain) {
        LinkedTreeMap researchesLevel = (LinkedTreeMap)factionInfos.get("researchesLevel");
        if (researchesLevel.containsKey((Object)domain)) {
            return ((Double)researchesLevel.get((Object)domain)).intValue();
        }
        return 0;
    }

    public static ArrayList<String> getPlayerTooltip(String playerName) {
        HashMap<String, Object> playerInfo = playerTooltip.get(playerName);
        ArrayList<String> tooltipLines = new ArrayList<String>();
        if (playerInfo != null) {
            if (!((String)playerInfo.get("title")).isEmpty() && !((String)playerInfo.get("title")).contains("no title set")) {
                tooltipLines.add((String)playerInfo.get("title"));
            }
            tooltipLines.add("\u00a7f" + I18n.func_135053_a((String)"faction.home.tooltip.member_since_1") + " " + playerInfo.get("member_days") + " " + I18n.func_135053_a((String)"faction.home.tooltip.member_since_2"));
            tooltipLines.add("\u00a77Power : " + playerInfo.get("power") + "/" + playerInfo.get("maxpower"));
            tooltipLines.add("\u00a78" + I18n.func_135053_a((String)"faction.home.tooltip.salary") + " : " + playerInfo.get("salary") + "$ | " + I18n.func_135053_a((String)"faction.home.tooltip.tax") + " : " + playerInfo.get("tax") + "$");
            if (playerInfo.containsKey("valid_playtime") && Long.parseLong((String)playerInfo.get("valid_playtime")) < 172800L) {
                tooltipLines.add("\u00a7b\u2605 " + I18n.func_135053_a((String)"faction.home.tooltip.playtime"));
            }
            tooltipLines.add("\u00a78----------------------");
            if (playerInfo.get("offline_days").equals("online")) {
                tooltipLines.add("\u00a7a" + I18n.func_135053_a((String)"faction.home.tooltip.online"));
            } else {
                tooltipLines.add("\u00a77" + I18n.func_135053_a((String)"faction.home.tooltip.offline_since_1") + " " + playerInfo.get("offline_days") + " " + I18n.func_135053_a((String)"faction.home.tooltip.offline_since_2"));
            }
        }
        return tooltipLines;
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        if (factionInfos == null || factionInfos.size() == 0 || !factionInfos.get("name").equals(this.targetName)) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.targetName, false)));
        } else {
            loaded = true;
        }
        this.leaderEntity = null;
        this.scrollBar = new GuiScrollBarGeneric(this.guiLeft + 444, this.guiTop + 147, 64, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    private float getSlideOnline() {
        return ((ArrayList)factionInfos.get("players_main_list")).size() > 6 ? (float)(-(((ArrayList)factionInfos.get("players_main_list")).size() - 6) * 13) * this.scrollBar.getSliderValue() : 0.0f;
    }

    public static boolean isNumeric(String str, boolean allowZero) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        if (Integer.parseInt(str) == 0 && !allowZero) {
            return false;
        }
        return Integer.parseInt(str) >= 0;
    }

    public static boolean hasPermissions(String permName) {
        return factionInfos.containsKey("permissions") && ((ArrayList)factionInfos.get("permissions")).contains(permName + "##true");
    }

    public void drawFactionMainButton(int mouseX, int mouseY, int buttonIndex, String buttonName, boolean enabled) {
        ClientEventHandler.STYLE.bindTexture("faction_main");
        if (!enabled) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + 62 * buttonIndex, this.guiTop + 79, 190 * GUI_SCALE, 138 * GUI_SCALE, 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.home.button." + buttonName)), this.guiLeft + 41 + 62 * buttonIndex + 27, this.guiTop + 82, 3682124, 0.5f, "center", false, "georamaSemiBold", 28);
        } else if (mouseX >= this.guiLeft + 41 + 62 * buttonIndex && mouseX <= this.guiLeft + 41 + 62 * buttonIndex + 55 && mouseY >= this.guiTop + 79 && mouseY <= this.guiTop + 79 + 13) {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + 62 * buttonIndex, this.guiTop + 79, 190 * GUI_SCALE, 106 * GUI_SCALE, 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.home.button." + buttonName)), this.guiLeft + 41 + 62 * buttonIndex + 27, this.guiTop + 82, 2234425, 0.5f, "center", false, "georamaSemiBold", 28);
            this.hoveredAction = buttonName;
        } else {
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + 62 * buttonIndex, this.guiTop + 79, 190 * GUI_SCALE, buttonRelations.get((String)factionInfos.get("actualRelation")) * GUI_SCALE, 55 * GUI_SCALE, 13 * GUI_SCALE, 55, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.home.button." + buttonName)), this.guiLeft + 41 + 62 * buttonIndex + 27, this.guiTop + 82, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 28);
        }
    }

    public void drawFactionMainBadge(int mouseX, int mouseY, int badgeIndex, String badgeName) {
        CFontRenderer cFontRenderer = ModernGui.getCustomFont("georamaSemiBold", 32);
        ClientEventHandler.STYLE.bindTexture("faction_global");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 39 + (int)cFontRenderer.getStringWidth((String)factionInfos.get("name")) + 5 + 17 * badgeIndex, this.guiTop + 15, badgesPositionTextureX.get(badgeName) * GUI_SCALE, 34 * GUI_SCALE, 12 * GUI_SCALE, 14 * GUI_SCALE, 12, 14, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        if (mouseX >= this.guiLeft + 39 + (int)cFontRenderer.getStringWidth((String)factionInfos.get("name")) + 5 + 17 * badgeIndex && mouseX <= this.guiLeft + 39 + (int)cFontRenderer.getStringWidth((String)factionInfos.get("name")) + 5 + 17 * badgeIndex + 12 && mouseY >= this.guiTop + 15 && mouseY <= this.guiTop + 15 + 14) {
            tooltipToDraw.addAll(Arrays.asList(I18n.func_135053_a((String)("faction.common.badge." + badgeName)).replaceAll("#empire#", (String)factionInfos.get("isColony")).split("##")));
            if (badgeName.equals("colony")) {
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.common.badge.colony_tax") + " " + factionInfos.get("colonyTax") + "%");
            } else if (badgeName.equals("surclaims")) {
                for (Map.Entry entry : ((LinkedTreeMap)factionInfos.get("availableSurclaims")).entrySet()) {
                    Date date = new Date(Long.parseLong((String)entry.getKey()));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy");
                    tooltipToDraw.add("\u00a7c" + simpleDateFormat.format(date) + ": \u00a77" + ((Double)entry.getValue()).intValue());
                }
            } else if (badgeName.equals("absence")) {
                String[] infos = ((String)factionInfos.get("absenceTime")).split("#");
                String duration = ModernGui.formatDuration((Long.parseLong(infos[1]) - System.currentTimeMillis()) / 1000L);
                tooltipToDraw.add(I18n.func_135053_a((String)"faction.main.absence").replaceAll("<duration>", "" + duration).replaceAll("<sender>", ((String)factionInfos.get("absenceRequesterInfos")).split("#")[0]));
            } else if (badgeName.equals("restrictDisband")) {
                tooltipToDraw.add(I18n.func_135053_a((String)("faction.main.warning." + factionInfos.get("disband_message"))));
            } else if (badgeName.equals("restrictNotation")) {
                tooltipToDraw.add(I18n.func_135053_a((String)("faction.main.warning_notations." + factionInfos.get("isEligibleForNotations"))));
            }
        }
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && !this.hoveredAction.isEmpty()) {
            if (this.hoveredAction.equals("edit_photo")) {
                ClientData.lastCaptureScreenshot.put("main", System.currentTimeMillis());
                Minecraft.func_71410_x().func_71373_a(null);
                Minecraft.func_71410_x().field_71439_g.func_70006_a(ChatMessageComponent.func_111066_d((String)I18n.func_135053_a((String)"faction.take_picture")));
            } else if (this.hoveredAction.equals("tphome")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainTPPacket((String)factionInfos.get("name"))));
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (this.hoveredAction.equals("join")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainJoinPacket((String)factionInfos.get("name"))));
            } else if (this.hoveredAction.equals("leave")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new LeaveConfirmGui(this));
            } else if (this.hoveredAction.contains("empire")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EmpireConfirmGui(this));
            } else if (this.hoveredAction.equals("buy")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new BuyCountryConfirmGui(this));
            } else if (this.hoveredAction.equals("discord")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new OpenDiscordConfirmGui(this));
            } else if (this.hoveredAction.equals("discord_remove")) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionRemoveDiscordPacket((String)factionInfos.get("name"))));
                factionInfos.put("discord", "");
            } else if (this.hoveredAction.equals("back_levels")) {
                displayLevels = false;
            } else if (this.hoveredAction.equals("level_prev")) {
                this.currentDisplayPalier = Math.max(0, this.currentDisplayPalier - 10);
                startLevelAnimation = 0L;
            } else if (this.hoveredAction.equals("level_next")) {
                this.currentDisplayPalier = Math.min(80, this.currentDisplayPalier + 10);
                startLevelAnimation = 0L;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        this.func_73873_v_();
        tooltipToDraw = new ArrayList();
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ClientEventHandler.STYLE.bindTexture("faction_main");
        if (factionInfos != null) {
            if (!displayLevels) {
                if (factionInfos.get("banners") != null && ((Map)factionInfos.get("banners")).containsKey("main")) {
                    ModernGui.bindRemoteTexture((String)((Map)factionInfos.get("banners")).get("main"));
                } else {
                    ModernGui.bindRemoteTexture("https://static.nationsglory.fr/N3255yGyNN.png");
                }
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30 + 154, this.guiTop + 0, 0.0f, 0.0f, 279 * GUI_SCALE, 110 * GUI_SCALE, 279, 110, 279 * GUI_SCALE, 110 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("faction_global");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 33 * GUI_SCALE, 375 * GUI_SCALE, 433 * GUI_SCALE, 110 * GUI_SCALE, 433, 110, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("name"), this.guiLeft + 39, this.guiTop + 13, 0xFFFFFF, 1.0f, "left", false, "georamaSemiBold", 32);
                int badgeIndex = 0;
                if (factionInfos.get("age") != null && Integer.parseInt((String)factionInfos.get("age")) < 7) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "young");
                    ++badgeIndex;
                }
                if (((Boolean)factionInfos.get("isEmpire")).booleanValue()) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "empire");
                    ++badgeIndex;
                } else if (!((String)factionInfos.get("isColony")).isEmpty()) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "colony");
                    ++badgeIndex;
                }
                if (((Boolean)factionInfos.get("isTopWarzone")).booleanValue()) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "warzone");
                    ++badgeIndex;
                }
                if (((Boolean)factionInfos.get("isReferent")).booleanValue()) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "referent");
                    ++badgeIndex;
                }
                if (factionInfos.containsKey("availableSurclaims") && factionInfos.get("availableSurclaims") != null && ((LinkedTreeMap)factionInfos.get("availableSurclaims")).size() > 0) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "surclaims");
                    ++badgeIndex;
                }
                if (factionInfos.containsKey("absenceTime") && !((String)factionInfos.get("absenceTime")).equals("")) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "absence");
                    ++badgeIndex;
                }
                if (factionInfos.containsKey("isRestrictAssault") && ((Boolean)factionInfos.get("isRestrictAssault")).booleanValue()) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "restrictAssault");
                    ++badgeIndex;
                }
                if (factionInfos.containsKey("isRestrictMissile") && ((Boolean)factionInfos.get("isRestrictMissile")).booleanValue()) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "restrictMissile");
                    ++badgeIndex;
                }
                if (factionInfos.containsKey("isEligibleForNotations") && factionInfos.get("isEligibleForNotations") != null) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "restrictNotation");
                    ++badgeIndex;
                }
                if (factionInfos.containsKey("disband_message") && factionInfos.get("disband_message") != null) {
                    this.drawFactionMainBadge(mouseX, mouseY, badgeIndex, "restrictDisband");
                    ++badgeIndex;
                }
                String tags = "";
                for (String[] tag : (ArrayList)factionInfos.get("tags")) {
                    tags = tags + "#" + I18n.func_135053_a((String)("faction.settings.tags." + (String)tag)) + " ";
                }
                ModernGui.drawScaledStringCustomFont("\u00a7o" + tags, this.guiLeft + 41, this.guiTop + 30, 0xDADAED, 0.5f, "left", false, "georamaMedium", 25);
                ModernGui.drawSectionStringCustomFont(((String)factionInfos.get("description")).replaceAll("\u00a7[0-9a-z]{1}", ""), this.guiLeft + 41, this.guiTop + 40, 10395075, 0.5f, "left", false, "georamaMedium", 25, 8, 350);
                if (this.leaderEntity == null && loaded && factionInfos.size() > 0 || loaded && factionInfos.size() > 0 && this.leaderEntity != null && !this.leaderEntity.getDisplayName().equals(factionInfos.get("leader"))) {
                    try {
                        this.leaderEntity = new EntityOtherPlayerMP((World)this.field_73882_e.field_71441_e, (String)factionInfos.get("leader"));
                    }
                    catch (Exception e) {
                        this.leaderEntity = null;
                    }
                }
                if (loaded && factionInfos != null && this.flagTexture == null && factionInfos != null && factionInfos.get("flagImage") != null && !((String)factionInfos.get("flagImage")).isEmpty()) {
                    BufferedImage image = ModernGui.decodeToImage((String)factionInfos.get("flagImage"));
                    this.flagTexture = new DynamicTexture(image);
                }
                if (this.leaderEntity != null) {
                    GUIUtils.startGLScissor(this.guiLeft + 295, this.guiTop + 10, 100, 100);
                    GuiInventory.func_110423_a((int)(this.guiLeft + 345), (int)(this.guiTop + 155), (int)75, (float)0.0f, (float)0.0f, (EntityLivingBase)this.leaderEntity);
                    GUIUtils.endGLScissor();
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    GL11.glEnable((int)3042);
                    GL11.glDisable((int)2929);
                    GL11.glDepthMask((boolean)false);
                    GL11.glBlendFunc((int)770, (int)771);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glDisable((int)3008);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 375, this.guiTop + 18, 792.0f, 129.0f, 4 * GUI_SCALE, 112 * GUI_SCALE, 4, 92, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    GL11.glDisable((int)3042);
                    GL11.glEnable((int)2929);
                    GL11.glDepthMask((boolean)true);
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    GL11.glEnable((int)3008);
                    if (this.flagTexture != null) {
                        GL11.glBindTexture((int)3553, (int)this.flagTexture.func_110552_b());
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 379, this.guiTop + 20, 0.0f, 0.0f, 156, 78, 50, 30, 156.0f, 78.0f, false);
                    }
                }
                int buttonIndex = 0;
                if (((Boolean)factionInfos.get("isOpen")).booleanValue() && !((Boolean)factionInfos.get("playerHasFaction")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue()) {
                    this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "join", true);
                    ++buttonIndex;
                } else if (((Boolean)factionInfos.get("isInvited")).booleanValue() && !((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue()) {
                    this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "join", true);
                    ++buttonIndex;
                } else if (((Boolean)factionInfos.get("isInCountry")).booleanValue() && !((Boolean)factionInfos.get("isLeader")).booleanValue()) {
                    this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "leave", true);
                    ++buttonIndex;
                }
                if (!((Boolean)factionInfos.get("isLeader")).booleanValue() && !((Boolean)factionInfos.get("isLeaderInHisCountry")).booleanValue() && ((Boolean)factionInfos.get("isForSale")).booleanValue()) {
                    this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "buy", true);
                    ++buttonIndex;
                }
                if ((((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isAdmin") && ((Boolean)factionInfos.get("isAdmin")).booleanValue()) && ((Boolean)factionInfos.get("hasHome")).booleanValue()) {
                    this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "tphome", true);
                    ++buttonIndex;
                }
                if (factionInfos != null && ((Boolean)factionInfos.get("isLeader")).booleanValue()) {
                    if (((String)factionInfos.get("name")).contains("Empire")) {
                        this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "empire_down", true);
                        ++buttonIndex;
                    } else {
                        this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "empire_up", FactionGUI.getResearchLevel("general") >= 11);
                        ++buttonIndex;
                    }
                }
                if (factionInfos != null && !((String)factionInfos.get("discord")).isEmpty() && factionInfos.get("canRemoveDiscord").equals("true")) {
                    this.drawFactionMainButton(mouseX, mouseY, buttonIndex, "discord_remove", true);
                    ++buttonIndex;
                }
                ClientEventHandler.STYLE.bindTexture("faction_global");
                if (Integer.parseInt((String)factionInfos.get("level")) >= 30) {
                    if (mouseX >= this.guiLeft + 41 + 62 * buttonIndex && mouseX <= this.guiLeft + 41 + 62 * buttonIndex + 10 && mouseY >= this.guiTop + 81 && mouseY <= this.guiTop + 81 + 11) {
                        if (!((String)factionInfos.get("discord")).isEmpty()) {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + 62 * buttonIndex, this.guiTop + 81, 16 * GUI_SCALE, 19 * GUI_SCALE, 10 * GUI_SCALE, 11 * GUI_SCALE, 10, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            this.hoveredAction = "discord";
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + 62 * buttonIndex, this.guiTop + 81, 16 * GUI_SCALE, 3 * GUI_SCALE, 10 * GUI_SCALE, 11 * GUI_SCALE, 10, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                            tooltipToDraw.add(I18n.func_135053_a((String)"faction.home.no_discord"));
                        }
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41 + 62 * buttonIndex, this.guiTop + 81, 16 * GUI_SCALE, 3 * GUI_SCALE, 10 * GUI_SCALE, 11 * GUI_SCALE, 10, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    }
                }
                if (factionInfos.containsKey("disband_message") && factionInfos.get("disband_message") != null && (!((String)factionInfos.get("disband_message")).toLowerCase().contains("warning") || ((Boolean)factionInfos.get("isInCountry")).booleanValue() || factionInfos.containsKey("isAdmin"))) {
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.main.warning." + factionInfos.get("disband_message"))), this.guiLeft + 41, this.guiTop + 95, 12002109, 0.5f, "left", false, "georamaMedium", 26);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.informations"), this.guiLeft + 41, this.guiTop + 117, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 32);
                if (((Boolean)factionInfos.get("isInCountry")).booleanValue()) {
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41, this.guiTop + 136, 0.0f, 0 * GUI_SCALE, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 50, this.guiTop + 147, 392 * GUI_SCALE, 46 * GUI_SCALE, 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.level") + " (" + String.format("%.0f", (Double)factionInfos.get("progress")) + "%)", this.guiLeft + 80, this.guiTop + 147, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont((String)factionInfos.get("level"), this.guiLeft + 80, this.guiTop + 155, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 30);
                    if (mouseX >= this.guiLeft + 41 && mouseX <= this.guiLeft + 41 + 91 && mouseY >= this.guiTop + 136 && mouseY <= this.guiTop + 136 + 39) {
                        this.hoveredAction = "display_levels";
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41, this.guiTop + 181, 190 * GUI_SCALE, 0.0f, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 53, this.guiTop + 192, 416 * GUI_SCALE, 0 * GUI_SCALE, 13 * GUI_SCALE, 18 * GUI_SCALE, 13, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.notations"), this.guiLeft + 80, this.guiTop + 192, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont((factionInfos.get("notationsPosition") instanceof Double ? String.format("%.0f", (Double)factionInfos.get("notationsPosition")) : factionInfos.get("notationsPosition")) + "/" + factionInfos.get("totalCountries"), this.guiLeft + 80, this.guiTop + 200, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 30);
                } else {
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41, this.guiTop + 136, 0.0f, blockRelation.get(factionInfos.get("actualRelation")) * GUI_SCALE, 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ClientEventHandler.STYLE.bindTexture("faction_global");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 45, this.guiTop + 138, iconRelations.get(factionInfos.get("actualRelation")) * GUI_SCALE, 212 * GUI_SCALE, 20 * GUI_SCALE, 20 * GUI_SCALE, 20, 20, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.relation"), this.guiLeft + 73, this.guiTop + 139, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.common." + factionInfos.get("actualRelation") + ".nocolor")), this.guiLeft + 73, this.guiTop + 147, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    if (mouseX >= this.guiLeft + 41 && mouseX <= this.guiLeft + 41 + 91 && mouseY >= this.guiTop + 165 && mouseY <= this.guiTop + 165 + 39) {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41, this.guiTop + 165, 0.0f, 401 * GUI_SCALE, 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 47, this.guiTop + 170, 392 * GUI_SCALE, 23 * GUI_SCALE, 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.level") + " (" + String.format("%.0f", (Double)factionInfos.get("progress")) + "%)", this.guiLeft + 73, this.guiTop + 168, 2234425, 0.5f, "left", false, "georamaMedium", 25);
                        ModernGui.drawScaledStringCustomFont((String)factionInfos.get("level"), this.guiLeft + 73, this.guiTop + 176, 2234425, 0.75f, "left", false, "georamaSemiBold", 30);
                        this.hoveredAction = "display_levels";
                    } else {
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41, this.guiTop + 165, 0.0f, 191 * GUI_SCALE, 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 47, this.guiTop + 170, 392 * GUI_SCALE, 0 * GUI_SCALE, 18 * GUI_SCALE, 16 * GUI_SCALE, 18, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.level") + " (" + String.format("%.0f", (Double)factionInfos.get("progress")) + "%)", this.guiLeft + 73, this.guiTop + 168, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
                        ModernGui.drawScaledStringCustomFont((String)factionInfos.get("level"), this.guiLeft + 73, this.guiTop + 176, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 30);
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_main");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 41, this.guiTop + 195, 0.0f, 191 * GUI_SCALE, 91 * GUI_SCALE, 25 * GUI_SCALE, 91, 25, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 50, this.guiTop + 199, 416 * GUI_SCALE, 0 * GUI_SCALE, 13 * GUI_SCALE, 18 * GUI_SCALE, 13, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.notations"), this.guiLeft + 73, this.guiTop + 198, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont((factionInfos.get("notationsPosition") instanceof Double ? String.format("%.0f", (Double)factionInfos.get("notationsPosition")) : factionInfos.get("notationsPosition")) + "/" + factionInfos.get("totalCountries"), this.guiLeft + 73, this.guiTop + 206, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 30);
                }
                ClientEventHandler.STYLE.bindTexture("faction_empty_map");
                ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/mods/get_map?server=" + ClientProxy.currentServerName + "&country=" + factionInfos.get("name"));
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 147, this.guiTop + 135, 0.0f, 0.0f, 91 * GUI_SCALE, 85 * GUI_SCALE, 91, 85, 91 * GUI_SCALE, 85 * GUI_SCALE, false);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 253, this.guiTop + 135, 190 * GUI_SCALE, 0.0f, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.claim"), this.guiLeft + 259, this.guiTop + 140, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("claims"), this.guiLeft + 302, this.guiTop + 140, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.mmr"), this.guiLeft + 259, this.guiTop + 151, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("mmr"), this.guiLeft + 302, this.guiTop + 151, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.power"), this.guiLeft + 259, this.guiTop + 162, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(factionInfos.get("power") + "/" + factionInfos.get("maxpower"), this.guiLeft + 302, this.guiTop + 162, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
                if (mouseX >= this.guiLeft + 259 && mouseX <= this.guiLeft + 259 + 80 && mouseY >= this.guiTop + 162 && mouseY <= this.guiTop + 162 + 10) {
                    tooltipToDraw.add("\u00a76Powerboost:");
                    tooltipToDraw.add("\u00a7eFixe: \u00a77" + factionInfos.get("powerboost_fixed"));
                    tooltipToDraw.add("\u00a7eWarzone: \u00a77" + factionInfos.get("powerboost_real"));
                    tooltipToDraw.add("\u00a7eUNESCO: \u00a77" + factionInfos.get("powerboost_unesco"));
                    tooltipToDraw.add("\u00a7eRecrutement: \u00a77" + factionInfos.get("newMembers"));
                    tooltipToDraw.add("\u00a7eMalus Assaut: \u00a77-" + factionInfos.get("powerboost_malus_assault"));
                    if (!((String)factionInfos.get("powerboost_others")).isEmpty()) {
                        for (String powerBoostInfos : ((String)factionInfos.get("powerboost_others")).split(",")) {
                            if (powerBoostInfos.split("#")[1].contains("-")) {
                                tooltipToDraw.add("\u00a78" + I18n.func_135053_a((String)"faction.home.tooltip.war_against") + " " + powerBoostInfos.split("#")[0] + ": \u00a7c" + powerBoostInfos.split("#")[1]);
                                continue;
                            }
                            tooltipToDraw.add("\u00a78" + I18n.func_135053_a((String)"faction.home.tooltip.war_against") + " " + powerBoostInfos.split("#")[0] + ": \u00a7a" + powerBoostInfos.split("#")[1]);
                        }
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 253, this.guiTop + 180, 190 * GUI_SCALE, 0.0f, 91 * GUI_SCALE, 39 * GUI_SCALE, 91, 39, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.ratings"), this.guiLeft + 259, this.guiTop + 185, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(factionInfos.get("notationsPosition") instanceof Double ? String.format("%.0f", (Double)factionInfos.get("notationsPosition")) : (String)factionInfos.get("notationsPosition"), this.guiLeft + 302, this.guiTop + 185, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.creation"), this.guiLeft + 259, this.guiTop + 196, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("age") + I18n.func_135053_a((String)"faction.common.days.short"), this.guiLeft + 302, this.guiTop + 196, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 28);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.home.players"), this.guiLeft + 259, this.guiTop + 207, 10395075, 0.5f, "left", false, "georamaSemiBold", 28);
                String players = ((List)factionInfos.get("players_online")).size() + "\u00a7f/" + String.format("%.0f", (Double)factionInfos.get("count_players_offline"));
                ModernGui.drawScaledStringCustomFont(players, this.guiLeft + 302, this.guiTop + 207, textColor.get(factionInfos.get("actualRelation")), 0.5f, "left", false, "georamaSemiBold", 28);
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 359, this.guiTop + 135, 95 * GUI_SCALE, 0.0f, 91 * GUI_SCALE, 85 * GUI_SCALE, 91, 85, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 444, this.guiTop + 147, 272 * GUI_SCALE, 43 * GUI_SCALE, 2 * GUI_SCALE, 64 * GUI_SCALE, 2, 64, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                if (((ArrayList)factionInfos.get("players_main_list")).size() > 0) {
                    GUIUtils.startGLScissor(this.guiLeft + 359, this.guiTop + 135, 84, 85);
                    for (int i = 0; i < ((ArrayList)factionInfos.get("players_main_list")).size(); ++i) {
                        String playerName = ((String)((ArrayList)factionInfos.get("players_main_list")).get(i)).split("#")[1];
                        String playerNamePrefix = "";
                        if (playerName.split(" ").length > 1) {
                            playerNamePrefix = playerName.split(" ")[0];
                            playerName = playerName.split(" ")[1];
                        }
                        int offsetX = this.guiLeft + 359;
                        Float offsetY = Float.valueOf((float)(this.guiTop + 140 + i * 13) + this.getSlideOnline());
                        if (mouseX > offsetX && mouseX < offsetX + 84 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 13.0f) {
                            this.hoveredPlayer = playerName;
                        }
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
                            GUIUtils.drawScaledCustomSizeModalRect(offsetX + 8 + 10, offsetY.intValue() + 0 + 10, 8.0f, 16.0f, 8, -8, -10, -10, 64.0f, 64.0f);
                        }
                        if (i < ((ArrayList)factionInfos.get("players_online")).size()) {
                            ClientEventHandler.STYLE.bindTexture("faction_global");
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 15, offsetY.intValue() - 2, 224 * GUI_SCALE, 52 * GUI_SCALE, 6 * GUI_SCALE, 6 * GUI_SCALE, 6, 6, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        }
                        ClientEventHandler.STYLE.bindTexture("faction_global");
                        if (playerNamePrefix.equals("**")) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 19, offsetY.intValue() + 2, 345 * GUI_SCALE, (i >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        } else if (playerNamePrefix.equals("*")) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 19, offsetY.intValue() + 2, 305 * GUI_SCALE, (i >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        } else if (playerNamePrefix.equals("-")) {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 19, offsetY.intValue() + 2, 265 * GUI_SCALE, (i >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        } else {
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 19, offsetY.intValue() + 2, 225 * GUI_SCALE, (i >= ((ArrayList)factionInfos.get("players_online")).size() ? 136 : 96) * GUI_SCALE, 40 * GUI_SCALE, 40 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
                        }
                        ModernGui.drawScaledStringCustomFont((i >= ((ArrayList)factionInfos.get("players_online")).size() ? "\u00a77" : "") + playerName, offsetX + 27, offsetY.intValue() + 2, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                    }
                    GUIUtils.endGLScissor();
                }
                this.scrollBar.draw(mouseX, mouseY);
            } else if (displayLevels) {
                Map.Entry<String, String> pair;
                ModernGui.drawScaledStringCustomFont((String)factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
                ClientEventHandler.STYLE.bindTexture("faction_levels");
                if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 8, 496 * GUI_SCALE, 40 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 9, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    this.hoveredAction = "back_levels";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 8, 505 * GUI_SCALE, 40 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 9, 10395075, 0.5f, "left", false, "georamaSemiBold", 30);
                }
                int currentLevel = Integer.parseInt((String)factionInfos.get("level"));
                int currentScore = Integer.parseInt((String)factionInfos.get("score"));
                int currentPalier = Math.min(80, currentLevel - currentLevel % 10);
                int nextPallier = Math.min(80, currentPalier + 10);
                int prevPalier = currentPalier - 10;
                int scoreActuelPalier = currentPalier * 151;
                int scoreNextPalier = nextPallier * 151;
                if (this.currentDisplayPalier == -1) {
                    this.currentDisplayPalier = currentPalier;
                }
                double offsetYAnimation = 0.0;
                double maxOffsetY = Math.max(0, (this.rewards.get(0).size() - 7) * 21);
                if (maxOffsetY > 0.0) {
                    double progress;
                    if (startLevelAnimation == 0L) {
                        startLevelAnimation = System.currentTimeMillis();
                    }
                    if ((progress = (double)(System.currentTimeMillis() - startLevelAnimation) * 1.0 / (double)durationLevelAnimation.longValue()) <= 1.0) {
                        offsetYAnimation = progress * maxOffsetY;
                    } else if (progress <= 2.0) {
                        offsetYAnimation = (1.0 - (progress - 1.0)) * maxOffsetY;
                    } else {
                        startLevelAnimation = System.currentTimeMillis();
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_levels");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 181, this.guiTop + 17, (currentPalier >= this.currentDisplayPalier ? blockMainPalierX.get(factionInfos.get("actualRelation")) : 0) * GUI_SCALE, (currentPalier >= this.currentDisplayPalier ? blockMainPalierY.get(factionInfos.get("actualRelation")) : 132) * GUI_SCALE, 126 * GUI_SCALE, 174 * GUI_SCALE, 126, 174, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier").toUpperCase() + " " + this.currentDisplayPalier, this.guiLeft + 190, this.guiTop + 24, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 32);
                int index = 0;
                GUIUtils.startGLScissor(this.guiLeft + 190, this.guiTop + 38, 115, 153);
                for (Map.Entry<String, String> pair2 : this.rewards.get(this.currentDisplayPalier).entrySet()) {
                    ClientEventHandler.STYLE.bindTexture("faction_levels_2");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 190, this.guiTop + 38 + 21 * index - (int)offsetYAnimation, (currentPalier >= this.currentDisplayPalier ? (pair2.getValue().equals("0") || pair2.getValue().equals("false") ? 43 : 87) : 65) * GUI_SCALE, iconsRewardsMainY.get(pair2.getKey()) * GUI_SCALE, 22 * GUI_SCALE, 22 * GUI_SCALE, 22, 22, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.level.label." + pair2.getKey())), this.guiLeft + 230, this.guiTop + 42 + 21 * index - (int)offsetYAnimation, 0xFFFFFF, 0.5f, "left", false, "georamaRegular", 24);
                    ModernGui.drawScaledStringCustomFont(FactionGUI.isNumeric(pair2.getValue(), true) ? pair2.getValue() : I18n.func_135053_a((String)("faction.level.label." + pair2.getValue())), this.guiLeft + 230, this.guiTop + 50 + 21 * index - (int)offsetYAnimation, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    ++index;
                }
                GUIUtils.endGLScissor();
                if (this.currentDisplayPalier - 10 >= 0) {
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 88, this.guiTop + 50, (currentPalier >= this.currentDisplayPalier - 10 ? blockSecondaryPalierX.get(factionInfos.get("actualRelation")) : 0) * GUI_SCALE, 0 * GUI_SCALE, 86 * GUI_SCALE, 130 * GUI_SCALE, 86, 130, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier").toUpperCase() + " " + (this.currentDisplayPalier - 10), this.guiLeft + 95, this.guiTop + 55, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 28);
                    Iterator<Map.Entry<String, String>> it2 = this.rewards.get(this.currentDisplayPalier - 10).entrySet().iterator();
                    for (index = 0; it2.hasNext() && index < 7; ++index) {
                        pair = it2.next();
                        ClientEventHandler.STYLE.bindTexture("faction_levels_2");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 94, this.guiTop + 65 + 16 * index, (currentPalier >= this.currentDisplayPalier - 10 ? (pair.getValue().equals("0") || pair.getValue().equals("false") ? 0 : 109) : 17) * GUI_SCALE, iconsRewardsSecondaryY.get(pair.getKey()) * GUI_SCALE, 17 * GUI_SCALE, 17 * GUI_SCALE, 17, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(FactionGUI.isNumeric(pair.getValue(), true) ? pair.getValue() : I18n.func_135053_a((String)("faction.level.label." + pair.getValue())), this.guiLeft + 123, this.guiTop + 70 + 16 * index, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 174 - 58, this.guiTop + 50, 128 * GUI_SCALE, 308 * GUI_SCALE, 58 * GUI_SCALE, 130 * GUI_SCALE, 58, 130, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                if (this.currentDisplayPalier + 10 <= 80) {
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 315, this.guiTop + 50, (currentPalier >= this.currentDisplayPalier + 10 ? blockSecondaryPalierX.get(factionInfos.get("actualRelation")) : 0) * GUI_SCALE, 0 * GUI_SCALE, 86 * GUI_SCALE, 130 * GUI_SCALE, 86, 130, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier").toUpperCase() + " " + (this.currentDisplayPalier + 10), this.guiLeft + 322, this.guiTop + 55, 0xFFFFFF, 0.5f, "left", false, "georamaBold", 28);
                    Iterator<Map.Entry<String, String>> it3 = this.rewards.get(this.currentDisplayPalier + 10).entrySet().iterator();
                    for (index = 0; it3.hasNext() && index < 7; ++index) {
                        pair = it3.next();
                        ClientEventHandler.STYLE.bindTexture("faction_levels_2");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 321, this.guiTop + 65 + 16 * index, (currentPalier >= this.currentDisplayPalier + 10 ? (pair.getValue().equals("0") || pair.getValue().equals("false") ? 0 : 109) : 17) * GUI_SCALE, iconsRewardsSecondaryY.get(pair.getKey()) * GUI_SCALE, 17 * GUI_SCALE, 17 * GUI_SCALE, 17, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        ModernGui.drawScaledStringCustomFont(FactionGUI.isNumeric(pair.getValue(), true) ? pair.getValue() : I18n.func_135053_a((String)("faction.level.label." + pair.getValue())), this.guiLeft + 350, this.guiTop + 70 + 16 * index, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    }
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 315, this.guiTop + 50, 188 * GUI_SCALE, 308 * GUI_SCALE, 58 * GUI_SCALE, 130 * GUI_SCALE, 58, 130, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ClientEventHandler.STYLE.bindTexture("faction_levels");
                if (mouseX >= this.guiLeft + 67 && mouseX <= this.guiLeft + 67 + 19 && mouseY >= this.guiTop + 103 && mouseY <= this.guiTop + 103 + 18) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 67, this.guiTop + 103, 487 * GUI_SCALE, 19 * GUI_SCALE, 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "level_prev";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 67, this.guiTop + 103, 501 * GUI_SCALE, 19 * GUI_SCALE, 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ClientEventHandler.STYLE.bindTexture("faction_levels");
                if (mouseX >= this.guiLeft + 412 && mouseX <= this.guiLeft + 412 + 19 && mouseY >= this.guiTop + 103 && mouseY <= this.guiTop + 103 + 18) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 412, this.guiTop + 103, 487 * GUI_SCALE, 0 * GUI_SCALE, 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "level_next";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 412, this.guiTop + 103, 501 * GUI_SCALE, 0 * GUI_SCALE, 11 * GUI_SCALE, 18 * GUI_SCALE, 11, 18, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ClientEventHandler.STYLE.bindTexture("faction_levels");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 137, this.guiTop + 201, 0 * GUI_SCALE, 484 * GUI_SCALE, 215 * GUI_SCALE, 8 * GUI_SCALE, 215, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                float progress = Math.min((float)(currentScore - scoreActuelPalier) / ((float)(scoreNextPalier - scoreActuelPalier) * 1.0f), 1.0f);
                if (System.currentTimeMillis() - this.levelGUIOpen <= 1000L) {
                    progress *= (float)(System.currentTimeMillis() - this.levelGUIOpen) / 1000.0f;
                }
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 137, this.guiTop + 201, progressBarX.get(factionInfos.get("actualRelation")) * GUI_SCALE, progressBarY.get(factionInfos.get("actualRelation")) * GUI_SCALE, (int)(215.0f * progress) * GUI_SCALE, 8 * GUI_SCALE, (int)(215.0f * progress), 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                int firstNumber = currentPalier / 10;
                ClientEventHandler.STYLE.bindTexture("faction_main");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 70, this.guiTop + 196, (383 + 14 * (firstNumber - 1)) * GUI_SCALE, numbersY.get(factionInfos.get("actualRelation")) * GUI_SCALE, 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 84, this.guiTop + 196, 367 * GUI_SCALE, numbersY.get(factionInfos.get("actualRelation")) * GUI_SCALE, 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier_actual").split(" ")[0], this.guiLeft + 102, this.guiTop + 197, textColor.get(factionInfos.get("actualRelation")), 0.5f, "left", false, "georamaSemiBold", 30);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier_actual").split(" ")[1], this.guiLeft + 102, this.guiTop + 206, textColor.get(factionInfos.get("actualRelation")), 0.5f, "left", false, "georamaSemiBold", 30);
                if (currentPalier < 80) {
                    firstNumber = (currentPalier + 10) / 10;
                    ClientEventHandler.STYLE.bindTexture("faction_levels");
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 392, this.guiTop + 196, (382 + 14 * (firstNumber - 1)) * GUI_SCALE, 456 * GUI_SCALE, 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 406, this.guiTop + 196, 366 * GUI_SCALE, 456 * GUI_SCALE, 14 * GUI_SCALE, 17 * GUI_SCALE, 14, 17, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier_next").split(" ")[0], this.guiLeft + 387, this.guiTop + 197, 0xDADAED, 0.5f, "right", false, "georamaSemiBold", 30);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.palier_next").split(" ")[1], this.guiLeft + 387, this.guiTop + 206, 0xDADAED, 0.5f, "right", false, "georamaSemiBold", 30);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.level.label.level") + " " + currentLevel, this.guiLeft + 244, this.guiTop + 214, 10395075, 0.5f, "center", false, "georamaBold", 32);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    static {
        loaded = false;
        durationLevelAnimation = 4000L;
        startLevelAnimation = 0L;
        displayLevels = false;
        achievementDone = false;
        textColor = new HashMap<String, Integer>(){
            {
                this.put("neutral", 0x6E76EE);
                this.put("enemy", 15017020);
                this.put("ally", 0xAE6EEE);
                this.put("colony", 16109642);
            }
        };
        blockLevel = new HashMap<String, Integer>(){
            {
                this.put("neutral", 0);
                this.put("enemy", 42);
                this.put("ally", 84);
                this.put("colony", 126);
            }
        };
        blockMainPalierX = new HashMap<String, Integer>(){
            {
                this.put("neutral", 128);
                this.put("enemy", 256);
                this.put("ally", 384);
                this.put("colony", 0);
            }
        };
        blockMainPalierY = new HashMap<String, Integer>(){
            {
                this.put("neutral", 132);
                this.put("enemy", 132);
                this.put("ally", 132);
                this.put("colony", 308);
            }
        };
        blockSecondaryPalierX = new HashMap<String, Integer>(){
            {
                this.put("neutral", 88);
                this.put("enemy", 176);
                this.put("ally", 264);
                this.put("colony", 352);
            }
        };
        blockRelation = new HashMap<String, Integer>(){
            {
                this.put("neutral", 219);
                this.put("enemy", 247);
                this.put("ally", 275);
                this.put("colony", 303);
            }
        };
        iconRelations = new HashMap<String, Integer>(){
            {
                this.put("neutral", 0);
                this.put("enemy", 20);
                this.put("ally", 40);
                this.put("colony", 60);
            }
        };
        buttonRelations = new HashMap<String, Integer>(){
            {
                this.put("neutral", 42);
                this.put("enemy", 58);
                this.put("ally", 74);
                this.put("colony", 90);
            }
        };
        badgesPositionTextureX = new HashMap<String, Integer>(){
            {
                this.put("empire", 225);
                this.put("young", 243);
                this.put("colony", 261);
                this.put("warzone", 279);
                this.put("referent", 297);
                this.put("surclaims", 315);
                this.put("absence", 351);
                this.put("restrictMissile", 369);
                this.put("restrictAssault", 387);
                this.put("restrictNotation", 404);
                this.put("restrictDisband", 422);
            }
        };
        iconsRewardsMainY = new HashMap<String, Integer>(){
            {
                this.put("members", 0);
                this.put("relations", 22);
                this.put("chest", 44);
                this.put("warps", 66);
                this.put("missiles", 88);
                this.put("empire", 110);
                this.put("colonies", 132);
                this.put("gallery", 154);
                this.put("discord", 176);
            }
        };
        iconsRewardsSecondaryY = new HashMap<String, Integer>(){
            {
                this.put("members", 0);
                this.put("relations", 17);
                this.put("chest", 34);
                this.put("warps", 51);
                this.put("missiles", 68);
                this.put("empire", 85);
                this.put("colonies", 102);
                this.put("gallery", 119);
                this.put("discord", 136);
            }
        };
        progressBarX = new HashMap<String, Integer>(){
            {
                this.put("neutral", 0);
                this.put("enemy", 0);
                this.put("ally", 217);
                this.put("colony", 217);
            }
        };
        progressBarY = new HashMap<String, Integer>(){
            {
                this.put("neutral", 494);
                this.put("enemy", 504);
                this.put("ally", 494);
                this.put("colony", 504);
            }
        };
        numbersY = new HashMap<String, Integer>(){
            {
                this.put("neutral", 434);
                this.put("enemy", 454);
                this.put("ally", 474);
                this.put("colony", 494);
            }
        };
    }
}

