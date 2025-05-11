/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.enterprise;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.enterprise.EnterpriseGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractCreate_Default_Packet;
import net.ilexiconn.nationsgui.forge.server.packet.impl.EnterpriseContractFormTraderPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class EnterpriseContractForm_Trader_Gui
extends GuiScreen {
    private GuiButton cancelButton;
    private GuiButton validButton;
    private GuiTextField priceInput;
    private RenderItem itemRenderer = new RenderItem();
    protected int xSize = 371;
    protected int ySize = 223;
    private int guiLeft;
    private int guiTop;
    public static boolean loaded = false;
    public static HashMap<String, Object> data = new HashMap();

    public void func_73876_c() {
        this.priceInput.func_73780_a();
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        loaded = false;
        PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractFormTraderPacket((String)EnterpriseGui.enterpriseInfos.get("name"))));
        this.guiLeft = (this.field_73880_f - this.xSize) / 2;
        this.guiTop = (this.field_73881_g - this.ySize) / 2;
        this.cancelButton = new GuiButton(0, this.guiLeft + 197, this.guiTop + 197, 80, 20, I18n.func_135053_a((String)"enterprise.contract.action.cancel"));
        this.validButton = new GuiButton(1, this.guiLeft + 282, this.guiTop + 197, 80, 20, I18n.func_135053_a((String)"enterprise.contract.action.valid"));
        this.priceInput = new GuiTextField(this.field_73886_k, this.guiLeft + 219, this.guiTop + 166, 58, 10);
        this.priceInput.func_73786_a(false);
        this.priceInput.func_73804_f(7);
        this.priceInput.func_73782_a("0");
    }

    public void func_73863_a(int mouseX, int mouseY, float par3) {
        this.func_73873_v_();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        ModernGui.drawModalRectWithCustomSizedTextureWithTransparency(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize, 512.0f, 512.0f, false);
        ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
        List<String> tooltipToDraw = null;
        ModernGui.drawModalRectWithCustomSizedTexture((int)((double)(this.guiLeft + 91) - (double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"enterprise.contract.title.services")) * 1.2 / 2.0 - 8.0 - 2.0), this.guiTop + 16, 0, 276, 16, 16, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.title.services"), this.guiLeft + 91 + 8, this.guiTop + 21, 0xFFFFFF, 1.2f, true, false);
        int index = 0;
        for (String serviceLine : ((String)EnterpriseGui.enterpriseInfos.get("services")).split("##")) {
            this.drawScaledString(serviceLine.replace("&", "\u00a7"), this.guiLeft + 6, this.guiTop + 54 + index * 9, 0xFFFFFF, 0.8f, false, false);
            ++index;
        }
        this.drawScaledString((String)EnterpriseGui.enterpriseInfos.get("name"), this.guiLeft + 280, this.guiTop + 11, 0xFFFFFF, 1.7f, true, true);
        String type = I18n.func_135053_a((String)("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase()));
        ClientEventHandler.STYLE.bindTexture("enterprise_main");
        ModernGui.drawModalRectWithCustomSizedTexture(this.guiLeft + 280 - this.field_73886_k.func_78256_a(type) / 2 - 7 - 4, this.guiTop + 23, EnterpriseGui.getTypeOffsetX((String)EnterpriseGui.enterpriseInfos.get("type")), 442, 16, 16, 512.0f, 512.0f, false);
        this.drawScaledString(I18n.func_135053_a((String)("enterprise.type." + ((String)EnterpriseGui.enterpriseInfos.get("type")).toLowerCase())), this.guiLeft + 280 + 7, this.guiTop + 29, 0xB4B4B4, 1.0f, true, false);
        if (loaded) {
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.label.mymoney"), this.guiLeft + 197, this.guiTop + 70 - 9, 0x191919, 1.0f, false, false);
            ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 70, 165, 20);
            this.drawScaledString(String.format("%.0f", (Double)data.get("playerMoney")) + "\u00a7a$", this.guiLeft + 197 + 3, this.guiTop + 70 + 6, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.label.invested").replace("<enterprise>", (String)EnterpriseGui.enterpriseInfos.get("name")), this.guiLeft + 197, this.guiTop + 115 - 9, 0x191919, 1.0f, false, false);
            ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 115, 165, 20);
            this.drawScaledString(String.format("%.2f", (Double)data.get("totalPlayerInvestment")) + "\u00a7a$", this.guiLeft + 197 + 3, this.guiTop + 115 + 6, 0xFFFFFF, 1.0f, false, false);
            this.drawScaledString(I18n.func_135053_a((String)"enterprise.contract.label.investment"), this.guiLeft + 197, this.guiTop + 151, 0x191919, 1.0f, false, false);
            ClientEventHandler.STYLE.bindTexture("enterprise_contract_form");
            ModernGui.drawNGBlackSquare(this.guiLeft + 197, this.guiTop + 160, 165, 20);
            this.drawScaledString("\u00a7a$", this.guiLeft + 207, this.guiTop + 165, 0xFFFFFF, 1.3f, true, false);
            this.priceInput.func_73795_f();
            if (this.isNumeric(this.priceInput.func_73781_b())) {
                Double investLimit = (Double)data.get("totalInvestment") * 0.1;
                investLimit = Math.max(50000.0, investLimit);
                Double playerInvest = (Double)data.get("totalPlayerInvestment");
                if ((playerInvest = Double.valueOf(playerInvest + (double)Integer.parseInt(this.priceInput.func_73781_b()))) > investLimit) {
                    this.validButton.field_73742_g = false;
                    double canInvest = investLimit - (Double)data.get("totalPlayerInvestment");
                    if (mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.contract.trader.investment_limit").replace("<limit>", canInvest + "").split("##"));
                    }
                } else if (data.containsKey("countContract") && (Double)data.get("countContract") >= 2.0) {
                    this.validButton.field_73742_g = false;
                    if (mouseX >= this.guiLeft + 282 && mouseX <= this.guiLeft + 282 + 80 && mouseY >= this.guiTop + 197 && mouseY <= this.guiTop + 197 + 20) {
                        tooltipToDraw = Arrays.asList(I18n.func_135053_a((String)"enterprise.contract.trader.count_limit").split("##"));
                    }
                } else {
                    this.validButton.field_73742_g = true;
                }
            }
        }
        this.cancelButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        this.validButton.func_73737_a(Minecraft.func_71410_x(), mouseX, mouseY);
        if (tooltipToDraw != null) {
            this.drawHoveringText(tooltipToDraw, mouseX, mouseY, this.field_73886_k);
        }
    }

    protected void func_73869_a(char typedChar, int keyCode) {
        this.priceInput.func_73802_a(typedChar, keyCode);
        super.func_73869_a(typedChar, keyCode);
    }

    protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            if (mouseX > this.guiLeft + 197 && mouseX < this.guiLeft + 197 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a((GuiScreen)new EnterpriseGui((String)EnterpriseGui.enterpriseInfos.get("name")));
            }
            if (this.validButton.field_73742_g && this.isNumeric(this.priceInput.func_73781_b()) && mouseX > this.guiLeft + 282 && mouseX < this.guiLeft + 282 + 80 && mouseY > this.guiTop + 197 && mouseY < this.guiTop + 197 + 20) {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                EnterpriseGui.lastContractDemand = System.currentTimeMillis();
                String content = I18n.func_135053_a((String)"enterprise.contract.content.trader");
                content = content.replace("<amount>", this.priceInput.func_73781_b());
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new EnterpriseContractCreate_Default_Packet((String)EnterpriseGui.enterpriseInfos.get("name"), content, Integer.parseInt(this.priceInput.func_73781_b()), -10L)));
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
        this.priceInput.func_73793_a(mouseX, mouseY, mouseButton);
        super.func_73864_a(mouseX, mouseY, mouseButton);
    }

    public boolean isNumeric(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) continue;
            return false;
        }
        return Integer.parseInt(str) > 0;
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

