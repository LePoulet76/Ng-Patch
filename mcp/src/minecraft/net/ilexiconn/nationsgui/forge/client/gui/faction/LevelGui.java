package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class LevelGui extends GuiScreen
{
    private String attFaction;
    private String defFaction;
    protected int xSize = 226;
    protected int ySize = 248;
    private int guiLeft;
    private int guiTop;
    private RenderItem itemRenderer = new RenderItem();
    private GuiScrollBarFaction scrollBarActual;
    private GuiScrollBarFaction scrollBarNext;
    private GuiScreen guiFrom;
    private HashMap<Integer, List<String>> rewards = new HashMap();
    private int currentLevel;
    private int currentScore;
    private int currentPalier;
    private int nextPallier;
    private int scoreActuelPalier;
    private int scoreNextPalier;

    public LevelGui(GuiScreen guiFrom)
    {
        this.guiFrom = guiFrom;
        this.rewards.put(Integer.valueOf(0), Arrays.asList(new String[] {"empire#false", "members#40", "relations#10", "chest#9", "warps#0", "missiles#0", "colonies#0"}));
        this.rewards.put(Integer.valueOf(10), Arrays.asList(new String[] {"empire#false", "members#70", "relations#15", "chest#9", "warps#0", "missiles#0", "colonies#0"}));
        this.rewards.put(Integer.valueOf(20), Arrays.asList(new String[] {"empire#false", "members#100", "relations#20", "chest#18", "warps#1", "missiles#1", "colonies#0"}));
        this.rewards.put(Integer.valueOf(30), Arrays.asList(new String[] {"empire#false", "members#140", "relations#25", "chest#18", "warps#1", "missiles#1", "colonies#0"}));
        this.rewards.put(Integer.valueOf(40), Arrays.asList(new String[] {"empire#false", "members#180", "relations#30", "chest#27", "warps#2", "missiles#1", "colonies#0"}));
        this.rewards.put(Integer.valueOf(50), Arrays.asList(new String[] {"empire#false", "members#220", "relations#35", "chest#36", "warps#2", "missiles#2", "colonies#0"}));
        this.rewards.put(Integer.valueOf(60), Arrays.asList(new String[] {"empire#true", "members#280", "relations#40", "chest#45", "warps#3", "missiles#2", "colonies#3"}));
        this.rewards.put(Integer.valueOf(70), Arrays.asList(new String[] {"empire#true", "members#340", "relations#45", "chest#54", "warps#3", "missiles#3", "colonies#5"}));
        this.rewards.put(Integer.valueOf(80), Arrays.asList(new String[] {"empire#true", "members#inf", "relations#inf", "chest#54", "warps#4", "missiles#3", "colonies#8"}));
        this.currentLevel = Integer.parseInt((String)FactionGUI.factionInfos.get("level"));
        this.currentScore = Integer.parseInt((String)FactionGUI.factionInfos.get("score"));
        this.currentPalier = Math.min(80, this.currentLevel - this.currentLevel % 10);
        this.nextPallier = Math.min(80, this.currentPalier + 10);
        this.scoreActuelPalier = this.currentPalier * 151;
        this.scoreNextPalier = this.nextPallier * 151;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.scrollBarActual = new GuiScrollBarFaction((float)(this.guiLeft + 211), (float)(this.guiTop + 116), 47);
        this.scrollBarNext = new GuiScrollBarFaction((float)(this.guiLeft + 211), (float)(this.guiTop + 183), 47);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float par3)
    {
        this.drawDefaultBackground();
        Object tooltipToDraw = null;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        ClientEventHandler.STYLE.bindTexture("faction_level");
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.guiLeft, (float)this.guiTop, 0, 0, this.xSize, this.ySize, 512.0F, 512.0F, false);
        ClientEventHandler.STYLE.bindTexture("faction_level");

        if (mouseX >= this.guiLeft + 212 && mouseX <= this.guiLeft + 212 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10)
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 212), (float)(this.guiTop - 6), 46, 249, 9, 10, 512.0F, 512.0F, false);
        }
        else
        {
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 212), (float)(this.guiTop - 6), 46, 259, 9, 10, 512.0F, 512.0F, false);
        }

        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 13), (float)(this.guiTop + 44 + (int)((double)this.fontRenderer.getStringWidth(I18n.getString("faction.level.title") + " " + FactionGUI.factionInfos.get("name")) * 1.5D)), 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef((float)(-(this.guiLeft + 13)), (float)(-(this.guiTop + 44 + (int)((double)this.fontRenderer.getStringWidth(I18n.getString("faction.level.title") + " " + FactionGUI.factionInfos.get("name")) * 1.5D))), 0.0F);
        this.drawScaledString(I18n.getString("faction.level.title") + " \u00a77" + FactionGUI.factionInfos.get("name"), this.guiLeft + 13, this.guiTop + 44 + (int)((double)this.fontRenderer.getStringWidth(I18n.getString("faction.level.title") + " " + FactionGUI.factionInfos.get("name")) * 1.5D), 16777215, 1.5F, false, false);
        GL11.glPopMatrix();
        this.drawScaledString("\u00a77" + I18n.getString("faction.level.palier"), this.guiLeft + 96, this.guiTop + 25, 16777215, 0.8F, true, false);
        this.drawScaledString("\u00a77" + I18n.getString("faction.level.actual"), this.guiLeft + 96, this.guiTop + 35, 16777215, 0.8F, true, false);
        this.drawScaledString("\u00a7l" + this.currentPalier, this.guiLeft + 98, this.guiTop + 54, 16777215, 1.5F, true, false);
        this.drawScaledString("\u00a77" + I18n.getString("faction.level.next"), this.guiLeft + 161, this.guiTop + 25, 16777215, 0.8F, true, false);
        this.drawScaledString(this.nextPallier != this.currentPalier ? "\u00a72\u00a7l" + this.nextPallier : "\u00a77\u00a7l-", this.guiLeft + 163, this.guiTop + 47, 16777215, 2.0F, true, false);
        float progress = Math.min((float)(this.currentScore - this.scoreActuelPalier) / ((float)(this.scoreNextPalier - this.scoreActuelPalier) * 1.0F), 1.0F);
        Gui.drawRect(this.guiLeft + 56, this.guiTop + 80, (int)((float)(this.guiLeft + 56) + 150.0F * progress), this.guiTop + 83, -13849059);
        this.drawScaledString("\u00a77" + I18n.getString("faction.level.actual_level") + " : " + this.currentLevel, this.guiLeft + 131, this.guiTop + 89, 16777215, 0.8F, true, false);
        ClientEventHandler.STYLE.bindTexture("faction_level");
        ModernGui.drawModalRectWithCustomSizedTexture((float)(this.guiLeft + 45), (float)(this.guiTop + 114), 0, 285, 162, 77, 512.0F, 512.0F, false);
        this.drawScaledString(I18n.getString("faction.level.actual_advantages"), this.guiLeft + 46, this.guiTop + 104, 0, 1.0F, false, false);
        GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 114, 164, 52);
        int i;
        int offsetX;
        Float offsetY;

        for (i = 0; i < ((List)this.rewards.get(Integer.valueOf(this.currentPalier))).size(); ++i)
        {
            offsetX = this.guiLeft + 47;
            offsetY = Float.valueOf((float)(this.guiTop + 114 + i * 23) + this.getSlideActual());
            ClientEventHandler.STYLE.bindTexture("faction_level");
            ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 47, 114, 164, 23, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 2), (float)(offsetY.intValue() + 2), 94, 251, 17, 17, 512.0F, 512.0F, false);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 4), (float)(offsetY.intValue() + 4), 13 * i, 290, 13, 13, 512.0F, 512.0F, false);
            this.drawScaledString(I18n.getString("faction.level.label." + ((String)((List)this.rewards.get(Integer.valueOf(this.currentPalier))).get(i)).split("#")[0]), offsetX + 22, offsetY.intValue() + 4, 16777215, 0.8F, false, false);

            if (this.isNumeric(((String)((List)this.rewards.get(Integer.valueOf(this.currentPalier))).get(i)).split("#")[1]))
            {
                this.drawScaledString("\u00a77" + ((String)((List)this.rewards.get(Integer.valueOf(this.currentPalier))).get(i)).split("#")[1], offsetX + 22, offsetY.intValue() + 12, 16777215, 0.8F, false, false);
            }
            else
            {
                this.drawScaledString(I18n.getString("faction.level.label." + ((String)((List)this.rewards.get(Integer.valueOf(this.currentPalier))).get(i)).split("#")[1]), offsetX + 22, offsetY.intValue() + 12, 16777215, 0.8F, false, false);
            }
        }

        GUIUtils.endGLScissor();

        if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 54)
        {
            this.scrollBarActual.draw(mouseX, mouseY);
        }

        this.drawScaledString(I18n.getString("faction.level.next_advantages"), this.guiLeft + 46, this.guiTop + 171, 0, 1.0F, false, false);

        if (this.nextPallier != this.currentPalier)
        {
            GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 181, 164, 52);

            for (i = 0; i < ((List)this.rewards.get(Integer.valueOf(this.nextPallier))).size(); ++i)
            {
                offsetX = this.guiLeft + 47;
                offsetY = Float.valueOf((float)(this.guiTop + 181 + i * 23) + this.getSlideNext());
                ClientEventHandler.STYLE.bindTexture("faction_level");
                ModernGui.drawModalRectWithCustomSizedTexture((float)offsetX, (float)offsetY.intValue(), 47, 114, 164, 23, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 2), (float)(offsetY.intValue() + 2), 94, 251, 17, 17, 512.0F, 512.0F, false);
                ModernGui.drawModalRectWithCustomSizedTexture((float)(offsetX + 4), (float)(offsetY.intValue() + 4), 13 * i, 290, 13, 13, 512.0F, 512.0F, false);
                this.drawScaledString(I18n.getString("faction.level.label." + ((String)((List)this.rewards.get(Integer.valueOf(this.nextPallier))).get(i)).split("#")[0]), offsetX + 22, offsetY.intValue() + 4, 16777215, 0.8F, false, false);

                if (this.isNumeric(((String)((List)this.rewards.get(Integer.valueOf(this.nextPallier))).get(i)).split("#")[1]))
                {
                    this.drawScaledString("\u00a77" + ((String)((List)this.rewards.get(Integer.valueOf(this.nextPallier))).get(i)).split("#")[1], offsetX + 22, offsetY.intValue() + 12, 16777215, 0.8F, false, false);
                }
                else
                {
                    this.drawScaledString(I18n.getString("faction.level.label." + ((String)((List)this.rewards.get(Integer.valueOf(this.nextPallier))).get(i)).split("#")[1]), offsetX + 22, offsetY.intValue() + 12, 16777215, 0.8F, false, false);
                }
            }

            GUIUtils.endGLScissor();

            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 180 && mouseY <= this.guiTop + 180 + 54)
            {
                this.scrollBarNext.draw(mouseX, mouseY);
            }
        }

        GL11.glEnable(GL11.GL_LIGHTING);
        RenderHelper.enableStandardItemLighting();
    }

    private float getSlideActual()
    {
        return ((List)this.rewards.get(Integer.valueOf(this.currentPalier))).size() > 2 ? (float)(-(((List)this.rewards.get(Integer.valueOf(this.currentPalier))).size() - 2) * 23) * this.scrollBarActual.getSliderValue() : 0.0F;
    }

    private float getSlideNext()
    {
        return ((List)this.rewards.get(Integer.valueOf(this.currentPalier))).size() > 2 ? (float)(-(((List)this.rewards.get(Integer.valueOf(this.currentPalier))).size() - 2) * 23) * this.scrollBarNext.getSliderValue() : 0.0F;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (mouseButton == 0 && mouseX > this.guiLeft + 212 && mouseX < this.guiLeft + 212 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10)
        {
            this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            this.mc.displayGuiScreen(this.guiFrom);
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
        if (str != null && str.length() != 0)
        {
            char[] var2 = str.toCharArray();
            int var3 = var2.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                char c = var2[var4];

                if (!Character.isDigit(c))
                {
                    return false;
                }
            }

            if (Integer.parseInt(str) < 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}
