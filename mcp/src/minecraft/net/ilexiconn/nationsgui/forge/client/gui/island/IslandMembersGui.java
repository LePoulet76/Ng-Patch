/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.island;

import com.google.gson.Gson;
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
import net.ilexiconn.nationsgui.forge.client.gui.island.IslandMainGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandExecuteTeamActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSaveTeamPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSetMemberPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamCreatePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamKickPlayerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamRemovePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandTeamsDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

@SideOnly(value=Side.CLIENT)
public class IslandMembersGui
extends GuiScreen {
    protected int xSize = 330;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarMembers;
    private GuiScrollBarFaction scrollBarVisitors;
    private String hoveredMember = "";
    private String selectedMember = "";
    public boolean helpOpened = false;
    public int helpSectionOffsetX = 0;
    public static ArrayList<HashMap<String, Object>> teamsInfos = new ArrayList();
    public static ArrayList<String> teamFlags = new ArrayList();
    private List<String> actions = new ArrayList<String>(Arrays.asList("clear", "reset_score", "tp", "clearspawn", "setkit"));
    public String tabMode = "members";
    public static HashMap<String, Object> selectedTeamInfos = null;
    public static HashMap<String, Object> hoveredTeamInfos = null;
    private String hoveredColor = "";
    private String hoveredScoreType = "";
    private String hoveredPlayer = "";
    private String hoveredFlag = "";
    private String hoveredAction = "";
    private boolean dropdownTeams = false;
    private boolean dropdownColors = false;
    private boolean dropdownScoreType = false;
    private boolean dropdownPlayers = false;
    private GuiTextField nameTextField;
    private GuiTextField maxPlayersTextField;
    private GuiTextField scoreTextField;
    private GuiTextField scoreGoalTextField;
    private GuiScrollBarFaction scrollBarTeams;
    private GuiScrollBarFaction scrollBarColors;
    private GuiScrollBarFaction scrollBarPlayers;
    private GuiScrollBarFaction scrollBarFlags;
    private GuiScrollBarFaction scrollBarActions;
    private ArrayList<String> availableColors = new ArrayList<String>(Arrays.asList("f", "4", "9", "e", "6", "d", "a", "2", "5", "d", "c"));
    private ArrayList<String> availableScoreTypes = new ArrayList<String>(Arrays.asList("kill", "capture_zone", "foot_goal"));
    public static boolean needUpdate = false;

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTeamsDataPacket(false)));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarMembers = new GuiScrollBarFaction(this.guiLeft + 156, this.guiTop + 37, 163);
        this.scrollBarVisitors = new GuiScrollBarFaction(this.guiLeft + 311, this.guiTop + 37, 163);
        this.scrollBarTeams = new GuiScrollBarFaction(this.guiLeft + 291, this.guiTop + 39, 39);
        this.scrollBarColors = new GuiScrollBarFaction(this.guiLeft + 84, this.guiTop + 61, 70);
        this.scrollBarFlags = new GuiScrollBarFaction(this.guiLeft + 174, this.guiTop + 116, 90);
        this.scrollBarActions = new GuiScrollBarFaction(this.guiLeft + 311, this.guiTop + 116, 90);
        this.scrollBarPlayers = new GuiScrollBarFaction(this.guiLeft + 311, this.guiTop + 110, 70);
        this.nameTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 93, this.guiTop + 47, 200, 10);
        this.nameTextField.func_73786_a(false);
        this.nameTextField.func_73804_f(16);
        this.maxPlayersTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 69, this.guiTop + 66, 23, 10);
        this.maxPlayersTextField.func_73786_a(false);
        this.maxPlayersTextField.func_73804_f(2);
        this.scoreTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 116, this.guiTop + 66, 25, 10);
        this.scoreTextField.func_73786_a(false);
        this.scoreTextField.func_73804_f(4);
        this.scoreGoalTextField = new GuiTextField(this.field_73886_k, this.guiLeft + 163, this.guiTop + 66, 25, 10);
        this.scoreGoalTextField.func_73786_a(false);
        this.scoreGoalTextField.func_73804_f(4);
    }

    public void func_73876_c() {
        this.nameTextField.func_73780_a();
        this.maxPlayersTextField.func_73780_a();
        this.scoreTextField.func_73780_a();
        this.scoreGoalTextField.func_73780_a();
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.hoveredColor = "";
        this.hoveredScoreType = "";
        this.hoveredPlayer = "";
        hoveredTeamInfos = null;
        this.hoveredFlag = "";
        this.hoveredAction = "";
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("island_members");
        this.helpSectionOffsetX = !this.helpOpened ? Math.max(this.helpSectionOffsetX - 1, 0) : Math.min(this.helpSectionOffsetX + 1, 107);
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 329 + this.helpSectionOffsetX, this.guiTop + 171, 69, 299, 23, 45, 512.0f, 512.0f, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 337 + this.helpSectionOffsetX), (float)(this.guiTop + 209), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 337 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 209)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"island.list.help.title"), this.guiLeft + 337 + this.helpSectionOffsetX, this.guiTop + 209, 0, 1.0f, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_members");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 329 - 107 + this.helpSectionOffsetX, this.guiTop + 8, 405, 0, 107, 232, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.title"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 20, 0, 1.3f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_1"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 50, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_2"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 60, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_3"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 70, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_4"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 80, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_5"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 95, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_6"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 105, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_7"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 115, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_8"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 125, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_9"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 135, 0, 1.0f, true, false);
        this.drawScaledString(I18n.func_135053_a((String)"island.members.help.text_10"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 145, 0, 1.0f, true, false);
        if (this.tabMode.equals("members")) {
            ClientEventHandler.STYLE.bindTexture("island_members");
        } else {
            ClientEventHandler.STYLE.bindTexture("island_teams");
        }
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        List<Object> tooltipToDraw = new ArrayList();
        ClientEventHandler.STYLE.bindTexture("island_main");
        for (int i = 0; i < IslandMainGui.TABS.size(); ++i) {
            GuiScreenTab type = IslandMainGui.TABS.get(i);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            int x = IslandMainGui.getTabIndex(IslandMainGui.TABS.get(i));
            if (((Object)((Object)this)).getClass() == type.getClassReferent()) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 23, 249, 29, 30, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
                continue;
            }
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23, this.guiTop + 47 + i * 31, 0, 249, 23, 30, 512.0f, 512.0f, false);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)3042);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.75f);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft - 23 + 4, this.guiTop + 47 + i * 31 + 5, x * 20, 331, 20, 20, 512.0f, 512.0f, false);
            GL11.glDisable((int)3042);
            if (mouseX < this.guiLeft - 23 || mouseX > this.guiLeft - 23 + 29 || mouseY < this.guiTop + 47 + i * 31 || mouseY > this.guiTop + 47 + 30 + i * 31) continue;
            tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.tab." + x)));
        }
        ClientEventHandler.STYLE.bindTexture("island_members");
        if (mouseX >= this.guiLeft + 316 && mouseX <= this.guiLeft + 316 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 316, this.guiTop - 8, 139, 259, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 316, this.guiTop - 8, 139, 249, 9, 10, 512.0f, 512.0f, false);
        }
        if (IslandMainGui.islandInfos != null && IslandMainGui.islandInfos.size() > 0) {
            int offsetX;
            GL11.glPushMatrix();
            Double titleOffsetY = (double)(this.guiTop + 45) + (double)this.field_73886_k.func_78256_a((String)IslandMainGui.islandInfos.get("name")) * 1.5;
            GL11.glTranslatef((float)(this.guiLeft + 14), (float)titleOffsetY.intValue(), (float)0.0f);
            GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-titleOffsetY.intValue()), (float)0.0f);
            this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, titleOffsetY.intValue(), 0xFFFFFF, 1.5f, false, false);
            GL11.glPopMatrix();
            ClientEventHandler.STYLE.bindTexture("island_teams");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 48, this.guiTop + 214, 48, 214, 112, 18, 512.0f, 512.0f, false);
            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 112 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 48, this.guiTop + 214, 139, 252, 112, 18, 512.0f, 512.0f, false);
            }
            if (this.tabMode.equals("members")) {
                this.drawScaledString(I18n.func_135053_a((String)"island.members.manager_teams"), this.guiLeft + 104, this.guiTop + 220, 0xFFFFFF, 1.0f, true, false);
            } else {
                this.drawScaledString(I18n.func_135053_a((String)"island.members.manager_members"), this.guiLeft + 104, this.guiTop + 220, 0xFFFFFF, 1.0f, true, false);
            }
            this.hoveredMember = "";
            if (this.tabMode.equals("members")) {
                Float offsetY;
                int l;
                this.drawScaledString(I18n.func_135053_a((String)"island.main.members"), this.guiLeft + 48, this.guiTop + 25, 0, 1.0f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 35, 107, 168);
                for (l = 0; l < ((ArrayList)IslandMainGui.islandInfos.get("members")).size(); ++l) {
                    offsetX = this.guiLeft + 49;
                    offsetY = Float.valueOf((float)(this.guiTop + 35 + l * 21) + this.getSlideMembers());
                    ClientEventHandler.STYLE.bindTexture("island_members");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 49, 35, 107, 21, 512.0f, 512.0f, false);
                    if (!((String)((ArrayList)IslandMainGui.islandInfos.get("members")).get(l)).replaceAll("\u00a7[0-9a-z]{1}", "").equals(IslandMainGui.islandInfos.get("creator")) && mouseX > offsetX && mouseX < offsetX + 107 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 21.0f) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue(), 0, 345, 107, 21, 512.0f, 512.0f, false);
                        this.hoveredMember = (String)((ArrayList)IslandMainGui.islandInfos.get("members")).get(l);
                    }
                    if (this.selectedMember.equals(((ArrayList)IslandMainGui.islandInfos.get("members")).get(l))) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue(), 0, 345, 107, 21, 512.0f, 512.0f, false);
                    }
                    this.drawScaledString((String)((ArrayList)IslandMainGui.islandInfos.get("members")).get(l), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 48 && mouseX < this.guiLeft + 48 + 113 && mouseY > this.guiTop + 34 && mouseY < this.guiTop + 34 + 170) {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }
                this.drawScaledString(I18n.func_135053_a((String)"island.main.visitors"), this.guiLeft + 203, this.guiTop + 25, 0, 1.0f, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 204, this.guiTop + 35, 107, 168);
                for (l = 0; l < ((ArrayList)IslandMainGui.islandInfos.get("visitors")).size(); ++l) {
                    offsetX = this.guiLeft + 204;
                    offsetY = Float.valueOf((float)(this.guiTop + 35 + l * 21) + this.getSlideVisitors());
                    ClientEventHandler.STYLE.bindTexture("island_members");
                    ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 49, 35, 107, 21, 512.0f, 512.0f, false);
                    if (mouseX > offsetX && mouseX < offsetX + 107 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 21.0f) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue(), 0, 345, 107, 21, 512.0f, 512.0f, false);
                        this.hoveredMember = (String)((ArrayList)IslandMainGui.islandInfos.get("visitors")).get(l);
                    }
                    if (this.selectedMember.equals(((ArrayList)IslandMainGui.islandInfos.get("visitors")).get(l))) {
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue(), 0, 345, 107, 21, 512.0f, 512.0f, false);
                    }
                    this.drawScaledString((String)((ArrayList)IslandMainGui.islandInfos.get("visitors")).get(l), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                }
                GUIUtils.endGLScissor();
                if (mouseX > this.guiLeft + 203 && mouseX < this.guiLeft + 203 + 113 && mouseY > this.guiTop + 34 && mouseY < this.guiTop + 106 + 170) {
                    this.scrollBarVisitors.draw(mouseX, mouseY);
                }
                ClientEventHandler.STYLE.bindTexture("island_members");
                if (mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 18) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 165, this.guiTop + 95, 0, 311, 34, 18, 512.0f, 512.0f, false);
                }
                if (mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 18) {
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 165, this.guiTop + 124, 34, 311, 34, 18, 512.0f, 512.0f, false);
                }
            } else if (this.tabMode.equals("teams")) {
                Float offsetY;
                if ((IslandMainGui.isPremium || teamsInfos.size() < 2) && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 20 && mouseY < this.guiTop + 20 + 16) {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 297, this.guiTop + 20, 346, 8, 19, 16, 512.0f, 512.0f, false);
                }
                if (!IslandMainGui.isPremium && teamsInfos.size() >= 2) {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 297, this.guiTop + 20, 84, 254, 19, 16, 512.0f, 512.0f, false);
                }
                if (selectedTeamInfos != null && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 16) {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 297, this.guiTop + 43, 346, 25, 19, 16, 512.0f, 512.0f, false);
                }
                if (selectedTeamInfos != null) {
                    int l;
                    this.drawScaledString("\u00a7" + selectedTeamInfos.get("color") + selectedTeamInfos.get("name"), this.guiLeft + 53, this.guiTop + 24, 0xFFFFFF, 1.0f, false, false);
                    Gui.func_73734_a((int)(this.guiLeft + 54), (int)(this.guiTop + 45), (int)(this.guiLeft + 54 + 12), (int)(this.guiTop + 45 + 12), (int)IslandMembersGui.getColorFromString((String)selectedTeamInfos.get("color")));
                    GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    this.nameTextField.func_73795_f();
                    if (this.nameTextField.func_73781_b().isEmpty() || needUpdate) {
                        this.nameTextField.func_73782_a((String)selectedTeamInfos.get("name"));
                    }
                    this.maxPlayersTextField.func_73795_f();
                    if (this.maxPlayersTextField.func_73781_b().isEmpty() || needUpdate) {
                        this.maxPlayersTextField.func_73782_a((String)selectedTeamInfos.get("maxPlayers"));
                    }
                    this.scoreTextField.func_73795_f();
                    if (this.scoreTextField.func_73781_b().isEmpty() || needUpdate) {
                        this.scoreTextField.func_73782_a((String)selectedTeamInfos.get("score"));
                    }
                    this.scoreGoalTextField.func_73795_f();
                    if (this.scoreGoalTextField.func_73781_b().isEmpty() || needUpdate) {
                        this.scoreGoalTextField.func_73782_a((String)selectedTeamInfos.get("scoreGoal"));
                    }
                    this.drawScaledString(I18n.func_135053_a((String)("island.members.scoreType." + selectedTeamInfos.get("scoreType"))), this.guiLeft + 194, this.guiTop + 66, 0xFFFFFF, 1.0f, false, false);
                    this.drawScaledString(I18n.func_135053_a((String)"island.members.label.players"), this.guiLeft + 53, this.guiTop + 96, 0xFFFFFF, 1.0f, false, false);
                    GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 113, 124, 96);
                    for (l = 0; l < teamFlags.size(); ++l) {
                        String flagString;
                        offsetX = this.guiLeft + 50;
                        offsetY = Float.valueOf((float)(this.guiTop + 113 + l * 21) + this.getSlideFlags());
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 50, 113, 106, 21, 512.0f, 512.0f, false);
                        if (((Boolean)((HashMap)selectedTeamInfos.get("flags")).get(teamFlags.get(l))).booleanValue()) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 107, offsetY.intValue() + 5, 0, 256, 10, 10, 512.0f, 512.0f, false);
                        } else {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 107, offsetY.intValue() + 5, 0, 268, 10, 10, 512.0f, 512.0f, false);
                        }
                        if (mouseX > offsetX + 107 && mouseX < offsetX + 107 + 10 && (float)mouseY > offsetY.floatValue() + 5.0f && (float)mouseY < offsetY.floatValue() + 5.0f + 10.0f) {
                            this.hoveredFlag = teamFlags.get(l);
                        }
                        if ((flagString = I18n.func_135053_a((String)("island.members.flags.label." + teamFlags.get(l)))).length() > 15) {
                            flagString = flagString.substring(0, 14) + "..";
                            if (mouseX > offsetX && mouseX < offsetX + 106 && mouseY > offsetY.intValue() && mouseY < offsetY.intValue() + 21) {
                                tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)("island.members.flags.label." + teamFlags.get(l))));
                            }
                        }
                        this.drawScaledString(flagString, offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 112 && mouseY > this.guiTop + 104 && mouseY < this.guiTop + 104 + 98) {
                        this.scrollBarFlags.draw(mouseX, mouseY);
                    }
                    GUIUtils.startGLScissor(this.guiLeft + 182, this.guiTop + 113, 129, 96);
                    for (l = 0; l < this.actions.size(); ++l) {
                        offsetX = this.guiLeft + 182;
                        offsetY = Float.valueOf((float)(this.guiTop + 113 + l * 21) + this.getSlideActions());
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 182, 113, 129, 21, 512.0f, 512.0f, false);
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 112, offsetY.intValue() + 2, 51, 253, 15, 15, 512.0f, 512.0f, false);
                        if (mouseX > offsetX + 112 && mouseX < offsetX + 112 + 15 && (float)mouseY > offsetY.floatValue() + 2.0f && (float)mouseY < offsetY.floatValue() + 2.0f + 15.0f) {
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 112, offsetY.intValue() + 2, 66, 253, 15, 15, 512.0f, 512.0f, false);
                            this.hoveredAction = this.actions.get(l);
                        }
                        this.drawScaledString(I18n.func_135053_a((String)("island.members.actions.label." + this.actions.get(l))), offsetX + 4, offsetY.intValue() + 6, 0xFFFFFF, 1.0f, false, false);
                    }
                    GUIUtils.endGLScissor();
                    if (mouseX > this.guiLeft + 181 && mouseX < this.guiLeft + 181 + 112 && mouseY > this.guiTop + 112 && mouseY < this.guiTop + 112 + 98) {
                        this.scrollBarActions.draw(mouseX, mouseY);
                    }
                    if (this.dropdownColors) {
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 58, 0, 292, 40, 76, 512.0f, 512.0f, false);
                        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 59, 34, 74);
                        for (l = 0; l < this.availableColors.size(); ++l) {
                            offsetX = this.guiLeft + 50;
                            offsetY = Float.valueOf((float)(this.guiTop + 59 + l * 15) + this.getSlideColors());
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 1, 293, 34, 15, 512.0f, 512.0f, false);
                            Gui.func_73734_a((int)(this.guiLeft + 60), (int)(offsetY.intValue() + 1), (int)(this.guiLeft + 60 + 12), (int)(offsetY.intValue() + 1 + 12), (int)IslandMembersGui.getColorFromString(this.availableColors.get(l)));
                            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            if (mouseX <= offsetX || mouseX >= offsetX + 34 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 15.0f)) continue;
                            this.hoveredColor = this.availableColors.get(l);
                        }
                        GUIUtils.endGLScissor();
                        this.scrollBarColors.draw(mouseX, mouseY);
                    }
                    if (this.dropdownScoreType) {
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        for (l = 0; l < this.availableScoreTypes.size(); ++l) {
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 190, this.guiTop + 77 + l * 15, 0, 403, 126, 16, 512.0f, 512.0f, false);
                            this.drawScaledString(I18n.func_135053_a((String)("island.members.scoreType." + this.availableScoreTypes.get(l))), this.guiLeft + 190 + 4, this.guiTop + 77 + l * 15 + 5, 0xFFFFFF, 1.0f, false, false);
                            if (mouseX <= this.guiLeft + 190 || mouseX >= this.guiLeft + 190 + 126 || mouseY <= this.guiTop + 77 + l * 15 || mouseY >= this.guiTop + 77 + l * 15 + 15) continue;
                            this.hoveredScoreType = this.availableScoreTypes.get(l);
                        }
                    }
                    if (this.dropdownPlayers) {
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 107, 181, 365, 267, 76, 512.0f, 512.0f, false);
                        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 108, 261, 74);
                        for (l = 0; l < ((ArrayList)selectedTeamInfos.get("players")).size(); ++l) {
                            offsetX = this.guiLeft + 50;
                            offsetY = Float.valueOf((float)(this.guiTop + 108 + l * 15) + this.getSlidePlayers());
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 182, 366, 261, 15, 512.0f, 512.0f, false);
                            this.drawScaledString((String)((ArrayList)selectedTeamInfos.get("players")).get(l), offsetX + 4, offsetY.intValue() + 4, 0xFFFFFF, 1.0f, false, false);
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 234, offsetY.intValue() + 1, 106, 253, 25, 12, 512.0f, 512.0f, false);
                            this.drawScaledString("Kick", offsetX + 248, offsetY.intValue() + 5, 0xFFFFFF, 0.7f, true, false);
                            if (mouseX <= offsetX + 234 || mouseX >= offsetX + 234 + 25 || !((float)mouseY > offsetY.floatValue() + 1.0f) || !((float)mouseY < offsetY.floatValue() + 1.0f + 12.0f)) continue;
                            this.hoveredPlayer = (String)((ArrayList)selectedTeamInfos.get("players")).get(l);
                        }
                        GUIUtils.endGLScissor();
                        this.scrollBarPlayers.draw(mouseX, mouseY);
                    }
                }
                if (this.dropdownTeams) {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 49, this.guiTop + 35, 86, 292, 247, 46, 512.0f, 512.0f, false);
                    GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 36, 241, 44);
                    for (int l = 0; l < teamsInfos.size(); ++l) {
                        offsetX = this.guiLeft + 50;
                        offsetY = Float.valueOf((float)(this.guiTop + 36 + l * 15) + this.getSlideTeams());
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 87, 293, 241, 15, 512.0f, 512.0f, false);
                        this.drawScaledString("\u00a7" + teamsInfos.get(l).get("color") + teamsInfos.get(l).get("name"), offsetX + 4, offsetY.intValue() + 4, 0xFFFFFF, 1.0f, false, false);
                        if (mouseX <= offsetX || mouseX >= offsetX + 241 || !((float)mouseY > offsetY.floatValue()) || !((float)mouseY < offsetY.floatValue() + 15.0f)) continue;
                        hoveredTeamInfos = teamsInfos.get(l);
                    }
                    GUIUtils.endGLScissor();
                    this.scrollBarTeams.draw(mouseX, mouseY);
                }
                if (selectedTeamInfos == null || mouseX > this.guiLeft + 203 && mouseX < this.guiLeft + 203 + 113 && mouseY > this.guiTop + 214 && mouseY < this.guiTop + 214 + 18) {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 203, this.guiTop + 214, 139, 270, 113, 18, 512.0f, 512.0f, false);
                }
                this.drawScaledString(I18n.func_135053_a((String)"island.global.save"), this.guiLeft + 259, this.guiTop + 220, 0xFFFFFF, 1.0f, true, false);
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
        needUpdate = false;
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
            if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("teams") && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 112 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                this.tabMode = this.tabMode.equals("members") ? "teams" : "members";
            }
            if (mouseX > this.guiLeft + 316 && mouseX < this.guiLeft + 316 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
            if (this.tabMode.equals("members")) {
                if (this.selectedMember != null && !this.selectedMember.isEmpty() && mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 18) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSetMemberPacket((String)IslandMainGui.islandInfos.get("id"), this.selectedMember, "promote")));
                    this.selectedMember = "";
                } else if (this.selectedMember != null && !this.selectedMember.isEmpty() && mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 18) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSetMemberPacket((String)IslandMainGui.islandInfos.get("id"), this.selectedMember, "demote")));
                    this.selectedMember = "";
                } else if (this.hoveredMember != null && !this.hoveredMember.isEmpty() && !this.hoveredMember.equals(IslandMainGui.islandInfos.get("creator"))) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    this.selectedMember = this.hoveredMember;
                }
            } else if (this.tabMode.equals("teams")) {
                if (!(this.dropdownTeams || this.dropdownColors || this.dropdownScoreType || this.dropdownPlayers)) {
                    this.nameTextField.func_73793_a(mouseX, mouseY, mouseButton);
                    this.maxPlayersTextField.func_73793_a(mouseX, mouseY, mouseButton);
                    this.scoreTextField.func_73793_a(mouseX, mouseY, mouseButton);
                    this.scoreGoalTextField.func_73793_a(mouseX, mouseY, mouseButton);
                }
                if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 247 && mouseY > this.guiTop + 20 && mouseY < this.guiTop + 20 + 16) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    boolean bl = this.dropdownTeams = !this.dropdownTeams;
                }
                if (!this.dropdownTeams && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 40 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 16) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    boolean bl = this.dropdownColors = !this.dropdownColors;
                }
                if (!this.dropdownTeams && mouseX > this.guiLeft + 190 && mouseX < this.guiLeft + 190 + 126 && mouseY > this.guiTop + 62 && mouseY < this.guiTop + 62 + 16) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    boolean bl = this.dropdownScoreType = !this.dropdownScoreType;
                }
                if (!(this.dropdownTeams || this.dropdownColors || this.dropdownScoreType || mouseX <= this.guiLeft + 49 || mouseX >= this.guiLeft + 49 + 267 || mouseY <= this.guiTop + 92 || mouseY >= this.guiTop + 92 + 16)) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    boolean bl = this.dropdownPlayers = !this.dropdownPlayers;
                }
                if ((IslandMainGui.isPremium || teamsInfos.size() < 2) && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 20 && mouseY < this.guiTop + 20 + 16) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTeamCreatePacket(teamsInfos.size() + 1, Integer.parseInt((String)IslandMainGui.islandInfos.get("id")))));
                }
                if (selectedTeamInfos != null && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 16) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTeamRemovePacket(Integer.parseInt((String)selectedTeamInfos.get("id")))));
                }
                if (this.dropdownColors && selectedTeamInfos != null && !this.hoveredColor.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 40 && mouseY > this.guiTop + 58 && mouseY < this.guiTop + 58 + 76) {
                    selectedTeamInfos.put("color", this.hoveredColor);
                    this.hoveredColor = "";
                    this.dropdownColors = false;
                }
                if (this.dropdownTeams && hoveredTeamInfos != null && mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 247 && mouseY > this.guiTop + 35 && mouseY < this.guiTop + 35 + 46) {
                    selectedTeamInfos = hoveredTeamInfos;
                    hoveredTeamInfos = null;
                    this.dropdownTeams = false;
                    needUpdate = true;
                }
                if (this.dropdownScoreType && selectedTeamInfos != null && !this.hoveredScoreType.isEmpty() && mouseX > this.guiLeft + 143 && mouseX < this.guiLeft + 143 + 173 && mouseY > this.guiTop + 77 && mouseY < this.guiTop + 77 + 46) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                    selectedTeamInfos.put("scoreType", this.hoveredScoreType);
                    this.hoveredScoreType = "";
                    this.dropdownScoreType = false;
                }
                if (this.dropdownPlayers && selectedTeamInfos != null && !this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 267 && mouseY > this.guiTop + 107 && mouseY < this.guiTop + 107 + 76) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandTeamKickPlayerPacket((String)IslandMainGui.islandInfos.get("id"), (String)selectedTeamInfos.get("id"), this.hoveredPlayer)));
                    ArrayList players = (ArrayList)selectedTeamInfos.get("players");
                    players.remove(this.hoveredPlayer);
                    selectedTeamInfos.put("players", players);
                    this.dropdownPlayers = false;
                    this.hoveredPlayer = "";
                }
                if (!(this.dropdownTeams || this.dropdownColors || this.dropdownScoreType || this.dropdownPlayers || selectedTeamInfos == null || this.hoveredFlag.isEmpty() || mouseX <= this.guiLeft + 49 || mouseX >= this.guiLeft + 49 + 130 || mouseY <= this.guiTop + 112 || mouseY >= this.guiTop + 112 + 98)) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                    ((HashMap)selectedTeamInfos.get("flags")).put(this.hoveredFlag, (Boolean)((HashMap)selectedTeamInfos.get("flags")).get(this.hoveredFlag) == false);
                }
                if (!(this.dropdownTeams || this.dropdownColors || this.dropdownScoreType || this.dropdownPlayers || selectedTeamInfos == null || this.hoveredAction.isEmpty() || mouseX <= this.guiLeft + 181 || mouseX >= this.guiLeft + 181 + 130 || mouseY <= this.guiTop + 112 || mouseY >= this.guiTop + 112 + 98)) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandExecuteTeamActionPacket((String)IslandMainGui.islandInfos.get("id"), (String)selectedTeamInfos.get("id"), this.hoveredAction)));
                }
                if (selectedTeamInfos != null && mouseX > this.guiLeft + 203 && mouseX < this.guiLeft + 203 + 113 && mouseY > this.guiTop + 214 && mouseY < this.guiTop + 214 + 18) {
                    this.field_73882_e.field_71416_A.func_77366_a("random.successful_hit", 1.0f, 1.0f);
                    HashMap data = (HashMap)selectedTeamInfos.clone();
                    data.put("flags", new Gson().toJson(data.get("flags")));
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IslandSaveTeamPacket(data)));
                }
            }
            if (!this.helpOpened && mouseX > this.guiLeft + 329 && mouseX < this.guiLeft + 329 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45) {
                this.helpOpened = true;
            } else if (this.helpOpened && mouseX > this.guiLeft + 329 + 107 && mouseX < this.guiLeft + 329 + 107 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45) {
                this.helpOpened = false;
            }
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    private float getSlideMembers() {
        return ((ArrayList)IslandMainGui.islandInfos.get("members")).size() > 4 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("members")).size() - 4) * 20) * this.scrollBarMembers.getSliderValue() : 0.0f;
    }

    private float getSlideVisitors() {
        return ((ArrayList)IslandMainGui.islandInfos.get("visitors")).size() > 4 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("visitors")).size() - 4) * 20) * this.scrollBarVisitors.getSliderValue() : 0.0f;
    }

    private float getSlideTeams() {
        return teamsInfos.size() > 3 ? (float)(-(teamsInfos.size() - 3) * 15) * this.scrollBarTeams.getSliderValue() : 0.0f;
    }

    private float getSlideColors() {
        return this.availableColors.size() > 5 ? (float)(-(this.availableColors.size() - 5) * 15) * this.scrollBarColors.getSliderValue() : 0.0f;
    }

    private float getSlidePlayers() {
        return ((ArrayList)selectedTeamInfos.get("players")).size() > 5 ? (float)(-(((ArrayList)selectedTeamInfos.get("players")).size() - 5) * 15) * this.scrollBarPlayers.getSliderValue() : 0.0f;
    }

    private float getSlideFlags() {
        return teamFlags.size() > 4 ? (float)(-(teamFlags.size() - 4) * 21) * this.scrollBarFlags.getSliderValue() : 0.0f;
    }

    private float getSlideActions() {
        return this.actions.size() > 4 ? (float)(-(this.actions.size() - 4) * 21) * this.scrollBarActions.getSliderValue() : 0.0f;
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        if (selectedTeamInfos != null) {
            if (this.nameTextField.func_73802_a(typedChar, keyCode)) {
                selectedTeamInfos.put("name", this.nameTextField.func_73781_b());
            }
            if (this.maxPlayersTextField.func_73802_a(typedChar, keyCode)) {
                selectedTeamInfos.put("maxPlayers", this.maxPlayersTextField.func_73781_b());
            }
            if (this.scoreTextField.func_73802_a(typedChar, keyCode)) {
                selectedTeamInfos.put("score", this.scoreTextField.func_73781_b());
            }
            if (this.scoreGoalTextField.func_73802_a(typedChar, keyCode)) {
                selectedTeamInfos.put("scoreGoal", this.scoreGoalTextField.func_73781_b());
            }
        }
        super.func_73869_a(typedChar, keyCode);
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

    public static int getColorFromString(String color) {
        switch (color) {
            case "4": {
                return -2880243;
            }
            case "9": {
                return -15638856;
            }
            case "e": {
                return -1515241;
            }
            case "6": {
                return -1859052;
            }
            case "d": {
                return -1371929;
            }
            case "a": {
                return -16451840;
            }
            case "2": {
                return -15752947;
            }
            case "5": {
                return -7402763;
            }
            case "c": {
                return -697509;
            }
        }
        return -1;
    }
}

