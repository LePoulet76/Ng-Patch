/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.LinkedTreeMap
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CustomTextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.DisbandConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SellCountryConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.CustomInputFieldGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSaveFlagDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSaveSettingsDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSellCountryPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionSettingsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Encoder;

public class SettingsGUI
extends TabbedFactionGUI {
    public static boolean loaded = false;
    public static boolean data_loaded = false;
    public static HashMap<String, Object> settingsData = new HashMap();
    public static List<String> tags = Arrays.asList("pvp", "build", "trade", "farm", "enterprises", "conquest", "pacifist", "aggresive");
    public static List<String> rolesIds = Arrays.asList("70", "60", "50", "45", "40", "35", "20", "10");
    public static TreeMap<Integer, Integer> flag_pixels = new TreeMap();
    public static HashMap<String, Integer> iconLogTypes = new HashMap<String, Integer>(){
        {
            this.put("promote_member", 225);
            this.put("promote_officer", 225);
            this.put("promote_leader", 225);
            this.put("demote_recruit", 233);
            this.put("demote_member", 233);
            this.put("invite_start", 241);
            this.put("invite_cancel", 249);
            this.put("exclude", 257);
            this.put("exclude_auto", 257);
            this.put("ban", 257);
            this.put("add_bank", 217);
            this.put("remove_bank", 209);
            this.put("edit_flag", 201);
            this.put("edit_perms", 193);
            this.put("leave", 257);
            this.put("research", 265);
        }
    };
    public static HashMap<String, Integer> checkboxX = new HashMap<String, Integer>(){
        {
            this.put("40", 30);
            this.put("35", 20);
            this.put("10", 40);
            this.put("20", 10);
        }
    };
    public String displayMode = "logs";
    private GuiScrollBarGeneric scrollBarSettings;
    private GuiScrollBarGeneric scrollBarLogs;
    private GuiScrollBarGeneric scrollBarPerms;
    private ArrayList<Integer> flag_colorList = new ArrayList();
    private int flag_pixelHoveredId = -1;
    private int flag_pixelHoveredColorId = 0;
    private boolean mouseDrawing = false;
    private int colorHovered = 0;
    private int colorSelected = 0;
    private GuiTextField descriptionText;
    private GuiTextField motdText;
    private GuiTextField entryText;
    private GuiTextField discordText;
    private GuiTextField roleInput_Leader;
    private GuiTextField roleInput_Officer;
    private GuiTextField roleInput_Member;
    private GuiTextField roleInput_Recruit;
    private String cachedFactionName = (String)FactionGUI.factionInfos.get("name");
    private boolean hasEditedPerms = false;

    public SettingsGUI() {
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSettingsDataPacket((String)FactionGUI.factionInfos.get("name"))));
        if (FactionGUI.factionInfos.get("flagPixels") != null && ((ArrayList)FactionGUI.factionInfos.get("flagPixels")).size() > 0) {
            for (int i = 0; i < ((ArrayList)FactionGUI.factionInfos.get("flagPixels")).size(); ++i) {
                flag_pixels.put(i, ((Double)((ArrayList)FactionGUI.factionInfos.get("flagPixels")).get(i)).intValue());
            }
        } else {
            for (int i = 0; i < 338; ++i) {
                flag_pixels.put(i, -1);
            }
        }
        this.flag_colorList.addAll(Arrays.asList(-793344, -145910, -815329, -1351392, -1891550, -3931010, -9553525, -12235111, -13930319, -16345413, -16740772, -7488731, -1, -16448251, -10471149, -5789785, -6274803, -3761043));
    }

    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write((RenderedImage)image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);
            bos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }

    public static <T> T mostCommon(List<T> list) {
        HashMap<T, Integer> map = new HashMap<T, Integer>();
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T t;
            Integer val = (Integer)map.get(t = iterator.next());
            map.put(t, val == null ? 1 : val + 1);
        }
        Map.Entry max = null;
        for (Map.Entry e : map.entrySet()) {
            if (max != null && (Integer)e.getValue() <= (Integer)max.getValue()) continue;
            max = e;
        }
        return (T)max.getKey();
    }

    @Override
    public void func_73866_w_() {
        super.func_73866_w_();
        this.scrollBarSettings = new GuiScrollBarGeneric(this.guiLeft + 235, this.guiTop + 40, 181, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarLogs = new GuiScrollBarGeneric(this.guiLeft + 451, this.guiTop + 40, 181, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarPerms = new GuiScrollBarGeneric(this.guiLeft + 443, this.guiTop + 67, 158, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        String data = this.descriptionText != null ? this.descriptionText.func_73781_b() : "";
        this.descriptionText = new CustomTextAreaGUI(this.guiLeft + 48, this.guiTop + 110, 165, "georamaMedium", 25, 52);
        this.descriptionText.func_73804_f(225);
        this.descriptionText.func_73782_a(data);
        data = this.motdText != null ? this.motdText.func_73781_b() : "";
        this.motdText = new CustomTextAreaGUI(this.guiLeft + 48, this.guiTop + 180, 165, "georamaMedium", 25, 27);
        this.motdText.func_73804_f(110);
        this.motdText.func_73782_a(data);
        data = this.entryText != null ? this.entryText.func_73781_b() : "";
        this.entryText = new CustomTextAreaGUI(this.guiLeft + 48, this.guiTop + 222, 165, "georamaMedium", 25, 27);
        this.entryText.func_73804_f(110);
        this.entryText.func_73782_a(data);
        data = this.discordText != null ? this.discordText.func_73781_b() : "";
        this.discordText = new CustomInputFieldGUI(this.guiLeft + 48, this.guiTop + 265, 165, 12, "georamaMedium", 25);
        this.discordText.func_73804_f(35);
        this.discordText.func_73782_a(data);
        data = this.roleInput_Leader != null ? this.roleInput_Leader.func_73781_b() : "";
        this.roleInput_Leader = new CustomInputFieldGUI(this.guiLeft + 71, this.guiTop + 373, 45, 5, "georamaMedium", 22);
        this.roleInput_Leader.func_73804_f(15);
        this.roleInput_Leader.func_73782_a(data);
        data = this.roleInput_Officer != null ? this.roleInput_Officer.func_73781_b() : "";
        this.roleInput_Officer = new CustomInputFieldGUI(this.guiLeft + 71, this.guiTop + 386, 45, 5, "georamaMedium", 22);
        this.roleInput_Officer.func_73804_f(15);
        this.roleInput_Officer.func_73782_a(data);
        data = this.roleInput_Member != null ? this.roleInput_Member.func_73781_b() : "";
        this.roleInput_Member = new CustomInputFieldGUI(this.guiLeft + 172, this.guiTop + 373, 45, 5, "georamaMedium", 22);
        this.roleInput_Member.func_73804_f(15);
        this.roleInput_Member.func_73782_a(data);
        data = this.roleInput_Recruit != null ? this.roleInput_Recruit.func_73781_b() : "";
        this.roleInput_Recruit = new CustomInputFieldGUI(this.guiLeft + 172, this.guiTop + 386, 45, 5, "georamaMedium", 22);
        this.roleInput_Recruit.func_73804_f(15);
        this.roleInput_Recruit.func_73782_a(data);
    }

    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTick) {
        if (loaded && !data_loaded && FactionGUI.factionInfos != null && !FactionGUI.factionInfos.isEmpty()) {
            this.descriptionText.func_73782_a((String)settingsData.get("description"));
            this.descriptionText.func_73797_d();
            this.motdText.func_73782_a((String)settingsData.get("motd"));
            this.motdText.func_73797_d();
            this.entryText.func_73782_a((String)settingsData.get("entryMsg"));
            this.entryText.func_73797_d();
            this.discordText.func_73782_a((Boolean)FactionGUI.factionInfos.get("isLeader") != false || (Boolean)FactionGUI.factionInfos.get("isOp") != false ? (String)settingsData.get("discord") : "***************");
            this.discordText.func_73797_d();
            this.roleInput_Leader.func_73782_a(((LinkedTreeMap)settingsData.get("rolesName")).containsKey((Object)"leader") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get((Object)"leader") : "");
            this.roleInput_Leader.func_73797_d();
            this.roleInput_Officer.func_73782_a(((LinkedTreeMap)settingsData.get("rolesName")).containsKey((Object)"officer") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get((Object)"officer") : "");
            this.roleInput_Officer.func_73797_d();
            this.roleInput_Member.func_73782_a(((LinkedTreeMap)settingsData.get("rolesName")).containsKey((Object)"member") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get((Object)"member") : "");
            this.roleInput_Member.func_73797_d();
            this.roleInput_Recruit.func_73782_a(((LinkedTreeMap)settingsData.get("rolesName")).containsKey((Object)"recruit") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get((Object)"recruit") : "");
            this.roleInput_Recruit.func_73797_d();
            data_loaded = true;
        }
        this.func_73873_v_();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.colorHovered = 0;
        this.flag_pixelHoveredColorId = 0;
        this.flag_pixelHoveredId = -1;
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 30, this.guiTop + 0, 0 * GUI_SCALE, 0 * GUI_SCALE, (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), this.guiLeft + 43, this.guiTop + 6, 10395075, 0.5f, "left", false, "georamaMedium", 32);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title"), this.guiLeft + 43, this.guiTop + 16, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 32);
        if (loaded) {
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 235, this.guiTop + 40, 275 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 181 * GUI_SCALE, 2, 181, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            GUIUtils.startGLScissor(this.guiLeft + 30, this.guiTop + 35, 200, 200);
            int offsetX = this.guiLeft + 30;
            Float offsetY = Float.valueOf((float)(this.guiTop + 35) + this.getSlideSettings());
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.general"), offsetX + 13, offsetY.intValue() + 9, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, offsetY.intValue() + 19, 324 * GUI_SCALE, 0 * GUI_SCALE, 188 * GUI_SCALE, 293 * GUI_SCALE, 188, 314, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.flag"), offsetX + 18, offsetY.intValue() + 23, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 32, 0 * GUI_SCALE, 269 * GUI_SCALE, 177 * GUI_SCALE, 28 * GUI_SCALE, 177, 28, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ClientProxy.loadCountryFlag((String)FactionGUI.factionInfos.get("name"));
            if (ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("name"))) {
                GL11.glBindTexture((int)3553, (int)ClientProxy.flagsTexture.get((String)FactionGUI.factionInfos.get("name")).func_110552_b());
                ModernGui.drawScaledCustomSizeModalRect(offsetX + 23, offsetY.intValue() + 36, 0.0f, 0.0f, 156, 78, 33, 20, 156.0f, 78.0f, false);
            }
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            if (mouseX >= this.guiLeft + 123 && mouseX <= this.guiLeft + 123 + 62 && mouseY >= offsetY.intValue() + 42 && mouseY <= offsetY.intValue() + 42 + 10) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 123, offsetY.intValue() + 42, 198 * GUI_SCALE, 73 * GUI_SCALE, 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                this.hoveredAction = "edit_flag";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 123, offsetY.intValue() + 42, 198 * GUI_SCALE, 49 * GUI_SCALE, 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.update_flag"), this.guiLeft + 123 + 31, offsetY.intValue() + 44, 2234425, 0.5f, "center", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.description"), offsetX + 18, offsetY.intValue() + 65, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 74, 0 * GUI_SCALE, 413 * GUI_SCALE, 177 * GUI_SCALE, 57 * GUI_SCALE, 177, 57, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomTextAreaGUI)this.descriptionText).posY = offsetY.intValue() + 74;
            this.descriptionText.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.motd"), offsetX + 18, offsetY.intValue() + 135, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 144, 0 * GUI_SCALE, 473 * GUI_SCALE, 177 * GUI_SCALE, 30 * GUI_SCALE, 177, 30, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomTextAreaGUI)this.motdText).posY = offsetY.intValue() + 144;
            this.motdText.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.entry_message"), offsetX + 18, offsetY.intValue() + 178, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 187, 0 * GUI_SCALE, 473 * GUI_SCALE, 177 * GUI_SCALE, 30 * GUI_SCALE, 177, 30, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomTextAreaGUI)this.entryText).posY = offsetY.intValue() + 187;
            this.entryText.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.discord"), offsetX + 18, offsetY.intValue() + 221, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 230, 0 * GUI_SCALE, 384 * GUI_SCALE, 150 * GUI_SCALE, 14 * GUI_SCALE, 150, 14, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomInputFieldGUI)this.discordText).posY = offsetY.intValue() + 230;
            this.discordText.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.tags"), offsetX + 18, offsetY.intValue() + 248, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            for (int i = 0; i < tags.size(); ++i) {
                int x = i % 4;
                int y = i / 4;
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                if (mouseX >= this.guiLeft + 48 + x * 42 && mouseX <= this.guiLeft + 48 + x * 42 + 39 && mouseY >= offsetY.intValue() + 257 + y * 12 && mouseY <= offsetY.intValue() + 257 + y * 12 + 9) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48 + x * 42, offsetY.intValue() + 257 + y * 12, 193 * GUI_SCALE, 170 * GUI_SCALE, 39 * GUI_SCALE, 9 * GUI_SCALE, 39, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "tag#" + tags.get(i);
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48 + x * 42, offsetY.intValue() + 257 + y * 12, 193 * GUI_SCALE, (((ArrayList)settingsData.get("tags")).contains(tags.get(i)) ? 181 : 192) * GUI_SCALE, 39 * GUI_SCALE, 9 * GUI_SCALE, 39, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.settings.tags." + tags.get(i))), this.guiLeft + 48 + x * 42 + 19, offsetY.intValue() + 257 + y * 12 + 2, 2234425, 0.5f, "center", false, "georamaMedium", 22);
            }
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 285, ((Boolean)settingsData.get("open") != false ? 198 : 10) * GUI_SCALE, ((Boolean)settingsData.get("open") != false ? 0 : 169) * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.checkbox_invitation"), this.guiLeft + 60, offsetY.intValue() + 287, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 9 && mouseY >= offsetY.intValue() + 285 && mouseY <= offsetY.intValue() + 285 + 9) {
                this.hoveredAction = "checkbox_invitation";
            }
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 297, ((Boolean)settingsData.get("recruitmentOpen") != false ? 10 : 198) * GUI_SCALE, ((Boolean)settingsData.get("recruitmentOpen") != false ? 169 : 0) * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.checkbox_recruitment"), this.guiLeft + 60, offsetY.intValue() + 299, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 9 && mouseY >= offsetY.intValue() + 297 && mouseY <= offsetY.intValue() + 297 + 9) {
                this.hoveredAction = "checkbox_recruitment";
            }
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 309, ((Boolean)settingsData.get("doKickRecruitWarReason") != false ? 10 : 198) * GUI_SCALE, ((Boolean)settingsData.get("doKickRecruitWarReason") != false ? 169 : 0) * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.checkbox_kick_war_recruit"), this.guiLeft + 60, offsetY.intValue() + 311, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 25);
            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 9 && mouseY >= offsetY.intValue() + 311 && mouseY <= offsetY.intValue() + 311 + 9) {
                this.hoveredAction = "checkbox_kick_war_recruit";
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.members"), offsetX + 13, offsetY.intValue() + 341, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 42, offsetY.intValue() + 351, 0 * GUI_SCALE, 300 * GUI_SCALE, 188 * GUI_SCALE, 58 * GUI_SCALE, 188, 58, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.role_names"), offsetX + 18, offsetY.intValue() + 355, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.leader"), this.guiLeft + 48, offsetY.intValue() + 367, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 73, offsetY.intValue() + 366, 0 * GUI_SCALE, 401 * GUI_SCALE, 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomInputFieldGUI)this.roleInput_Leader).posY = offsetY.intValue() + 366 - 2;
            this.roleInput_Leader.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.officer"), this.guiLeft + 48, offsetY.intValue() + 379, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 73, offsetY.intValue() + 378, 0 * GUI_SCALE, 401 * GUI_SCALE, 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomInputFieldGUI)this.roleInput_Officer).posY = offsetY.intValue() + 378 - 2;
            this.roleInput_Officer.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.member"), this.guiLeft + 149, offsetY.intValue() + 367, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 174, offsetY.intValue() + 366, 0 * GUI_SCALE, 401 * GUI_SCALE, 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomInputFieldGUI)this.roleInput_Member).posY = offsetY.intValue() + 366 - 2;
            this.roleInput_Member.func_73795_f();
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.recruit"), this.guiLeft + 149, offsetY.intValue() + 379, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 174, offsetY.intValue() + 378, 0 * GUI_SCALE, 401 * GUI_SCALE, 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            ((CustomInputFieldGUI)this.roleInput_Recruit).posY = offsetY.intValue() + 378 - 2;
            this.roleInput_Recruit.func_73795_f();
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            if (!FactionGUI.hasPermissions("perms")) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 392, 193 * GUI_SCALE, 230 * GUI_SCALE, 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.edit_permissions"), this.guiLeft + 48 + 37, offsetY.intValue() + 392 + 2, 3682124, 0.5f, "center", false, "georamaSemiBold", 22);
            } else {
                if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 74 && mouseY >= offsetY.intValue() + 392 && mouseY <= offsetY.intValue() + 392 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 392, 193 * GUI_SCALE, 217 * GUI_SCALE, 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "edit_permissions";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 48, offsetY.intValue() + 392, 193 * GUI_SCALE, 204 * GUI_SCALE, 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.edit_permissions"), this.guiLeft + 48 + 37, offsetY.intValue() + 392 + 2, 2234425, 0.5f, "center", false, "georamaSemiBold", 22);
            }
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            if (mouseX >= this.guiLeft + 149 && mouseX <= this.guiLeft + 149 + 74 && mouseY >= offsetY.intValue() + 392 && mouseY <= offsetY.intValue() + 392 + 10) {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 149, offsetY.intValue() + 392, 193 * GUI_SCALE, 217 * GUI_SCALE, 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                this.hoveredAction = "show_logs";
            } else {
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 149, offsetY.intValue() + 392, 193 * GUI_SCALE, 204 * GUI_SCALE, 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            }
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.show_logs"), this.guiLeft + 149 + 37, offsetY.intValue() + 392 + 2, 2234425, 0.5f, "center", false, "georamaSemiBold", 22);
            if (((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()) {
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                if (!((Boolean)FactionGUI.factionInfos.get("canBeSale")).booleanValue() || mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 35 && mouseY >= offsetY.intValue() + 416 && mouseY <= offsetY.intValue() + 416 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, offsetY.intValue() + 416, 231 * GUI_SCALE, 243 * GUI_SCALE, 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont((Boolean)FactionGUI.factionInfos.get("isForSale") != false ? I18n.func_135053_a((String)"faction.settings.label.stop_sell") : I18n.func_135053_a((String)"faction.settings.label.sell"), this.guiLeft + 43 + 18, offsetY.intValue() + 416 + 2, 15017020, 0.5f, "center", false, "georamaSemiBold", 22);
                    if (!((Boolean)FactionGUI.factionInfos.get("canBeSale")).booleanValue()) {
                        if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 36 && mouseY >= offsetY.intValue() + 416 && mouseY <= offsetY.intValue() + 416 + 10) {
                            tooltipToDraw.add(I18n.func_135053_a((String)"faction.settings.cant_sell_1"));
                            tooltipToDraw.add(I18n.func_135053_a((String)"faction.settings.cant_sell_2"));
                            tooltipToDraw.add(I18n.func_135053_a((String)"faction.settings.cant_sell_3"));
                        }
                    } else {
                        this.hoveredAction = "sell_country";
                    }
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 43, offsetY.intValue() + 416, 193 * GUI_SCALE, 243 * GUI_SCALE, 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont((Boolean)FactionGUI.factionInfos.get("isForSale") != false ? I18n.func_135053_a((String)"faction.settings.label.stop_sell") : I18n.func_135053_a((String)"faction.settings.label.sell"), this.guiLeft + 43 + 18, offsetY.intValue() + 416 + 2, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 22);
                }
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                if (mouseX >= this.guiLeft + 81 && mouseX <= this.guiLeft + 81 + 35 && mouseY >= offsetY.intValue() + 416 && mouseY <= offsetY.intValue() + 416 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 81, offsetY.intValue() + 416, 231 * GUI_SCALE, 243 * GUI_SCALE, 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.disband"), this.guiLeft + 81 + 18, offsetY.intValue() + 416 + 2, 15017020, 0.5f, "center", false, "georamaSemiBold", 22);
                    this.hoveredAction = "disband_country";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 81, offsetY.intValue() + 416, 193 * GUI_SCALE, 243 * GUI_SCALE, 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.disband"), this.guiLeft + 81 + 18, offsetY.intValue() + 416 + 2, 0xFFFFFF, 0.5f, "center", false, "georamaSemiBold", 22);
                }
            }
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 30 && mouseX <= this.guiLeft + 30 + 200 && mouseY >= this.guiTop && mouseY <= this.guiTop + this.ySize) {
                this.scrollBarSettings.draw(mouseX, mouseY);
            }
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("faction_global_2");
            ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 242, this.guiTop + 0, 0 * GUI_SCALE, 240 * GUI_SCALE, (this.xSize - 242) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 242, this.ySize, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
            if (this.displayMode.equals("logs")) {
                CFontRenderer cFontRendererDate = ModernGui.getCustomFont("georamaMedium", 25);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.logs"), this.guiLeft + 255, this.guiTop + 20, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 451, this.guiTop + 40, 275 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 181 * GUI_SCALE, 2, 181, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                GUIUtils.startGLScissor(this.guiLeft + 242, this.guiTop + 35, 209, 197);
                int index = 0;
                for (String log : (ArrayList)settingsData.get("logs")) {
                    offsetX = this.guiLeft + 242;
                    offsetY = Float.valueOf((float)(this.guiTop + 35 + index * 23) + this.getSlideLogs());
                    Date date = new Date(log.split("##").length == 3 ? Long.parseLong(log.split("##")[2]) : Long.parseLong(log.split("##")[3]));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyy HH:mm");
                    String dateStr = simpleDateFormat.format(date);
                    ModernGui.drawScaledStringCustomFont(dateStr, offsetX + 18, offsetY.intValue() + 6, 10395075, 0.5f, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont(log.split("##")[0], offsetX + 21 + (int)cFontRendererDate.getStringWidth(dateStr) / 2, offsetY.intValue() + 6, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 24);
                    ClientEventHandler.STYLE.bindTexture("faction_settings");
                    ModernGui.drawScaledCustomSizeModalRect(offsetX + 18, offsetY.intValue() + 14, iconLogTypes.get(log.split("##")[1]) * GUI_SCALE, 299 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("faction.settings.logs." + log.split("##")[1])).replace("#target#", log.split("##")[2]), offsetX + 18 + 8 + 2, offsetY.intValue() + 14, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 30);
                    ++index;
                }
                GUIUtils.endGLScissor();
                if (mouseX >= this.guiLeft + 242 && mouseX <= this.guiLeft + this.xSize && mouseY >= this.guiTop && mouseY <= this.guiTop + this.ySize) {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }
            } else if (this.displayMode.equals("flag")) {
                int i;
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.edit_flag"), this.guiLeft + 255, this.guiTop + 20, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 26);
                int x = 0;
                int y = 0;
                for (i = 0; i < this.flag_colorList.size(); ++i) {
                    int colodId = this.flag_colorList.get(i);
                    if (mouseX >= this.guiLeft + 414 + x * 13 && mouseX <= this.guiLeft + 414 + x * 13 + 18 && mouseY >= this.guiTop + 62 + y * 13 && mouseY <= this.guiTop + 62 + y * 13 + 18) {
                        this.colorHovered = colodId;
                    }
                    Gui.func_73734_a((int)(this.guiLeft + 414 + x * 13), (int)(this.guiTop + 62 + y * 13), (int)(this.guiLeft + 414 + x * 13 + 9), (int)(this.guiTop + 62 + y * 13 + 9), (int)colodId);
                    if (colodId == this.colorSelected) {
                        ClientEventHandler.STYLE.bindTexture("faction_settings");
                        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 414 + x * 13 - 1, this.guiTop + 62 + y * 13 - 1, 224 * GUI_SCALE, 0 * GUI_SCALE, 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    }
                    x = x < 2 ? x + 1 : 0;
                    y = x == 0 ? y + 1 : y;
                }
                x = 0;
                y = 0;
                i = 0;
                if (flag_pixels.size() > 0) {
                    for (Map.Entry<Integer, Integer> pair : flag_pixels.entrySet()) {
                        int colorId = pair.getValue();
                        if (FactionGUI.hasPermissions("flag") && mouseX >= this.guiLeft + 250 + x * 6 && mouseX <= this.guiLeft + 250 + x * 6 + 6 && mouseY >= this.guiTop + 60 + y * 6 && mouseY <= this.guiTop + 60 + y * 6 + 6) {
                            this.flag_pixelHoveredId = i;
                        }
                        if (FactionGUI.hasPermissions("flag") && this.mouseDrawing && colorId != this.colorSelected && this.flag_pixelHoveredId == i) {
                            flag_pixels.put(i, this.colorSelected);
                            colorId = this.colorSelected;
                        }
                        Gui.func_73734_a((int)(this.guiLeft + 250 + x * 6), (int)(this.guiTop + 60 + y * 6), (int)(this.guiLeft + 250 + x * 6 + 6), (int)(this.guiTop + 60 + y * 6 + 6), (int)colorId);
                        x = x < 25 ? x + 1 : 0;
                        y = x == 0 ? y + 1 : y;
                        ++i;
                    }
                }
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                if (mouseX >= this.guiLeft + 337 && mouseX <= this.guiLeft + 337 + 51 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 10) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 206, 193 * GUI_SCALE, 276 * GUI_SCALE, 51 * GUI_SCALE, 10 * GUI_SCALE, 51, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "save_flag";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 337, this.guiTop + 206, 193 * GUI_SCALE, 261 * GUI_SCALE, 51 * GUI_SCALE, 10 * GUI_SCALE, 51, 10, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.valid"), this.guiLeft + 337 + 25, this.guiTop + 206 + 2, 2234425, 0.5f, "center", false, "georamaSemiBold", 24);
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                if (mouseX >= this.guiLeft + 392 && mouseX <= this.guiLeft + 392 + 57 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 13) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 392, this.guiTop + 206, 248 * GUI_SCALE, 276 * GUI_SCALE, 57 * GUI_SCALE, 13 * GUI_SCALE, 57, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    this.hoveredAction = "erase_flag";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 392, this.guiTop + 206, 248 * GUI_SCALE, 261 * GUI_SCALE, 57 * GUI_SCALE, 13 * GUI_SCALE, 57, 13, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.erase"), this.guiLeft + 392 + 28, this.guiTop + 206 + 2, 15463162, 0.5f, "center", false, "georamaSemiBold", 24);
            } else if (this.displayMode.equals("permissions")) {
                CFontRenderer permNameFont = ModernGui.getCustomFont("georamaMedium", 28);
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6) {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 8, 206 * GUI_SCALE, 34 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 9, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 30);
                    this.hoveredAction = "back";
                } else {
                    ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 403, this.guiTop + 8, 197 * GUI_SCALE, 34 * GUI_SCALE, 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.label.back"), this.guiLeft + 412, this.guiTop + 9, 10395075, 0.5f, "left", false, "georamaSemiBold", 30);
                }
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"faction.settings.title.edit_permissions"), this.guiLeft + 255, this.guiTop + 20, 0xFFFFFF, 0.75f, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 256, this.guiTop + 63, 0 * GUI_SCALE, 0 * GUI_SCALE, 193 * GUI_SCALE, 166 * GUI_SCALE, 193, 166, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                ModernGui.drawScaledCustomSizeModalRect(this.guiLeft + 443, this.guiTop + 67, 269 * GUI_SCALE, 0 * GUI_SCALE, 2 * GUI_SCALE, 158 * GUI_SCALE, 2, 158, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                GUIUtils.startGLScissor(this.guiLeft + 256, this.guiTop + 63, 188, 163);
                LinkedTreeMap factionPermInfos = (LinkedTreeMap)settingsData.get("perms");
                int index = 0;
                Iterator it = factionPermInfos.entrySet().iterator();
                while (it.hasNext()) {
                    offsetX = this.guiLeft + 256;
                    offsetY = Float.valueOf((float)(this.guiTop + 63 + index * 15) + this.getSlidePerms());
                    Map.Entry pair = (Map.Entry)it.next();
                    String permName = (String)pair.getKey();
                    LinkedTreeMap infos = (LinkedTreeMap)pair.getValue();
                    ArrayList authorizedRoleIds = (ArrayList)infos.get((Object)"permissions");
                    ModernGui.drawScaledStringCustomFont(permName.substring(0, 1).toUpperCase() + permName.substring(1), offsetX + 4, offsetY.intValue() + 4, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                    ClientEventHandler.STYLE.bindTexture("faction_settings");
                    int iconOffsetX = offsetX + 4 + (int)permNameFont.getStringWidth(permName.substring(0, 1).toUpperCase() + permName.substring(1)) / 2 + 2;
                    ModernGui.drawScaledCustomSizeModalRect(iconOffsetX, offsetY.intValue() + 3, 201 * GUI_SCALE, 308 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                    if (mouseX >= iconOffsetX && mouseX <= iconOffsetX + 8 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 8) {
                        ModernGui.drawScaledCustomSizeModalRect(iconOffsetX, offsetY.intValue() + 3, 193 * GUI_SCALE, 308 * GUI_SCALE, 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        tooltipToDraw.add(((String)infos.get((Object)"description")).substring(0, 1).toUpperCase() + ((String)infos.get((Object)"description")).substring(1));
                    }
                    int indexRole = 0;
                    for (String roleId : rolesIds) {
                        boolean forbidden = false;
                        if (Integer.parseInt(roleId) <= 45) {
                            if (Arrays.asList("assault", "join assault", "wars", "relations", "actions", "territory", "locations", "perms", "taxes", "access", "setwarp", "sethome", "settings", "kick", "chest_access").contains(permName)) {
                                forbidden = true;
                            }
                        } else if (Integer.parseInt(roleId) <= 50) {
                            if (Arrays.asList("wars", "actions", "relations", "locations", "perms", "setwarp", "sethome", "settings", "kick", "chest_access", "assault", "join assault").contains(permName)) {
                                forbidden = true;
                            }
                        } else if (Integer.parseInt(roleId) == 70 && Arrays.asList("perms").contains(permName)) {
                            forbidden = true;
                        }
                        if (authorizedRoleIds.contains(roleId)) {
                            ClientEventHandler.STYLE.bindTexture("faction_settings");
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 83 + indexRole * 13, offsetY.intValue() + 3, (checkboxX.containsKey(roleId) ? checkboxX.get(roleId) : 0) * GUI_SCALE, 169 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            if (!(forbidden || mouseX < offsetX + 83 + indexRole * 13 || mouseX > offsetX + 83 + indexRole * 13 + 9 || mouseY < offsetY.intValue() + 3 || mouseY > offsetY.intValue() + 3 + 9 || roleId.equals("70") && !((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue())) {
                                this.hoveredAction = "permission#" + permName + "#" + roleId + "#no";
                            }
                        } else if (!forbidden) {
                            ClientEventHandler.STYLE.bindTexture("faction_settings");
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 83 + indexRole * 13, offsetY.intValue() + 3, 198 * GUI_SCALE, 0 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                            if (mouseX >= offsetX + 83 + indexRole * 13 && mouseX <= offsetX + 83 + indexRole * 13 + 9 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 9 && (!roleId.equals("70") || ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue())) {
                                this.hoveredAction = "permission#" + permName + "#" + roleId + "#yes";
                            }
                        } else {
                            ClientEventHandler.STYLE.bindTexture("faction_settings");
                            ModernGui.drawScaledCustomSizeModalRect(offsetX + 83 + indexRole * 13, offsetY.intValue() + 3, 238 * GUI_SCALE, 0 * GUI_SCALE, 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, 512 * GUI_SCALE, 512 * GUI_SCALE, true);
                        }
                        ++indexRole;
                    }
                    ++index;
                }
                GUIUtils.endGLScissor();
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(this.guiLeft + 340), (float)(this.guiTop + 60), (float)0.0f);
                GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                GL11.glTranslatef((float)(-(this.guiLeft + 340)), (float)(-(this.guiTop + 60)), (float)0.0f);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#70#") ? "\u00a7l" : "") + (this.roleInput_Leader.func_73781_b().isEmpty() ? I18n.func_135053_a((String)"faction.settings.label.leader") : this.roleInput_Leader.func_73781_b()), this.guiLeft + 340, this.guiTop + 60, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#60#") ? "\u00a7l" : "") + (this.roleInput_Officer.func_73781_b().isEmpty() ? I18n.func_135053_a((String)"faction.settings.label.officer") : this.roleInput_Officer.func_73781_b()), this.guiLeft + 340, this.guiTop + 73, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#50#") ? "\u00a7l" : "") + (this.roleInput_Member.func_73781_b().isEmpty() ? I18n.func_135053_a((String)"faction.settings.label.member") : this.roleInput_Member.func_73781_b()), this.guiLeft + 340, this.guiTop + 86, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#45#") ? "\u00a7l" : "") + (this.roleInput_Recruit.func_73781_b().isEmpty() ? I18n.func_135053_a((String)"faction.settings.label.recruit") : this.roleInput_Recruit.func_73781_b()), this.guiLeft + 340, this.guiTop + 99, 0xFFFFFF, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#40#") ? "\u00a7l" : "") + "Colony", this.guiLeft + 340, this.guiTop + 112, 16109642, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#35#") ? "\u00a7l" : "") + "Ally", this.guiLeft + 340, this.guiTop + 125, 0xAE6EEE, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#20#") ? "\u00a7l" : "") + "Neutral", this.guiLeft + 340, this.guiTop + 138, 0x6E76EE, 0.5f, "left", false, "georamaMedium", 28);
                ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#10#") ? "\u00a7l" : "") + "Enemy", this.guiLeft + 340, this.guiTop + 151, 15017020, 0.5f, "left", false, "georamaMedium", 28);
                GL11.glPopMatrix();
                this.scrollBarPerms.draw(mouseX, mouseY);
            }
        }
        super.func_73863_a(mouseX, mouseY, partialTick);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
    }

    protected void func_73879_b(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            this.mouseDrawing = false;
        }
        super.func_73879_b(mouseX, mouseY, mouseButton);
    }

    private float getSlideSettings() {
        return -231.0f * this.scrollBarSettings.getSliderValue();
    }

    private float getSlidePerms() {
        return ((LinkedTreeMap)settingsData.get("perms")).size() > 11 ? (float)(-(((LinkedTreeMap)settingsData.get("perms")).size() - 11) * 15) * this.scrollBarPerms.getSliderValue() : 0.0f;
    }

    private float getSlideLogs() {
        return ((ArrayList)settingsData.get("logs")).size() > 8 ? (float)(-(((ArrayList)settingsData.get("logs")).size() - 8) * 23) * this.scrollBarLogs.getSliderValue() : 0.0f;
    }

    public void func_73876_c() {
        this.descriptionText.func_73780_a();
        this.motdText.func_73780_a();
        this.entryText.func_73780_a();
        this.discordText.func_73780_a();
        this.roleInput_Leader.func_73780_a();
        this.roleInput_Officer.func_73780_a();
        this.roleInput_Member.func_73780_a();
        this.roleInput_Recruit.func_73780_a();
    }

    @Override
    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (!this.hoveredAction.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (this.hoveredAction.equals("back") || this.hoveredAction.equals("show_logs")) {
                    this.displayMode = "logs";
                } else if (this.hoveredAction.equals("edit_permissions")) {
                    this.displayMode = "permissions";
                } else if (this.hoveredAction.equals("edit_flag")) {
                    this.displayMode = "flag";
                } else if (this.hoveredAction.equals("erase_flag")) {
                    for (int i = 0; i < 338; ++i) {
                        flag_pixels.put(i, -1);
                    }
                } else if (this.hoveredAction.equals("checkbox_invitation")) {
                    settingsData.put("open", (Boolean)settingsData.get("open") == false);
                } else if (this.hoveredAction.equals("checkbox_recruitment")) {
                    settingsData.put("recruitmentOpen", (Boolean)settingsData.get("recruitmentOpen") == false);
                } else if (this.hoveredAction.equals("checkbox_kick_war_recruit")) {
                    settingsData.put("doKickRecruitWarReason", (Boolean)settingsData.get("doKickRecruitWarReason") == false);
                } else if (this.hoveredAction.equals("sell_country") && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()) {
                    if (((Boolean)FactionGUI.factionInfos.get("isForSale")).booleanValue()) {
                        this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSellCountryPacket((String)FactionGUI.factionInfos.get("id"), -1)));
                        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket((String)FactionGUI.factionInfos.get("name"), true)));
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new FactionGUI((String)FactionGUI.factionInfos.get("name")));
                        return;
                    }
                    if (((Boolean)FactionGUI.factionInfos.get("canBeSale")).booleanValue()) {
                        this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                        Minecraft.func_71410_x().func_71373_a((GuiScreen)new SellCountryConfirmGui(this));
                    }
                } else if (this.hoveredAction.equals("disband_country") && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    Minecraft.func_71410_x().func_71373_a((GuiScreen)new DisbandConfirmGui(this));
                } else if (this.hoveredAction.contains("tag#")) {
                    String tagName;
                    ArrayList tags = (ArrayList)settingsData.get("tags");
                    if (tags.contains(tagName = this.hoveredAction.replaceAll("tag#", ""))) {
                        tags.remove(tagName);
                    } else if (tags.size() < 4) {
                        tags.add(tagName);
                    }
                    settingsData.put("tags", tags);
                } else if (this.hoveredAction.equals("save_flag") && FactionGUI.hasPermissions("flag")) {
                    ArrayList<Integer> imagePixels = new ArrayList<Integer>();
                    BufferedImage image = new BufferedImage(156, 78, 2);
                    Graphics2D graphics2D = image.createGraphics();
                    int x = 0;
                    int y = 0;
                    for (Map.Entry<Integer, Integer> pair : flag_pixels.entrySet()) {
                        imagePixels.add(pair.getValue());
                        graphics2D.setPaint(new Color(pair.getValue()));
                        graphics2D.fillRect(x * 6, y * 6, 6, 6);
                        x = x < 25 ? x + 1 : 0;
                        y = x == 0 ? y + 1 : y;
                    }
                    graphics2D.dispose();
                    String base64Img = SettingsGUI.encodeToString(image, "png").replace("\r\n", "");
                    HashMap<String, Object> dataToPacket = new HashMap<String, Object>();
                    dataToPacket.put("imageCode", base64Img);
                    dataToPacket.put("imagePixels", imagePixels);
                    dataToPacket.put("factionName", (String)FactionGUI.factionInfos.get("name"));
                    dataToPacket.put("mainFlagColor", SettingsGUI.mostCommon(imagePixels));
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSaveFlagDataPacket(dataToPacket)));
                } else if (this.hoveredAction.contains("permission#") && FactionGUI.hasPermissions("perms")) {
                    this.hasEditedPerms = true;
                    String permName = this.hoveredAction.split("#")[1];
                    String roleId = this.hoveredAction.split("#")[2];
                    String state = this.hoveredAction.split("#")[3];
                    LinkedTreeMap permInfos = (LinkedTreeMap)((LinkedTreeMap)settingsData.get("perms")).get((Object)permName);
                    List permissions = (List)permInfos.get((Object)"permissions");
                    if (state.equals("yes")) {
                        if (!permissions.contains(roleId)) {
                            permissions.add(roleId);
                        }
                    } else if (permissions.contains(roleId)) {
                        permissions.remove(roleId);
                    }
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    ((LinkedTreeMap)((LinkedTreeMap)settingsData.get("perms")).get((Object)permName)).put((Object)"permissions", (Object)permissions);
                }
                this.hoveredAction = "";
            }
            if (FactionGUI.hasPermissions("flag") && this.colorHovered != 0) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.colorSelected = this.colorHovered;
                this.colorHovered = 0;
            }
            if (FactionGUI.hasPermissions("flag") && mouseX >= this.guiLeft + 250 && mouseX <= this.guiLeft + 250 + 156 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 60 + 78) {
                this.mouseDrawing = true;
            }
        }
        this.descriptionText.func_73793_a(mouseX, mouseY, mouseButton);
        this.motdText.func_73793_a(mouseX, mouseY, mouseButton);
        this.entryText.func_73793_a(mouseX, mouseY, mouseButton);
        if (((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue() || ((Boolean)FactionGUI.factionInfos.get("isOp")).booleanValue()) {
            this.discordText.func_73793_a(mouseX, mouseY, mouseButton);
        }
        this.roleInput_Leader.func_73793_a(mouseX, mouseY - 4, mouseButton);
        this.roleInput_Officer.func_73793_a(mouseX, mouseY - 4, mouseButton);
        this.roleInput_Member.func_73793_a(mouseX, mouseY - 4, mouseButton);
        this.roleInput_Recruit.func_73793_a(mouseX, mouseY - 4, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public void func_73874_b() {
        if (loaded) {
            LinkedTreeMap rolesName = (LinkedTreeMap)settingsData.get("rolesName");
            rolesName.put((Object)"leader", (Object)this.roleInput_Leader.func_73781_b());
            rolesName.put((Object)"officer", (Object)this.roleInput_Officer.func_73781_b());
            rolesName.put((Object)"member", (Object)this.roleInput_Member.func_73781_b());
            rolesName.put((Object)"recruit", (Object)this.roleInput_Recruit.func_73781_b());
            HashMap<String, Object> hashMapForPacket = new HashMap<String, Object>();
            if (this.hasEditedPerms) {
                for (Map.Entry pair : ((LinkedTreeMap)settingsData.get("perms")).entrySet()) {
                    hashMapForPacket.put((String)pair.getKey(), (ArrayList)((LinkedTreeMap)pair.getValue()).get((Object)"permissions"));
                }
            }
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new FactionSaveSettingsDataPacket(this.cachedFactionName, this.descriptionText.func_73781_b(), this.motdText.func_73781_b(), this.entryText.func_73781_b(), this.discordText.func_73781_b(), (ArrayList)settingsData.get("tags"), (Boolean)settingsData.get("open"), (Boolean)settingsData.get("recruitmentOpen"), (LinkedTreeMap<String, String>)rolesName, hashMapForPacket, (Boolean)settingsData.get("doKickRecruitWarReason"))));
        }
        super.func_73874_b();
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.descriptionText.func_73802_a(typedChar, keyCode);
        this.motdText.func_73802_a(typedChar, keyCode);
        this.entryText.func_73802_a(typedChar, keyCode);
        this.discordText.func_73802_a(typedChar, keyCode);
        this.roleInput_Leader.func_73802_a(typedChar, keyCode);
        this.roleInput_Officer.func_73802_a(typedChar, keyCode);
        this.roleInput_Member.func_73802_a(typedChar, keyCode);
        this.roleInput_Recruit.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }
}

