package net.ilexiconn.nationsgui.forge.client.gui.warzone;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzoneTPPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.WarzonesDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class WarzonesGui_OLD extends GuiScreen
{
    protected int xSize = 289;
    protected int ySize = 172;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    public static HashMap<String, String> bateauInfos = new HashMap();
    public static HashMap<String, String> petrolInfos = new HashMap();
    public static boolean loaded = false;
    public static String rank = "";
    public static int tpLeft = 0;

    public WarzonesGui_OLD()
    {
        loaded = false;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzonesDataPacket()));
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
        ClientEventHandler.STYLE.bindTexture("warzones");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 148), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 148)), 0.0F);
        this.drawScaledString(I18n.getString("warzones.title"), this.guiLeft + 14, this.guiTop + 148, 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        Object tooltipToDraw = new ArrayList();

        if (mouseX >= this.guiLeft + 278 && mouseX <= this.guiLeft + 278 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10)
        {
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 110, 186, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 278), (float)(this.guiTop - 8), 110, 176, 9, 10, 512.0F, 512.0F, false);
        }

        ClientEventHandler.STYLE.bindTexture("warzones");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 25), (float)(this.guiTop + 30), 23, 213, 29, 30, 512.0F, 512.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 25 + 9), (float)(this.guiTop + 30 + 9), 2, 250, 13, 12, 512.0F, 512.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23), (float)(this.guiTop + 61), 0, 213, 23, 30, 512.0F, 512.0F, false);
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft - 23 + 8), (float)(this.guiTop + 61 + 10), 24, 252, 9, 10, 512.0F, 512.0F, false);

        if (loaded && bateauInfos != null && petrolInfos != null)
        {
            ClientEventHandler.STYLE.bindTexture("warzones");
            this.drawScaledString("Warzone bateau", this.guiLeft + 57, this.guiTop + 80, 16777215, 1.0F, false, true);
            String bonus = "Bonus " + (String)bateauInfos.get("type");
            this.drawScaledString("Bonus " + (String)bateauInfos.get("type"), this.guiLeft + 57, this.guiTop + 90, 11842740, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 57 + this.fontRenderer.getStringWidth(bonus) + 5), (float)(this.guiTop + 90), 96, 175, 10, 11, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 57 + this.fontRenderer.getStringWidth(bonus) + 5 && mouseX <= this.guiLeft + 57 + this.fontRenderer.getStringWidth(bonus) + 5 + 10 && mouseY >= this.guiTop + 90 && mouseY <= this.guiTop + 90 + 11)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("warzones.infos." + (String)bateauInfos.get("type") + "_1"), I18n.getString("warzones.infos." + (String)bateauInfos.get("type") + "_2"), I18n.getString("warzones.infos." + (String)bateauInfos.get("type") + "_3")});
            }

            this.drawScaledString(I18n.getString("warzones.claim_by"), this.guiLeft + 57, this.guiTop + 105, 11842740, 1.0F, false, false);
            this.drawScaledString(((String)bateauInfos.get("factionName")).replace('&', '\u00a7'), this.guiLeft + 57, this.guiTop + 115, 16777215, 1.0F, false, false);
            Double progress = Double.valueOf(Double.parseDouble((String)bateauInfos.get("percent")));
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 56), (float)(this.guiTop + 127), 0, 207, (int)(94.0D * (progress.doubleValue() / 100.0D)), 4, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 127 && mouseY <= this.guiTop + 131)
            {
                tooltipToDraw = Arrays.asList(new String[] {"\u00a77" + (String)bateauInfos.get("percent") + "%"});
            }

            if (((String)bateauInfos.get("tpLeft")).equals("0"))
            {
                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 56), (float)(this.guiTop + 133), 0, 190, 94, 15, 512.0F, 512.0F, false);
            }

            if (mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148)
            {
                if (!((String)bateauInfos.get("tpLeft")).equals("0"))
                {
                    if (Integer.parseInt((String)bateauInfos.get("tpLeft")) <= 7)
                    {
                        tooltipToDraw = Arrays.asList(new String[] {"\u00a7a" + (String)bateauInfos.get("tpLeft") + " " + I18n.getString("warzones.teleport_remaining")});
                    }
                }
                else
                {
                    tooltipToDraw = Arrays.asList(new String[] {"\u00a7c" + I18n.getString("warzones.teleport_unavailable")});
                }

                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 56), (float)(this.guiTop + 133), 0, 190, 94, 15, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.getString("warzones.teleport"), this.guiLeft + 103, this.guiTop + 137, 16777215, 1.0F, true, false);
            this.drawScaledString("Warzone petrol", this.guiLeft + 175, this.guiTop + 80, 16777215, 1.0F, false, true);
            bonus = "Bonus " + (String)petrolInfos.get("type");
            this.drawScaledString(bonus, this.guiLeft + 175, this.guiTop + 90, 11842740, 1.0F, false, false);
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 175 + this.fontRenderer.getStringWidth(bonus) + 5), (float)(this.guiTop + 90), 96, 175, 10, 11, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 175 + this.fontRenderer.getStringWidth(bonus) + 5 && mouseX <= this.guiLeft + 175 + this.fontRenderer.getStringWidth(bonus) + 5 + 10 && mouseY >= this.guiTop + 90 && mouseY <= this.guiTop + 90 + 11)
            {
                tooltipToDraw = Arrays.asList(new String[] {I18n.getString("warzones.infos." + (String)petrolInfos.get("type") + "_1"), I18n.getString("warzones.infos." + (String)petrolInfos.get("type") + "_2"), I18n.getString("warzones.infos." + (String)petrolInfos.get("type") + "_3")});
            }

            this.drawScaledString(I18n.getString("warzones.claim_by"), this.guiLeft + 175, this.guiTop + 105, 11842740, 1.0F, false, false);
            this.drawScaledString(((String)petrolInfos.get("factionName")).replace('&', '\u00a7'), this.guiLeft + 175, this.guiTop + 115, 16777215, 1.0F, false, false);
            progress = Double.valueOf(Double.parseDouble((String)petrolInfos.get("percent")));
            ClientEventHandler.STYLE.bindTexture("warzones");
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 174), (float)(this.guiTop + 127), 0, 207, (int)(94.0D * (progress.doubleValue() / 100.0D)), 4, 512.0F, 512.0F, false);

            if (mouseX >= this.guiLeft + 174 && mouseX <= this.guiLeft + 268 && mouseY >= this.guiTop + 127 && mouseY <= this.guiTop + 131)
            {
                tooltipToDraw = Arrays.asList(new String[] {"\u00a77" + (String)petrolInfos.get("percent") + "%"});
            }

            if (((String)petrolInfos.get("tpLeft")).equals("0"))
            {
                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 174), (float)(this.guiTop + 133), 0, 190, 94, 15, 512.0F, 512.0F, false);
            }

            if (mouseX >= this.guiLeft + 174 && mouseX <= this.guiLeft + 268 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148)
            {
                if (!((String)petrolInfos.get("tpLeft")).equals("0"))
                {
                    if (Integer.parseInt((String)petrolInfos.get("tpLeft")) <= 7)
                    {
                        tooltipToDraw = Arrays.asList(new String[] {"\u00a7a" + (String)petrolInfos.get("tpLeft") + " " + I18n.getString("warzones.teleport_remaining")});
                    }
                }
                else
                {
                    tooltipToDraw = Arrays.asList(new String[] {"\u00a7c" + I18n.getString("warzones.teleport_unavailable")});
                }

                ClientEventHandler.STYLE.bindTexture("warzones");
                ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 174), (float)(this.guiTop + 133), 0, 190, 94, 15, 512.0F, 512.0F, false);
            }

            this.drawScaledString(I18n.getString("warzones.teleport"), this.guiLeft + 221, this.guiTop + 137, 16777215, 1.0F, true, false);
        }

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
            if (mouseX > this.guiLeft + 278 && mouseX < this.guiLeft + 278 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }

            if (mouseX > this.guiLeft - 23 && mouseX < this.guiLeft - 23 + 23 && mouseY > this.guiTop + 61 && mouseY < this.guiTop + 61 + 30)
            {
                Minecraft.getMinecraft().displayGuiScreen(new WarzonesLeaderboardGui_OLD());
            }

            if (bateauInfos != null && !((String)bateauInfos.get("tpLeft")).equals("0") && mouseX >= this.guiLeft + 56 && mouseX <= this.guiLeft + 150 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzoneTPPacket("bateau")));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
            }
            else if (petrolInfos != null && !((String)petrolInfos.get("tpLeft")).equals("0") && mouseX >= this.guiLeft + 174 && mouseX <= this.guiLeft + 268 && mouseY >= this.guiTop + 133 && mouseY <= this.guiTop + 148)
            {
                this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new WarzoneTPPacket("petrol")));
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)null);
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
