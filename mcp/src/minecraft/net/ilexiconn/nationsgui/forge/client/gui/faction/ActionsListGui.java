package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionActionsListDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.FactionMainDataPacket;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ActionsListGui extends GuiScreen
{
    protected int xSize = 319;
    protected int ySize = 234;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    private GuiScrollBarFaction scrollBar;
    public String hoveredCountry;
    public static ArrayList<HashMap<String, String>> actions = new ArrayList();

    public ActionsListGui()
    {
        loaded = false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionActionsListDataPacket((String)FactionGUI.factionInfos.get("name"))));
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBar = new GuiScrollBarFaction((float)(this.guiLeft + 296), (float)(this.guiTop + 64), 150);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_actions_list");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);

        if (mouseX >= this.guiLeft + 304 && mouseX <= this.guiLeft + 304 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 247, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 304), (float)(this.guiTop - 6), 0, 237, 9, 10, 512.0F, 512.0F, false);
        }

        this.drawScaledString(I18n.getString("faction.actions_list.title") + " " + FactionGUI.factionInfos.get("name"), this.guiLeft + 50, this.guiTop + 20, 1644825, 1.0F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.country"), this.guiLeft + 51 + 5, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.countActions_1"), this.guiLeft + 51 + 80, this.guiTop + 40, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.countActions_2"), this.guiLeft + 51 + 80, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.dividendes_1"), this.guiLeft + 51 + 130, this.guiTop + 40, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.dividendes_2"), this.guiLeft + 51 + 130, this.guiTop + 50, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.value_1"), this.guiLeft + 51 + 195, this.guiTop + 40, 1644825, 0.9F, false, false);
        this.drawScaledString(I18n.getString("faction.actions_list.value_2"), this.guiLeft + 51 + 195, this.guiTop + 50, 1644825, 0.9F, false, false);

        if (loaded && actions.size() > 0)
        {
            this.hoveredCountry = "";
            GUIUtils.startGLScissor(this.guiLeft + 51, this.guiTop + 60, 245, 158);

            for (int i = 0; i < actions.size(); ++i)
            {
                int offsetX = this.guiLeft + 51;
                Float offsetY = Float.valueOf((float)(this.guiTop + 60 + i * 20) + this.getSlide());
                ClientEventHandler.STYLE.bindTexture("faction_actions_list");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 51, 60, 245, 20, 512.0F, 512.0F, false);
                this.drawScaledString(((String)((HashMap)actions.get(i)).get("name")).length() < 14 ? (String)((HashMap)actions.get(i)).get("name") : ((String)((HashMap)actions.get(i)).get("name")).substring(0, 13) + "..", offsetX + 5, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                this.drawScaledString((String)((HashMap)actions.get(i)).get("countActions"), offsetX + 80, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                this.drawScaledString((String)((HashMap)actions.get(i)).get("dividendes"), offsetX + 130, offsetY.intValue() + 5, 16777215, 1.0F, false, false);
                this.drawScaledString((String)((HashMap)actions.get(i)).get("value") + "$", offsetX + 195, offsetY.intValue() + 5, 16777215, 1.0F, false, false);

                if (mouseX > this.guiLeft + 51 && mouseX < this.guiLeft + 51 + 245 && mouseY > this.guiTop + 60 && mouseY < this.guiTop + 60 + 158 && mouseX > offsetX && mouseX < offsetX + 245 && (float)mouseY > offsetY.floatValue() && (float)mouseY < offsetY.floatValue() + 20.0F)
                {
                    this.hoveredCountry = (String)((HashMap)actions.get(i)).get("name");
                }
            }

            GUIUtils.endGLScissor();
            this.scrollBar.draw(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, par3);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlide()
    {
        return actions.size() > 8 ? (float)(-(actions.size() - 8) * 20) * this.scrollBar.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (mouseX > this.guiLeft + 304 && mouseX < this.guiLeft + 304 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                this.mc.displayGuiScreen((GuiScreen)null);
            }

            if (this.hoveredCountry != null && !this.hoveredCountry.isEmpty())
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionMainDataPacket(this.hoveredCountry, true)));
                FactionGUI.loaded = false;
                this.mc.displayGuiScreen(new BankGUI());
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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
}
