package net.ilexiconn.nationsgui.forge.client.gui.island;

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
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandExecuteActionPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IslandSavePropertiesPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import sun.misc.BASE64Decoder;

@SideOnly(Side.CLIENT)
public class IslandPropertiesGui extends GuiScreen
{
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
    private List<String> biomes = new ArrayList();
    private List<String> weatherModes = new ArrayList();
    private List<String> timeModes = new ArrayList();
    private List<String> actions = new ArrayList();
    private int biomeOffsetXMax = 0;
    private String hoveredBiome = "";
    private String selectedBiome = "";
    private int biomeOffsetNumber = 0;
    private int biomeOffsetX = 0;
    private HashMap<String, Boolean> islandFlags = new HashMap();
    private ArrayList<String> flagList = new ArrayList();

    public IslandPropertiesGui()
    {
        this.biomes.addAll(Arrays.asList(new String[] {"plaine", "marais", "desert", "neige", "jungle"}));
        this.weatherModes.addAll(Arrays.asList(new String[] {"storm", "rain", "sun", "cycle"}));
        this.timeModes.addAll(Arrays.asList(new String[] {"day", "night", "cycle"}));
        this.actions.addAll(Arrays.asList(new String[] {"kill_hostile", "kill_passive", "set_spawn", "kick_players", "tp_players", "respawn_all", "reset_score"}));
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

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBarFlags = new GuiScrollBarFaction((float)(this.guiLeft + 156), (float)(this.guiTop + 116), 90);
        this.scrollBarActions = new GuiScrollBarFaction((float)(this.guiLeft + 270), (float)(this.guiTop + 116), 90);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        Object tooltipToDraw = new ArrayList();
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("island_properties");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        ClientEventHandler.STYLE.bindTexture("island_main");
        int i;

        for (int titleOffsetY = 0; titleOffsetY < IslandMainGui.TABS.size(); ++titleOffsetY)
        {
            GuiScreenTab biomeOffsetXTarget = (GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            i = IslandMainGui.getTabIndex((GuiScreenTab)IslandMainGui.TABS.get(titleOffsetY));

            if (this.getClass() == biomeOffsetXTarget.getClassReferent())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 23, 249, 29, 30, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), i * 20, 331, 20, 20, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 47 + titleOffsetY * 31), 0, 249, 23, 30, 512.0F, 512.0F, false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 4), (float)(this.guiTop + 47 + titleOffsetY * 31 + 5), i * 20, 331, 20, 20, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);

                if (mouseX >= this.guiLeft - 23 && mouseX <= this.guiLeft - 23 + 29 && mouseY >= this.guiTop + 47 + titleOffsetY * 31 && mouseY <= this.guiTop + 47 + 30 + titleOffsetY * 31)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.tab." + i)});
                }
            }
        }

        ClientEventHandler.STYLE.bindTexture("island_properties");

        if (mouseX >= this.guiLeft + 275 && mouseX <= this.guiLeft + 275 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 275), (float)(this.guiTop - 8), 121, 260, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 275), (float)(this.guiTop - 8), 121, 250, 9, 10, 512.0F, 512.0F, false);
        }

        GL11.glPushMatrix();
        Double var11 = Double.valueOf((double)(this.guiTop + 45) + (double)this.fontRenderer.getStringWidth((String)IslandMainGui.islandInfos.get("name")) * 1.5D);
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)var11.intValue(), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-var11.intValue()), 0.0F);
        this.drawScaledString((String)IslandMainGui.islandInfos.get("name"), this.guiLeft + 14, var11.intValue(), 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("island_properties");
        this.drawScaledString(I18n.getString("island.properties.biome"), this.guiLeft + 50, this.guiTop + 17, 0, 1.0F, false, false);
        ClientEventHandler.STYLE.bindTexture("island_properties");
        int var12 = 51 * this.biomeOffsetNumber;

        if (this.biomeOffsetX < var12)
        {
            ++this.biomeOffsetX;
        }
        else if (this.biomeOffsetX > var12)
        {
            --this.biomeOffsetX;
        }

        this.hoveredBiome = "";
        this.hoveredTime = "";
        this.hoveredWeather = "";
        this.hoveredFlag = "";
        this.hoveredAction = "";
        GUIUtils.startGLScissor(this.guiLeft + 60, this.guiTop + 27, 203, 50);
        int offsetX;

        for (i = 0; i < this.biomes.size(); ++i)
        {
            offsetX = this.guiLeft + 60 - this.biomeOffsetX + 51 * i;
            int offsetY = this.guiTop + 27;
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0 + 51 * i, 279, 50, 50, 512.0F, 512.0F, false);

            if (mouseX >= offsetX && mouseX <= offsetX + 50 && mouseY >= offsetY && mouseY <= offsetY + 50)
            {
                this.hoveredBiome = (String)this.biomes.get(i);
            }

            if (((String)this.biomes.get(i)).equals(this.selectedBiome))
            {
                ClientEventHandler.STYLE.bindTexture("island_properties");
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(false);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY, 0, 331, 50, 50, 512.0F, 512.0F, false);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
            }
        }

        GUIUtils.endGLScissor();

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome"))
        {
            drawRect(this.guiLeft + 49, this.guiTop + 27, this.guiLeft + 49 + 225, this.guiTop + 27 + 50, -1157627904);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 154), (float)(this.guiTop + 39), 293, 57, 16, 26, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 154 && mouseX <= this.guiLeft + 154 + 16 && mouseY >= this.guiTop + 39 && mouseY <= this.guiTop + 39 + 26)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.permission_required")});
            }
        }
        else
        {
            ClientEventHandler.STYLE.bindTexture("island_properties");

            if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 10 && mouseY >= this.guiTop + 27 && mouseY <= this.guiTop + 27 + 50)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 27), 51, 331, 10, 50, 512.0F, 512.0F, false);
            }
            else if (mouseX >= this.guiLeft + 264 && mouseX <= this.guiLeft + 264 + 10 && mouseY >= this.guiTop + 27 && mouseY <= this.guiTop + 27 + 50)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 264), (float)(this.guiTop + 27), 63, 331, 10, 50, 512.0F, 512.0F, false);
            }
        }

        this.drawScaledString(I18n.getString("island.properties.flags"), this.guiLeft + 49, this.guiTop + 102, 0, 1.0F, false, false);
        GUIUtils.startGLScissor(this.guiLeft + 50, this.guiTop + 112, 106, 96);
        Float var13;

        for (i = 0; i < this.flagList.size(); ++i)
        {
            offsetX = this.guiLeft + 50;
            var13 = Float.valueOf((float)(this.guiTop + 112 + i * 21) + this.getSlideFlags());
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var13.intValue(), 50, 112, 106, 21, 512.0F, 512.0F, false);

            if (((Boolean)this.islandFlags.get(this.flagList.get(i))).booleanValue())
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 89), (float)(var13.intValue() + 5), 0, 256, 10, 10, 512.0F, 512.0F, false);
            }
            else
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 89), (float)(var13.intValue() + 5), 0, 268, 10, 10, 512.0F, 512.0F, false);
            }

            if (mouseX > offsetX + 89 && mouseX < offsetX + 89 + 10 && (float)mouseY > var13.floatValue() + 5.0F && (float)mouseY < var13.floatValue() + 5.0F + 10.0F)
            {
                this.hoveredFlag = (String)this.flagList.get(i);
            }

            String flagString = I18n.getString("island.properties.flags.label." + (String)this.flagList.get(i));

            if (flagString.length() > 15)
            {
                flagString = flagString.substring(0, 14) + "..";

                if (mouseX > offsetX && mouseX < offsetX + 106 && mouseY > var13.intValue() && mouseY < var13.intValue() + 21)
                {
                    tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.properties.flags.label." + (String)this.flagList.get(i))});
                }
            }

            this.drawScaledString(flagString, offsetX + 4, var13.intValue() + 6, 16777215, 1.0F, false, false);
        }

        GUIUtils.endGLScissor();

        if (mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 112 && mouseY > this.guiTop + 104 && mouseY < this.guiTop + 104 + 98)
        {
            this.scrollBarFlags.draw(mouseX, mouseY);
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags"))
        {
            drawRect(this.guiLeft + 49, this.guiTop + 111, this.guiLeft + 49 + 112, this.guiTop + 111 + 98, -1157627904);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 97), (float)(this.guiTop + 145), 293, 57, 16, 26, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 97 && mouseX <= this.guiLeft + 97 + 16 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 26)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.permission_required")});
            }
        }

        this.drawScaledString(I18n.getString("island.properties.actions"), this.guiLeft + 162, this.guiTop + 102, 0, 1.0F, false, false);
        GUIUtils.startGLScissor(this.guiLeft + 163, this.guiTop + 112, 107, 96);

        for (i = 0; i < this.actions.size(); ++i)
        {
            offsetX = this.guiLeft + 163;
            var13 = Float.valueOf((float)(this.guiTop + 112 + i * 21) + this.getSlideActions());
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)var13.intValue(), 50, 112, 107, 21, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 90), (float)(var13.intValue() + 2), 138, 248, 15, 15, 512.0F, 512.0F, false);

            if (mouseX > offsetX + 90 && mouseX < offsetX + 90 + 15 && (float)mouseY > var13.floatValue() + 2.0F && (float)mouseY < var13.floatValue() + 2.0F + 15.0F)
            {
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 90), (float)(var13.intValue() + 2), 153, 248, 15, 15, 512.0F, 512.0F, false);
                this.hoveredAction = (String)this.actions.get(i);
            }

            this.drawScaledString(I18n.getString("island.properties.actions.label." + (String)this.actions.get(i)), offsetX + 4, var13.intValue() + 6, 16777215, 1.0F, false, false);
        }

        GUIUtils.endGLScissor();

        if (mouseX > this.guiLeft + 162 && mouseX < this.guiLeft + 162 + 113 && mouseY > this.guiTop + 111 && mouseY < this.guiTop + 111 + 98)
        {
            this.scrollBarActions.draw(mouseX, mouseY);
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("execute_actions"))
        {
            drawRect(this.guiLeft + 162, this.guiTop + 111, this.guiLeft + 162 + 112, this.guiTop + 111 + 98, -1157627904);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            ClientEventHandler.STYLE.bindTexture("island_properties");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 210), (float)(this.guiTop + 145), 293, 57, 16, 26, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 210 && mouseX <= this.guiLeft + 210 + 16 && mouseY >= this.guiTop + 145 && mouseY <= this.guiTop + 145 + 26)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.permission_required")});
            }
        }

        ClientEventHandler.STYLE.bindTexture("island_properties");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 86), 292, 6 + this.timeModes.indexOf(this.selectedTimeMode) * 10, 10, 10, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("island.properties.time.label." + this.selectedTimeMode), this.guiLeft + 67, this.guiTop + 87, 16777215, 1.0F, false, false);

        if (this.expandTime)
        {
            for (i = 0; i < this.timeModes.size(); ++i)
            {
                ClientEventHandler.STYLE.bindTexture("island_properties");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 49), (float)(this.guiTop + 98 + 15 * i), 137, 331, 65, 16, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 55), (float)(this.guiTop + 98 + 15 * i + 3), 292, 6 + i * 10, 10, 10, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("island.properties.time.label." + (String)this.timeModes.get(i)), this.guiLeft + 68, this.guiTop + 98 + 15 * i + 3 + 2, 16777215, 1.0F, false, false);

                if (mouseX >= this.guiLeft + 49 && mouseX <= this.guiLeft + 49 + 65 && mouseY >= this.guiTop + 98 + 15 * i && mouseY <= this.guiTop + 98 + 15 * i + 16)
                {
                    this.hoveredTime = (String)this.timeModes.get(i);
                }
            }
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time"))
        {
            drawRect(this.guiLeft + 49, this.guiTop + 83, this.guiLeft + 49 + 65, this.guiTop + 83 + 16, -1157627904);
        }

        ClientEventHandler.STYLE.bindTexture("island_properties");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 121), (float)(this.guiTop + 85), 305, 6 + this.weatherModes.indexOf(this.selectedWeatherMode) * 12, 17, 12, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("island.properties.time.label." + this.selectedTimeMode), this.guiLeft + 67, this.guiTop + 87, 16777215, 1.0F, false, false);

        if (this.expandWeather)
        {
            for (i = 0; i < this.weatherModes.size(); ++i)
            {
                ClientEventHandler.STYLE.bindTexture("island_properties");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 115), (float)(this.guiTop + 98 + 15 * i), 89, 331, 46, 16, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 121), (float)(this.guiTop + 98 + 15 * i + 2), 305, 6 + i * 12, 17, 12, 512.0F, 512.0F, false);

                if (mouseX >= this.guiLeft + 115 && mouseX <= this.guiLeft + 115 + 46 && mouseY >= this.guiTop + 98 + 15 * i && mouseY <= this.guiTop + 98 + 15 * i + 16)
                {
                    this.hoveredWeather = (String)this.weatherModes.get(i);
                }
            }
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather"))
        {
            drawRect(this.guiLeft + 115, this.guiTop + 83, this.guiLeft + 115 + 46, this.guiTop + 83 + 16, -1157627904);
        }

        ClientEventHandler.STYLE.bindTexture("island_properties");

        if (((Boolean)this.islandFlags.get("mob_hostile")).booleanValue())
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 167), (float)(this.guiTop + 86), 0, 256, 10, 10, 512.0F, 512.0F, false);
        }

        if (mouseX >= this.guiLeft + 167 && mouseX <= this.guiLeft + 167 + 10 && mouseY >= this.guiTop + 86 && mouseY <= this.guiTop + 86 + 10)
        {
            this.hoveredFlag = "mob_hostile";
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags"))
        {
            drawRect(this.guiLeft + 162, this.guiTop + 83, this.guiLeft + 162 + 35, this.guiTop + 83 + 16, -1157627904);
        }

        ClientEventHandler.STYLE.bindTexture("island_properties");

        if (((Boolean)this.islandFlags.get("mob_passive")).booleanValue())
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 203), (float)(this.guiTop + 86), 0, 256, 10, 10, 512.0F, 512.0F, false);
        }

        if (mouseX >= this.guiLeft + 203 && mouseX <= this.guiLeft + 203 + 10 && mouseY >= this.guiTop + 86 && mouseY <= this.guiTop + 86 + 10)
        {
            this.hoveredFlag = "mob_passive";
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags"))
        {
            drawRect(this.guiLeft + 198, this.guiTop + 83, this.guiLeft + 198 + 35, this.guiTop + 83 + 16, -1157627904);
        }

        ClientEventHandler.STYLE.bindTexture("island_properties");

        if (((Boolean)this.islandFlags.get("pvp")).booleanValue())
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 239), (float)(this.guiTop + 86), 0, 256, 10, 10, 512.0F, 512.0F, false);
        }

        if (mouseX >= this.guiLeft + 239 && mouseX <= this.guiLeft + 239 + 10 && mouseY >= this.guiTop + 86 && mouseY <= this.guiTop + 86 + 10)
        {
            this.hoveredFlag = "pvp";
        }

        if (!((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags"))
        {
            drawRect(this.guiLeft + 234, this.guiTop + 83, this.guiLeft + 234 + 40, this.guiTop + 83 + 16, -1157627904);
        }

        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (this.resetStarted || System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L || !((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("reset_island") || mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 48), (float)(this.guiTop + 214), 0, 256, 113, 18, 512.0F, 512.0F, false);

            if ((this.resetStarted || System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) < 300000L) && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("island.global.cooldown_1"), I18n.getString("island.global.cooldown_2")});
            }
        }

        this.drawScaledString(I18n.getString("island.properties.reset"), this.guiLeft + 104, this.guiTop + 219, 16777215, 1.0F, true, false);
        ClientEventHandler.STYLE.bindTexture("island_settings");

        if (mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 163), (float)(this.guiTop + 214), 113, 256, 113, 18, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("island.global.save"), this.guiLeft + 219, this.guiTop + 219, 16777215, 1.0F, true, false);

        if (!((List)tooltipToDraw).isEmpty())
        {
            this.drawHoveringText((List)tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
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
            for (int i = 0; i < IslandMainGui.TABS.size(); ++i)
            {
                GuiScreenTab type = (GuiScreenTab)IslandMainGui.TABS.get(i);

                if (mouseX >= this.guiLeft - 20 && mouseX <= this.guiLeft + 3 && mouseY >= this.guiTop + 47 + i * 31 && mouseY <= this.guiTop + 47 + 30 + i * 31)
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

            if (mouseX > this.guiLeft + 275 && mouseX < this.guiLeft + 275 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 10 && mouseY > this.guiTop + 27 && mouseY < this.guiTop + 27 + 50)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.biomeOffsetNumber = this.biomeOffsetNumber - 1 > 0 ? this.biomeOffsetNumber - 1 : 0;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") && mouseX > this.guiLeft + 264 && mouseX < this.guiLeft + 264 + 10 && mouseY > this.guiTop + 27 && mouseY < this.guiTop + 27 + 50)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.biomeOffsetNumber = this.biomeOffsetNumber + 1 < this.biomeOffsetXMax ? this.biomeOffsetNumber + 1 : this.biomeOffsetXMax;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") && !this.hoveredBiome.isEmpty() && mouseX > this.guiLeft + 60 && mouseX < this.guiLeft + 60 + 203 && mouseY > this.guiTop + 27 && mouseY < this.guiTop + 27 + 50)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.selectedBiome = this.hoveredBiome;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time") && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 65 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 16)
            {
                this.expandTime = !this.expandTime;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather") && mouseX > this.guiLeft + 115 && mouseX < this.guiLeft + 115 + 46 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 16)
            {
                this.expandWeather = !this.expandWeather;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time") && !this.hoveredTime.isEmpty() && mouseX > this.guiLeft + 49 && mouseX < this.guiLeft + 49 + 65 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 61)
            {
                this.selectedTimeMode = this.hoveredTime;
                this.expandTime = false;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather") && !this.hoveredWeather.isEmpty() && mouseX > this.guiLeft + 115 && mouseX < this.guiLeft + 115 + 65 && mouseY > this.guiTop + 83 && mouseY < this.guiTop + 83 + 77)
            {
                this.selectedWeatherMode = this.hoveredWeather;
                this.expandWeather = false;
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags") && !this.hoveredFlag.isEmpty())
            {
                this.islandFlags.put(this.hoveredFlag, Boolean.valueOf(!((Boolean)this.islandFlags.get(this.hoveredFlag)).booleanValue()));
            }
            else if (((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("execute_actions") && !this.hoveredAction.isEmpty())
            {
                this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandExecuteActionPacket((String)IslandMainGui.islandInfos.get("id"), this.hoveredAction)));
            }
            else if ((((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_biome") || ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_time") || ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_weather") || ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("change_flags")) && mouseX >= this.guiLeft + 163 && mouseX <= this.guiLeft + 163 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                this.mc.sndManager.playSoundFX("random.successful_hit", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new IslandSavePropertiesPacket((String)IslandMainGui.islandInfos.get("id"), this.islandFlags, this.selectedTimeMode, this.selectedWeatherMode, this.selectedBiome)));
            }
            else if (!this.resetStarted && System.currentTimeMillis() - Long.parseLong((String)IslandMainGui.islandInfos.get("regenTime")) > 300000L && ((ArrayList)IslandMainGui.islandInfos.get("playerPermissions")).contains("reset_island") && mouseX >= this.guiLeft + 48 && mouseX <= this.guiLeft + 48 + 113 && mouseY >= this.guiTop + 214 && mouseY <= this.guiTop + 214 + 18)
            {
                this.resetStarted = true;
                Minecraft.getMinecraft().displayGuiScreen(new ConfirmResetGui(this));
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private float getSlideFlags()
    {
        return ((ArrayList)IslandMainGui.islandInfos.get("flags")).size() > 4 ? (float)(-(((ArrayList)IslandMainGui.islandInfos.get("flags")).size() - 4) * 21) * this.scrollBarFlags.getSliderValue() : 0.0F;
    }

    private float getSlideActions()
    {
        return this.actions.size() > 4 ? (float)(-(this.actions.size() - 4) * 21) * this.scrollBarActions.getSliderValue() : 0.0F;
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
}
