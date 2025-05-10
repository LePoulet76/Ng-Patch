package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.BonusesGui$1;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class BonusesGui extends GuiScreen
{
    public static int GUI_SCALE = 3;
    private float rotationAngle = 0.0F;
    private long lastUpdateTime = System.currentTimeMillis();
    public static LinkedHashMap<String, Integer> bonusIconX = new BonusesGui$1();
    public long lastMusicCheck = 0L;
    public String hoveredAction = "";
    protected int xSize = 481;
    protected int ySize = 245;
    private RenderItem itemRenderer = new RenderItem();
    private int guiLeft;
    private int guiTop;

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
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L)
        {
            this.lastMusicCheck = System.currentTimeMillis();

            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying())
            {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N4y5243GN4.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.getMinecraft().gameSettings.soundVolume * 0.15F);
                (new Thread(ClientProxy.commandPlayer)).start();
            }
        }

        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        long currentTime = System.currentTimeMillis();
        float deltaTime = (float)(currentTime - this.lastUpdateTime) / 1000.0F;
        this.lastUpdateTime = currentTime;
        float rotationSpeed = 10.0F;
        this.rotationAngle += rotationSpeed * deltaTime;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + this.ySize / 2), 0.0F);
        GL11.glRotatef(this.rotationAngle, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + this.xSize / 2)), (float)(-(this.guiTop + this.ySize / 2)), 0.0F);
        ClientEventHandler.STYLE.bindTexture("bonus");
        ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft - 63), (float)(this.guiTop - 181), 1719.0F, 0.0F, 1826, 1826, 1826 / GUI_SCALE, 1826 / GUI_SCALE, 3546.0F, 1825.0F, true);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        boolean hoveringClose = mouseX >= this.width - 22 && mouseX < this.width - 22 + 16 && mouseY >= 4 && mouseY < 20;
        ModernGui.drawScaledCustomSizeModalRect((float)(this.width - 22), 4.0F, (float)(465 * GUI_SCALE), (float)((hoveringClose ? 235 : 215) * GUI_SCALE), 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

        if (hoveringClose)
        {
            this.hoveredAction = "close";
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.bonus.title"), (float)(this.guiLeft + 240), (float)this.guiTop, 16761186, 1.0F, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.getString("gui.bonus.subtitle"), (float)(this.guiLeft + 240), (float)(this.guiTop + 20), 12434877, 0.5F, "center", false, "georamaMedium", 23);
        ClientEventHandler.STYLE.bindTexture("bonus");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)(this.guiTop + 40), 0.0F, 0.0F, 1444, 617, 1444 / GUI_SCALE, 617 / GUI_SCALE, 3546.0F, 1825.0F, false);
        long remainingTime = ClientData.bonusEndTime.longValue() - System.currentTimeMillis();
        long days = remainingTime / 86400000L;
        long hours = remainingTime % 86400000L / 3600000L;
        long minutes = remainingTime % 3600000L / 60000L;
        long seconds = remainingTime % 60000L / 1000L;
        String remainingTimeStr = I18n.getString("gui.bonus.remaining_time");
        remainingTimeStr = remainingTimeStr.replaceAll("<days>", String.valueOf(days));
        remainingTimeStr = remainingTimeStr.replaceAll("<hours>", String.valueOf(hours));
        remainingTimeStr = remainingTimeStr.replaceAll("<minutes>", String.valueOf(minutes));
        remainingTimeStr = remainingTimeStr.replaceAll("<seconds>", String.valueOf(seconds));
        ModernGui.drawScaledStringCustomFont(remainingTimeStr, (float)(this.guiLeft + 243), (float)this.guiTop + 45.5F, 6962944, 0.5F, "center", false, "georamaSemiBold", 23);

        for (int index = 0; index < 16; ++index)
        {
            int offsetX = this.guiLeft + 13 + index % 4 * 116;
            int offsetY = this.guiTop + 71 + index / 4 * 41;

            if (ClientData.bonuses.keySet().size() > index)
            {
                String bonus = ((String[])ClientData.bonuses.keySet().toArray(new String[0]))[index];
                float value = ((Float)ClientData.bonuses.get(bonus)).floatValue();
                ClientEventHandler.STYLE.bindTexture("bonus");
                ModernGui.drawScaledCustomSizeModalRect((float)offsetX, (float)offsetY, 0.0F, 641.0F, 315, 115, 315 / GUI_SCALE, 115 / GUI_SCALE, 3546.0F, 1825.0F, false);
                int iconX = bonusIconX.containsKey(bonus) ? ((Integer)bonusIconX.get(bonus)).intValue() : 0;
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 1), (float)offsetY, (float)iconX, 807.0F, 107, 107, 107 / GUI_SCALE, 107 / GUI_SCALE, 3546.0F, 1825.0F, false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("gui.bonus.label." + bonus), (float)(offsetX + 40), (float)(offsetY + 10), 16777215, 0.5F, "left", false, "georamaSemiBold", 23);
                float value_percent = value - 1.0F;
                value_percent *= 100.0F;
                ModernGui.drawScaledStringCustomFont(I18n.getString("gui.bonus.description." + bonus).replaceAll("<value_percent>", String.format("%.0f", new Object[] {Float.valueOf(value_percent)})).replaceAll("<value>", String.format("%.0f", new Object[] {Float.valueOf(value)})), (float)(offsetX + 40), (float)(offsetY + 18), 8421504, 0.5F, "left", false, "georamaMedium", 21);
            }
            else
            {
                ClientEventHandler.STYLE.bindTexture("bonus");
                ModernGui.drawScaledCustomSizeModalRect((float)offsetX, (float)offsetY, 315.0F, 641.0F, 315, 115, 315 / GUI_SCALE, 115 / GUI_SCALE, 3546.0F, 1825.0F, false);
                ModernGui.drawScaledCustomSizeModalRect((float)(offsetX + 1), (float)(offsetY + 1), 0.0F, 807.0F, 107, 107, 107 / GUI_SCALE, 107 / GUI_SCALE, 3546.0F, 1825.0F, false);
                ModernGui.drawScaledStringCustomFont(I18n.getString("gui.bonus.label.soon"), (float)(offsetX + 40), (float)(offsetY + 10), 4145221, 0.5F, "left", false, "georamaSemiBold", 23);
            }
        }

        super.drawScreen(mouseX, mouseY, par3);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying())
        {
            ClientProxy.commandPlayer.softClose();
        }

        super.onGuiClosed();
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
