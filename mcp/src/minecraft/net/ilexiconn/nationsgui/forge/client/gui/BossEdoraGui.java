package net.ilexiconn.nationsgui.forge.client.gui;

import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BossEdoraGUIDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class BossEdoraGui extends GuiScreen
{
    private static final double A = 0.00885D;
    private static final double B = -0.00865D;
    private static final double C = 135.25D;
    private static final double D = 0.00515D;
    private static final double E = 0.00515D;
    private static final double F = 92.25D;
    public static int GUI_SCALE = 3;
    public static CFontRenderer dg23 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(23));
    public static HashMap<String, Object> data;
    public String hoveredAction = "";
    public List<String> tooltipToDraw = new ArrayList();
    public RenderItem itemRenderer = new RenderItem();
    protected int xSize = 460;
    protected int ySize = 202;
    private int guiLeft;
    private int guiTop;
    private boolean forceBoss;

    public BossEdoraGui(boolean forceBoss)
    {
        this.forceBoss = forceBoss;
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new BossEdoraGUIDataPacket()));
        data = new HashMap();
    }

    public static int[] minecraftCoordonatesToGuiPosition(int minecraftX, int minecraftZ)
    {
        int guiX = (int)(0.00885D * (double)minecraftX + -0.00865D * (double)minecraftZ + 135.25D);
        int guiY = (int)(0.00515D * (double)minecraftX + 0.00515D * (double)minecraftZ + 92.25D);
        return new int[] {guiX, guiY};
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.hoveredAction = "";
        this.tooltipToDraw = new ArrayList();
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("background_dark");
        ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 1920 * GUI_SCALE, 1920 * GUI_SCALE, this.width, this.height, (float)(1920 * GUI_SCALE), (float)(1080 * GUI_SCALE), true);
        ClientEventHandler.STYLE.bindTexture("boss_edora");

        if (mouseX > this.width - 14 - 5 && mouseX < this.width - 14 - 5 + 14 && mouseY > 5 && mouseY < 19)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.width - 14 - 5), 5.0F, (float)(498 * GUI_SCALE), (float)(33 * GUI_SCALE), 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            this.hoveredAction = "close";
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.width - 14 - 5), 5.0F, (float)(483 * GUI_SCALE), (float)(33 * GUI_SCALE), 14 * GUI_SCALE, 14 * GUI_SCALE, 14, 14, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        }

        boolean isBossMode = data.containsKey("boss");
        ClientEventHandler.STYLE.bindTexture("boss_edora");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 24), (float)(0 * GUI_SCALE), (float)((isBossMode ? 180 : 0) * GUI_SCALE), 460 * GUI_SCALE, 177 * GUI_SCALE, 460, 177, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.orientation.north"), (float)(this.guiLeft + 190), (float)(this.guiTop + 55), 8684678, 0.5F, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.orientation.south"), (float)(this.guiLeft + 78), (float)(this.guiTop + 132), 8684678, 0.5F, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.orientation.west"), (float)(this.guiLeft + 80), (float)(this.guiTop + 52), 8684678, 0.5F, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.orientation.east"), (float)(this.guiLeft + 195), (float)(this.guiTop + 132), 8684678, 0.5F, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.title"), (float)(this.guiLeft + 135), (float)(this.guiTop + 3), 16777215, 0.75F, "center", true, "minecraftDungeons", 23);
        ClientEventHandler.STYLE.bindTexture("boss_edora");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 135) - dg23.getStringWidth(I18n.getString("gui.boss.edora.title")) * 0.75F / 2.0F - 10.0F, (float)(this.guiTop + 5), (float)((isBossMode ? 503 : 508) * GUI_SCALE), (float)(10 * GUI_SCALE), 4 * GUI_SCALE, 10 * GUI_SCALE, 4, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 135) + dg23.getStringWidth(I18n.getString("gui.boss.edora.title")) * 0.75F / 2.0F + 4.0F, (float)(this.guiTop + 5), (float)((isBossMode ? 503 : 508) * GUI_SCALE), (float)(0 * GUI_SCALE), 4 * GUI_SCALE, 10 * GUI_SCALE, 4, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.description"), (float)(this.guiLeft + 285), (float)(this.guiTop + 34), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
        ModernGui.drawSectionStringCustomFont(I18n.getString("gui.boss.edora.description." + (isBossMode ? "boss" : "autels")), (float)(this.guiLeft + 285), (float)(this.guiTop + 48), 8684678, 0.5F, "left", false, "georamaMedium", 24, 7, 320);
        int countNotCaptured = 0;
        int countCaptured = 0;
        int countInProgress = 0;

        if (!isBossMode && data.containsKey("autels"))
        {
            Iterator var13 = ((LinkedTreeMap)data.get("autels")).entrySet().iterator();

            while (var13.hasNext())
            {
                Entry var15 = (Entry)var13.next();
                LinkedTreeMap autel = (LinkedTreeMap)var15.getValue();
                int[] guiCoords1 = minecraftCoordonatesToGuiPosition(((Double)((ArrayList)autel.get("center")).get(0)).intValue(), ((Double)((ArrayList)autel.get("center")).get(1)).intValue());
                int capturePercent = ((Double)autel.get("capturePercent")).intValue();
                ClientEventHandler.STYLE.bindTexture("boss_edora");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + guiCoords1[0] - 4), (float)(this.guiTop + guiCoords1[1] - 4), (float)((capturePercent == 0 ? 486 : (capturePercent == 100 ? 504 : 495)) * GUI_SCALE), (float)(22 * GUI_SCALE), 8 * GUI_SCALE, 9 * GUI_SCALE, 8, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

                if (capturePercent < 100)
                {
                    ModernGui.drawScaledStringCustomFont((String)var15.getKey(), (float)(this.guiLeft + guiCoords1[0]) + 0.5F, (float)(this.guiTop + guiCoords1[1] - 3), 2565671, 0.5F, "center", false, "minecraftDungeons", 16);
                }

                if (capturePercent == 100)
                {
                    ++countCaptured;
                }
                else if (capturePercent == 0)
                {
                    ++countNotCaptured;
                }
                else
                {
                    ++countInProgress;
                }

                if (mouseX >= this.guiLeft + guiCoords1[0] - 4 && mouseX <= this.guiLeft + guiCoords1[0] + 4 && mouseY >= this.guiTop + guiCoords1[1] - 4 && mouseY <= this.guiTop + guiCoords1[1] + 4)
                {
                    if (!autel.containsKey("nextAvailableTime"))
                    {
                        this.tooltipToDraw = Arrays.asList(new String[] {"\u00a7fAutel " + var15.getKey(), "\u00a77Capture: \u00a7f" + capturePercent + "%", "", "\u00a77X:\u00a7b" + ((Double)((ArrayList)autel.get("center")).get(0)).intValue() + " \u00a77Z:\u00a7b" + ((Double)((ArrayList)autel.get("center")).get(1)).intValue()});
                    }
                    else
                    {
                        this.tooltipToDraw = Arrays.asList(new String[] {"\u00a7fAutel " + var15.getKey(), "\u00a77Disponible dans", "\u00a7f" + ModernGui.chronoTimeToStr(Long.valueOf(((Double)autel.get("nextAvailableTime")).longValue() - System.currentTimeMillis()), false), "", "\u00a77X:\u00a7b" + ((Double)((ArrayList)autel.get("center")).get(0)).intValue() + " \u00a77Z:\u00a7b" + ((Double)((ArrayList)autel.get("center")).get(1)).intValue()});
                    }
                }
            }
        }
        else if (isBossMode && data.containsKey("boss"))
        {
            LinkedTreeMap percent = (LinkedTreeMap)data.get("boss");
            int[] guiCoords = minecraftCoordonatesToGuiPosition(((Double)((ArrayList)percent.get("activePosition")).get(0)).intValue(), ((Double)((ArrayList)percent.get("activePosition")).get(1)).intValue());
            ClientEventHandler.STYLE.bindTexture("boss_edora");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + guiCoords[0] - 4), (float)(this.guiTop + guiCoords[1] - 4), (float)(477 * GUI_SCALE), (float)(22 * GUI_SCALE), 8 * GUI_SCALE, 9 * GUI_SCALE, 8, 9, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);

            if (mouseX >= this.guiLeft + guiCoords[0] - 4 && mouseX <= this.guiLeft + guiCoords[0] + 4 && mouseY >= this.guiTop + guiCoords[1] - 4 && mouseY <= this.guiTop + guiCoords[1] + 4)
            {
                this.tooltipToDraw = Arrays.asList(new String[] {"\u00a77X:\u00a7c" + ((Double)((ArrayList)percent.get("activePosition")).get(0)).intValue() + " \u00a77Z:\u00a7c" + ((Double)((ArrayList)percent.get("activePosition")).get(1)).intValue()});
            }
        }

        if (!isBossMode)
        {
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.legend"), (float)(this.guiLeft + 285), (float)(this.guiTop + 125), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.legend.not_captured"), (float)(this.guiLeft + 308), (float)(this.guiTop + 147), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(countNotCaptured + "", (float)(this.guiLeft + 445), (float)(this.guiTop + 147), 8684678, 0.5F, "right", false, "georamaSemiBold", 27);
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.legend.capture_inprogress"), (float)(this.guiLeft + 307), (float)(this.guiTop + 161), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(countInProgress + "", (float)(this.guiLeft + 445), (float)(this.guiTop + 161), 8684678, 0.5F, "right", false, "georamaSemiBold", 27);
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.legend.captured"), (float)(this.guiLeft + 307), (float)(this.guiTop + 175), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
            ModernGui.drawScaledStringCustomFont(countCaptured + "", (float)(this.guiLeft + 445), (float)(this.guiTop + 175), 8684678, 0.5F, "right", false, "georamaSemiBold", 27);
        }
        else
        {
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.legend"), (float)(this.guiLeft + 286), (float)(this.guiTop + 149), 16777215, 0.5F, "left", false, "georamaSemiBold", 28);
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.boss.edora.legend.boss"), (float)(this.guiLeft + 308), (float)(this.guiTop + 172), 16777215, 0.5F, "left", false, "georamaSemiBold", 25);
        }

        if (!isBossMode)
        {
            float var14 = (float)countCaptured / (float)(countCaptured + countInProgress + countNotCaptured);
            ClientEventHandler.STYLE.bindTexture("boss_edora");
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 10), (float)(this.guiTop + 178), (float)(11 * GUI_SCALE), (float)(368 * GUI_SCALE), (int)(234.0F * var14) * GUI_SCALE, 8 * GUI_SCALE, (int)(234.0F * var14), 8, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        }

        super.drawScreen(mouseX, mouseY, par3);

        if (!this.tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && this.hoveredAction.equals("close"))
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
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
