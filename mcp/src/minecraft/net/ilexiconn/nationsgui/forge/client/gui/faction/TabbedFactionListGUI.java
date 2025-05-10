package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenTab;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI$2;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI$3;
import net.ilexiconn.nationsgui.forge.client.gui.faction.TabbedFactionListGUI$4;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.GUIGetHelpPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class TabbedFactionListGUI extends GuiScreen
{
    public static final List<GuiScreenTab> TABS = new ArrayList();
    public static int GUI_SCALE = 3;
    protected int xSize = 463;
    protected int ySize = 235;
    protected int guiLeft;
    protected int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static List<String> tooltipToDraw = new ArrayList();
    protected String hoveredAction = "";
    public static HashMap<String, Integer> tabIconsPositionY = new TabbedFactionListGUI$1();

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new GUIGetHelpPacket(this.getClass().getSimpleName())));
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_global");
        ClientEventHandler.STYLE.bindTexture("faction_list");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)this.guiTop, (float)(415 * GUI_SCALE), (float)(0 * GUI_SCALE), 19 * GUI_SCALE, 55 * GUI_SCALE, 19, 55, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        int i;
        GuiScreenTab type;

        for (i = 0; i < TABS.size(); ++i)
        {
            type = (GuiScreenTab)TABS.get(i);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int x = i % 9;
            int y = i / 9;

            if (this.getClass() == type.getClassReferent())
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + i * 18), (float)(434 * GUI_SCALE), (float)(0 * GUI_SCALE + i * 18 * GUI_SCALE), 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + i * 18 - 1), (float)(312 * GUI_SCALE), (float)(((Integer)tabIconsPositionY.get(type.getClassReferent().getSimpleName())).intValue() * GUI_SCALE), 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }
            else if (mouseX > this.guiLeft + 11 && mouseX < this.guiLeft + 11 + 19 && mouseY > this.guiTop + 1 + i * 18 && mouseY < this.guiTop + 1 + i * 18 + 19)
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + i * 18 - 1), (float)(274 * GUI_SCALE), (float)(((Integer)tabIconsPositionY.get(type.getClassReferent().getSimpleName())).intValue() * GUI_SCALE), 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }
            else
            {
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 11), (float)(this.guiTop + i * 18 - 1), (float)(293 * GUI_SCALE), (float)(((Integer)tabIconsPositionY.get(type.getClassReferent().getSimpleName())).intValue() * GUI_SCALE), 19 * GUI_SCALE, 18 * GUI_SCALE, 19, 18, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }
        }

        for (i = 0; i < TABS.size(); ++i)
        {
            type = (GuiScreenTab)TABS.get(i);

            if (mouseX > this.guiLeft + 11 && mouseX < this.guiLeft + 11 + 19 && mouseY > this.guiTop + 1 + i * 19 && mouseY < this.guiTop + 1 + i * 19 + 19)
            {
                this.drawHoveringText(Arrays.asList(new String[] {I18n.getString("faction.tab." + type.getClassReferent().getSimpleName())}), mouseX, mouseY, this.fontRenderer);
            }
        }

        if (tooltipToDraw != null && !tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    public abstract void drawScreen(int var1, int var2);

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        ModernGui.mouseClickedCommon(this, mouseX, mouseY, mouseButton);

        if (mouseButton == 0)
        {
            for (int i = 0; i < TABS.size(); ++i)
            {
                GuiScreenTab type = (GuiScreenTab)TABS.get(i);

                if (mouseX >= this.guiLeft + 11 && mouseX <= this.guiLeft + 11 + 19 && mouseY >= this.guiTop + 1 + i * 19 && mouseY <= this.guiTop + 1 + i * 19 + 19)
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

            if (mouseX > this.guiLeft + 445 && mouseX < this.guiLeft + 445 + 8 && mouseY > this.guiTop + 9 && mouseY < this.guiTop + 9 + 8)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    public static void initTabs()
    {
        TABS.clear();
        TABS.add(new TabbedFactionListGUI$2());
        TABS.add(new TabbedFactionListGUI$3());
        TABS.add(new TabbedFactionListGUI$4());
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
}
