/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiTextField
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 *  org.apache.commons.lang3.StringUtils
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.data.MarketSale;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.ChangePageButton;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.ChoiseSelectorGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketSimpleButton;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.NumberSelector;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.RemoveConfirmGui;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.StatsButton;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionDataPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.BuyMarketPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.IncrementObjectivePacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ItemStackMarketStatsPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.RemoveSalePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

public class MarketGui
extends GuiScreen {
    public static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");
    private List<MarketSale> marketSales = new ArrayList<MarketSale>();
    private List<MarketSale> filteredMarket = new ArrayList<MarketSale>();
    private int posX;
    private int posY;
    private RenderItem renderItem;
    private GuiScrollBar guiScrollBar;
    private MarketSale selected = null;
    private GuiButton guiButton;
    private NumberSelector numberSelector;
    private StatsButton statsButton;
    public static int currentPage = 1;
    private List<MarketSale> currentSale = new ArrayList<MarketSale>();
    public static GuiTextField search;
    public static boolean mySalesOnly;
    private ChoiseSelectorGui<Comparator<MarketSale>> filterSelector;
    private static Map<String, Comparator<MarketSale>> filters;
    private static long lastBuy;
    public static int totalResults;
    public static boolean canRemoveAll;
    public static boolean achievementDone;

    public MarketGui() {
        this.renderItem = new RenderItem();
        search = null;
        currentPage = 1;
        mySalesOnly = false;
        if (!achievementDone) {
            achievementDone = true;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new IncrementObjectivePacket("player_open_hdv", 1)));
        }
    }

    public void func_73866_w_() {
        PacketCallbacks.MONEY.send(new String[0]);
        this.posX = this.field_73880_f / 2 - 171;
        this.posY = this.field_73881_g / 2 - 128;
        this.field_73887_h.clear();
        ChangePageButton b1 = new ChangePageButton(0, this.posX + 270, this.posY + 235, false);
        b1.field_73742_g = currentPage > 1;
        this.field_73887_h.add(b1);
        ChangePageButton b2 = new ChangePageButton(1, this.posX + 322, this.posY + 235, true);
        b2.field_73742_g = currentPage < totalResults / 10 + 1;
        this.field_73887_h.add(b2);
        this.field_73887_h.add(new MarketSimpleButton(2, this.posX + 230, this.posY + 35, 100, 16, I18n.func_135053_a((String)(mySalesOnly ? "hdv.othersales" : "hdv.mysales"))));
        Map.Entry<String, Comparator<MarketSale>> s = null;
        if (this.filterSelector != null) {
            s = this.filterSelector.saveSelection();
        }
        this.filterSelector = new ChoiseSelectorGui<Comparator<MarketSale>>(this.posX + 127, this.posY + 35, 75, filters);
        if (s != null) {
            this.filterSelector.restoreSelection(s);
        }
        if (search == null) {
            search = new GuiTextField(this.field_73886_k, this.posX + 27, this.posY + 39, 90, 16);
            search.func_73786_a(false);
        }
        float save = 0.0f;
        if (this.guiScrollBar != null) {
            save = this.guiScrollBar.getSliderValue();
        }
        this.guiScrollBar = new GuiScrollBar(this.posX + 326, this.posY + 79, 149){

            @Override
            protected void drawScroller() {
                MarketGui.this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
                ModernGui.drawModalRectWithCustomSizedTexture((int)this.x, (int)(this.y + (float)(this.height - 15) * this.sliderValue), 343, 70, 9, 14, 372.0f, 400.0f, false);
            }
        };
        this.guiScrollBar.setSliderValue(save);
        this.guiButton = null;
        this.numberSelector = null;
        if (this.filteredMarket != null) {
            int i = 0;
            for (MarketSale marketSale : this.currentSale) {
                int y = i * 20;
                if (marketSale.equals(this.selected) && marketSale.getItemStack() != null) {
                    if (marketSale.getPseudo().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
                        this.guiButton = null;
                        this.numberSelector = null;
                    } else if (marketSale.getExpiry() > System.currentTimeMillis()) {
                        this.guiButton = new GuiButton(1, this.posX + 6 + 65, this.posY + 79 + y + 60, 80, 20, I18n.func_135053_a((String)"hdv.buy"));
                        this.numberSelector = new NumberSelector(this.field_73882_e, this.posX + 6 + 150, this.posY + 79 + y + 60, 80);
                        this.numberSelector.setText("1");
                        int a = 0;
                        for (ItemStack itemStack : Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a) {
                            if (itemStack != null && itemStack.func_77969_a(marketSale.getItemStack()) && ItemStack.func_77970_a((ItemStack)itemStack, (ItemStack)marketSale.getItemStack())) {
                                a += itemStack.func_77976_d() - itemStack.field_77994_a;
                                continue;
                            }
                            if (itemStack != null) continue;
                            a += marketSale.getItemStack().func_77976_d();
                        }
                        int qt = marketSale.getQuantity() - marketSale.getSold();
                        this.numberSelector.setMax(a >= qt ? qt : a);
                    }
                    this.statsButton = new StatsButton(3, this.posX + 252, this.posY + y + 149);
                }
                ++i;
            }
        }
        super.func_73866_w_();
    }

    public void func_73876_c() {
        if (this.numberSelector != null) {
            this.numberSelector.update();
        }
        if (this.selected != null && !this.selected.getPseudo().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) && this.guiButton != null) {
            long buyDiff = (System.currentTimeMillis() - lastBuy) / 1000L;
            this.guiButton.field_73742_g = this.selected.getExpiry() > System.currentTimeMillis() && this.numberSelector.getNumber() > 0 && ShopGUI.CURRENT_MONEY >= (double)(this.selected.getPrice() * this.numberSelector.getNumber()) && this.numberSelector.getMax() > 0 && buyDiff >= 3L;
            this.guiButton.field_73744_e = buyDiff < 3L ? 3L - buyDiff + " s" : I18n.func_135053_a((String)"hdv.buy");
        }
        if (search != null) {
            search.func_73780_a();
        }
        super.func_73876_c();
    }

    public void func_73863_a(int par1, int par2, float par3) {
        int y;
        ItemStack itemStack;
        int add;
        int i;
        int offset;
        int a;
        this.func_73873_v_();
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX, this.posY, 0, 0, 343, 256, 372.0f, 400.0f, false);
        this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX + 217, this.posY + 259, 244, 373, 125, 26, 372.0f, 400.0f, false);
        ClientEventHandler.STYLE.bindTexture("auction_house");
        int textWidth = (int)((double)this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"auctions.title")) * 1.2);
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX + 217 + 5, this.posY + 265, 6, 5, 25, 15, 372.0f, 400.0f, false);
        ModernGui.drawScaledString(I18n.func_135053_a((String)"auctions.title"), this.posX + 288, this.posY + 269, 0xFFFFFF, 1.2f, true, true);
        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
        if (search != null) {
            search.func_73795_f();
        }
        String page = currentPage + "";
        this.func_73732_a(this.field_73886_k, page, this.posX + 269 + 33, this.posY + 238, 0xFFFFFF);
        String money = (int)ShopGUI.CURRENT_MONEY + " $";
        this.field_73886_k.func_78276_b(money, this.posX + 312 - this.field_73886_k.func_78256_a(money), this.posY + 10, 0xFFFFFF);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.posX + 28), (float)(this.posY + 3), (float)0.0f);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
        this.field_73886_k.func_78261_a(I18n.func_135053_a((String)"hdv.title"), 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
        if (this.marketSales != null && this.filteredMarket != null && !this.filteredMarket.isEmpty()) {
            GUIUtils.startGLScissor(this.posX + 6, this.posY + 79, 317, 149);
            GL11.glPushMatrix();
            a = this.selected == null ? 0 : 67;
            offset = this.currentSale.size() > 7 || this.selected != null && this.currentSale.size() > 4 ? (int)((float)((this.currentSale.size() - 7) * 20 + a) * -this.guiScrollBar.getSliderValue()) : 0;
            GL11.glTranslatef((float)(this.posX + 6), (float)(this.posY + 79 + offset), (float)0.0f);
            i = 0;
            add = 0;
            for (MarketSale marketSale : this.currentSale) {
                itemStack = marketSale.getItemStack();
                if (itemStack != null) {
                    itemStack.field_77994_a = 1;
                    y = i * 20 + add;
                    int price = marketSale.getPrice();
                    String priceString = marketSale.getExpiry() > System.currentTimeMillis() ? (price < 100000 ? Integer.valueOf(price) : String.format("%.0f", (double)price / 1000.0) + "k") + "\u00a72$" : (marketSale.getSuperexpiry() > System.currentTimeMillis() ? I18n.func_135053_a((String)"hdv.expired") : I18n.func_135053_a((String)"hdv.superexpired"));
                    this.field_73882_e.func_110434_K().func_110577_a(BACKGROUND);
                    if (!marketSale.equals(this.selected)) {
                        if (par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + offset && this.cursorOnMarket(par1, par2)) {
                            GL11.glColor3f((float)0.7f, (float)0.7f, (float)0.7f);
                        }
                        ModernGui.drawModalRectWithCustomSizedTexture(0.0f, y, 1, 262, 317, 18, 372.0f, 400.0f, false);
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                        this.field_73886_k.func_78276_b(itemStack.func_82833_r().replaceAll("^\\\u00a7[0-9a-z]", ""), 22, y + 5, -6381922);
                        this.field_73886_k.func_78276_b(priceString, 293 - this.field_73886_k.func_78256_a(priceString) / 2, y + 5, -1);
                        this.drawDeleteButton(marketSale, y, par1, par2);
                        RenderHelper.func_74520_c();
                        GL11.glEnable((int)32826);
                        this.renderItem.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 1, y + 1);
                        this.renderItem.func_94148_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 1, y + 1, "\u00a77" + Integer.toString(marketSale.getQuantity() - marketSale.getSold()));
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                        GL11.glDisable((int)2896);
                        GL11.glDisable((int)32826);
                        RenderHelper.func_74518_a();
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                    } else {
                        String output;
                        String msg;
                        add = 67;
                        ModernGui.drawModalRectWithCustomSizedTexture(0.0f, y, 1, 283, 317, 85, 372.0f, 400.0f, false);
                        this.field_73886_k.func_78276_b(priceString, 293 - this.field_73886_k.func_78256_a(priceString) / 2, y + 5, -1);
                        String itemName = itemStack.func_82833_r().replaceAll("^\\\u00a7[0-9a-z]", "");
                        if (itemName.length() > (itemName.contains("\u00a7l") ? 15 : 25)) {
                            itemName = itemName.substring(0, itemName.contains("\u00a7l") ? 14 : 24) + "...";
                        }
                        this.field_73886_k.func_78276_b("\u00a7l" + itemName, 65, y + 5, -6381922);
                        this.field_73886_k.func_78276_b(I18n.func_135052_a((String)"hdv.seller", (Object[])new Object[]{marketSale.getPseudo()}), 65, y + 20, -1);
                        if (this.numberSelector != null) {
                            String totalPrice = I18n.func_135052_a((String)"hdv.totalprice", (Object[])new Object[]{marketSale.getPrice() * this.numberSelector.getNumber()});
                            this.field_73886_k.func_78276_b(totalPrice, 65, y + 50, -1);
                        }
                        if (marketSale.getExpiry() > System.currentTimeMillis()) {
                            msg = I18n.func_135053_a((String)"hdv.expiry.time");
                            this.field_73886_k.func_78276_b(msg, 314 - this.field_73886_k.func_78256_a(msg), y + 40, -1);
                            output = this.getCountdownString(marketSale.getExpiry());
                            this.field_73886_k.func_78276_b(output, 314 - this.field_73886_k.func_78256_a(output), y + 50, -1);
                        } else if (marketSale.getPseudo().equals(this.field_73882_e.field_71439_g.field_71092_bJ) && marketSale.getSuperexpiry() > System.currentTimeMillis()) {
                            msg = I18n.func_135053_a((String)"hdv.superexpiry.time");
                            this.field_73886_k.func_78276_b(msg, 314 - this.field_73886_k.func_78256_a(msg), y + 40, -1);
                            output = this.getCountdownString(marketSale.getSuperexpiry());
                            this.field_73886_k.func_78276_b(output, 314 - this.field_73886_k.func_78256_a(output), y + 50, -1);
                        }
                        this.drawDeleteButton(marketSale, y, par1, par2);
                        RenderHelper.func_74520_c();
                        GL11.glEnable((int)32826);
                        GL11.glPushMatrix();
                        float size = 3.0f;
                        GL11.glTranslatef((float)(30.0f - 8.0f * size + 1.0f), (float)((float)(y + 42) - 8.0f * size), (float)0.0f);
                        GL11.glScalef((float)size, (float)size, (float)size);
                        this.renderItem.func_82406_b(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 0, 0);
                        this.renderItem.func_94148_a(this.field_73886_k, this.field_73882_e.func_110434_K(), itemStack, 0, 0, "\u00a77" + Integer.toString(marketSale.getQuantity() - marketSale.getSold()));
                        GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                        GL11.glPopMatrix();
                        GL11.glDisable((int)2896);
                        GL11.glDisable((int)32826);
                        RenderHelper.func_74518_a();
                    }
                }
                ++i;
            }
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)offset, (float)0.0f);
            if (this.guiButton != null) {
                this.guiButton.func_73737_a(this.field_73882_e, par1, par2 - offset);
            }
            if (this.numberSelector != null) {
                GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
                this.numberSelector.draw(par1, par2 - offset);
            }
            if (this.statsButton != null) {
                this.statsButton.func_73737_a(this.field_73882_e, par1, par2 - offset);
            }
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
            GUIUtils.endGLScissor();
        } else {
            String text = I18n.func_135053_a((String)"hdv.unavailable");
            this.func_73732_a(this.field_73886_k, text, this.posX + 170, this.posY + 75, 0xFFFFFF);
        }
        if (this.guiScrollBar != null) {
            this.guiScrollBar.draw(par1, par2);
        }
        if (this.filterSelector != null) {
            this.filterSelector.draw(par1, par2);
        }
        if (this.marketSales != null && this.filteredMarket != null && !this.filteredMarket.isEmpty() && par1 >= this.posX + 9 && par1 <= this.posX + 9 + 317 && par2 >= this.posY + 79 && par2 <= this.posY + 79 + 149) {
            a = this.selected == null ? 0 : 67;
            offset = this.currentSale.size() > 7 || this.selected != null && this.currentSale.size() > 4 ? (int)((float)((this.currentSale.size() - 7) * 20 + a) * -this.guiScrollBar.getSliderValue()) : 0;
            i = 0;
            add = 0;
            for (MarketSale marketSale : this.currentSale) {
                itemStack = marketSale.getItemStack();
                if (itemStack != null) {
                    itemStack.field_77994_a = 1;
                    y = i * 20 + add;
                    if (this.selected != null && this.selected.equals(marketSale)) {
                        add = 67;
                    }
                    if (par1 >= this.posX + 7 && par2 >= this.posY + 79 + offset + y + 1 && (marketSale.equals(this.selected) ? par1 <= this.posX + 7 + 60 && par2 <= this.posY + 79 + offset + y + 84 : par1 <= this.posX + 7 + 16 && par2 <= this.posY + 79 + offset + y + 17)) {
                        NationsGUIClientHooks.drawItemStackTooltip(itemStack, par1, par2);
                        GL11.glDisable((int)2896);
                    }
                }
                ++i;
            }
        }
        super.func_73863_a(par1, par2, par3);
    }

    private String getCountdownString(long time) {
        int SECONDS_IN_A_DAY = 86400;
        long diff = time - System.currentTimeMillis();
        long diffSec = diff / 1000L;
        long secondsDay = diffSec % (long)SECONDS_IN_A_DAY;
        long seconds = secondsDay % 60L;
        long minutes = secondsDay / 60L % 60L;
        long hours = diffSec / 3600L;
        String output = "";
        if (hours > 0L) {
            output = output + hours + "h ";
        }
        if (minutes > 0L) {
            output = output + minutes + "m ";
        }
        output = output + seconds + "s";
        return output;
    }

    protected void func_73869_a(char par1, int par2) {
        if (search.func_73802_a(par1, par2)) {
            this.resetSelection();
            currentPage = 1;
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
        }
        if (this.numberSelector != null) {
            this.numberSelector.keyTyped(par1, par2);
        }
        super.func_73869_a(par1, par2);
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        int offset;
        search.func_73793_a(par1, par2, par3);
        int add = 0;
        int i = 0;
        int a = this.selected == null ? 0 : 67;
        int n = offset = this.currentSale.size() > 7 || this.selected != null && this.currentSale.size() > 4 ? (int)((float)((this.currentSale.size() - 7) * 20 + a) * -this.guiScrollBar.getSliderValue()) : 0;
        if (this.statsButton != null && this.statsButton.func_73736_c(this.field_73882_e, par1, par2 - offset)) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new ItemStackMarketStatsPacket(this.selected.getUuid())));
        }
        if (this.numberSelector != null) {
            this.numberSelector.mouseClicked(par1, par2 - offset, par3);
        }
        if (this.filterSelector != null && this.filterSelector.mousePressed(par1, par2)) {
            this.resetSelection();
        }
        if (this.guiButton != null && this.guiButton.func_73736_c(this.field_73882_e, par1, par2 - offset)) {
            if (this.selected.getPseudo().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) || canRemoveAll) {
                if (this.canRemove(this.selected)) {
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoveSalePacket(this.selected.getUuid(), false)));
                    this.removeSale(this.selected);
                    this.resetSelection();
                } else {
                    this.field_73882_e.func_71373_a((GuiScreen)new RemoveConfirmGui(this, this.selected));
                }
            } else if (this.numberSelector != null && par1 > this.posX + 3 && par1 < this.posX + 336 && par2 > this.posY + 79 && par2 < this.posY + 228) {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new BuyMarketPacket(this.selected.getUuid(), this.numberSelector.getNumber())));
                PacketCallbacks.MONEY.send(new String[0]);
                if (this.numberSelector.getNumber() == this.selected.getQuantity() - this.selected.getSold()) {
                    this.removeSale(this.selected);
                    this.resetSelection();
                }
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
                lastBuy = System.currentTimeMillis();
            }
        }
        if (this.cursorOnMarket(par1, par2)) {
            for (MarketSale marketSale : this.currentSale) {
                int y = i * 20 + add;
                if (marketSale.equals(this.selected)) {
                    add = 67;
                }
                if (par1 >= this.posX + 6 && par1 <= this.posX + 6 + 317 && par2 >= this.posY + 79 + y + offset && par2 <= this.posY + y + 79 + 18 + offset) {
                    if ((marketSale.getPseudo().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) || canRemoveAll) && par1 >= this.posX + 6 + 242 && par1 <= this.posX + 6 + 242 + 19 && par2 >= this.posY + 79 + y + offset + 1 && par2 <= this.posY + 79 + y + offset + 20) {
                        if (this.canRemove(marketSale)) {
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new RemoveSalePacket(marketSale.getUuid(), false)));
                            if (marketSale == this.selected) {
                                this.resetSelection();
                            }
                            System.out.println("remove3");
                            this.removeSale(marketSale);
                            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
                            break;
                        }
                        this.field_73882_e.func_71373_a((GuiScreen)new RemoveConfirmGui(this, marketSale));
                        break;
                    }
                    if (this.selected != null && this.selected.equals(marketSale)) break;
                    this.selected = marketSale;
                    this.func_73866_w_();
                    break;
                }
                ++i;
            }
        }
        if (par1 > this.posX + 217 && par1 < this.posX + 217 + 125 && par2 > this.posY + 259 && par2 < this.posY + 259 + 26) {
            this.field_73882_e.func_71373_a((GuiScreen)new AuctionGui());
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket("", "", 0, false)));
        }
        super.func_73864_a(par1, par2, par3);
    }

    public void setMarketSales(List<MarketSale> marketSales) {
        this.marketSales = marketSales;
        this.currentSale = this.getCurrentList();
    }

    public List<MarketSale> getMarketSales() {
        return this.marketSales;
    }

    public void resetSelection() {
        this.selected = null;
        this.guiButton = null;
        this.numberSelector = null;
        this.statsButton = null;
    }

    public MarketSale getSelected() {
        return this.selected;
    }

    private void drawDeleteButton(MarketSale marketSale, int y, int mouseX, int mouseY) {
        if ((marketSale.getPseudo().equals(Minecraft.func_71410_x().field_71439_g.field_71092_bJ) || canRemoveAll) && marketSale.getSuperexpiry() > System.currentTimeMillis()) {
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            Minecraft.func_71410_x().func_110434_K().func_110577_a(BACKGROUND);
            ModernGui.drawModalRectWithCustomSizedTexture(242.0f, y + 1, 16, 373, 19, 19, 372.0f, 400.0f, false);
        }
    }

    private boolean canRemove(MarketSale marketSale) {
        if (marketSale.getSuperexpiry() < System.currentTimeMillis()) {
            return false;
        }
        int withdrawAmount = marketSale.getQuantity() - marketSale.getSold();
        if (withdrawAmount > 0) {
            for (ItemStack itemStack : Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a) {
                if (itemStack != null && itemStack.func_77969_a(marketSale.getItemStack()) && ItemStack.func_77970_a((ItemStack)itemStack, (ItemStack)marketSale.getItemStack())) {
                    int o = itemStack.func_77976_d() - itemStack.field_77994_a;
                    if ((withdrawAmount -= o) > 0) continue;
                    return true;
                }
                if (itemStack != null) continue;
                if (withdrawAmount > marketSale.getItemStack().func_77976_d()) {
                    withdrawAmount -= marketSale.getItemStack().func_77976_d();
                    continue;
                }
                return true;
            }
        } else {
            return true;
        }
        return false;
    }

    public void updateList() {
        this.currentSale = this.getCurrentList();
    }

    private List<MarketSale> getCurrentList() {
        this.filteredMarket = new ArrayList<MarketSale>();
        if (this.marketSales != null) {
            for (MarketSale marketSale : this.marketSales) {
                if (marketSale.getSuperexpiry() <= System.currentTimeMillis()) continue;
                if (marketSale.getPseudo().equals(this.field_73882_e.field_71439_g.field_71092_bJ) && mySalesOnly) {
                    this.filteredMarket.add(marketSale);
                    continue;
                }
                if (marketSale.getPseudo().equals(this.field_73882_e.field_71439_g.field_71092_bJ) || mySalesOnly) continue;
                this.filteredMarket.add(marketSale);
            }
        }
        if (this.filteredMarket.size() > 0) {
            return this.filteredMarket.subList(10 * (currentPage - 1), this.filteredMarket.size() / 10 + 1 == currentPage ? this.filteredMarket.size() : 10 * currentPage);
        }
        return new ArrayList<MarketSale>();
    }

    private List<MarketSale> searchFilter(List<MarketSale> target) {
        ArrayList<MarketSale> result = new ArrayList();
        if (search != null && !search.func_73781_b().equals("")) {
            for (MarketSale marketSale : target) {
                if (search.func_73781_b().startsWith("@")) {
                    String pseudo = search.func_73781_b().substring(1, search.func_73781_b().length());
                    if (!StringUtils.containsIgnoreCase((CharSequence)marketSale.getPseudo(), (CharSequence)pseudo)) continue;
                    result.add(marketSale);
                    continue;
                }
                int id = -1;
                try {
                    id = Integer.parseInt(search.func_73781_b());
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
                if (marketSale == null || marketSale.getItemStack() == null || !StringUtils.containsIgnoreCase((CharSequence)marketSale.getItemStack().func_82833_r(), (CharSequence)search.func_73781_b()) && (id == -1 || id != marketSale.getItemStack().func_77973_b().field_77779_bT)) continue;
                result.add(marketSale);
            }
        } else {
            result = target;
        }
        return result;
    }

    private boolean cursorOnMarket(int mouseX, int mouseY) {
        return mouseX >= this.posX + 6 && mouseX <= this.posX + 6 + 317 && mouseY >= this.posY + 79 && mouseY <= this.posY + 79 + 149;
    }

    public void removeSale(MarketSale marketSale) {
        if (marketSale.equals(this.selected)) {
            this.resetSelection();
        }
        this.currentSale.remove(marketSale);
        this.filteredMarket.remove(marketSale);
        this.marketSales.remove(marketSale);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        switch (par1GuiButton.field_73741_f) {
            case 1: {
                if (currentPage + 1 > this.filteredMarket.size() / 10 + 1) break;
                this.resetSelection();
                this.guiScrollBar.setSliderValue(0.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), ++currentPage - 1, mySalesOnly)));
                break;
            }
            case 0: {
                if (currentPage - 1 < 1) break;
                this.resetSelection();
                this.guiScrollBar.setSliderValue(0.0f);
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), --currentPage - 1, mySalesOnly)));
                break;
            }
            case 2: {
                mySalesOnly = !mySalesOnly;
                currentPage = 1;
                this.resetSelection();
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(search.func_73781_b(), this.filterSelector.getSelectedFilterString(), currentPage - 1, mySalesOnly)));
            }
        }
    }

    static {
        mySalesOnly = false;
        filters = new LinkedHashMap<String, Comparator<MarketSale>>();
        lastBuy = 0L;
        totalResults = 0;
        canRemoveAll = false;
        achievementDone = false;
        filters.put(I18n.func_135053_a((String)"hdv.filter.price.increasing"), new Comparator<MarketSale>(){

            @Override
            public int compare(MarketSale o1, MarketSale o2) {
                return o1.getPrice() - o2.getPrice();
            }
        });
        filters.put(I18n.func_135053_a((String)"hdv.filter.price.declining"), new Comparator<MarketSale>(){

            @Override
            public int compare(MarketSale o1, MarketSale o2) {
                return o2.getPrice() - o1.getPrice();
            }
        });
        filters.put(I18n.func_135053_a((String)"hdv.filter.quantity.increasing"), new Comparator<MarketSale>(){

            @Override
            public int compare(MarketSale o1, MarketSale o2) {
                return o1.getQuantity() - o2.getQuantity();
            }
        });
        filters.put(I18n.func_135053_a((String)"hdv.filter.quantity.declining"), new Comparator<MarketSale>(){

            @Override
            public int compare(MarketSale o1, MarketSale o2) {
                return o2.getQuantity() - o1.getQuantity();
            }
        });
        filters.put(I18n.func_135053_a((String)"hdv.filter.date.increasing"), new Comparator<MarketSale>(){

            @Override
            public int compare(MarketSale o1, MarketSale o2) {
                return (int)(o1.getExpiry() - o2.getExpiry());
            }
        });
        filters.put(I18n.func_135053_a((String)"hdv.filter.date.declining"), new Comparator<MarketSale>(){

            @Override
            public int compare(MarketSale o1, MarketSale o2) {
                return (int)(o2.getExpiry() - o1.getExpiry());
            }
        });
    }
}

