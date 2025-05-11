/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.faction;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBarFaction;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class LevelGui
extends GuiScreen {
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

    public LevelGui(GuiScreen guiFrom) {
        this.guiFrom = guiFrom;
        this.rewards.put(0, Arrays.asList("empire#false", "members#40", "relations#10", "chest#9", "warps#0", "missiles#0", "colonies#0"));
        this.rewards.put(10, Arrays.asList("empire#false", "members#70", "relations#15", "chest#9", "warps#0", "missiles#0", "colonies#0"));
        this.rewards.put(20, Arrays.asList("empire#false", "members#100", "relations#20", "chest#18", "warps#1", "missiles#1", "colonies#0"));
        this.rewards.put(30, Arrays.asList("empire#false", "members#140", "relations#25", "chest#18", "warps#1", "missiles#1", "colonies#0"));
        this.rewards.put(40, Arrays.asList("empire#false", "members#180", "relations#30", "chest#27", "warps#2", "missiles#1", "colonies#0"));
        this.rewards.put(50, Arrays.asList("empire#false", "members#220", "relations#35", "chest#36", "warps#2", "missiles#2", "colonies#0"));
        this.rewards.put(60, Arrays.asList("empire#true", "members#280", "relations#40", "chest#45", "warps#3", "missiles#2", "colonies#3"));
        this.rewards.put(70, Arrays.asList("empire#true", "members#340", "relations#45", "chest#54", "warps#3", "missiles#3", "colonies#5"));
        this.rewards.put(80, Arrays.asList("empire#true", "members#inf", "relations#inf", "chest#54", "warps#4", "missiles#3", "colonies#8"));
        this.currentLevel = Integer.parseInt((String)FactionGUI.factionInfos.get("level"));
        this.currentScore = Integer.parseInt((String)FactionGUI.factionInfos.get("score"));
        this.currentPalier = Math.min(80, this.currentLevel - this.currentLevel % 10);
        this.nextPallier = Math.min(80, this.currentPalier + 10);
        this.scoreActuelPalier = this.currentPalier * 151;
        this.scoreNextPalier = this.nextPallier * 151;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.scrollBarActual = new GuiScrollBarFaction(this.guiLeft + 211, this.guiTop + 116, 47);
        this.scrollBarNext = new GuiScrollBarFaction(this.guiLeft + 211, this.guiTop + 183, 47);
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        Float offsetY;
        int offsetX;
        int i;
        this.func_73873_v_();
        Object tooltipToDraw = null;
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("faction_level");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        ClientEventHandler.STYLE.bindTexture("faction_level");
        if (mouseX >= this.guiLeft + 212 && mouseX <= this.guiLeft + 212 + 9 && mouseY >= this.guiTop - 6 && mouseY <= this.guiTop - 6 + 10) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 212, this.guiTop - 6, 46, 249, 9, 10, 512.0f, 512.0f, false);
        } else {
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 212, this.guiTop - 6, 46, 259, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 13), (float)(this.guiTop + 44 + (int)((double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.level.title") + " " + FactionGUI.factionInfos.get("name")) * 1.5)), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 13)), (float)(-(this.guiTop + 44 + (int)((double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.level.title") + " " + FactionGUI.factionInfos.get("name")) * 1.5))), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"faction.level.title") + " \u00a77" + FactionGUI.factionInfos.get("name"), this.guiLeft + 13, this.guiTop + 44 + (int)((double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"faction.level.title") + " " + FactionGUI.factionInfos.get("name")) * 1.5), 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.level.palier"), this.guiLeft + 96, this.guiTop + 25, 0xFFFFFF, 0.8f, true, false);
        this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.level.actual"), this.guiLeft + 96, this.guiTop + 35, 0xFFFFFF, 0.8f, true, false);
        this.drawScaledString("\u00a7l" + this.currentPalier, this.guiLeft + 98, this.guiTop + 54, 0xFFFFFF, 1.5f, true, false);
        this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.level.next"), this.guiLeft + 161, this.guiTop + 25, 0xFFFFFF, 0.8f, true, false);
        this.drawScaledString(this.nextPallier != this.currentPalier ? "\u00a72\u00a7l" + this.nextPallier : "\u00a77\u00a7l-", this.guiLeft + 163, this.guiTop + 47, 0xFFFFFF, 2.0f, true, false);
        float progress = Math.min((float)(this.currentScore - this.scoreActuelPalier) / ((float)(this.scoreNextPalier - this.scoreActuelPalier) * 1.0f), 1.0f);
        Gui.func_73734_a((int)(this.guiLeft + 56), (int)(this.guiTop + 80), (int)((int)((float)(this.guiLeft + 56) + 150.0f * progress)), (int)(this.guiTop + 83), (int)-13849059);
        this.drawScaledString("\u00a77" + I18n.func_135053_a((String)"faction.level.actual_level") + " : " + this.currentLevel, this.guiLeft + 131, this.guiTop + 89, 0xFFFFFF, 0.8f, true, false);
        ClientEventHandler.STYLE.bindTexture("faction_level");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 45, this.guiTop + 114, 0, 285, 162, 77, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"faction.level.actual_advantages"), this.guiLeft + 46, this.guiTop + 104, 0, 1.0f, false, false);
        GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 114, 164, 52);
        for (i = 0; i < this.rewards.get(this.currentPalier).size(); ++i) {
            offsetX = this.guiLeft + 47;
            offsetY = Float.valueOf((float)(this.guiTop + 114 + i * 23) + this.getSlideActual());
            ClientEventHandler.STYLE.bindTexture("faction_level");
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 47, 114, 164, 23, 512.0f, 512.0f, false);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue() + 2, 94, 251, 17, 17, 512.0f, 512.0f, false);
            ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 4, 13 * i, 290, 13, 13, 512.0f, 512.0f, false);
            this.drawScaledString(I18n.func_135053_a((String)("faction.level.label." + this.rewards.get(this.currentPalier).get(i).split("#")[0])), offsetX + 22, offsetY.intValue() + 4, 0xFFFFFF, 0.8f, false, false);
            if (this.isNumeric(this.rewards.get(this.currentPalier).get(i).split("#")[1])) {
                this.drawScaledString("\u00a77" + this.rewards.get(this.currentPalier).get(i).split("#")[1], offsetX + 22, offsetY.intValue() + 12, 0xFFFFFF, 0.8f, false, false);
                continue;
            }
            this.drawScaledString(I18n.func_135053_a((String)("faction.level.label." + this.rewards.get(this.currentPalier).get(i).split("#")[1])), offsetX + 22, offsetY.intValue() + 12, 0xFFFFFF, 0.8f, false, false);
        }
        GUIUtils.endGLScissor();
        if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 113 && mouseY <= this.guiTop + 113 + 54) {
            this.scrollBarActual.draw(mouseX, mouseY);
        }
        this.drawScaledString(I18n.func_135053_a((String)"faction.level.next_advantages"), this.guiLeft + 46, this.guiTop + 171, 0, 1.0f, false, false);
        if (this.nextPallier != this.currentPalier) {
            GUIUtils.startGLScissor(this.guiLeft + 47, this.guiTop + 181, 164, 52);
            for (i = 0; i < this.rewards.get(this.nextPallier).size(); ++i) {
                offsetX = this.guiLeft + 47;
                offsetY = Float.valueOf((float)(this.guiTop + 181 + i * 23) + this.getSlideNext());
                ClientEventHandler.STYLE.bindTexture("faction_level");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX, offsetY.intValue(), 47, 114, 164, 23, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 2, offsetY.intValue() + 2, 94, 251, 17, 17, 512.0f, 512.0f, false);
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 4, offsetY.intValue() + 4, 13 * i, 290, 13, 13, 512.0f, 512.0f, false);
                this.drawScaledString(I18n.func_135053_a((String)("faction.level.label." + this.rewards.get(this.nextPallier).get(i).split("#")[0])), offsetX + 22, offsetY.intValue() + 4, 0xFFFFFF, 0.8f, false, false);
                if (this.isNumeric(this.rewards.get(this.nextPallier).get(i).split("#")[1])) {
                    this.drawScaledString("\u00a77" + this.rewards.get(this.nextPallier).get(i).split("#")[1], offsetX + 22, offsetY.intValue() + 12, 0xFFFFFF, 0.8f, false, false);
                    continue;
                }
                this.drawScaledString(I18n.func_135053_a((String)("faction.level.label." + this.rewards.get(this.nextPallier).get(i).split("#")[1])), offsetX + 22, offsetY.intValue() + 12, 0xFFFFFF, 0.8f, false, false);
            }
            GUIUtils.endGLScissor();
            if (mouseX >= this.guiLeft + 46 && mouseX <= this.guiLeft + 46 + 170 && mouseY >= this.guiTop + 180 && mouseY <= this.guiTop + 180 + 54) {
                this.scrollBarNext.draw(mouseX, mouseY);
            }
        }
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
    }

    private float getSlideActual() {
        return this.rewards.get(this.currentPalier).size() > 2 ? (float)(-(this.rewards.get(this.currentPalier).size() - 2) * 23) * this.scrollBarActual.getSliderValue() : 0.0f;
    }

    private float getSlideNext() {
        return this.rewards.get(this.currentPalier).size() > 2 ? (float)(-(this.rewards.get(this.currentPalier).size() - 2) * 23) * this.scrollBarNext.getSliderValue() : 0.0f;
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX > this.guiLeft + 212 && mouseX < this.guiLeft + 212 + 9 && mouseY > this.guiTop - 6 && mouseY < this.guiTop - 6 + 10) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            this.field_73882_e.func_71373_a(this.guiFrom);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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

    public boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return Integer.parseInt(str) >= 0;
    }
}

