/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.gui.inventory.GuiContainer
 *  net.minecraft.client.gui.inventory.GuiInventory
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.inventory.Container
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.trade;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.gui.trade.ITrade;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.TradeUpdateMoneyPacket;
import net.ilexiconn.nationsgui.forge.server.trade.ContainerTrade;
import net.ilexiconn.nationsgui.forge.server.trade.TradeManager;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumPacketServer;
import net.ilexiconn.nationsgui.forge.server.trade.enums.EnumTradeState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiTrade
extends GuiContainer
implements ITrade {
    private static ResourceLocation resource = new ResourceLocation("nationsgui", "textures/gui/trade/troc.png");
    public Map items = new HashMap();
    public EntityPlayer trader = null;
    private ContainerTrade container;
    private GuiButton accept;
    private GuiTextField moneyField;
    private int moneyUpdater;
    public int moneyTrader;
    public boolean traderIsReady = false;
    public boolean hasEnoughMoney = true;
    private boolean imReady = false;
    public long lastInteraction = 0L;
    public static long stopCooldown;

    public GuiTrade(Container container) {
        super(container);
        this.container = (ContainerTrade)container;
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        PacketCallbacks.MONEY.send(new String[0]);
        this.field_74194_b = 401;
        this.field_74195_c = 216;
        this.field_74198_m = (this.field_73880_f - this.field_74194_b) / 2;
        this.field_74197_n = (this.field_73881_g - this.field_74195_c) / 2;
        this.field_73887_h.clear();
        this.accept = new GuiButton(0, this.field_74198_m + 16, this.field_74197_n + 95, 35, 20, "Ok");
        this.field_73887_h.add(this.accept);
        this.field_73887_h.add(new VoidButton(1, this.field_74198_m + 181, this.field_74197_n + 17, 9, 10));
        this.moneyField = new GuiTextField(this.field_73886_k, this.field_74198_m + 113, this.field_74197_n + 102, 58, 20);
        this.moneyField.func_73804_f(8);
        this.moneyField.func_73796_b(false);
        this.moneyField.func_73782_a("0");
        this.moneyField.func_73786_a(false);
        if (this.trader != null) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.func_73781_b()), this.trader.field_71092_bJ, true)));
        }
    }

    protected void func_74185_a(float partialsTicks, int mouseX, int mouseY) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.field_74198_m = (this.field_73880_f - this.field_74194_b) / 2;
        this.field_74197_n = (this.field_73881_g - this.field_74195_c) / 2;
        GL11.glPushMatrix();
        GUIUtils.startGLScissor(this.field_74198_m + 16, this.field_74197_n + 56, 35, 35);
        GuiInventory.func_110423_a((int)(this.field_74198_m + 35), (int)(this.field_74197_n + 125), (int)35, (float)((-mouseX + this.field_74198_m + 32) / 4), (float)((-mouseY + this.field_74197_n + 32) / 4), (EntityLivingBase)this.field_73882_e.field_71439_g);
        GUIUtils.endGLScissor();
        if (this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING) {
            GUIUtils.startGLScissor(this.field_74198_m + 16, this.field_74197_n + 146, 35, 35);
            GuiInventory.func_110423_a((int)(this.field_74198_m + 35), (int)(this.field_74197_n + 215), (int)35, (float)((-mouseX + this.field_74198_m + 32) / 4), (float)((-mouseY + this.field_74197_n + 32) / 4), (EntityLivingBase)this.trader);
            GUIUtils.endGLScissor();
        }
        GL11.glPopMatrix();
        this.field_73882_e.func_110434_K().func_110577_a(resource);
        ModernGui.drawModalRectWithCustomSizedTexture(this.field_74198_m, this.field_74197_n, 0, 0, this.field_74194_b, this.field_74195_c, 512.0f, 512.0f, false);
        if (this.imReady) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.field_74198_m + 57, this.field_74197_n + 56, 0, 218, 4, 59, 512.0f, 512.0f, false);
        }
        if (this.traderIsReady) {
            ModernGui.drawModalRectWithCustomSizedTexture(this.field_74198_m + 57, this.field_74197_n + 146, 0, 218, 4, 59, 512.0f, 512.0f, false);
        }
        this.func_73731_b(this.field_73886_k, String.valueOf((int)ShopGUI.CURRENT_MONEY) + "$", this.field_74198_m + 378 - this.field_73886_k.func_78256_a(String.valueOf((int)ShopGUI.CURRENT_MONEY) + "$"), this.field_74197_n + 63, -1);
        this.func_73731_b(this.field_73886_k, "Mon Inventaire", this.field_74198_m + 249, this.field_74197_n + 63, -1);
        this.func_73731_b(this.field_73886_k, String.valueOf(this.moneyTrader), this.field_74198_m + 113, this.field_74197_n + 193, -1);
        if (this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING) {
            this.func_73731_b(this.field_73886_k, "Echange", this.field_74198_m + 33, this.field_74197_n + 12, -1);
            this.func_73731_b(this.field_73886_k, "Avec " + this.trader.field_71092_bJ, this.field_74198_m + 33, this.field_74197_n + 23, 0xB4B4B4);
            ItemStack hover = null;
            for (int k = 0; k < 14; ++k) {
                ItemStack item = (ItemStack)this.items.get(k);
                if (item == null) continue;
                int x = this.field_74198_m + 66 + k % 7 * 18;
                int y = this.field_74197_n + 147 + k / 7 * 18;
                this.drawItemStack(item, x, y, null);
                if (!this.func_74188_c(x - this.field_74198_m, y - this.field_74197_n, 16, 16, mouseX, mouseY)) continue;
                hover = item;
            }
            if (hover != null) {
                this.func_74184_a(hover, mouseX, mouseY);
            }
        }
        if (!this.accept.field_73742_g && mouseX >= this.field_74198_m + 16 && mouseX <= this.field_74198_m + 16 + 35 && mouseY >= this.field_74197_n + 95 && mouseY <= this.field_74197_n + 95 + 20 && !this.hasEnoughMoney) {
            this.drawHoveringText(Arrays.asList(I18n.func_135053_a((String)"trade.not_enough_money")), mouseX, mouseY, this.field_73886_k);
        }
    }

    protected void func_73869_a(char par1, int par2) {
        super.func_73869_a(par1, par2);
        this.lastInteraction = System.currentTimeMillis();
        if (this.moneyField.func_73802_a(par1, par2)) {
            if (Character.isDigit(par1) || par2 == 14 || par2 == 54 || par2 == 42 || par2 == 205 || par2 == 203 || par2 == 211) {
                if (Character.isDigit(par1) && this.moneyField.func_73781_b().equals("0")) {
                    this.moneyField.func_73782_a("");
                }
                if (this.moneyField.func_73781_b().isEmpty() || this.moneyField.func_73781_b().matches("0*")) {
                    this.moneyField.func_73782_a("0");
                }
            }
            if (this.moneyField.func_73781_b() != null && !this.moneyField.func_73781_b().isEmpty() && this.isNumeric(this.moneyField.func_73781_b()) && this.trader != null) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.func_73781_b()), this.trader.field_71092_bJ, true)));
            } else if (this.trader != null) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(0, this.trader.field_71092_bJ, true)));
            }
        }
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        super.func_73864_a(par1, par2, par3);
        this.moneyField.func_73793_a(par1, par2, par3);
    }

    public void func_73876_c() {
        super.func_73876_c();
        this.moneyField.func_73780_a();
        if (this.moneyUpdater > 10) {
            PacketCallbacks.MONEY.send(new String[0]);
            if (this.isNumeric(this.moneyField.func_73781_b()) && this.trader != null && this.container.state != EnumTradeState.DONE && this.container.state != EnumTradeState.WAITING && this.container.state != EnumTradeState.TRADER_ACCEPTED) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new TradeUpdateMoneyPacket(Integer.parseInt(this.moneyField.func_73781_b()), this.trader.field_71092_bJ, false)));
            }
            this.moneyUpdater = 0;
        }
        ++this.moneyUpdater;
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

    public void func_73863_a(int par1, int par2, float par3) {
        super.func_73863_a(par1, par2, par3);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.moneyField.func_73795_f();
        this.accept.field_73742_g = !this.imReady && this.hasEnoughMoney && stopCooldown <= System.currentTimeMillis();
    }

    protected void func_73875_a(GuiButton btn) {
        switch (btn.field_73741_f) {
            case 0: {
                if (!this.isNumeric(this.moneyField.func_73781_b())) {
                    this.moneyField.func_73782_a("0");
                    return;
                }
                if (!this.accept.field_73742_g || System.currentTimeMillis() - this.lastInteraction <= 1000L || this.trader == null || this.container.state == EnumTradeState.DONE || this.container.state == EnumTradeState.WAITING) break;
                TradeManager.sendData(EnumPacketServer.TRADE_COMPLETE, Integer.parseInt(this.moneyField.func_73781_b()));
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                break;
            }
            case 1: {
                this.field_73882_e.field_71416_A.func_77366_a("random.click", 1.0f, 1.0f);
                Minecraft.func_71410_x().func_71373_a(null);
            }
        }
    }

    public void func_73874_b() {
        super.func_73874_b();
        PacketCallbacks.MONEY.send(new String[0]);
        TradeManager.sendData(EnumPacketServer.TRADE_CANCEL, 0);
    }

    @Override
    public EntityPlayer getTrader() {
        return this.trader;
    }

    public static void addCooldown(long timeMillis) {
        stopCooldown = System.currentTimeMillis() + timeMillis;
    }

    public void updateState(EnumTradeState state) {
        this.container.state = state;
        this.imReady = state != EnumTradeState.STARTED && state != EnumTradeState.TRADER_ACCEPTED;
        this.accept.field_73742_g = !this.imReady && this.hasEnoughMoney && stopCooldown <= System.currentTimeMillis();
        this.traderIsReady = state == EnumTradeState.TRADER_ACCEPTED;
    }

    private void drawItemStack(ItemStack par1ItemStack, int par2, int par3, String par4Str) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)32.0f);
        RenderHelper.func_74520_c();
        GL11.glDisable((int)2896);
        GL11.glEnable((int)32826);
        GL11.glEnable((int)2903);
        GL11.glEnable((int)2896);
        this.field_73735_i = 200.0f;
        GuiTrade.field_74196_a.field_77023_b = 200.0f;
        FontRenderer font = null;
        if (par1ItemStack != null) {
            font = par1ItemStack.func_77973_b().getFontRenderer(par1ItemStack);
        }
        if (font == null) {
            font = this.field_73886_k;
        }
        field_74196_a.func_82406_b(font, this.field_73882_e.func_110434_K(), par1ItemStack, par2, par3);
        field_74196_a.func_94148_a(font, this.field_73882_e.func_110434_K(), par1ItemStack, par2, par3, par4Str);
        this.field_73735_i = 0.0f;
        GuiTrade.field_74196_a.field_77023_b = 0.0f;
        GL11.glPopMatrix();
        GL11.glEnable((int)2896);
        GL11.glEnable((int)2929);
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
            GuiTrade.field_74196_a.field_77023_b = 300.0f;
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
            GuiTrade.field_74196_a.field_77023_b = 0.0f;
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glEnable((int)32826);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        }
    }

    private class VoidButton
    extends GuiButton {
        public VoidButton(int id, int posX, int posY, int width, int height) {
            super(id, posX, posY, width, height, "");
        }

        public void func_73737_a(Minecraft par1Minecraft, int par2, int par3) {
        }
    }
}

