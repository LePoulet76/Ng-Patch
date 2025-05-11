/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  fr.nationsglory.ngcontent.NGContent
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fr.nationsglory.ngcontent.NGContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseGlobalMarketDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class EnterpriseGlobalMarketGui
extends GuiScreen {
    protected int xSize = 405;
    protected int ySize = 250;
    private int guiLeft;
    private int guiTop;
    public static HashMap<String, Integer> cerealsPrice;
    public static HashMap<String, String> cerealsPriceChange;
    private RenderItem itemRenderer = new RenderItem();
    public static boolean loaded;
    public String chartToDisplay = "prices";
    public String hoveredChartToDisplay = "";
    public static final ResourceLocation PRICES_TEXTURE;
    public static final ResourceLocation SALES_TEXTURE;
    public static final ResourceLocation STOCKS_TEXTURE;

    public EnterpriseGlobalMarketGui() {
        loaded = false;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseGlobalMarketDataPacket()));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        List<Object> tooltipToDraw = new ArrayList();
        this.hoveredChartToDisplay = "";
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
        if (mouseX >= this.guiLeft + 392 && mouseX <= this.guiLeft + 392 + 9 && mouseY >= this.guiTop - 8 && mouseY <= this.guiTop - 8 + 10) {
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 392, this.guiTop - 8, 83, 254, 9, 10, 512.0f, 512.0f, false);
        } else {
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 392, this.guiTop - 8, 83, 264, 9, 10, 512.0f, 512.0f, false);
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.guiLeft + 14), (float)(this.guiTop + 170), (float)0.0f);
        GL11.glRotatef((float)-90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslatef((float)(-(this.guiLeft + 14)), (float)(-(this.guiTop + 170)), (float)0.0f);
        this.drawScaledString(I18n.func_135053_a((String)"globalmarket.title"), this.guiLeft + 14, this.guiTop + 170, 0xFFFFFF, 1.5f, false, false);
        GL11.glPopMatrix();
        if (this.chartToDisplay.equals("prices")) {
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 258, this.guiTop + 19, 0, 259, 43, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"globalmarket.label.prices"), this.guiLeft + 281, this.guiTop + 21, 0, 1.0f, true, false);
        if (mouseX > this.guiLeft + 258 && mouseX < this.guiLeft + 258 + 43 && mouseY > this.guiTop + 19 && mouseY < this.guiTop + 19 + 10) {
            this.hoveredChartToDisplay = "prices";
        }
        if (this.chartToDisplay.equals("sales")) {
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 303, this.guiTop + 19, 0, 259, 43, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"globalmarket.label.sales"), this.guiLeft + 326, this.guiTop + 21, 0, 1.0f, true, false);
        if (mouseX > this.guiLeft + 303 && mouseX < this.guiLeft + 303 + 43 && mouseY > this.guiTop + 19 && mouseY < this.guiTop + 19 + 10) {
            this.hoveredChartToDisplay = "sales";
        }
        if (this.chartToDisplay.equals("stocks")) {
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 348, this.guiTop + 19, 0, 259, 43, 10, 512.0f, 512.0f, false);
        }
        this.drawScaledString(I18n.func_135053_a((String)"globalmarket.label.stocks"), this.guiLeft + 371, this.guiTop + 21, 0, 1.0f, true, false);
        if (mouseX > this.guiLeft + 348 && mouseX < this.guiLeft + 348 + 43 && mouseY > this.guiTop + 19 && mouseY < this.guiTop + 19 + 10) {
            this.hoveredChartToDisplay = "stocks";
        }
        if (loaded) {
            ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
            ResourceLocation chartTexture = PRICES_TEXTURE;
            if (this.chartToDisplay.equals("sales")) {
                chartTexture = SALES_TEXTURE;
            } else if (this.chartToDisplay.equals("stocks")) {
                chartTexture = STOCKS_TEXTURE;
            }
            Minecraft.func_71410_x().func_110434_K().func_110577_a(chartTexture);
            ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 50, this.guiTop + 32, 0, 0, 338, 133, 338.0f, 133.0f, true);
            int indexX = 0;
            int indexY = 0;
            for (int i = 0; i < cerealsPrice.size(); ++i) {
                GL11.glDisable((int)2896);
                GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                String cerealName = (String)cerealsPrice.keySet().toArray()[i];
                Integer cerealPrice = cerealsPrice.get(cerealName);
                int offsetX = this.guiLeft + 47 + indexX * 88;
                int offsetY = this.guiTop + 173 + indexY * 22;
                ModernGui.drawNGBlackSquare(offsetX, offsetY, 80, 18);
                if (mouseX >= offsetX && mouseX <= offsetX + 80 && mouseY >= offsetY && mouseY <= offsetY + 18) {
                    tooltipToDraw = Arrays.asList(cerealName.substring(0, 1).toUpperCase() + cerealName.substring(1));
                }
                String price = cerealPrice + (cerealsPriceChange.get(cerealName).equals("+") ? "\u00a7a" : "\u00a7c") + "$";
                this.drawScaledString(price, offsetX + 80 - this.field_73886_k.func_78256_a(price) - 4, offsetY + 5, 0xFFFFFF, 1.0f, false, false);
                ClientEventHandler.STYLE.bindTexture("enterprise_globalmarket");
                ModernGui.drawModalRectWithCustomSizedTexture(offsetX + 80 - this.field_73886_k.func_78256_a(price) - 12, offsetY + 6, cerealsPriceChange.get(cerealName).equals("+") ? 70 : 77, 311, 6, 6, 512.0f, 512.0f, false);
                GL11.glEnable((int)2896);
                ItemStack cereal = new ItemStack(NGContent.getCerealIdFromName((String)cerealName).intValue(), 1, 0);
                this.itemRenderer.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), cereal, offsetX + 3, offsetY + 1);
                if (++indexX != 4) continue;
                ++indexY;
                indexX = 0;
            }
        }
        if (!tooltipToDraw.isEmpty()) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
        super.func_73863_a(mouseX, mouseY, par3);
        GL11.glEnable((int)2896);
        RenderHelper.func_74519_b();
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

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 392 && mouseX < this.guiLeft + 392 + 9 && mouseY > this.guiTop - 8 && mouseY < this.guiTop - 8 + 10) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
            if (!this.hoveredChartToDisplay.isEmpty()) {
                this.chartToDisplay = this.hoveredChartToDisplay;
                this.hoveredChartToDisplay = "";
            }
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

    static {
        loaded = false;
        PRICES_TEXTURE = new ResourceLocation("nationsgui", "tmp/globalmarket_prices");
        SALES_TEXTURE = new ResourceLocation("nationsgui", "tmp/globalmarket_sales");
        STOCKS_TEXTURE = new ResourceLocation("nationsgui", "tmp/globalmarket_stocks");
    }
}

