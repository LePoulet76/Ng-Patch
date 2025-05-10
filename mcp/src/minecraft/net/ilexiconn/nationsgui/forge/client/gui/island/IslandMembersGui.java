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
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
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
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandMembersGui extends GuiScreen
{
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
    private List<String> actions = new ArrayList(Arrays.asList(new String[] {"clear", "reset_score", "tp", "clearspawn", "setkit"}));
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
    private ArrayList<String> availableColors = new ArrayList(Arrays.asList(new String[] {"f", "4", "9", "e", "6", "d", "a", "2", "5", "d", "c"}));
    private ArrayList<String> availableScoreTypes = new ArrayList(Arrays.asList(new String[] {"kill", "capture_zone", "foot_goal"}));
    public static boolean needUpdate = false;

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTeamsDataPacket(false)));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBarMembers = new GuiScrollBarFaction((float)(this.guiLeft + 156), (float)(this.guiTop + 37), 163);
        this.scrollBarVisitors = new GuiScrollBarFaction((float)(this.guiLeft + 311), (float)(this.guiTop + 37), 163);
        this.scrollBarTeams = new GuiScrollBarFaction((float)(this.guiLeft + 291), (float)(this.guiTop + 39), 39);
        this.scrollBarColors = new GuiScrollBarFaction((float)(this.guiLeft + 84), (float)(this.guiTop + 61), 70);
        this.scrollBarFlags = new GuiScrollBarFaction((float)(this.guiLeft + 174), (float)(this.guiTop + 116), 90);
        this.scrollBarActions = new GuiScrollBarFaction((float)(this.guiLeft + 311), (float)(this.guiTop + 116), 90);
        this.scrollBarPlayers = new GuiScrollBarFaction((float)(this.guiLeft + 311), (float)(this.guiTop + 110), 70);
        this.nameTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 93, this.guiTop + 47, 200, 10);
        this.nameTextField.setEnableBackgroundDrawing(false);
        this.nameTextField.setMaxStringLength(16);
        this.maxPlayersTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 69, this.guiTop + 66, 23, 10);
        this.maxPlayersTextField.setEnableBackgroundDrawing(false);
        this.maxPlayersTextField.setMaxStringLength(2);
        this.scoreTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 116, this.guiTop + 66, 25, 10);
        this.scoreTextField.setEnableBackgroundDrawing(false);
        this.scoreTextField.setMaxStringLength(4);
        this.scoreGoalTextField = new GuiTextField(this.fontRenderer, this.guiLeft + 163, this.guiTop + 66, 25, 10);
        this.scoreGoalTextField.setEnableBackgroundDrawing(false);
        this.scoreGoalTextField.setMaxStringLength(4);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.nameTextField.updateCursorCounter();
        this.maxPlayersTextField.updateCursorCounter();
        this.scoreTextField.updateCursorCounter();
        this.scoreGoalTextField.updateCursorCounter();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.hoveredColor = "";
        this.hoveredScoreType = "";
        this.hoveredPlayer = "";
        hoveredTeamInfos = null;
        this.hoveredFlag = "";
        this.hoveredAction = "";
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("island_members");

        if (!this.helpOpened)
        {
            this.helpSectionOffsetX = Math.max(this.helpSectionOffsetX - 1, 0);
        }
        else
        {
            this.helpSectionOffsetX = Math.min(this.helpSectionOffsetX + 1, 107);
        }

        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 329 + this.helpSectionOffsetX), (float)(this.guiTop + 171), 69, 299, 23, 45, 512.0F, 512.0F, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 337 + this.helpSectionOffsetX), (float)(this.guiTop + 209), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 337 + this.helpSectionOffsetX)), (float)(-(this.guiTop + 209)), 0.0F);
        this.drawScaledString(I18n.getString("island.list.help.title"), this.guiLeft + 337 + this.helpSectionOffsetX, this.guiTop + 209, 0, 1.0F, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_members");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 329 - 107 + this.helpSectionOffsetX), (float)(this.guiTop + 8), 405, 0, 107, 232, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("island.members.help.title"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 20, 0, 1.3F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_1"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 50, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_2"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 60, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_3"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 70, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_4"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 80, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_5"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 95, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_6"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 105, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_7"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 115, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_8"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 125, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_9"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 135, 0, 1.0F, true, false);
        this.drawScaledString(I18n.getString("island.members.help.text_10"), this.guiLeft + 381 - 107 + this.helpSectionOffsetX, this.guiTop + 145, 0, 1.0F, true, false);

        if (this.tabMode.equals("members"))
        {
            ClientEventHandler.STYLE.bindTexture("island_members");
        }
        else
        {
            ClientEventHandler.STYLE.bindTexture("island_teams");
        }

        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        Object tooltipToDraw = new ArrayList();
        ClientEventHandler.STYLE.bindTexture("island_main");
        int offsetX;

        for (int titleOffsetY = 0; titleOffsetY < IslandMainGui.TABS.size(); ++titleOffsetY)
        {
            GuiScreenTab l = (GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            offsetX = IslandMainGui.getTabIndex((GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY));

            if (this.getClass() == l.getClassReferent())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 23, 249, 29, 30, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), offsetX * 20, 331, 20, 20, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 0, 249, 23, 30, 512.0F, 512.0F, false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), offsetX * 20, 331, 20, 20, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);

                if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 47 + titleOffsetY * 31 && mouseY <= this.guiTop + 47 + 30 + titleOffsetY * 31)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.tab." + offsetX)});
                }
            }
        }

        ClientEventHandler.STYLE.bindTexture("island_members");

        if (mouseX >= this.guiLeft + 316 && mouseX <= this.guiLeft + 316 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 316), (float)(this.guiTop - 8), 139, 259, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 316), (float)(this.guiTop - 8), 139, 249, 9, 10, 512.0F, 512.0F, false);
        }

        if (IslandMainGui.islandInfos != null && IslandMainGui.islandInfos.size() > 0)
        {
            GL11.glPushMatrix();
            Double var10 = Double.valueOf((double)(this.guiTop + 45) + (double)this.fontRenderer.getStringWidth((String)IslandMainGui.islandInfos.get("name")) * 1.5D);
            GL11.glTranslatef((float)(this.guiLeft + 14), (float)var10.intValue(), 0.0F);
            GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-var10.intValue()), 0.0F);
            this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, var10.intValue(), 16777215, 1.5F, false, false);
            GL11.glPopMatrix();
            ClientEventHandler.STYLE.bindTexture("island_teams");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 48), (float)(this.guiTop + 214), 48, 214, 112, 18, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 112 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 48), (float)(this.guiTop + 214), 139, 252, 112, 18, 512.0F, 512.0F, false);
            }

            if (this.tabMode.equals("members"))
            {
                this.drawScaledString(I18n.getString("island.members.manager_teams"), this.guiLeft + 104, this.guiTop + 220, 16777215, 1.0F, true, false);
            }
            else
            {
                this.drawScaledString(I18n.getString("island.members.manager_members"), this.guiLeft + 104, this.guiTop + 220, 16777215, 1.0F, true, false);
            }

            this.hoveredMember = "";
            Float offsetY;
            int var11;

            if (this.tabMode.equals("members"))
            {
                this.drawScaledString(I18n.getString("island.main.members"), this.guiLeft + 48, this.guiTop + 25, 0, 1.0F, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 49, this.guiTop + 35, 107, 168);

                for (var11 = 0; var11 < ((ArrayList)IslandMainGui.islandInfos.get("members")).size(); ++var11)
                {
                    offsetX = this.guiLeft + 49;
                    offsetY = Float.valueOf((float)(this.guiTop + 35 + var11 * 21) + this.getSlideMembers());
                    ClientEventHandler.STYLE.bindTexture("island_members");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 49, 35, 107, 21, 512.0F, 512.0F, false);

                    if (!((String)((ArrayList)IslandMainGui.islandInfos.get("members")).get(var11)).replaceAll("\u00a7[0-9a-z]{1}", "").equals(IslandMainGui.islandInfos.get("creator")) && mouseX > offsetX && mouseX < offsetX + 107 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 21.0F)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 2), (float)offsetY.intValue(), 0, 345, 107, 21, 512.0F, 512.0F, false);
                        this.hoveredMember = (String)((ArrayList)IslandMainGui.islandInfos.get("members")).get(var11);
                    }

                    if (this.selectedMember.equals(((ArrayList)IslandMainGui.islandInfos.get("members")).get(var11)))
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 2), (float)offsetY.intValue(), 0, 345, 107, 21, 512.0F, 512.0F, false);
                    }

                    this.drawScaledString((String)((ArrayList)IslandMainGui.islandInfos.get("members")).get(var11), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                }

                GUIUtils.endGLScissor();

                if (mouseX > this.guiLeft + 48 && mouseX < this.guiLeft + 48 + 113 && mouseY > this.guiTop + 34 && mouseY < this.guiTop + 34 + 170)
                {
                    this.scrollBarMembers.draw(mouseX, mouseY);
                }

                this.drawScaledString(I18n.getString("island.main.visitors"), this.guiLeft + 203, this.guiTop + 25, 0, 1.0F, false, false);
                GUIUtils.startGLScissor(this.guiLeft + 204, this.guiTop + 35, 107, 168);

                for (var11 = 0; var11 < ((ArrayList)IslandMainGui.islandInfos.get("visitors")).size(); ++var11)
                {
                    offsetX = this.guiLeft + 204;
                    offsetY = Float.valueOf((float)(this.guiTop + 35 + var11 * 21) + this.getSlideVisitors());
                    ClientEventHandler.STYLE.bindTexture("island_members");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 49, 35, 107, 21, 512.0F, 512.0F, false);

                    if (mouseX > offsetX && mouseX < offsetX + 107 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 21.0F)
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 2), (float)offsetY.intValue(), 0, 345, 107, 21, 512.0F, 512.0F, false);
                        this.hoveredMember = (String)((ArrayList)IslandMainGui.islandInfos.get("visitors")).get(var11);
                    }

                    if (this.selectedMember.equals(((ArrayList)IslandMainGui.islandInfos.get("visitors")).get(var11)))
                    {
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 2), (float)offsetY.intValue(), 0, 345, 107, 21, 512.0F, 512.0F, false);
                    }

                    this.drawScaledString((String)((ArrayList)IslandMainGui.islandInfos.get("visitors")).get(var11), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                }

                GUIUtils.endGLScissor();

                if (mouseX > this.guiLeft + 203 && mouseX < this.guiLeft + 203 + 113 && mouseY > this.guiTop + 34 && mouseY < this.guiTop + 106 + 170)
                {
                    this.scrollBarVisitors.draw(mouseX, mouseY);
                }

                ClientEventHandler.STYLE.bindTexture("island_members");

                if (mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 18)
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 165), (float)(this.guiTop + 95), 0, 311, 34, 18, 512.0F, 512.0F, false);
                }

                if (mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 18)
                {
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 165), (float)(this.guiTop + 124), 34, 311, 34, 18, 512.0F, 512.0F, false);
                }
            }
            else if (this.tabMode.equals("teams"))
            {
                if ((IslandMainGui.isPremium || teamsInfos.size() < 2) && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 20 && mouseY < this.guiTop + 20 + 16)
                {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 297), (float)(this.guiTop + 20), 346, 8, 19, 16, 512.0F, 512.0F, false);
                }

                if (!IslandMainGui.isPremium && teamsInfos.size() >= 2)
                {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 297), (float)(this.guiTop + 20), 84, 254, 19, 16, 512.0F, 512.0F, false);
                }

                if (selectedTeamInfos != null && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 16)
                {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 297), (float)(this.guiTop + 43), 346, 25, 19, 16, 512.0F, 512.0F, false);
                }

                if (selectedTeamInfos != null)
                {
                    this.drawScaledString("\u00a7" + selectedTeamInfos.get("color") + selectedTeamInfos.get("name"), this.guiLeft + 53, this.guiTop + 24, 16777215, 1.0F, false, false);
                    Gui.drawRect(this.guiLeft + 54, this.guiTop + 45, this.guiLeft + 54 + 12, this.guiTop + 45 + 12, getColorFromString((String)selectedTeamInfos.get("color")));
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.nameTextField.drawTextBox();

                    if (this.nameTextField.getText().isEmpty() || needUpdate)
                    {
                        this.nameTextField.setText((String)selectedTeamInfos.get("name"));
                    }

                    this.maxPlayersTextField.drawTextBox();

                    if (this.maxPlayersTextField.getText().isEmpty() || needUpdate)
                    {
                        this.maxPlayersTextField.setText((String)selectedTeamInfos.get("maxPlayers"));
                    }

                    this.scoreTextField.drawTextBox();

                    if (this.scoreTextField.getText().isEmpty() || needUpdate)
                    {
                        this.scoreTextField.setText((String)selectedTeamInfos.get("score"));
                    }

                    this.scoreGoalTextField.drawTextBox();

                    if (this.scoreGoalTextField.getText().isEmpty() || needUpdate)
                    {
                        this.scoreGoalTextField.setText((String)selectedTeamInfos.get("scoreGoal"));
                    }

                    this.drawScaledString(I18n.getString("island.members.scoreType." + selectedTeamInfos.get("scoreType")), this.guiLeft + 194, this.guiTop + 66, 16777215, 1.0F, false, false);
                    this.drawScaledString(I18n.getString("island.members.label.players"), this.guiLeft + 53, this.guiTop + 96, 16777215, 1.0F, false, false);
                    GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 113, 124, 96);

                    for (var11 = 0; var11 < teamFlags.size(); ++var11)
                    {
                        offsetX = this.guiLeft + 50;
                        offsetY = Float.valueOf((float)(this.guiTop + 113 + var11 * 21) + this.getSlideFlags());
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 50, 113, 106, 21, 512.0F, 512.0F, false);

                        if (((Boolean)((HashMap)selectedTeamInfos.get("flags")).get(teamFlags.get(var11))).booleanValue())
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 107), (float)(offsetY.intValue() + 5), 0, 256, 10, 10, 512.0F, 512.0F, false);
                        }
                        else
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 107), (float)(offsetY.intValue() + 5), 0, 268, 10, 10, 512.0F, 512.0F, false);
                        }

                        if (mouseX > offsetX + 107 && mouseX < offsetX + 107 + 10 && (float)mouseY > offsetY.floatValue() + 5.0F && (float)mouseY < offsetY.floatValue() + 5.0F + 10.0F)
                        {
                            this.hoveredFlag = (String)teamFlags.get(var11);
                        }

                        String flagString = I18n.getString("island.members.flags.label." + (String)teamFlags.get(var11));

                        if (flagString.length() > 15)
                        {
                            flagString = flagString.substring(0, 14) + "..";

                            if (mouseX > offsetX && mouseX < offsetX + 106 && mouseY > offsetY.intValue() && mouseY < offsetY.intValue() + 21)
                            {
                                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.members.flags.label." + (String)teamFlags.get(var11))});
                            }
                        }

                        this.drawScaledString(flagString, offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                    }

                    GUIUtils.endGLScissor();

                    if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 112 && mouseY > this.guiTop + 104 && mouseY < this.guiTop + 104 + 98)
                    {
                        this.scrollBarFlags.draw(mouseX, mouseY);
                    }

                    GUIUtils.startGLScissor(this.guiLeft + 182, this.guiTop + 113, 129, 96);

                    for (var11 = 0; var11 < this.actions.size(); ++var11)
                    {
                        offsetX = this.guiLeft + 182;
                        offsetY = Float.valueOf((float)(this.guiTop + 113 + var11 * 21) + this.getSlideActions());
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 182, 113, 129, 21, 512.0F, 512.0F, false);
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 112), (float)(offsetY.intValue() + 2), 51, 253, 15, 15, 512.0F, 512.0F, false);

                        if (mouseX > offsetX + 112 && mouseX < offsetX + 112 + 15 && (float)mouseY > offsetY.floatValue() + 2.0F && (float)mouseY < offsetY.floatValue() + 2.0F + 15.0F)
                        {
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 112), (float)(offsetY.intValue() + 2), 66, 253, 15, 15, 512.0F, 512.0F, false);
                            this.hoveredAction = (String)this.actions.get(var11);
                        }

                        this.drawScaledString(I18n.getString("island.members.actions.label." + (String)this.actions.get(var11)), offsetX + 4, offsetY.intValue() + 6, 16777215, 1.0F, false, false);
                    }

                    GUIUtils.endGLScissor();

                    if (mouseX > this.guiLeft + 181 && mouseX < this.guiLeft + 181 + 112 && mouseY > this.guiTop + 112 && mouseY < this.guiTop + 112 + 98)
                    {
                        this.scrollBarActions.draw(mouseX, mouseY);
                    }

                    if (this.dropdownColors)
                    {
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 58), 0, 292, 40, 76, 512.0F, 512.0F, false);
                        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 59, 34, 74);

                        for (var11 = 0; var11 < this.availableColors.size(); ++var11)
                        {
                            offsetX = this.guiLeft + 50;
                            offsetY = Float.valueOf((float)(this.guiTop + 59 + var11 * 15) + this.getSlideColors());
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 1, 293, 34, 15, 512.0F, 512.0F, false);
                            Gui.drawRect(this.guiLeft + 60, offsetY.intValue() + 1, this.guiLeft + 60 + 12, offsetY.intValue() + 1 + 12, getColorFromString((String)this.availableColors.get(var11)));
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

                            if (mouseX > offsetX && mouseX < offsetX + 34 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 15.0F)
                            {
                                this.hoveredColor = (String)this.availableColors.get(var11);
                            }
                        }

                        GUIUtils.endGLScissor();
                        this.scrollBarColors.draw(mouseX, mouseY);
                    }

                    if (this.dropdownScoreType)
                    {
                        ClientEventHandler.STYLE.bindTexture("island_teams");

                        for (var11 = 0; var11 < this.availableScoreTypes.size(); ++var11)
                        {
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 190), (float)(this.guiTop + 77 + var11 * 15), 0, 403, 126, 16, 512.0F, 512.0F, false);
                            this.drawScaledString(I18n.getString("island.members.scoreType." + (String)this.availableScoreTypes.get(var11)), this.guiLeft + 190 + 4, this.guiTop + 77 + var11 * 15 + 5, 16777215, 1.0F, false, false);

                            if (mouseX > this.guiLeft + 190 && mouseX < this.guiLeft + 190 + 126 && mouseY > this.guiTop + 77 + var11 * 15 && mouseY < this.guiTop + 77 + var11 * 15 + 15)
                            {
                                this.hoveredScoreType = (String)this.availableScoreTypes.get(var11);
                            }
                        }
                    }

                    if (this.dropdownPlayers)
                    {
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 107), 181, 365, 267, 76, 512.0F, 512.0F, false);
                        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 108, 261, 74);

                        for (var11 = 0; var11 < ((ArrayList)selectedTeamInfos.get("players")).size(); ++var11)
                        {
                            offsetX = this.guiLeft + 50;
                            offsetY = Float.valueOf((float)(this.guiTop + 108 + var11 * 15) + this.getSlidePlayers());
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 182, 366, 261, 15, 512.0F, 512.0F, false);
                            this.drawScaledString((String)((ArrayList)selectedTeamInfos.get("players")).get(var11), offsetX + 4, offsetY.intValue() + 4, 16777215, 1.0F, false, false);
                            ClientEventHandler.STYLE.bindTexture("island_teams");
                            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 234), (float)(offsetY.intValue() + 1), 106, 253, 25, 12, 512.0F, 512.0F, false);
                            this.drawScaledString("Kick", offsetX + 248, offsetY.intValue() + 5, 16777215, 0.7F, true, false);

                            if (mouseX > offsetX + 234 && mouseX < offsetX + 234 + 25 && (float)mouseY > offsetY.floatValue() + 1.0F && (float)mouseY < offsetY.floatValue() + 1.0F + 12.0F)
                            {
                                this.hoveredPlayer = (String)((ArrayList)selectedTeamInfos.get("players")).get(var11);
                            }
                        }

                        GUIUtils.endGLScissor();
                        this.scrollBarPlayers.draw(mouseX, mouseY);
                    }
                }

                if (this.dropdownTeams)
                {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 35), 86, 292, 247, 46, 512.0F, 512.0F, false);
                    GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 36, 241, 44);

                    for (var11 = 0; var11 < teamsInfos.size(); ++var11)
                    {
                        offsetX = this.guiLeft + 50;
                        offsetY = Float.valueOf((float)(this.guiTop + 36 + var11 * 15) + this.getSlideTeams());
                        ClientEventHandler.STYLE.bindTexture("island_teams");
                        ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 87, 293, 241, 15, 512.0F, 512.0F, false);
                        this.drawScaledString("\u00a7" + ((HashMap)teamsInfos.get(var11)).get("color") + ((HashMap)teamsInfos.get(var11)).get("name"), offsetX + 4, offsetY.intValue() + 4, 16777215, 1.0F, false, false);

                        if (mouseX > offsetX && mouseX < offsetX + 241 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 15.0F)
                        {
                            hoveredTeamInfos = (HashMap)teamsInfos.get(var11);
                        }
                    }

                    GUIUtils.endGLScissor();
                    this.scrollBarTeams.draw(mouseX, mouseY);
                }

                if (selectedTeamInfos == null || mouseX > this.guiLeft + 203 && mouseX < this.guiLeft + 203 + 113 && mouseY > this.guiTop + 214 && mouseY < this.guiTop + 214 + 18)
                {
                    ClientEventHandler.STYLE.bindTexture("island_teams");
                    ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 203), (float)(this.guiTop + 214), 139, 270, 113, 18, 512.0F, 512.0F, false);
                }

                this.drawScaledString(I18n.getString("island.global.save"), this.guiLeft + 259, this.guiTop + 220, 16777215, 1.0F, true, false);
            }
        }

        if (!((List)tooltipToDraw).isEmpty())
        {
            this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
        needUpdate = false;
    }

    protected void drawHoveringText(List par1List, int par2, int par3, FontRenderer font)
    {
        if (!par1List.isEmpty())
        {
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            int k = 0;
            Iterator iterator = par1List.iterator();
            int j1;

            while (iterator.hasNext())
            {
                String i1 = (String)iterator.next();
                j1 = font.getStringWidth(i1);

                if (j1 > k)
                {
                    k = j1;
                }
            }

            int var15 = par2 + 12;
            j1 = par3 - 12;
            int k1 = 8;

            if (par1List.size() > 1)
            {
                k1 += 2 + (par1List.size() - 1) * 10;
            }

            if (var15 + k > this.width)
            {
                var15 -= 28 + k;
            }

            if (j1 + k1 + 6 > this.height)
            {
                j1 = this.height - k1 - 6;
            }

            this.zLevel = 300.0F;
            this.itemRenderer.zLevel = 300.0F;
            int l1 = -267386864;
            this.drawGradientRect(var15 - 3, j1 - 4, var15 + k + 3, j1 - 3, l1, l1);
            this.drawGradientRect(var15 - 3, j1 + k1 + 3, var15 + k + 3, j1 + k1 + 4, l1, l1);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 - 4, j1 - 3, var15 - 3, j1 + k1 + 3, l1, l1);
            this.drawGradientRect(var15 + k + 3, j1 - 3, var15 + k + 4, j1 + k1 + 3, l1, l1);
            int i2 = 1347420415;
            int j2 = (i2 & 16711422) >> 1 | i2 & -16777216;
            this.drawGradientRect(var15 - 3, j1 - 3 + 1, var15 - 3 + 1, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 + k + 2, j1 - 3 + 1, var15 + k + 3, j1 + k1 + 3 - 1, i2, j2);
            this.drawGradientRect(var15 - 3, j1 - 3, var15 + k + 3, j1 - 3 + 1, i2, i2);
            this.drawGradientRect(var15 - 3, j1 + k1 + 2, var15 + k + 3, j1 + k1 + 3, j2, j2);

            for (int k2 = 0; k2 < par1List.size(); ++k2)
            {
                String s1 = (String)par1List.get(k2);
                font.drawStringWithShadow(s1, var15, j1, -1);

                if (k2 == 0)
                {
                    j1 += 2;
                }

                j1 += 10;
            }

            this.zLevel = 0.0F;
            this.itemRenderer.zLevel = 0.0F;
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (int data = 0; data < IslandMainGui.TABS.size(); ++data)
            {
                GuiScreenTab type = (GuiScreenTab)IslandMainGui.TABS.get(data);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 47 + data * 31 && mouseY <= this.guiTop + 47 + 30 + data * 31)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                    if (this.getClass() != type.getClassReferent())
                    {
                        try
                        {
                            type.call();
                        }
                        catch (Exception var7)
                        {
                            var7.printStackTrace();
                        }
                    }
                }
            }

            if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("teams") && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 112 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);

                if (this.tabMode.equals("members"))
                {
                    this.tabMode = "teams";
                }
                else
                {
                    this.tabMode = "members";
                }
            }

            if (mouseX > this.guiLeft + 316 && mouseX < this.guiLeft + 316 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }

            if (this.tabMode.equals("members"))
            {
                if (this.selectedMember != null && !this.selectedMember.isEmpty() && mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 95 && mouseY < this.guiTop + 95 + 18)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSetMemberPacket((String)IslandMainGui.islandInfos.get("id"), this.selectedMember, "promote")));
                    this.selectedMember = "";
                }
                else if (this.selectedMember != null && !this.selectedMember.isEmpty() && mouseX > this.guiLeft + 165 && mouseX < this.guiLeft + 165 + 34 && mouseY > this.guiTop + 124 && mouseY < this.guiTop + 124 + 18)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSetMemberPacket((String)IslandMainGui.islandInfos.get("id"), this.selectedMember, "demote")));
                    this.selectedMember = "";
                }
                else if (this.hoveredMember != null && !this.hoveredMember.isEmpty() && !this.hoveredMember.equals(IslandMainGui.islandInfos.get("creator")))
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.selectedMember = this.hoveredMember;
                }
            }
            else if (this.tabMode.equals("teams"))
            {
                if (!this.dropdownTeams && !this.dropdownColors && !this.dropdownScoreType && !this.dropdownPlayers)
                {
                    this.nameTextField.mouseClicked(mouseX, mouseY, mouseButton);
                    this.maxPlayersTextField.mouseClicked(mouseX, mouseY, mouseButton);
                    this.scoreTextField.mouseClicked(mouseX, mouseY, mouseButton);
                    this.scoreGoalTextField.mouseClicked(mouseX, mouseY, mouseButton);
                }

                if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 247 && mouseY > this.guiTop + 20 && mouseY < this.guiTop + 20 + 16)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.dropdownTeams = !this.dropdownTeams;
                }

                if (!this.dropdownTeams && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 40 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 16)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.dropdownColors = !this.dropdownColors;
                }

                if (!this.dropdownTeams && mouseX > this.guiLeft + 190 && mouseX < this.guiLeft + 190 + 126 && mouseY > this.guiTop + 62 && mouseY < this.guiTop + 62 + 16)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.dropdownScoreType = !this.dropdownScoreType;
                }

                if (!this.dropdownTeams && !this.dropdownColors && !this.dropdownScoreType && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 267 && mouseY > this.guiTop + 92 && mouseY < this.guiTop + 92 + 16)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    this.dropdownPlayers = !this.dropdownPlayers;
                }

                if ((IslandMainGui.isPremium || teamsInfos.size() < 2) && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 20 && mouseY < this.guiTop + 20 + 16)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTeamCreatePacket(teamsInfos.size() + 1, Integer.parseInt((String)IslandMainGui.islandInfos.get("id")))));
                }

                if (selectedTeamInfos != null && mouseX > this.guiLeft + 297 && mouseX < this.guiLeft + 297 + 19 && mouseY > this.guiTop + 43 && mouseY < this.guiTop + 43 + 16)
                {
                    this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTeamRemovePacket(Integer.parseInt((String)selectedTeamInfos.get("id")))));
                }

                if (this.dropdownColors && selectedTeamInfos != null && !this.hoveredColor.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 40 && mouseY > this.guiTop + 58 && mouseY < this.guiTop + 58 + 76)
                {
                    selectedTeamInfos.put("color", this.hoveredColor);
                    this.hoveredColor = "";
                    this.dropdownColors = false;
                }

                if (this.dropdownTeams && hoveredTeamInfos != null && mouseX > this.guiLeft + 50 && mouseX < this.guiLeft + 50 + 247 && mouseY > this.guiTop + 35 && mouseY < this.guiTop + 35 + 46)
                {
                    selectedTeamInfos = hoveredTeamInfos;
                    hoveredTeamInfos = null;
                    this.dropdownTeams = false;
                    needUpdate = true;
                }

                if (this.dropdownScoreType && selectedTeamInfos != null && !this.hoveredScoreType.isEmpty() && mouseX > this.guiLeft + 143 && mouseX < this.guiLeft + 143 + 173 && mouseY > this.guiTop + 77 && mouseY < this.guiTop + 77 + 46)
                {
                    this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                    selectedTeamInfos.put("scoreType", this.hoveredScoreType);
                    this.hoveredScoreType = "";
                    this.dropdownScoreType = false;
                }

                if (this.dropdownPlayers && selectedTeamInfos != null && !this.hoveredPlayer.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 267 && mouseY > this.guiTop + 107 && mouseY < this.guiTop + 107 + 76)
                {
                    this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandTeamKickPlayerPacket((String)IslandMainGui.islandInfos.get("id"), (String)selectedTeamInfos.get("id"), this.hoveredPlayer)));
                    ArrayList var8 = (ArrayList)selectedTeamInfos.get("players");
                    var8.remove(this.hoveredPlayer);
                    selectedTeamInfos.put("players", var8);
                    this.dropdownPlayers = false;
                    this.hoveredPlayer = "";
                }

                if (!this.dropdownTeams && !this.dropdownColors && !this.dropdownScoreType && !this.dropdownPlayers && selectedTeamInfos != null && !this.hoveredFlag.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 130 && mouseY > this.guiTop + 112 && mouseY < this.guiTop + 112 + 98)
                {
                    this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                    ((HashMap)selectedTeamInfos.get("flags")).put(this.hoveredFlag, Boolean.valueOf(!((Boolean)((HashMap)selectedTeamInfos.get("flags")).get(this.hoveredFlag)).booleanValue()));
                }

                if (!this.dropdownTeams && !this.dropdownColors && !this.dropdownScoreType && !this.dropdownPlayers && selectedTeamInfos != null && !this.hoveredAction.isEmpty() && mouseX > this.guiLeft + 181 && mouseX < this.guiLeft + 181 + 130 && mouseY > this.guiTop + 112 && mouseY < this.guiTop + 112 + 98)
                {
                    this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandExecuteTeamActionPacket((String)IslandMainGui.islandInfos.get("id"), (String)selectedTeamInfos.get("id"), this.hoveredAction)));
                }

                if (selectedTeamInfos != null && mouseX > this.guiLeft + 203 && mouseX < this.guiLeft + 203 + 113 && mouseY > this.guiTop + 214 && mouseY < this.guiTop + 214 + 18)
                {
                    this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                    HashMap var9 = (HashMap)selectedTeamInfos.clone();
                    var9.put("flags", (new Gson()).toJson(var9.get("flags")));
                    PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSaveTeamPacket(var9)));
                }
            }

            if (!this.helpOpened && mouseX > this.guiLeft + 329 && mouseX < this.guiLeft + 329 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45)
            {
                this.helpOpened = true;
            }
            else if (this.helpOpened && mouseX > this.guiLeft + 329 + 107 && mouseX < this.guiLeft + 329 + 107 + 23 && mouseY > this.guiTop + 171 && mouseY < this.guiTop + 171 + 45)
            {
                this.helpOpened = false;
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private float getSlideMembers()
    {
        return ((ArrayList)IslandMainGui.islandInfos.get("members")).size() > 4 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("members")).size() - 4) * 20) * this.scrollBarMembers.getSliderValue() : 0.0F;
    }

    private float getSlideVisitors()
    {
        return ((ArrayList)IslandMainGui.islandInfos.get("visitors")).size() > 4 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("visitors")).size() - 4) * 20) * this.scrollBarVisitors.getSliderValue() : 0.0F;
    }

    private float getSlideTeams()
    {
        return teamsInfos.size() > 3 ? (float)(-(teamsInfos.size() - 3) * 15) * this.scrollBarTeams.getSliderValue() : 0.0F;
    }

    private float getSlideColors()
    {
        return this.availableColors.size() > 5 ? (float)(-(this.availableColors.size() - 5) * 15) * this.scrollBarColors.getSliderValue() : 0.0F;
    }

    private float getSlidePlayers()
    {
        return ((ArrayList)selectedTeamInfos.get("players")).size() > 5 ? (float)(-(((ArrayList)selectedTeamInfos.get("players")).size() - 5) * 15) * this.scrollBarPlayers.getSliderValue() : 0.0F;
    }

    private float getSlideFlags()
    {
        return teamFlags.size() > 4 ? (float)(-(teamFlags.size() - 4) * 21) * this.scrollBarFlags.getSliderValue() : 0.0F;
    }

    private float getSlideActions()
    {
        return this.actions.size() > 4 ? (float)(-(this.actions.size() - 4) * 21) * this.scrollBarActions.getSliderValue() : 0.0F;
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char typedChar, int keyCode)
    {
        if (selectedTeamInfos != null)
        {
            if (this.nameTextField.textboxKeyTyped(typedChar, keyCode))
            {
                selectedTeamInfos.put("name", this.nameTextField.getText());
            }

            if (this.maxPlayersTextField.textboxKeyTyped(typedChar, keyCode))
            {
                selectedTeamInfos.put("maxPlayers", this.maxPlayersTextField.getText());
            }

            if (this.scoreTextField.textboxKeyTyped(typedChar, keyCode))
            {
                selectedTeamInfos.put("score", this.scoreTextField.getText());
            }

            if (this.scoreGoalTextField.textboxKeyTyped(typedChar, keyCode))
            {
                selectedTeamInfos.put("scoreGoal", this.scoreGoalTextField.getText());
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    public void drawScaledString(String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)this.fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        this.fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }

    public static int getColorFromString(String color)
    {
        byte var2 = -1;

        switch (color.hashCode())
        {
            case 50:
                if (color.equals("2"))
                {
                    var2 = 6;
                }

                break;

            case 52:
                if (color.equals("4"))
                {
                    var2 = 0;
                }

                break;

            case 53:
                if (color.equals("5"))
                {
                    var2 = 7;
                }

                break;

            case 54:
                if (color.equals("6"))
                {
                    var2 = 3;
                }

                break;

            case 57:
                if (color.equals("9"))
                {
                    var2 = 1;
                }

                break;

            case 97:
                if (color.equals("a"))
                {
                    var2 = 5;
                }

                break;

            case 99:
                if (color.equals("c"))
                {
                    var2 = 8;
                }

                break;

            case 100:
                if (color.equals("d"))
                {
                    var2 = 4;
                }

                break;

            case 101:
                if (color.equals("e"))
                {
                    var2 = 2;
                }
        }

        switch (var2)
        {
            case 0:
                return -2880243;

            case 1:
                return -15638856;

            case 2:
                return -1515241;

            case 3:
                return -1859052;

            case 4:
                return -1371929;

            case 5:
                return -16451840;

            case 6:
                return -15752947;

            case 7:
                return -7402763;

            case 8:
                return -697509;

            default:
                return -1;
        }
    }
}
