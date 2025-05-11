/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.island.ConfirmResetGui;
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandExecuteActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSavePropertiesPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandPropertiesGui
extends GuiScreen {
    protected int xSize = 289;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarFlags;
    private GuiScrollBarFaction scrollBarActions;
    private String hoveredTime = "";
    private String hoveredWeather = "";
    private String hoveredFlag = "";
    private String hoveredAction = "";
    private String selectedWeatherMode = "";
    private String selectedTimeMode = "";
    private boolean expandWeather = false;
    private boolean expandTime = false;
    private boolean resetStarted = false;
    private List<String> biomes = new ArrayList<String>();
    private List<String> weatherModes = new ArrayList<String>();
    private List<String> timeModes = new ArrayList<String>();
    private List<String> actions = new ArrayList<String>();
    private int biomeOffsetXMax = 0;
    private String hoveredBiome = "";
    private String selectedBiome = "";
    private int biomeOffsetNumber = 0;
    private int biomeOffsetX = 0;
    private HashMap<String, Boolean> islandFlags = new HashMap();
    private ArrayList<String> flagList = new ArrayList();

    public IslandPropertiesGui() {
        this.biomes.addAll(Arrays.asList("plaine", "marais", "desert", "neige", "jungle"));
        this.weatherModes.addAll(Arrays.asList("storm", "rain", "sun", "cycle"));
        this.timeModes.addAll(Arrays.asList("day", "night", "cycle"));
        this.actions.addAll(Arrays.asList("kill_hostile", "kill_passive", "set_spawn", "kick_players", "tp_players", "respawn_all", "reset_score"));
        this.selectedBiome = (String)IslandMainGui.islandInfos.get("biome");
        this.biomeOffsetXMax = this.biomes.size() - 4;
        this.selectedWeatherMode = IslandMainGui.islandInfos.get("weatherMode") != null ? (String)IslandMainGui.islandInfos.get("weatherMode") : "cycle";
        this.selectedTimeMode = IslandMainGui.islandInfos.get("timeMode") != null ? (String)IslandMainGui.islandInfos.get("timeMode") : "cycle";
        this.islandFlags = IslandMainGui.islandFlags;
        this.flagList = (ArrayList)IslandMainGui.islandInfos.get("flags");
        this.flagList.remove("mob_hostile");
        this.flagList.remove("mob_passive");
        this.flagList.remove("pvp");
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarFlags = new GuiScrollBarFaction(this.guiLeft + 156, this.guiTop + 116, 90);
        this.scrollBarActions = new GuiScrollBarFaction(this.guiLeft + 270, this.guiTop + 116, 90);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        int l;
        int offsetX;
        int i;
        List<Object> tooltipToDraw = new ArrayList();
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_properties");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        ClientEventHandler.STYLE.bindTexture("island_main");
        for (int i2 = 0; i2 < IslandMainGui.TABS.size(); ++i2) {
            GuiScreenTab type = IslandMainGui.TABS.get(i2);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = IslandMainGui.getTabIndex(IslandMainGui.TABS.get(i2));
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i2 * 31, 23, 249, 29, 30, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i2 * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
                continue;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i2 * 31, 0, 249, 23, 30, 512.0f, 512.0f, false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i2 * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 47 + i2 * 31 || mouseY > this.guiTop + 47 + 30 + i2 * 31) continue;
            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.tab." + x)));
        }
        ClientEventHandler.STYLE.bindTexture("island_properties");
        if (mouseX >= this.guiLeft + 275 && mouseX <= this.guiLeft + 275 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 275, this.guiTop - 8, 121, 260, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 275, this.guiTop - 8, 121, 250, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5;
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_properties");
        this.drawScaledString(I18n.func_135053_a((String)"island.properties.biome"), this.guiLeft + 50, this.guiTop + 17, 0, 1.0f, false, false);
        ClientEventHandler.STYLE.bindTexture("island_properties");
        int biomeOffsetXTarget = 51 * this.biomeOffsetNumber;
        if (this.biomeOffsetX < biomeOffsetXTarget) {
            ++this.biomeOffsetX;
        } else if (this.biomeOffsetX > biomeOffsetXTarget) {
            --this.biomeOffsetX;
        }
        this.hoveredBiome = "";
        this.hoveredTime = "";
        this.hoveredWeather = "";
        this.hoveredFlag = "";
        this.hoveredAction = "";
        GUIUtils.startGLScissor(this.guiLeft + 60, this.guiTop + 27, 203, 50);
        for (i = 0; i < this.biomes.size(); ++i) {
            offsetX = this.guiLeft + 60 - this.biomeOffsetX + 51 * i;
            int offsetY = this.guiTop + 27;
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0 + 51 * i, 279, 50, 50, 512.0f, 512.0f, false);
            if (mouseX >= offsetX && mouseX <= offsetX + 50 && mouseY >= offsetY && mouseY <= offsetY + 50) {
                this.hoveredBiome = this.biomes.get(i);
            }
            if (!this.biomes.get(i).equals(this.selectedBiome)) continue;
            ClientEventHandler.STYLE.bindTexture("island_properties");
            GL11.glEnable((int)3042);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDisable((int)3008);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY, 0, 331, 50, 50, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            GL11.glEnable((int)2929);
            GL11.glDepthMask((boolean)true);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)3008);
        }
        GUIUtils.endGLScissor();
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 49), (int)(this.guiTop + 27), (int)(this.guiLeft + 49 + 225), (int)(this.guiTop + 27 + 50), (int)-1157627904);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 154, this.guiTop + 39, 293, 57, 16, 26, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 154 && mouseX <= this.guiLeft + 154 + 16 && mouseY >= this.guiTop + 39 && mouseY <= this.guiTop + 39 + 26) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.permission_required"));
            }
        } else {
            ClientEventHandler.STYLE.bindTexture("island_properties");
            if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 10 && mouseY >= this.guiTop + 27 && mouseY <= this.guiTop + 27 + 50) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 27, 51, 331, 10, 50, 512.0f, 512.0f, false);
            } else if (mouseX >= this.guiLeft + 264 && mouseX <= this.guiLeft + 264 + 10 && mouseY >= this.guiTop + 27 && mouseY <= this.guiTop + 27 + 50) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 264, this.guiTop + 27, 63, 331, 10, 50, 512.0f, 512.0f, false);
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.properties.flags"), this.guiLeft + 49, this.guiTop + 102, 0, 1.0f, false, false);
        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 112, 106, 96);
        for (l = 0; l < this.flagList.size(); ++l) {
            String flagString;
            offsetX = this.guiLeft + 50;
            Float offsetY = Float.valueOf((float)(this.guiTop + 112 + l * 21) + this.getSlideFlags());
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 50, 112, 106, 21, 512.0f, 512.0f, false);
            if (this.islandFlags.get(this.flagList.get(l)).booleanValue()) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 89, offsetY.intValue() + 5, 0, 256, 10, 10, 512.0f, 512.0f, false);
            } else {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 89, offsetY.intValue() + 5, 0, 268, 10, 10, 512.0f, 512.0f, false);
            }
            if (mouseX > offsetX + 89 && mouseX < offsetX + 89 + 10 && (float)mouseY > offsetY.floatValue() + 5.0f && (float)mouseY < offsetY.floatValue() + 5.0f + 10.0f) {
                this.hoveredFlag = this.flagList.get(l);
            }
            if ((flagString = I18n.func_135053_a((String)("island.properties.flags.label." + this.flagList.get(l)))).length() > 15) {
                flagString = flagString.substring(0, 14) + "..";
                if (mouseX > offsetX && mouseX < offsetX + 106 && mouseY > offsetY.intValue() && mouseY < offsetY.intValue() + 21) {
                    tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.properties.flags.label." + this.flagList.get(l))));
                }
            }
            this.drawScaledString(flagString, offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
        }
        GUIUtils.endGLScissor();
        if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 112 && mouseY > this.guiTop + 104 && mouseY < this.guiTop + 104 + 98) {
            this.scrollBarFlags.draw(mouseX, mouseY);
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 49), (int)(this.guiTop + 111), (int)(this.guiLeft + 49 + 112), (int)(this.guiTop + 111 + 98), (int)-1157627904);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 97, this.guiTop + 145, 293, 57, 16, 26, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 97 && mouseX <= this.guiLeft + 97 + 16 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 26) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.permission_required"));
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.properties.actions"), this.guiLeft + 162, this.guiTop + 102, 0, 1.0f, false, false);
        GUIUtils.startGLScissor(this.guiLeft + 163, this.guiTop + 112, 107, 96);
        for (l = 0; l < this.actions.size(); ++l) {
            offsetX = this.guiLeft + 163;
            Float offsetY = Float.valueOf((float)(this.guiTop + 112 + l * 21) + this.getSlideActions());
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 50, 112, 107, 21, 512.0f, 512.0f, false);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 90, offsetY.intValue() + 2, 138, 248, 15, 15, 512.0f, 512.0f, false);
            if (mouseX > offsetX + 90 && mouseX < offsetX + 90 + 15 && (float)mouseY > offsetY.floatValue() + 2.0f && (float)mouseY < offsetY.floatValue() + 2.0f + 15.0f) {
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 90, offsetY.intValue() + 2, 153, 248, 15, 15, 512.0f, 512.0f, false);
                this.hoveredAction = this.actions.get(l);
            }
            this.drawScaledString(I18n.func_135053_a((String)("island.properties.actions.label." + this.actions.get(l))), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
        }
        GUIUtils.endGLScissor();
        if (mouseX > this.guiLeft + 162 && mouseX < this.guiLeft + 162 + 113 && mouseY > this.guiTop + 111 && mouseY < this.guiTop + 111 + 98) {
            this.scrollBarActions.draw(mouseX, mouseY);
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("execute_actions")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 162), (int)(this.guiTop + 111), (int)(this.guiLeft + 162 + 112), (int)(this.guiTop + 111 + 98), (int)-1157627904);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 210, this.guiTop + 145, 293, 57, 16, 26, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 210 && mouseX <= this.guiLeft + 210 + 16 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 26) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.permission_required"));
            }
        }
        ClientEventHandler.STYLE.bindTexture("island_properties");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 86, 292, 6 + this.timeModes.indexOf(this.selectedTimeMode) * 10, 10, 10, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)("island.properties.time.label." + this.selectedTimeMode)), this.guiLeft + 67, this.guiTop + 87, 0xFFFFFF, 1.0f, false, false);
        if (this.expandTime) {
            for (i = 0; i < this.timeModes.size(); ++i) {
                ClientEventHandler.STYLE.bindTexture("island_properties");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 98 + 15 * i, 137, 331, 65, 16, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 55, this.guiTop + 98 + 15 * i + 3, 292, 6 + i * 10, 10, 10, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("island.properties.time.label." + this.timeModes.get(i))), this.guiLeft + 68, this.guiTop + 98 + 15 * i + 3 + 2, 0xFFFFFF, 1.0f, false, false);
                if (mouseX < this.guiLeft + 49 || mouseX > this.guiLeft + 49 + 65 || mouseY < this.guiTop + 98 + 15 * i || mouseY > this.guiTop + 98 + 15 * i + 16) continue;
                this.hoveredTime = this.timeModes.get(i);
            }
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 49), (int)(this.guiTop + 83), (int)(this.guiLeft + 49 + 65), (int)(this.guiTop + 83 + 16), (int)-1157627904);
        }
        ClientEventHandler.STYLE.bindTexture("island_properties");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 121, this.guiTop + 85, 305, 6 + this.weatherModes.indexOf(this.selectedWeatherMode) * 12, 17, 12, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)("island.properties.time.label." + this.selectedTimeMode)), this.guiLeft + 67, this.guiTop + 87, 0xFFFFFF, 1.0f, false, false);
        if (this.expandWeather) {
            for (i = 0; i < this.weatherModes.size(); ++i) {
                ClientEventHandler.STYLE.bindTexture("island_properties");
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 115, this.guiTop + 98 + 15 * i, 89, 331, 46, 16, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 121, this.guiTop + 98 + 15 * i + 2, 305, 6 + i * 12, 17, 12, 512.0f, 512.0f, false);
                if (mouseX < this.guiLeft + 115 || mouseX > this.guiLeft + 115 + 46 || mouseY < this.guiTop + 98 + 15 * i || mouseY > this.guiTop + 98 + 15 * i + 16) continue;
                this.hoveredWeather = this.weatherModes.get(i);
            }
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 115), (int)(this.guiTop + 83), (int)(this.guiLeft + 115 + 46), (int)(this.guiTop + 83 + 16), (int)-1157627904);
        }
        ClientEventHandler.STYLE.bindTexture("island_properties");
        if (this.islandFlags.get("mob_hostile").booleanValue()) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 167, this.guiTop + 86, 0, 256, 10, 10, 512.0f, 512.0f, false);
        }
        if (mouseX >= this.guiLeft + 167 && mouseX <= this.guiLeft + 167 + 10 && mouseY >= this.guiTop + 86 && mouseY <= this.guiTop + 86 + 10) {
            this.hoveredFlag = "mob_hostile";
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 162), (int)(this.guiTop + 83), (int)(this.guiLeft + 162 + 35), (int)(this.guiTop + 83 + 16), (int)-1157627904);
        }
        ClientEventHandler.STYLE.bindTexture("island_properties");
        if (this.islandFlags.get("mob_passive").booleanValue()) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 203, this.guiTop + 86, 0, 256, 10, 10, 512.0f, 512.0f, false);
        }
        if (mouseX >= this.guiLeft + 203 && mouseX <= this.guiLeft + 203 + 10 && mouseY >= this.guiTop + 86 && mouseY <= this.guiTop + 86 + 10) {
            this.hoveredFlag = "mob_passive";
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 198), (int)(this.guiTop + 83), (int)(this.guiLeft + 198 + 35), (int)(this.guiTop + 83 + 16), (int)-1157627904);
        }
        ClientEventHandler.STYLE.bindTexture("island_properties");
        if (this.islandFlags.get("pvp").booleanValue()) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 239, this.guiTop + 86, 0, 256, 10, 10, 512.0f, 512.0f, false);
        }
        if (mouseX >= this.guiLeft + 239 && mouseX <= this.guiLeft + 239 + 10 && mouseY >= this.guiTop + 86 && mouseY <= this.guiTop + 86 + 10) {
            this.hoveredFlag = "pvp";
        }
        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags")) {
            IslandPropertiesGui.func_73734_a((int)(this.guiLeft + 234), (int)(this.guiTop + 83), (int)(this.guiLeft + 234 + 40), (int)(this.guiTop + 83 + 16), (int)-1157627904);
        }
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (this.resetStarted || System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L || !((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("reset_island") || mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 48, this.guiTop + 214, 0, 256, 113, 18, 512.0f, 512.0f, false);
            if ((this.resetStarted || System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L) && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"island.global.cooldown_1"), I18n.func_135053_a((String)"island.global.cooldown_2"));
            }
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.properties.reset"), this.guiLeft + 104, this.guiTop + 219, 0xFFFFFF, 1.0f, true, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");
        if (mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 163, this.guiTop + 214, 113, 256, 113, 18, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"island.global.save"), this.guiLeft + 219, this.guiTop + 219, 0xFFFFFF, 1.0f, true, false);
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font) {
        if (!par1List.isEmpty()) {
            GL11.glDisable((int)32826);
            RenderHelper.func_74518_a();
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            int k = 0;
            for (String s : par1List) {
                int l = font.func_78256_a(s);
                if (l <= k) continue;
                k = l;
            }
            int i1 = par2 + 12;
            int j1 = par3 - 12;
            int k1 = 8;
            if (par1List.size() > 1) {
                k1 += 2 + (par1List.size() - 1) * 10;
            }
            if (i1 + k > this.field_73880_f) {
                i1 -= 28 + k;
            }
            if (j1 + k1 + 6 > this.field_73881_g) {
                j1 = this.field_73881_g - k1 - 6;
            }
            this.field_73735_i = 300.0f;
            this.itemRenderer.field_77023_b = 300.0f;
            int l1 = -267386864;
            this.func_73733_a(i1 - 3, j1 - 4, i1 + k + 3, j1 - 3, l1, l1);
            this.func_73733_a(i1 - 3, j1 + k1 + 3, i1 + k + 3, j1 + k1 + 4, l1, l1);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 - 4, j1 - 3, i1 - 3, j1 + k1 + 3, l1, l1);
            this.func_73733_a(i1 + k + 3, j1 - 3, i1 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 0x505000FF;
            int j2 = (i2 & 0xFEFEFE) >> 1 | i2 & 0xFF000000;
            this.func_73733_a(i1 - 3, j1 - 3 + 1, i1 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 + k + 2, j1 - 3 + 1, i1 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.func_73733_a(i1 - 3, j1 - 3, i1 + k + 3, j1 - 3 + 1, i2, i2);
            this.func_73733_a(i1 - 3, j1 + k1 + 2, i1 + k + 3, j1 + k1 + 3, j2, j2);
            for (int k2 = 0; k2 < par1List.size(); ++k2) {
                String s1 = (String)par1List.get(k2);
                font.func_78261_a(s1, i1, j1, -1);
                if (k2 == 0) {
                    j1 += 2;
                }
                j1 += 10;
            }
            this.field_73735_i = 0.0f;
            this.itemRenderer.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            for (int i = 0; i < IslandMainGui.TABS.size(); ++i) {
                GuiScreenTab type = IslandMainGui.TABS.get(i);
                if (mouseX < this.guiLeft - 20 || mouseX > this.guiLeft + 3 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                if (((Object)((Object)this)).getClass() == type.getClassReferent()) continue;
                try {
                    type.call();
                    continue;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mouseX > this.guiLeft + 275 && mouseX < this.guiLeft + 275 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 10 && mouseY > this.guiTop + 27 && mouseY < this.guiTop + 27 + 50) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.biomeOffsetNumber = this.biomeOffsetNumber - 1 > 0 ? this.biomeOffsetNumber - 1 : 0;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") && mouseX > this.guiLeft + 264 && mouseX < this.guiLeft + 264 + 10 && mouseY > this.guiTop + 27 && mouseY < this.guiTop + 27 + 50) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.biomeOffsetNumber = this.biomeOffsetNumber + 1 < this.biomeOffsetXMax ? this.biomeOffsetNumber + 1 : this.biomeOffsetXMax;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") && !this.hoveredBiome.isEmpty() && mouseX > this.guiLeft + 60 && mouseX < this.guiLeft + 60 + 203 && mouseY > this.guiTop + 27 && mouseY < this.guiTop + 27 + 50) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.selectedBiome = this.hoveredBiome;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time") && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 65 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 16) {
                this.expandTime = !this.expandTime;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather") && mouseX > this.guiLeft + 115 && mouseX < this.guiLeft + 115 + 46 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 16) {
                this.expandWeather = !this.expandWeather;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time") && !this.hoveredTime.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 65 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 61) {
                this.selectedTimeMode = this.hoveredTime;
                this.expandTime = false;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather") && !this.hoveredWeather.isEmpty() && mouseX > this.guiLeft + 115 && mouseX < this.guiLeft + 115 + 65 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 77) {
                this.selectedWeatherMode = this.hoveredWeather;
                this.expandWeather = false;
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags") && !this.hoveredFlag.isEmpty()) {
                this.islandFlags.put(this.hoveredFlag, this.islandFlags.get(this.hoveredFlag) == false);
            } else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("execute_actions") && !this.hoveredAction.isEmpty()) {
                this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandExecuteActionPacket((String)IslandMainGui.islandInfos.get("id"), this.hoveredAction)));
            } else if ((((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") || ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time") || ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather") || ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags")) && mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSavePropertiesPacket((String)IslandMainGui.islandInfos.get("id"), this.islandFlags, this.selectedTimeMode, this.selectedWeatherMode, this.selectedBiome)));
            } else if (!this.resetStarted && System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) > 300000L && ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("reset_island") && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                this.resetStarted = true;
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new ConfirmResetGui(this));
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlideFlags() {
        return ((ArrayList)IslandMainGui.islandInfos.get("flags")).size() > 4 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("flags")).size() - 4) * 21) * this.scrollBarFlags.getSliderValue() : 0.0f;
    }

    private float getSlideActions() {
        return this.actions.size() > 4 ? (float)(-(this.actions.size() - 4) * 21) * this.scrollBarActions.getSliderValue() : 0.0f;
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)this.field_73886_k.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        this.field_73886_k.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public boolean func_73868_f() {
        return false;
    }

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}

