package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RollbackAssaultStartPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RollbackAssaultGUI extends GuiScreen
{
    public static int GUI_SCALE = 3;
    public static HashMap<String, Object> data = new HashMap();
    public static CFontRenderer semiBold40 = ModernGui.getCustomFont("georamaSemiBold", Integer.valueOf(40));
    public static CFontRenderer dungeons18 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(18));
    public static CFontRenderer dungeons24 = ModernGui.getCustomFont("minecraftDungeons", Integer.valueOf(24));
    public static boolean loaded = false;
    public String hoveredAction = "";
    public int xSize = 357;
    public int ySize = 180;
    public RenderItem itemRenderer = new RenderItem();
    public int guiLeft;
    public int guiTop;
    public List<String> tooltipToDraw = new ArrayList();
    private double repairPercent = 0.01D;
    private double dollarsPercent = 0.5D;

    public RollbackAssaultGUI()
    {
        loaded = false;
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
        this.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.tooltipToDraw.clear();
        this.hoveredAction = "";
        ClientEventHandler.STYLE.bindTexture("faction_rollback");
        ModernGui.drawScaledCustomSizeModalRect((float)this.guiLeft, (float)this.guiTop, (float)(0 * GUI_SCALE), (float)(0 * GUI_SCALE), this.xSize * GUI_SCALE, this.ySize * GUI_SCALE, this.xSize, this.ySize, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), true);
        ClientEventHandler.STYLE.bindTexture("faction_rollback");

        if (mouseX >= this.guiLeft + 336 && mouseX <= this.guiLeft + 336 + 10 && mouseY >= this.guiTop + 12 && mouseY <= this.guiTop + 12 + 10)
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 336), (float)(this.guiTop + 12), (float)(379 * GUI_SCALE), (float)(19 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            this.hoveredAction = "close";
        }
        else
        {
            ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 336), (float)(this.guiTop + 12), (float)(379 * GUI_SCALE), (float)(8 * GUI_SCALE), 10 * GUI_SCALE, 10 * GUI_SCALE, 10, 10, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
        }

        ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.title").toUpperCase(), (float)(this.guiLeft + 13), (float)(this.guiTop + 10), 16316667, 0.75F, "left", false, "minecraftDungeons", 23);
        ModernGui.drawSectionStringCustomFont(I18n.getString("faction.rollback.desc"), (float)(this.guiLeft + 13), (float)(this.guiTop + 25), 12237530, 0.5F, "left", false, "georamaMedium", 24, 8, 500);

        if (loaded)
        {
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.informations").toUpperCase(), (float)(this.guiLeft + 22), (float)(this.guiTop + 62), 16316667, 0.5F, "left", false, "minecraftDungeons", 24);
            Date date = new Date(Long.parseLong((String)data.get("time")));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.assault_date").toUpperCase() + " :", (float)(this.guiLeft + 22), (float)(this.guiTop + 75), 7961015, 0.5F, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont(sdf.format(date), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.getString("faction.rollback.assault_date").toUpperCase() + " :") / 2.0F + 3.0F, (float)this.guiTop + 75.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.country_enemy").toUpperCase() + " :", (float)(this.guiLeft + 22), (float)(this.guiTop + 85), 7961015, 0.5F, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont((String)data.get("enemyName"), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.getString("faction.rollback.country_enemy").toUpperCase() + " :") / 2.0F + 3.0F, (float)this.guiTop + 85.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.block_count").toUpperCase() + " :", (float)(this.guiLeft + 22), (float)(this.guiTop + 105), 7961015, 0.5F, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {(Double)data.get("blocksCount")}), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.getString("faction.rollback.block_count").toUpperCase() + " :") / 2.0F + 3.0F, (float)this.guiTop + 105.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.price").toUpperCase() + " :", (float)(this.guiLeft + 22), (float)(this.guiTop + 115), 7961015, 0.5F, "left", false, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont("10$/bloc - 0.006 power/bloc", (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.getString("faction.rollback.price").toUpperCase() + " :") / 2.0F + 3.0F, (float)(this.guiTop + 115), 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.remaining_time").toUpperCase() + " :", (float)(this.guiLeft + 22), (float)(this.guiTop + 125), 7961015, 0.5F, "left", false, "minecraftDungeons", 18);
            long endTime = Long.parseLong((String)data.get("time")) + 3600000L;
            ModernGui.drawScaledStringCustomFont(endTime > System.currentTimeMillis() ? ModernGui.getFormattedTimeDiff(endTime, System.currentTimeMillis()) : I18n.getString("faction.rollback.expired"), (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.getString("faction.rollback.remaining_time").toUpperCase() + " :") / 2.0F + 3.0F, (float)(this.guiTop + 125), 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.malus").toUpperCase() + " :", (float)(this.guiLeft + 22), (float)(this.guiTop + 145), 7961015, 0.5F, "left", false, "minecraftDungeons", 18);
            double malus = data.get("countRepair") != null ? ((Double)data.get("countRepair")).doubleValue() / 1000.0D : 0.0D;
            ModernGui.drawScaledStringCustomFont("+" + String.format("%.0f", new Object[] {Double.valueOf(malus)}) + "% (+1%/1000 blocs)", (float)(this.guiLeft + 22) + dungeons18.getStringWidth(I18n.getString("faction.rollback.malus").toUpperCase() + " :") / 2.0F + 3.0F, (float)this.guiTop + 145.5F, 14606061, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {Double.valueOf(this.repairPercent * 100.0D)}) + "%", (float)(this.guiLeft + 189), (float)(this.guiTop + 61), 16316667, 0.5F, "left", false, "minecraftDungeons", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.repair_percent"), (float)(this.guiLeft + 190) + dungeons24.getStringWidth(String.format("%.0f", new Object[] {Double.valueOf(this.repairPercent * 100.0D)}) + "%") / 2.0F + 2.0F, (float)(this.guiTop + 64), 7961015, 0.5F, "left", false, "minecraftDungeons", 16);

            if (((Double)data.get("maxRepairPercent")).doubleValue() < 1.0D)
            {
                ClientEventHandler.STYLE.bindTexture("faction_rollback");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 203) + 122.0F * ((Double)data.get("maxRepairPercent")).floatValue(), (float)(this.guiTop + 81), (367.0F + 122.0F * ((Double)data.get("maxRepairPercent")).floatValue()) * (float)GUI_SCALE, (float)(81 * GUI_SCALE), (int)(122.0F * (1.0F - ((Double)data.get("maxRepairPercent")).floatValue()) * (float)GUI_SCALE), 3 * GUI_SCALE, (int)(122.0F * (1.0F - ((Double)data.get("maxRepairPercent")).floatValue())), 3, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }

            double cursorPercentX = (double)(this.guiLeft + 203) + 122.0D * this.repairPercent - 3.0D;
            ClientEventHandler.STYLE.bindTexture("faction_rollback");
            ModernGui.drawScaledCustomSizeModalRect((float)cursorPercentX, (float)(this.guiTop + 75), (float)(367 * GUI_SCALE), (float)(51 * GUI_SCALE), 6 * GUI_SCALE, 15 * GUI_SCALE, 6, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

            if (mouseX >= this.guiLeft + 200 && mouseX <= this.guiLeft + 200 + 126 && mouseY >= this.guiTop + 75 && mouseY <= this.guiTop + 90)
            {
                this.hoveredAction = "cursorRepair";
            }

            double totalDollarsPrice = ((Double)data.get("blocksCount")).doubleValue() * this.repairPercent * 10.0D * (1.0D + malus / 100.0D);
            double priceDollars = totalDollarsPrice * this.dollarsPercent;
            double pricePower = totalDollarsPrice * (1.0D - this.dollarsPercent) / 10.0D * 0.006D;
            double cursorDollarsX = (double)(this.guiLeft + 203) + 122.0D * this.dollarsPercent - 3.0D;
            ClientEventHandler.STYLE.bindTexture("faction_rollback");
            ModernGui.drawScaledCustomSizeModalRect((float)cursorDollarsX, (float)(this.guiTop + 117), (float)(367 * GUI_SCALE), (float)(51 * GUI_SCALE), 6 * GUI_SCALE, 15 * GUI_SCALE, 6, 15, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);

            if (mouseX >= this.guiLeft + 200 && mouseX <= this.guiLeft + 200 + 126 && mouseY >= this.guiTop + 117 && mouseY <= this.guiTop + 117 + 15)
            {
                this.hoveredAction = "cursorDollars";
            }

            ModernGui.drawScaledStringCustomFont(String.format("%.0f", new Object[] {Double.valueOf(pricePower)}) + " Pow + " + String.format("%.0f", new Object[] {Double.valueOf(priceDollars)}) + "$", (float)(this.guiLeft + 189), (float)(this.guiTop + 103), 16316667, 0.5F, "left", false, "minecraftDungeons", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.repair_cost"), (float)(this.guiLeft + 190) + dungeons24.getStringWidth(String.format("%.0f", new Object[] {Double.valueOf(pricePower)}) + " Pow + " + String.format("%.0f", new Object[] {Double.valueOf(priceDollars)}) + "$") / 2.0F + 2.0F, (float)(this.guiTop + 106), 7961015, 0.5F, "left", false, "minecraftDungeons", 16);

            if (mouseX >= this.guiLeft + 184 && mouseX <= this.guiLeft + 184 + 160 && mouseY >= this.guiTop + 152 && mouseY <= this.guiTop + 152 + 17)
            {
                this.hoveredAction = "rollback";
                ClientEventHandler.STYLE.bindTexture("faction_rollback");
                ModernGui.drawScaledCustomSizeModalRect((float)(this.guiLeft + 184), (float)(this.guiTop + 152), (float)(184 * GUI_SCALE), (float)(211 * GUI_SCALE), 160 * GUI_SCALE, 17 * GUI_SCALE, 160, 17, (float)(512 * GUI_SCALE), (float)(512 * GUI_SCALE), false);
            }

            ModernGui.drawScaledStringCustomFont(I18n.getString("faction.rollback.repair_button").toUpperCase(), (float)(this.guiLeft + 184 + 80), (float)(this.guiTop + 156), 2434373, 0.5F, "center", false, "minecraftDungeons", 21);
        }

        super.drawScreen(mouseX, mouseY, par3);

        if (!this.tooltipToDraw.isEmpty())
        {
            this.drawHoveringText(this.tooltipToDraw, mouseX, mouseY, this.fontRenderer);
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0)
        {
            if (this.hoveredAction.equals("close"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (this.hoveredAction.equals("rollback"))
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                data.put("repairPercent", Double.valueOf(this.repairPercent));
                data.put("dollarsPercent", Double.valueOf(this.dollarsPercent));
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new RollbackAssaultStartPacket(data)));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int par2, int par3, long par4)
    {
        int min;
        int max;
        float percent;

        if (this.hoveredAction.equals("cursorRepair"))
        {
            min = this.guiLeft + 203;
            max = this.guiLeft + 203 + 122;
            percent = (float)(mouseX - min) / (float)(max - min);
            this.repairPercent = Math.min(((Double)data.get("maxRepairPercent")).doubleValue(), (double)Math.max(0.0F, percent));
        }
        else if (this.hoveredAction.equals("cursorDollars"))
        {
            min = this.guiLeft + 203;
            max = this.guiLeft + 203 + 122;
            percent = (float)(mouseX - min) / (float)(max - min);
            this.dollarsPercent = (double)Math.min(100.0F, Math.max(0.0F, percent));
        }

        super.mouseClickMove(mouseX, par2, par3, par4);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
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

    public boolean isNumeric(String str)
    {
        try
        {
            Double.parseDouble(str);
            return true;
        }
        catch (NumberFormatException var3)
        {
            return false;
        }
    }
}
