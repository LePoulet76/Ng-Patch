package net.ilexiconn.nationsgui.forge.client.gui.faction;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.CustomTextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarGeneric;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.SettingsGUI$2;
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
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Encoder;

public class SettingsGUI extends TabbedFactionGUI
{
    public static boolean loaded = false;
    public static boolean data_loaded = false;
    public static HashMap<String, Object> settingsData = new HashMap();
    public static List<String> tags = Arrays.asList(new String[] {"pvp", "build", "trade", "farm", "enterprises", "conquest", "pacifist", "aggresive"});
    public static List<String> rolesIds = Arrays.asList(new String[] {"70", "60", "50", "45", "40", "35", "20", "10"});
    public static TreeMap<Integer, Integer> flag_pixels = new TreeMap();
    public static HashMap<String, Integer> iconLogTypes = new SettingsGUI$1();
    public static HashMap<String, Integer> checkboxX = new SettingsGUI$2();
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
    private String cachedFactionName = "";
    private boolean hasEditedPerms = false;

    public SettingsGUI()
    {
        this.cachedFactionName = (String)FactionGUI.factionInfos.get("name");
        this.hasEditedPerms = false;
        loaded = false;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSettingsDataPacket((String)FactionGUI.factionInfos.get("name"))));
        int i;

        if (FactionGUI.factionInfos.get("flagPixels") != null && ((ArrayList)FactionGUI.factionInfos.get("flagPixels")).size() > 0)
        {
            for (i = 0; i < ((ArrayList)FactionGUI.factionInfos.get("flagPixels")).size(); ++i)
            {
                flag_pixels.put(Integer.valueOf(i), Integer.valueOf(((Double)((ArrayList)FactionGUI.factionInfos.get("flagPixels")).get(i)).intValue()));
            }
        }
        else
        {
            for (i = 0; i < 338; ++i)
            {
                flag_pixels.put(Integer.valueOf(i), Integer.valueOf(-1));
            }
        }

        this.flag_colorList.addAll(Arrays.asList(new Integer[] {Integer.valueOf(-793344), Integer.valueOf(-145910), Integer.valueOf(-815329), Integer.valueOf(-1351392), Integer.valueOf(-1891550), Integer.valueOf(-3931010), Integer.valueOf(-9553525), Integer.valueOf(-12235111), Integer.valueOf(-13930319), Integer.valueOf(-16345413), Integer.valueOf(-16740772), Integer.valueOf(-7488731), Integer.valueOf(-1), Integer.valueOf(-16448251), Integer.valueOf(-10471149), Integer.valueOf(-5789785), Integer.valueOf(-6274803), Integer.valueOf(-3761043)}));
    }

    public static String encodeToString(BufferedImage image, String type)
    {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(image, type, bos);
            byte[] e = bos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(e);
            bos.close();
        }
        catch (IOException var6)
        {
            var6.printStackTrace();
        }

        return imageString;
    }

    public static <T extends Object> T mostCommon(List<T> list)
    {
        HashMap map = new HashMap();
        Iterator max = list.iterator();

        while (max.hasNext())
        {
            Object t = max.next();
            Integer e = (Integer)map.get(t);
            map.put(t, Integer.valueOf(e == null ? 1 : e.intValue() + 1));
        }

        Entry max1 = null;
        Iterator t1 = map.entrySet().iterator();

        while (t1.hasNext())
        {
            Entry e1 = (Entry)t1.next();

            if (max1 == null || ((Integer)e1.getValue()).intValue() > ((Integer)max1.getValue()).intValue())
            {
                max1 = e1;
            }
        }

        return max1.getKey();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.scrollBarSettings = new GuiScrollBarGeneric((float)(this.guiLeft + 235), (float)(this.guiTop + 40), 181, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarLogs = new GuiScrollBarGeneric((float)(this.guiLeft + 451), (float)(this.guiTop + 40), 181, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        this.scrollBarPerms = new GuiScrollBarGeneric((float)(this.guiLeft + 443), (float)(this.guiTop + 67), 158, new ResourceLocation("nationsgui", "textures/gui/generic_ingame/cursor_faction.png"), 2, 8);
        String data = this.descriptionText != null ? this.descriptionText.getText() : "";
        this.descriptionText = new CustomTextAreaGUI(this.guiLeft + 48, this.guiTop + 110, 165, "georamaMedium", 25, 52);
        this.descriptionText.setMaxStringLength(225);
        this.descriptionText.setText(data);
        data = this.motdText != null ? this.motdText.getText() : "";
        this.motdText = new CustomTextAreaGUI(this.guiLeft + 48, this.guiTop + 180, 165, "georamaMedium", 25, 27);
        this.motdText.setMaxStringLength(110);
        this.motdText.setText(data);
        data = this.entryText != null ? this.entryText.getText() : "";
        this.entryText = new CustomTextAreaGUI(this.guiLeft + 48, this.guiTop + 222, 165, "georamaMedium", 25, 27);
        this.entryText.setMaxStringLength(110);
        this.entryText.setText(data);
        data = this.discordText != null ? this.discordText.getText() : "";
        this.discordText = new CustomInputFieldGUI(this.guiLeft + 48, this.guiTop + 265, 165, 12, "georamaMedium", 25);
        this.discordText.setMaxStringLength(35);
        this.discordText.setText(data);
        data = this.roleInput_Leader != null ? this.roleInput_Leader.getText() : "";
        this.roleInput_Leader = new CustomInputFieldGUI(this.guiLeft + 71, this.guiTop + 373, 45, 5, "georamaMedium", 22);
        this.roleInput_Leader.setMaxStringLength(15);
        this.roleInput_Leader.setText(data);
        data = this.roleInput_Officer != null ? this.roleInput_Officer.getText() : "";
        this.roleInput_Officer = new CustomInputFieldGUI(this.guiLeft + 71, this.guiTop + 386, 45, 5, "georamaMedium", 22);
        this.roleInput_Officer.setMaxStringLength(15);
        this.roleInput_Officer.setText(data);
        data = this.roleInput_Member != null ? this.roleInput_Member.getText() : "";
        this.roleInput_Member = new CustomInputFieldGUI(this.guiLeft + 172, this.guiTop + 373, 45, 5, "georamaMedium", 22);
        this.roleInput_Member.setMaxStringLength(15);
        this.roleInput_Member.setText(data);
        data = this.roleInput_Recruit != null ? this.roleInput_Recruit.getText() : "";
        this.roleInput_Recruit = new CustomInputFieldGUI(this.guiLeft + 172, this.guiTop + 386, 45, 5, "georamaMedium", 22);
        this.roleInput_Recruit.setMaxStringLength(15);
        this.roleInput_Recruit.setText(data);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTick)
    {
        if (loaded && !data_loaded && FactionGUI.factionInfos != null && !FactionGUI.factionInfos.isEmpty())
        {
            this.descriptionText.setText((String)settingsData.get("description"));
            this.descriptionText.setCursorPositionZero();
            this.motdText.setText((String)settingsData.get("motd"));
            this.motdText.setCursorPositionZero();
            this.entryText.setText((String)settingsData.get("entryMsg"));
            this.entryText.setCursorPositionZero();
            this.discordText.setText(!((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue() && !((Boolean)FactionGUI.factionInfos.get("isOp")).booleanValue() ? "***************" : (String)settingsData.get("discord"));
            this.discordText.setCursorPositionZero();
            this.roleInput_Leader.setText(((LinkedTreeMap)settingsData.get("rolesName")).containsKey("leader") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get("leader") : "");
            this.roleInput_Leader.setCursorPositionZero();
            this.roleInput_Officer.setText(((LinkedTreeMap)settingsData.get("rolesName")).containsKey("officer") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get("officer") : "");
            this.roleInput_Officer.setCursorPositionZero();
            this.roleInput_Member.setText(((LinkedTreeMap)settingsData.get("rolesName")).containsKey("member") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get("member") : "");
            this.roleInput_Member.setCursorPositionZero();
            this.roleInput_Recruit.setText(((LinkedTreeMap)settingsData.get("rolesName")).containsKey("recruit") ? (String)((LinkedTreeMap)settingsData.get("rolesName")).get("recruit") : "");
            this.roleInput_Recruit.setCursorPositionZero();
            data_loaded = true;
        }

        this.drawDefaultBackground();
        tooltipToDraw.clear();
        this.hoveredAction = "";
        this.colorHovered = 0;
        this.flag_pixelHoveredColorId = 0;
        this.flag_pixelHoveredId = -1;
        ClientEventHandler.STYLE.bindTexture("faction_global_2");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 30), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), (this.xSize - 30) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 30, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ModernGui.drawScaledStringCustomFont((String)FactionGUI.factionInfos.get("name"), (float)(this.guiLeft + 43), (float)(this.guiTop + 6), 10395075, 0.5F, "left", false, "georamaMedium", 32);
        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title"), (float)(this.guiLeft + 43), (float)(this.guiTop + 16), 16777215, 0.75F, "left", false, "georamaSemiBold", 32);

        if (loaded)
        {
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 235), (float)(this.guiTop + 40), (float)(275 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 181 * GUI_SCALE, 2, 181, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            GUIUtils.startGLScissor(this.guiLeft + 30, this.guiTop + 35, 200, 200);
            int offsetX = this.guiLeft + 30;
            Float offsetY = Float.valueOf((float)(this.guiTop + 35) + this.getSlideSettings());
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.general"), (float)(offsetX + 13), (float)(offsetY.intValue() + 9), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(offsetY.intValue() + 19), (float)(324 * GUI_SCALE), (float)(0 * GUI_SCALE), 188 * GUI_SCALE, 293 * GUI_SCALE, 188, 314, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.flag"), (float)(offsetX + 18), (float)(offsetY.intValue() + 23), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 32), (float)(0 * GUI_SCALE), (float)(269 * GUI_SCALE), 177 * GUI_SCALE, 28 * GUI_SCALE, 177, 28, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ClientProxy.loadCountryFlag((String)FactionGUI.factionInfos.get("name"));

            if (ClientProxy.flagsTexture.containsKey((String)FactionGUI.factionInfos.get("name")))
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, ((DynamicTexture)ClientProxy.flagsTexture.get((String)FactionGUI.factionInfos.get("name"))).getGlTextureId());
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 23), (float)(offsetY.intValue() + 36), 0.0F, 0.0F, 156, 78, 33, 20, 156.0F, 78.0F, false);
            }

            ClientEventHandler.STYLE.bindTexture("faction_settings");

            if (mouseX >= this.guiLeft + 123 && mouseX <= this.guiLeft + 123 + 62 && mouseY >= offsetY.intValue() + 42 && mouseY <= offsetY.intValue() + 42 + 10)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 123), (float)(offsetY.intValue() + 42), (float)(198 * GUI_SCALE), (float)(73 * GUI_SCALE), 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                this.hoveredAction = "edit_flag";
            }
            else
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 123), (float)(offsetY.intValue() + 42), (float)(198 * GUI_SCALE), (float)(49 * GUI_SCALE), 62 * GUI_SCALE, 10 * GUI_SCALE, 62, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.update_flag"), (float)(this.guiLeft + 123 + 31), (float)(offsetY.intValue() + 44), 2234425, 0.5F, "center", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.description"), (float)(offsetX + 18), (float)(offsetY.intValue() + 65), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 74), (float)(0 * GUI_SCALE), (float)(413 * GUI_SCALE), 177 * GUI_SCALE, 57 * GUI_SCALE, 177, 57, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomTextAreaGUI)this.descriptionText).posY = offsetY.intValue() + 74;
            this.descriptionText.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.motd"), (float)(offsetX + 18), (float)(offsetY.intValue() + 135), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 144), (float)(0 * GUI_SCALE), (float)(473 * GUI_SCALE), 177 * GUI_SCALE, 30 * GUI_SCALE, 177, 30, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomTextAreaGUI)this.motdText).posY = offsetY.intValue() + 144;
            this.motdText.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.entry_message"), (float)(offsetX + 18), (float)(offsetY.intValue() + 178), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 187), (float)(0 * GUI_SCALE), (float)(473 * GUI_SCALE), 177 * GUI_SCALE, 30 * GUI_SCALE, 177, 30, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomTextAreaGUI)this.entryText).posY = offsetY.intValue() + 187;
            this.entryText.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.discord"), (float)(offsetX + 18), (float)(offsetY.intValue() + 221), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 230), (float)(0 * GUI_SCALE), (float)(384 * GUI_SCALE), 150 * GUI_SCALE, 14 * GUI_SCALE, 150, 14, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomInputFieldGUI)this.discordText).posY = offsetY.intValue() + 230;
            this.discordText.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.tags"), (float)(offsetX + 18), (float)(offsetY.intValue() + 248), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            int permNameFont;
            int factionPermInfos;
            int index;

            for (permNameFont = 0; permNameFont < tags.size(); ++permNameFont)
            {
                factionPermInfos = permNameFont % 4;
                index = permNameFont / 4;
                ClientEventHandler.STYLE.bindTexture("faction_settings");

                if (mouseX >= this.guiLeft + 48 + factionPermInfos * 42 && mouseX <= this.guiLeft + 48 + factionPermInfos * 42 + 39 && mouseY >= offsetY.intValue() + 257 + index * 12 && mouseY <= offsetY.intValue() + 257 + index * 12 + 9)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48 + factionPermInfos * 42), (float)(offsetY.intValue() + 257 + index * 12), (float)(193 * GUI_SCALE), (float)(170 * GUI_SCALE), 39 * GUI_SCALE, 9 * GUI_SCALE, 39, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    this.hoveredAction = "tag#" + (String)tags.get(permNameFont);
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48 + factionPermInfos * 42), (float)(offsetY.intValue() + 257 + index * 12), (float)(193 * GUI_SCALE), (float)((((ArrayList)settingsData.get("tags")).contains(tags.get(permNameFont)) ? 181 : 192) * GUI_SCALE), 39 * GUI_SCALE, 9 * GUI_SCALE, 39, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.tags." + (String)tags.get(permNameFont)), (float)(this.guiLeft + 48 + factionPermInfos * 42 + 19), (float)(offsetY.intValue() + 257 + index * 12 + 2), 2234425, 0.5F, "center", false, "georamaMedium", 22);
            }

            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 285), (float)((((Boolean)settingsData.get("open")).booleanValue() ? 198 : 10) * GUI_SCALE), (float)((((Boolean)settingsData.get("open")).booleanValue() ? 0 : 169) * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.checkbox_invitation"), (float)(this.guiLeft + 60), (float)(offsetY.intValue() + 287), 16777215, 0.5F, "left", false, "georamaMedium", 25);

            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 9 && mouseY >= offsetY.intValue() + 285 && mouseY <= offsetY.intValue() + 285 + 9)
            {
                this.hoveredAction = "checkbox_invitation";
            }

            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 297), (float)((((Boolean)settingsData.get("recruitmentOpen")).booleanValue() ? 10 : 198) * GUI_SCALE), (float)((((Boolean)settingsData.get("recruitmentOpen")).booleanValue() ? 169 : 0) * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.checkbox_recruitment"), (float)(this.guiLeft + 60), (float)(offsetY.intValue() + 299), 16777215, 0.5F, "left", false, "georamaMedium", 25);

            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 9 && mouseY >= offsetY.intValue() + 297 && mouseY <= offsetY.intValue() + 297 + 9)
            {
                this.hoveredAction = "checkbox_recruitment";
            }

            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 309), (float)((((Boolean)settingsData.get("doKickRecruitWarReason")).booleanValue() ? 10 : 198) * GUI_SCALE), (float)((((Boolean)settingsData.get("doKickRecruitWarReason")).booleanValue() ? 169 : 0) * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.checkbox_kick_war_recruit"), (float)(this.guiLeft + 60), (float)(offsetY.intValue() + 311), 16777215, 0.5F, "left", false, "georamaMedium", 25);

            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 9 && mouseY >= offsetY.intValue() + 311 && mouseY <= offsetY.intValue() + 311 + 9)
            {
                this.hoveredAction = "checkbox_kick_war_recruit";
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.members"), (float)(offsetX + 13), (float)(offsetY.intValue() + 341), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 42), (float)(offsetY.intValue() + 351), (float)(0 * GUI_SCALE), (float)(300 * GUI_SCALE), 188 * GUI_SCALE, 58 * GUI_SCALE, 188, 58, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.role_names"), (float)(offsetX + 18), (float)(offsetY.intValue() + 355), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.leader"), (float)(this.guiLeft + 48), (float)(offsetY.intValue() + 367), 16777215, 0.5F, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 73), (float)(offsetY.intValue() + 366), (float)(0 * GUI_SCALE), (float)(401 * GUI_SCALE), 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomInputFieldGUI)this.roleInput_Leader).posY = offsetY.intValue() + 366 - 2;
            this.roleInput_Leader.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.officer"), (float)(this.guiLeft + 48), (float)(offsetY.intValue() + 379), 16777215, 0.5F, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 73), (float)(offsetY.intValue() + 378), (float)(0 * GUI_SCALE), (float)(401 * GUI_SCALE), 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomInputFieldGUI)this.roleInput_Officer).posY = offsetY.intValue() + 378 - 2;
            this.roleInput_Officer.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.member"), (float)(this.guiLeft + 149), (float)(offsetY.intValue() + 367), 16777215, 0.5F, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 174), (float)(offsetY.intValue() + 366), (float)(0 * GUI_SCALE), (float)(401 * GUI_SCALE), 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomInputFieldGUI)this.roleInput_Member).posY = offsetY.intValue() + 366 - 2;
            this.roleInput_Member.drawTextBox();
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.recruit"), (float)(this.guiLeft + 149), (float)(offsetY.intValue() + 379), 16777215, 0.5F, "left", false, "georamaMedium", 22);
            ClientEventHandler.STYLE.bindTexture("faction_settings");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 174), (float)(offsetY.intValue() + 378), (float)(0 * GUI_SCALE), (float)(401 * GUI_SCALE), 49 * GUI_SCALE, 9 * GUI_SCALE, 49, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            ((CustomInputFieldGUI)this.roleInput_Recruit).posY = offsetY.intValue() + 378 - 2;
            this.roleInput_Recruit.drawTextBox();
            ClientEventHandler.STYLE.bindTexture("faction_settings");

            if (!FactionGUI.hasPermissions("perms"))
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 392), (float)(193 * GUI_SCALE), (float)(230 * GUI_SCALE), 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.edit_permissions"), (float)(this.guiLeft + 48 + 37), (float)(offsetY.intValue() + 392 + 2), 3682124, 0.5F, "center", false, "georamaSemiBold", 22);
            }
            else
            {
                if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 74 && mouseY >= offsetY.intValue() + 392 && mouseY <= offsetY.intValue() + 392 + 10)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 392), (float)(193 * GUI_SCALE), (float)(217 * GUI_SCALE), 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    this.hoveredAction = "edit_permissions";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 48), (float)(offsetY.intValue() + 392), (float)(193 * GUI_SCALE), (float)(204 * GUI_SCALE), 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                }

                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.edit_permissions"), (float)(this.guiLeft + 48 + 37), (float)(offsetY.intValue() + 392 + 2), 2234425, 0.5F, "center", false, "georamaSemiBold", 22);
            }

            ClientEventHandler.STYLE.bindTexture("faction_settings");

            if (mouseX >= this.guiLeft + 149 && mouseX <= this.guiLeft + 149 + 74 && mouseY >= offsetY.intValue() + 392 && mouseY <= offsetY.intValue() + 392 + 10)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 149), (float)(offsetY.intValue() + 392), (float)(193 * GUI_SCALE), (float)(217 * GUI_SCALE), 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                this.hoveredAction = "show_logs";
            }
            else
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 149), (float)(offsetY.intValue() + 392), (float)(193 * GUI_SCALE), (float)(204 * GUI_SCALE), 74 * GUI_SCALE, 10 * GUI_SCALE, 74, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.show_logs"), (float)(this.guiLeft + 149 + 37), (float)(offsetY.intValue() + 392 + 2), 2234425, 0.5F, "center", false, "georamaSemiBold", 22);

            if (((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue())
            {
                ClientEventHandler.STYLE.bindTexture("faction_settings");

                if (((Boolean)FactionGUI.factionInfos.get("canBeSale")).booleanValue() && (mouseX < this.guiLeft + 43 || mouseX > this.guiLeft + 43 + 35 || mouseY < offsetY.intValue() + 416 || mouseY > offsetY.intValue() + 416 + 10))
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(offsetY.intValue() + 416), (float)(193 * GUI_SCALE), (float)(243 * GUI_SCALE), 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(((Boolean)FactionGUI.factionInfos.get("isForSale")).booleanValue() ? I18n.getString("faction.settings.label.stop_sell") : I18n.getString("faction.settings.label.sell"), (float)(this.guiLeft + 43 + 18), (float)(offsetY.intValue() + 416 + 2), 16777215, 0.5F, "center", false, "georamaSemiBold", 22);
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 43), (float)(offsetY.intValue() + 416), (float)(231 * GUI_SCALE), (float)(243 * GUI_SCALE), 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(((Boolean)FactionGUI.factionInfos.get("isForSale")).booleanValue() ? I18n.getString("faction.settings.label.stop_sell") : I18n.getString("faction.settings.label.sell"), (float)(this.guiLeft + 43 + 18), (float)(offsetY.intValue() + 416 + 2), 15017020, 0.5F, "center", false, "georamaSemiBold", 22);

                    if (!((Boolean)FactionGUI.factionInfos.get("canBeSale")).booleanValue())
                    {
                        if (mouseX >= this.guiLeft + 43 && mouseX <= this.guiLeft + 43 + 36 && mouseY >= offsetY.intValue() + 416 && mouseY <= offsetY.intValue() + 416 + 10)
                        {
                            tooltipToDraw.add(I18n.getString("faction.settings.cant_sell_1"));
                            tooltipToDraw.add(I18n.getString("faction.settings.cant_sell_2"));
                            tooltipToDraw.add(I18n.getString("faction.settings.cant_sell_3"));
                        }
                    }
                    else
                    {
                        this.hoveredAction = "sell_country";
                    }
                }

                ClientEventHandler.STYLE.bindTexture("faction_settings");

                if (mouseX >= this.guiLeft + 81 && mouseX <= this.guiLeft + 81 + 35 && mouseY >= offsetY.intValue() + 416 && mouseY <= offsetY.intValue() + 416 + 10)
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 81), (float)(offsetY.intValue() + 416), (float)(231 * GUI_SCALE), (float)(243 * GUI_SCALE), 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.disband"), (float)(this.guiLeft + 81 + 18), (float)(offsetY.intValue() + 416 + 2), 15017020, 0.5F, "center", false, "georamaSemiBold", 22);
                    this.hoveredAction = "disband_country";
                }
                else
                {
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 81), (float)(offsetY.intValue() + 416), (float)(193 * GUI_SCALE), (float)(243 * GUI_SCALE), 36 * GUI_SCALE, 10 * GUI_SCALE, 36, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.disband"), (float)(this.guiLeft + 81 + 18), (float)(offsetY.intValue() + 416 + 2), 16777215, 0.5F, "center", false, "georamaSemiBold", 22);
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX >= this.guiLeft + 30 && mouseX <= this.guiLeft + 30 + 200 && mouseY >= this.guiTop && mouseY <= this.guiTop + this.ySize)
            {
                this.scrollBarSettings.draw(mouseX, mouseY);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("faction_global_2");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 242), (float)(this.guiTop + 0), (float)(0 * GUI_SCALE), (float)(240 * GUI_SCALE), (this.xSize - 242) * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize - 242, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
            CFontRenderer var19;

            if (this.displayMode.equals("logs"))
            {
                var19 = ModernGui.getCustomFont("georamaMedium", Integer.valueOf(25));
                ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.logs"), (float)(this.guiLeft + 255), (float)(this.guiTop + 20), 16777215, 0.75F, "left", false, "georamaSemiBold", 26);
                ClientEventHandler.STYLE.bindTexture("faction_settings");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 451), (float)(this.guiTop + 40), (float)(275 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 181 * GUI_SCALE, 2, 181, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                GUIUtils.startGLScissor(this.guiLeft + 242, this.guiTop + 35, 209, 197);
                factionPermInfos = 0;

                for (Iterator var20 = ((ArrayList)settingsData.get("logs")).iterator(); var20.hasNext(); ++factionPermInfos)
                {
                    String it = (String)var20.next();
                    offsetX = this.guiLeft + 242;
                    offsetY = Float.valueOf((float)(this.guiTop + 35 + factionPermInfos * 23) + this.getSlideLogs());
                    Date pair = new Date(it.split("##").length == 3 ? Long.parseLong(it.split("##")[2]) : Long.parseLong(it.split("##")[3]));
                    SimpleDateFormat permName = new SimpleDateFormat("dd/MM/yyy HH:mm");
                    String infos = permName.format(pair);
                    ModernGui.drawScaledStringCustomFont(infos, (float)(offsetX + 18), (float)(offsetY.intValue() + 6), 10395075, 0.5F, "left", false, "georamaMedium", 25);
                    ModernGui.drawScaledStringCustomFont(it.split("##")[0], (float)(offsetX + 21 + (int)var19.getStringWidth(infos) / 2), (float)(offsetY.intValue() + 6), 7239406, 0.5F, "left", false, "georamaMedium", 24);
                    ClientEventHandler.STYLE.bindTexture("faction_settings");
                    ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 18), (float)(offsetY.intValue() + 14), (float)(((Integer)iconLogTypes.get(it.split("##")[1])).intValue() * GUI_SCALE), (float)(299 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.logs." + it.split("##")[1]).replace("#target#", it.split("##")[2]), (float)(offsetX + 18 + 8 + 2), (float)(offsetY.intValue() + 14), 16777215, 0.5F, "left", false, "georamaMedium", 30);
                }

                GUIUtils.endGLScissor();

                if (mouseX >= this.guiLeft + 242 && mouseX <= this.guiLeft + this.xSize && mouseY >= this.guiTop && mouseY <= this.guiTop + this.ySize)
                {
                    this.scrollBarLogs.draw(mouseX, mouseY);
                }
            }
            else
            {
                Iterator var23;
                Entry var24;

                if (this.displayMode.equals("flag"))
                {
                    ClientEventHandler.STYLE.bindTexture("faction_settings");
                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.edit_flag"), (float)(this.guiLeft + 255), (float)(this.guiTop + 20), 16777215, 0.75F, "left", false, "georamaSemiBold", 26);
                    permNameFont = 0;
                    factionPermInfos = 0;

                    for (index = 0; index < this.flag_colorList.size(); ++index)
                    {
                        int var21 = ((Integer)this.flag_colorList.get(index)).intValue();

                        if (mouseX >= this.guiLeft + 414 + permNameFont * 13 && mouseX <= this.guiLeft + 414 + permNameFont * 13 + 18 && mouseY >= this.guiTop + 62 + factionPermInfos * 13 && mouseY <= this.guiTop + 62 + factionPermInfos * 13 + 18)
                        {
                            this.colorHovered = var21;
                        }

                        Gui.drawRect(this.guiLeft + 414 + permNameFont * 13, this.guiTop + 62 + factionPermInfos * 13, this.guiLeft + 414 + permNameFont * 13 + 9, this.guiTop + 62 + factionPermInfos * 13 + 9, var21);

                        if (var21 == this.colorSelected)
                        {
                            ClientEventHandler.STYLE.bindTexture("faction_settings");
                            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 414 + permNameFont * 13 - 1), (float)(this.guiTop + 62 + factionPermInfos * 13 - 1), (float)(224 * GUI_SCALE), (float)(0 * GUI_SCALE), 11 * GUI_SCALE, 11 * GUI_SCALE, 11, 11, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        }

                        permNameFont = permNameFont < 2 ? permNameFont + 1 : 0;
                        factionPermInfos = permNameFont == 0 ? factionPermInfos + 1 : factionPermInfos;
                    }

                    permNameFont = 0;
                    factionPermInfos = 0;
                    index = 0;

                    if (flag_pixels.size() > 0)
                    {
                        for (var23 = flag_pixels.entrySet().iterator(); var23.hasNext(); ++index)
                        {
                            var24 = (Entry)var23.next();
                            int var25 = ((Integer)var24.getValue()).intValue();

                            if (FactionGUI.hasPermissions("flag") && mouseX >= this.guiLeft + 250 + permNameFont * 6 && mouseX <= this.guiLeft + 250 + permNameFont * 6 + 6 && mouseY >= this.guiTop + 60 + factionPermInfos * 6 && mouseY <= this.guiTop + 60 + factionPermInfos * 6 + 6)
                            {
                                this.flag_pixelHoveredId = index;
                            }

                            if (FactionGUI.hasPermissions("flag") && this.mouseDrawing && var25 != this.colorSelected && this.flag_pixelHoveredId == index)
                            {
                                flag_pixels.put(Integer.valueOf(index), Integer.valueOf(this.colorSelected));
                                var25 = this.colorSelected;
                            }

                            Gui.drawRect(this.guiLeft + 250 + permNameFont * 6, this.guiTop + 60 + factionPermInfos * 6, this.guiLeft + 250 + permNameFont * 6 + 6, this.guiTop + 60 + factionPermInfos * 6 + 6, var25);
                            permNameFont = permNameFont < 25 ? permNameFont + 1 : 0;
                            factionPermInfos = permNameFont == 0 ? factionPermInfos + 1 : factionPermInfos;
                        }
                    }

                    ClientEventHandler.STYLE.bindTexture("faction_settings");

                    if (mouseX >= this.guiLeft + 337 && mouseX <= this.guiLeft + 337 + 51 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 10)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 206), (float)(193 * GUI_SCALE), (float)(276 * GUI_SCALE), 51 * GUI_SCALE, 10 * GUI_SCALE, 51, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredAction = "save_flag";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 337), (float)(this.guiTop + 206), (float)(193 * GUI_SCALE), (float)(261 * GUI_SCALE), 51 * GUI_SCALE, 10 * GUI_SCALE, 51, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    }

                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.valid"), (float)(this.guiLeft + 337 + 25), (float)(this.guiTop + 206 + 2), 2234425, 0.5F, "center", false, "georamaSemiBold", 24);
                    ClientEventHandler.STYLE.bindTexture("faction_settings");

                    if (mouseX >= this.guiLeft + 392 && mouseX <= this.guiLeft + 392 + 57 && mouseY >= this.guiTop + 206 && mouseY <= this.guiTop + 206 + 13)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 392), (float)(this.guiTop + 206), (float)(248 * GUI_SCALE), (float)(276 * GUI_SCALE), 57 * GUI_SCALE, 13 * GUI_SCALE, 57, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        this.hoveredAction = "erase_flag";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 392), (float)(this.guiTop + 206), (float)(248 * GUI_SCALE), (float)(261 * GUI_SCALE), 57 * GUI_SCALE, 13 * GUI_SCALE, 57, 13, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    }

                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.erase"), (float)(this.guiLeft + 392 + 28), (float)(this.guiTop + 206 + 2), 15463162, 0.5F, "center", false, "georamaSemiBold", 24);
                }
                else if (this.displayMode.equals("permissions"))
                {
                    var19 = ModernGui.getCustomFont("georamaMedium", Integer.valueOf(28));
                    ClientEventHandler.STYLE.bindTexture("faction_settings");

                    if (mouseX >= this.guiLeft + 403 && mouseX <= this.guiLeft + 403 + 40 && mouseY >= this.guiTop + 9 && mouseY <= this.guiTop + 9 + 6)
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 8), (float)(206 * GUI_SCALE), (float)(34 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 9), 16777215, 0.5F, "left", false, "georamaSemiBold", 30);
                        this.hoveredAction = "back";
                    }
                    else
                    {
                        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 403), (float)(this.guiTop + 8), (float)(197 * GUI_SCALE), (float)(34 * GUI_SCALE), 6 * GUI_SCALE, 9 * GUI_SCALE, 6, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.label.back"), (float)(this.guiLeft + 412), (float)(this.guiTop + 9), 10395075, 0.5F, "left", false, "georamaSemiBold", 30);
                    }

                    ModernGui.drawScaledStringCustomFont(I18n.getString("faction.settings.title.edit_permissions"), (float)(this.guiLeft + 255), (float)(this.guiTop + 20), 16777215, 0.75F, "left", false, "georamaSemiBold", 26);
                    ClientEventHandler.STYLE.bindTexture("faction_settings");
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 256), (float)(this.guiTop + 63), (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), 193 * GUI_SCALE, 166 * GUI_SCALE, 193, 166, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 443), (float)(this.guiTop + 67), (float)(269 * GUI_SCALE), (float)(0 * GUI_SCALE), 2 * GUI_SCALE, 158 * GUI_SCALE, 2, 158, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                    GUIUtils.startGLScissor(this.guiLeft + 256, this.guiTop + 63, 188, 163);
                    LinkedTreeMap var22 = (LinkedTreeMap)settingsData.get("perms");
                    index = 0;

                    for (var23 = var22.entrySet().iterator(); var23.hasNext(); ++index)
                    {
                        offsetX = this.guiLeft + 256;
                        offsetY = Float.valueOf((float)(this.guiTop + 63 + index * 15) + this.getSlidePerms());
                        var24 = (Entry)var23.next();
                        String var26 = (String)var24.getKey();
                        LinkedTreeMap var27 = (LinkedTreeMap)var24.getValue();
                        ArrayList authorizedRoleIds = (ArrayList)var27.get("permissions");
                        ModernGui.drawScaledStringCustomFont(var26.substring(0, 1).toUpperCase() + var26.substring(1), (float)(offsetX + 4), (float)(offsetY.intValue() + 4), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                        ClientEventHandler.STYLE.bindTexture("faction_settings");
                        int iconOffsetX = offsetX + 4 + (int)var19.getStringWidth(var26.substring(0, 1).toUpperCase() + var26.substring(1)) / 2 + 2;
                        ModernGui.drawScaledCustomSizeModalRect((float)iconOffsetX, (float)(offsetY.intValue() + 3), (float)(201 * GUI_SCALE), (float)(308 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                        if (mouseX >= iconOffsetX && mouseX <= iconOffsetX + 8 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 8)
                        {
                            ModernGui.drawScaledCustomSizeModalRect((float)iconOffsetX, (float)(offsetY.intValue() + 3), (float)(193 * GUI_SCALE), (float)(308 * GUI_SCALE), 8 * GUI_SCALE, 8 * GUI_SCALE, 8, 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            tooltipToDraw.add(((String)var27.get("description")).substring(0, 1).toUpperCase() + ((String)var27.get("description")).substring(1));
                        }

                        int indexRole = 0;

                        for (Iterator var16 = rolesIds.iterator(); var16.hasNext(); ++indexRole)
                        {
                            String roleId = (String)var16.next();
                            boolean forbidden = false;

                            if (Integer.parseInt(roleId) <= 45)
                            {
                                if (Arrays.asList(new String[] {"assault", "join assault", "wars", "relations", "actions", "territory", "locations", "perms", "taxes", "access", "setwarp", "sethome", "settings", "kick", "chest_access"}).contains(var26))
                                {
                                    forbidden = true;
                                }
                            }
                            else if (Integer.parseInt(roleId) <= 50)
                            {
                                if (Arrays.asList(new String[] {"wars", "actions", "relations", "locations", "perms", "setwarp", "sethome", "settings", "kick", "chest_access", "assault", "join assault"}).contains(var26))
                                {
                                    forbidden = true;
                                }
                            }
                            else if (Integer.parseInt(roleId) == 70 && Arrays.asList(new String[] {"perms"}).contains(var26))
                            {
                                forbidden = true;
                            }

                            if (authorizedRoleIds.contains(roleId))
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_settings");
                                ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 83 + indexRole * 13), (float)(offsetY.intValue() + 3), (float)((checkboxX.containsKey(roleId) ? ((Integer)checkboxX.get(roleId)).intValue() : 0) * GUI_SCALE), (float)(169 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                if (!forbidden && mouseX >= offsetX + 83 + indexRole * 13 && mouseX <= offsetX + 83 + indexRole * 13 + 9 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 9 && (!roleId.equals("70") || ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()))
                                {
                                    this.hoveredAction = "permission#" + var26 + "#" + roleId + "#no";
                                }
                            }
                            else if (!forbidden)
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_settings");
                                ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 83 + indexRole * 13), (float)(offsetY.intValue() + 3), (float)(198 * GUI_SCALE), (float)(0 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                                if (mouseX >= offsetX + 83 + indexRole * 13 && mouseX <= offsetX + 83 + indexRole * 13 + 9 && mouseY >= offsetY.intValue() + 3 && mouseY <= offsetY.intValue() + 3 + 9 && (!roleId.equals("70") || ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue()))
                                {
                                    this.hoveredAction = "permission#" + var26 + "#" + roleId + "#yes";
                                }
                            }
                            else
                            {
                                ClientEventHandler.STYLE.bindTexture("faction_settings");
                                ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 83 + indexRole * 13), (float)(offsetY.intValue() + 3), (float)(238 * GUI_SCALE), (float)(0 * GUI_SCALE), 9 * GUI_SCALE, 9 * GUI_SCALE, 9, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
                            }
                        }
                    }

                    GUIUtils.endGLScissor();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float)(this.guiLeft + 340), (float)(this.guiTop + 60), 0.0F);
                    GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef((float)(-(this.guiLeft + 340)), (float)(-(this.guiTop + 60)), 0.0F);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#70#") ? "\u00a7l" : "") + (this.roleInput_Leader.getText().isEmpty() ? I18n.getString("faction.settings.label.leader") : this.roleInput_Leader.getText()), (float)(this.guiLeft + 340), (float)(this.guiTop + 60), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#60#") ? "\u00a7l" : "") + (this.roleInput_Officer.getText().isEmpty() ? I18n.getString("faction.settings.label.officer") : this.roleInput_Officer.getText()), (float)(this.guiLeft + 340), (float)(this.guiTop + 73), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#50#") ? "\u00a7l" : "") + (this.roleInput_Member.getText().isEmpty() ? I18n.getString("faction.settings.label.member") : this.roleInput_Member.getText()), (float)(this.guiLeft + 340), (float)(this.guiTop + 86), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#45#") ? "\u00a7l" : "") + (this.roleInput_Recruit.getText().isEmpty() ? I18n.getString("faction.settings.label.recruit") : this.roleInput_Recruit.getText()), (float)(this.guiLeft + 340), (float)(this.guiTop + 99), 16777215, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#40#") ? "\u00a7l" : "") + "Colony", (float)(this.guiLeft + 340), (float)(this.guiTop + 112), 16109642, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#35#") ? "\u00a7l" : "") + "Ally", (float)(this.guiLeft + 340), (float)(this.guiTop + 125), 11431662, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#20#") ? "\u00a7l" : "") + "Neutral", (float)(this.guiLeft + 340), (float)(this.guiTop + 138), 7239406, 0.5F, "left", false, "georamaMedium", 28);
                    ModernGui.drawScaledStringCustomFont((this.hoveredAction.contains("#10#") ? "\u00a7l" : "") + "Enemy", (float)(this.guiLeft + 340), (float)(this.guiTop + 151), 15017020, 0.5F, "left", false, "georamaMedium", 28);
                    GL11.glPopMatrix();
                    this.scrollBarPerms.draw(mouseX, mouseY);
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTick);
    }

    public void drawScreen(int mouseX, int mouseY) {}

    /**
     * Called when the mouse is moved or a mouse button is released.  Signature: (mouseX, mouseY, which) which==-1 is
     * mouseMove, which==0 or which==1 is mouseUp
     */
    protected void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            this.mouseDrawing = false;
        }

        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
    }

    private float getSlideSettings()
    {
        return -231.0F * this.scrollBarSettings.getSliderValue();
    }

    private float getSlidePerms()
    {
        return ((LinkedTreeMap)settingsData.get("perms")).size() > 11 ? (float)(-(((LinkedTreeMap)settingsData.get("perms")).size() - 11) * 15) * this.scrollBarPerms.getSliderValue() : 0.0F;
    }

    private float getSlideLogs()
    {
        return ((ArrayList)settingsData.get("logs")).size() > 8 ? (float)(-(((ArrayList)settingsData.get("logs")).size() - 8) * 23) * this.scrollBarLogs.getSliderValue() : 0.0F;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.descriptionText.updateCursorCounter();
        this.motdText.updateCursorCounter();
        this.entryText.updateCursorCounter();
        this.discordText.updateCursorCounter();
        this.roleInput_Leader.updateCursorCounter();
        this.roleInput_Officer.updateCursorCounter();
        this.roleInput_Member.updateCursorCounter();
        this.roleInput_Recruit.updateCursorCounter();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (!this.hoveredAction.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                if (!this.hoveredAction.equals("back") && !this.hoveredAction.equals("show_logs"))
                {
                    if (this.hoveredAction.equals("edit_permissions"))
                    {
                        this.displayMode = "permissions";
                    }
                    else if (this.hoveredAction.equals("edit_flag"))
                    {
                        this.displayMode = "flag";
                    }
                    else if (this.hoveredAction.equals("erase_flag"))
                    {
                        for (int permName = 0; permName < 338; ++permName)
                        {
                            flag_pixels.put(Integer.valueOf(permName), Integer.valueOf(-1));
                        }
                    }
                    else if (this.hoveredAction.equals("checkbox_invitation"))
                    {
                        settingsData.put("open", Boolean.valueOf(!((Boolean)settingsData.get("open")).booleanValue()));
                    }
                    else if (this.hoveredAction.equals("checkbox_recruitment"))
                    {
                        settingsData.put("recruitmentOpen", Boolean.valueOf(!((Boolean)settingsData.get("recruitmentOpen")).booleanValue()));
                    }
                    else if (this.hoveredAction.equals("checkbox_kick_war_recruit"))
                    {
                        settingsData.put("doKickRecruitWarReason", Boolean.valueOf(!((Boolean)settingsData.get("doKickRecruitWarReason")).booleanValue()));
                    }
                    else if (this.hoveredAction.equals("sell_country") && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue())
                    {
                        if (((Boolean)FactionGUI.factionInfos.get("isForSale")).booleanValue())
                        {
                            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSellCountryPacket((String)FactionGUI.factionInfos.get("id"), -1)));
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket((String)FactionGUI.factionInfos.get("name"), true)));
                            Minecraft.getMinecraft().displayGuiScreen(new FactionGUI((String)FactionGUI.factionInfos.get("name")));
                            return;
                        }

                        if (((Boolean)FactionGUI.factionInfos.get("canBeSale")).booleanValue())
                        {
                            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                            Minecraft.getMinecraft().displayGuiScreen(new SellCountryConfirmGui(this));
                        }
                    }
                    else if (this.hoveredAction.equals("disband_country") && ((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue())
                    {
                        this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                        Minecraft.getMinecraft().displayGuiScreen(new DisbandConfirmGui(this));
                    }
                    else
                    {
                        String roleId;
                        ArrayList var12;

                        if (this.hoveredAction.contains("tag#"))
                        {
                            var12 = (ArrayList)settingsData.get("tags");
                            roleId = this.hoveredAction.replaceAll("tag#", "");

                            if (var12.contains(roleId))
                            {
                                var12.remove(roleId);
                            }
                            else if (var12.size() < 4)
                            {
                                var12.add(roleId);
                            }

                            settingsData.put("tags", var12);
                        }
                        else if (this.hoveredAction.equals("save_flag") && FactionGUI.hasPermissions("flag"))
                        {
                            var12 = new ArrayList();
                            BufferedImage var14 = new BufferedImage(156, 78, 2);
                            Graphics2D var15 = var14.createGraphics();
                            int var16 = 0;
                            int var17 = 0;

                            for (Iterator it = flag_pixels.entrySet().iterator(); it.hasNext(); var17 = var16 == 0 ? var17 + 1 : var17)
                            {
                                Entry base64Img = (Entry)it.next();
                                var12.add(Integer.valueOf(((Integer)base64Img.getValue()).intValue()));
                                var15.setPaint(new Color(((Integer)base64Img.getValue()).intValue()));
                                var15.fillRect(var16 * 6, var17 * 6, 6, 6);
                                var16 = var16 < 25 ? var16 + 1 : 0;
                            }

                            var15.dispose();
                            String var18 = encodeToString(var14, "png").replace("\r\n", "");
                            HashMap dataToPacket = new HashMap();
                            dataToPacket.put("imageCode", var18);
                            dataToPacket.put("imagePixels", var12);
                            dataToPacket.put("factionName", (String)FactionGUI.factionInfos.get("name"));
                            dataToPacket.put("mainFlagColor", mostCommon(var12));
                            this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSaveFlagDataPacket(dataToPacket)));
                        }
                        else if (this.hoveredAction.contains("permission#") && FactionGUI.hasPermissions("perms"))
                        {
                            this.hasEditedPerms = true;
                            String var13 = this.hoveredAction.split("#")[1];
                            roleId = this.hoveredAction.split("#")[2];
                            String state = this.hoveredAction.split("#")[3];
                            LinkedTreeMap permInfos = (LinkedTreeMap)((LinkedTreeMap)settingsData.get("perms")).get(var13);
                            List permissions = (List)permInfos.get("permissions");

                            if (state.equals("yes"))
                            {
                                if (!permissions.contains(roleId))
                                {
                                    permissions.add(roleId);
                                }
                            }
                            else if (permissions.contains(roleId))
                            {
                                permissions.remove(roleId);
                            }

                            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                            ((LinkedTreeMap)((LinkedTreeMap)settingsData.get("perms")).get(var13)).put("permissions", permissions);
                        }
                    }
                }
                else
                {
                    this.displayMode = "logs";
                }

                this.hoveredAction = "";
            }

            if (FactionGUI.hasPermissions("flag") && this.colorHovered != 0)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.colorSelected = this.colorHovered;
                this.colorHovered = 0;
            }

            if (FactionGUI.hasPermissions("flag") && mouseX >= this.guiLeft + 250 && mouseX <= this.guiLeft + 250 + 156 && mouseY >= this.guiTop + 60 && mouseY <= this.guiTop + 60 + 78)
            {
                this.mouseDrawing = true;
            }
        }

        this.descriptionText.mouseClicked(mouseX, mouseY, mouseButton);
        this.motdText.mouseClicked(mouseX, mouseY, mouseButton);
        this.entryText.mouseClicked(mouseX, mouseY, mouseButton);

        if (((Boolean)FactionGUI.factionInfos.get("isLeader")).booleanValue() || ((Boolean)FactionGUI.factionInfos.get("isOp")).booleanValue())
        {
            this.discordText.mouseClicked(mouseX, mouseY, mouseButton);
        }

        this.roleInput_Leader.mouseClicked(mouseX, mouseY - 4, mouseButton);
        this.roleInput_Officer.mouseClicked(mouseX, mouseY - 4, mouseButton);
        this.roleInput_Member.mouseClicked(mouseX, mouseY - 4, mouseButton);
        this.roleInput_Recruit.mouseClicked(mouseX, mouseY - 4, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        if (loaded)
        {
            LinkedTreeMap rolesName = (LinkedTreeMap)settingsData.get("rolesName");
            rolesName.put("leader", this.roleInput_Leader.getText());
            rolesName.put("officer", this.roleInput_Officer.getText());
            rolesName.put("member", this.roleInput_Member.getText());
            rolesName.put("recruit", this.roleInput_Recruit.getText());
            HashMap hashMapForPacket = new HashMap();

            if (this.hasEditedPerms)
            {
                Iterator it = ((LinkedTreeMap)settingsData.get("perms")).entrySet().iterator();

                while (it.hasNext())
                {
                    Entry pair = (Entry)it.next();
                    hashMapForPacket.put((String)pair.getKey(), (ArrayList)((ArrayList)((LinkedTreeMap)pair.getValue()).get("permissions")));
                }
            }

            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionSaveSettingsDataPacket(this.cachedFactionName, this.descriptionText.getText(), this.motdText.getText(), this.entryText.getText(), this.discordText.getText(), (ArrayList)settingsData.get("tags"), ((Boolean)settingsData.get("open")).booleanValue(), ((Boolean)settingsData.get("recruitmentOpen")).booleanValue(), rolesName, hashMapForPacket, ((Boolean)settingsData.get("doKickRecruitWarReason")).booleanValue())));
        }

        super.onGuiClosed();
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        this.descriptionText.textboxKeyTyped(typedChar, keyCode);
        this.motdText.textboxKeyTyped(typedChar, keyCode);
        this.entryText.textboxKeyTyped(typedChar, keyCode);
        this.discordText.textboxKeyTyped(typedChar, keyCode);
        this.roleInput_Leader.textboxKeyTyped(typedChar, keyCode);
        this.roleInput_Officer.textboxKeyTyped(typedChar, keyCode);
        this.roleInput_Member.textboxKeyTyped(typedChar, keyCode);
        this.roleInput_Recruit.textboxKeyTyped(typedChar, keyCode);
        super.keyTyped(typedChar, keyCode);
    }
}
