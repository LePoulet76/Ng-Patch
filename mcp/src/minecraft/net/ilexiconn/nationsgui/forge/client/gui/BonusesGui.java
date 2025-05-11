/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.LinkedHashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.util.SoundStreamer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class BonusesGui
extends GuiScreen {
    public static int GUI_SCALE = 3;
    private float rotationAngle = 0.0f;
    private long lastUpdateTime = System.currentTimeMillis();
    public static LinkedHashMap<String, Integer> bonusIconX = new LinkedHashMap<String, Integer>(){
        {
            this.put("none", 0);
            this.put("parrot", 107);
            this.put("hdv", 214);
            this.put("chestshop", 214);
            this.put("r&d", 321);
            this.put("atm", 428);
            this.put("xp", 535);
            this.put("lootbox", 642);
            this.put("notations", 749);
            this.put("skills", 856);
            this.put("enterprises", 963);
            this.put("tp_warzones", 1070);
            this.put("limit_warzone", 1070);
            this.put("boutique", 1284);
            this.put("resources", 1391);
            this.put("catalog", 1498);
        }
    };
    public long lastMusicCheck = 0L;
    public String hoveredAction = "";
    protected int xSize = 481;
    protected int ySize = 245;
    private RenderItem itemRenderer = new RenderItem();
    private int guiLeft;
    private int guiTop;

    public void func_73866_w_() {
        super.func_73866_w_();
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        if (System.currentTimeMillis() - this.lastMusicCheck > 1000L) {
            this.lastMusicCheck = System.currentTimeMillis();
            if (ClientProxy.commandPlayer == null || !ClientProxy.commandPlayer.isPlaying()) {
                ClientProxy.commandPlayer = new SoundStreamer("https://static.nationsglory.fr/N4y5243GN4.mp3");
                ClientProxy.commandPlayer.setVolume(Minecraft.func_71410_x().field_71474_y.field_74340_b * 0.15f);
                new Thread(ClientProxy.commandPlayer).start();
            }
        }
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        long currentTime = System.currentTimeMillis();
        float deltaTime = (float)(currentTime - this.lastUpdateTime) / 1000.0f;
        this.lastUpdateTime = currentTime;
        float rotationSpeed = 10.0f;
        this.rotationAngle += rotationSpeed * deltaTime;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + this.xSize / 2), (float)(this.guiTop + this.ySize / 2), (float)0.0f);
        GL11.glRotatef((float)this.rotationAngle, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + this.xSize / 2)), (float)(-(this.guiTop + this.ySize / 2)), (float)0.0f);
        ClientEventHandler.STYLE.bindTexture("bonus");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft - 63, this.guiTop - 181, 1719.0f, 0.0f, 1826, 1826, 1826 / GUI_SCALE, 1826 / GUI_SCALE, 3546.0f, 1825.0f, true);
        GL11.glPopMatrix();
        ClientEventHandler.STYLE.bindTexture("faction_plots_3");
        boolean hoveringClose = mouseX >= this.field_73880_f - 22 && mouseX < this.field_73880_f - 22 + 16 && mouseY >= 4 && mouseY < 20;
        ModernGui.drawScaledCustomSizeModalRect(this.field_73880_f - 22, 4.0f, 465 * GUI_SCALE, (hoveringClose ? 235 : 215) * GUI_SCALE, 16 * GUI_SCALE, 16 * GUI_SCALE, 16, 16, 512 * GUI_SCALE, 512 * GUI_SCALE, false);
        if (hoveringClose) {
            this.hoveredAction = "close";
        }
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.bonus.title"), this.guiLeft + 240, this.guiTop, 16761186, 1.0f, "center", true, "minecraftDungeons", 23);
        ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.bonus.subtitle"), this.guiLeft + 240, this.guiTop + 20, 0xBDBDBD, 0.5f, "center", false, "georamaMedium", 23);
        ClientEventHandler.STYLE.bindTexture("bonus");
        ModernGui.drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 40, 0.0f, 0.0f, 1444, 617, 1444 / GUI_SCALE, 617 / GUI_SCALE, 3546.0f, 1825.0f, false);
        long remainingTime = ClientData.bonusEndTime - System.currentTimeMillis();
        long days = remainingTime / 86400000L;
        long hours = remainingTime % 86400000L / 3600000L;
        long minutes = remainingTime % 3600000L / 60000L;
        long seconds = remainingTime % 60000L / 1000L;
        String remainingTimeStr = I18n.func_135053_a((String)"gui.bonus.remaining_time");
        remainingTimeStr = remainingTimeStr.replaceAll("<days>", String.valueOf(days));
        remainingTimeStr = remainingTimeStr.replaceAll("<hours>", String.valueOf(hours));
        remainingTimeStr = remainingTimeStr.replaceAll("<minutes>", String.valueOf(minutes));
        remainingTimeStr = remainingTimeStr.replaceAll("<seconds>", String.valueOf(seconds));
        ModernGui.drawScaledStringCustomFont(remainingTimeStr, this.guiLeft + 243, (float)this.guiTop + 45.5f, 6962944, 0.5f, "center", false, "georamaSemiBold", 23);
        for (int index = 0; index < 16; ++index) {
            int offsetX = this.guiLeft + 13 + index % 4 * 116;
            int offsetY = this.guiTop + 71 + index / 4 * 41;
            if (ClientData.bonuses.keySet().size() > index) {
                String bonus = ClientData.bonuses.keySet().toArray(new String[0])[index];
                float value = ClientData.bonuses.get(bonus).floatValue();
                ClientEventHandler.STYLE.bindTexture("bonus");
                ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, 0.0f, 641.0f, 315, 115, 315 / GUI_SCALE, 115 / GUI_SCALE, 3546.0f, 1825.0f, false);
                int iconX = bonusIconX.containsKey(bonus) ? bonusIconX.get(bonus) : 0;
                ModernGui.drawScaledCustomSizeModalRect(offsetX + 1, offsetY, iconX, 807.0f, 107, 107, 107 / GUI_SCALE, 107 / GUI_SCALE, 3546.0f, 1825.0f, false);
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("gui.bonus.label." + bonus)), offsetX + 40, offsetY + 10, 0xFFFFFF, 0.5f, "left", false, "georamaSemiBold", 23);
                float value_percent = value - 1.0f;
                ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)("gui.bonus.description." + bonus)).replaceAll("<value_percent>", String.format("%.0f", Float.valueOf(value_percent *= 100.0f))).replaceAll("<value>", String.format("%.0f", Float.valueOf(value))), offsetX + 40, offsetY + 18, 0x808080, 0.5f, "left", false, "georamaMedium", 21);
                continue;
            }
            ClientEventHandler.STYLE.bindTexture("bonus");
            ModernGui.drawScaledCustomSizeModalRect(offsetX, offsetY, 315.0f, 641.0f, 315, 115, 315 / GUI_SCALE, 115 / GUI_SCALE, 3546.0f, 1825.0f, false);
            ModernGui.drawScaledCustomSizeModalRect(offsetX + 1, offsetY + 1, 0.0f, 807.0f, 107, 107, 107 / GUI_SCALE, 107 / GUI_SCALE, 3546.0f, 1825.0f, false);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.bonus.label.soon"), offsetX + 40, offsetY + 10, 4145221, 0.5f, "left", false, "georamaSemiBold", 23);
        }
        super.func_73863_a(mouseX, mouseY, par3);
    }

    public boolean func_73868_f() {
        return false;
    }

    public void func_73874_b() {
        if (ClientProxy.commandPlayer != null && ClientProxy.commandPlayer.isPlaying()) {
            ClientProxy.commandPlayer.softClose();
        }
        super.func_73874_b();
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.hoveredAction.equals("close")) {
            this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
            Minecraft.func_71410_x().func_71373_a(null);
        }
        super.func_73864_a(mouseX, mouseY, mouseButton);
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
}

