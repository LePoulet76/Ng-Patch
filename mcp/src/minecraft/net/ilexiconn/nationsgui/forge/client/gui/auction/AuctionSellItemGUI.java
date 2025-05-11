/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.renderer.entity.RenderItem
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.item.ItemStack
 *  net.minecraft.network.packet.Packet
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.NumberSelector;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.RadioButton;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.SellItemGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionSellPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import org.lwjgl.opengl.GL11;

public class AuctionSellItemGUI
extends GuiScreen {
    private ItemStack itemStack;
    private NumberSelector startPrice;
    private NumberSelector quantity;
    private NumberSelector time;
    private int posX;
    private int posY;
    private int amount;
    private int taxe = 0;
    private int pubPrice = 200;
    private GuiButton sellButton;
    private int slotID;
    private RadioButton radioButton;

    public AuctionSellItemGUI(ItemStack itemStack, int slotID) {
        this.itemStack = itemStack;
        this.slotID = slotID;
        for (ItemStack itemStackInInventory : Minecraft.func_71410_x().field_71439_g.field_71071_by.field_70462_a) {
            if (itemStackInInventory == null || !itemStackInInventory.func_77969_a(itemStack) || !ItemStack.func_77970_a((ItemStack)itemStackInInventory, (ItemStack)itemStack)) continue;
            this.amount += itemStackInInventory.field_77994_a;
        }
        PacketCallbacks.MONEY.send(new String[0]);
    }

    protected void func_73869_a(char par1, int par2) {
        this.startPrice.keyTyped(par1, par2);
        this.quantity.keyTyped(par1, par2);
        this.time.keyTyped(par1, par2);
        super.func_73869_a(par1, par2);
    }

    protected void func_73864_a(int mouseX, int mouseY, int par3) {
        if (mouseX >= this.posX + 5 && mouseY >= this.posY + 118 && mouseX <= this.posX + 5 + 183 && mouseY <= this.posY + 118 + 19) {
            Minecraft.func_71410_x().func_71373_a((GuiScreen)new SellItemGUI(this.itemStack, this.slotID));
        }
        this.startPrice.mouseClicked(mouseX, mouseY, par3);
        this.quantity.mouseClicked(mouseX, mouseY, par3);
        this.time.mouseClicked(mouseX, mouseY, par3);
        this.radioButton.mousePressed(mouseX, mouseY, par3);
        super.func_73864_a(mouseX, mouseY, par3);
    }

    public void func_73866_w_() {
        this.posX = this.field_73880_f / 2 - 137;
        this.posY = this.field_73881_g / 2 - 59;
        String txt = this.startPrice != null ? this.startPrice.getText() : "0";
        this.startPrice = new NumberSelector(this.field_73882_e, this.posX + 174, this.posY + 16, 80, " $");
        this.startPrice.setText(txt);
        txt = this.quantity != null ? this.quantity.getText() : "0";
        this.quantity = new NumberSelector(this.field_73882_e, this.posX + 174, this.posY + 37, 80);
        this.quantity.setText(txt);
        this.quantity.setMax(this.amount);
        txt = this.time != null ? this.time.getText() : "0";
        this.time = new NumberSelector(this.field_73882_e, this.posX + 174, this.posY + 58, 80);
        this.time.setText(txt);
        this.time.setMax(168);
        this.sellButton = new GuiButton(0, this.posX + 173, this.posY + 83, 80, 20, I18n.func_135053_a((String)"auctions.sell.action"));
        this.field_73887_h.add(this.sellButton);
        this.radioButton = new RadioButton(this.posX + 82, this.posY + 79);
        super.func_73866_w_();
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.func_73873_v_();
        ClientEventHandler.STYLE.bindTexture("auction_sell");
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX, this.posY, 0, 0, 275, 117, 275.0f, 256.0f, false);
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX + 5, this.posY + 117, 79, 117, 183, 19, 275.0f, 256.0f, false);
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX + 5 + 27, this.posY + 117 + 2, 8, 150, 15, 16, 275.0f, 256.0f, false);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"auctions.sell.toHDV"), this.posX + 5 + 23 + 25 + 10, this.posY + 118 + 5, 0xFFFFFF);
        ModernGui.drawScaledString(this.itemStack.func_82833_r(), this.posX + 47, this.posY + 17, 0xFFFFFF, 0.75f, true, false);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"auctions.sell.price"), this.posX + 84, this.posY + 22, 0xFFFFFF);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"auctions.sell.quantity"), this.posX + 84, this.posY + 44, 0xFFFFFF);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"auctions.sell.time"), this.posX + 84, this.posY + 66, 0xFFFFFF);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"auctions.ad"), this.posX + 82 + 14, this.posY + 79, 0xFFFFFF);
        String t = "...";
        this.taxe = 200;
        if (this.radioButton.getState()) {
            this.taxe += this.pubPrice;
        }
        t = Integer.toString(this.taxe);
        String s = I18n.func_135052_a((String)"auctions.sell.taxes", (Object[])new Object[]{t});
        this.field_73886_k.func_78276_b(s, this.posX + 82, this.posY + 92, 0xFFFFFF);
        if (this.startPrice != null) {
            this.startPrice.draw(par1, par2);
        }
        if (this.quantity != null) {
            this.quantity.draw(par1, par2);
        }
        if (this.time != null) {
            this.time.draw(par1, par2);
        }
        RenderItem renderItem = new RenderItem();
        float modifier = 3.2f;
        int size = (int)(16.0f * modifier);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(this.posX + 47 - size / 2), (float)(this.posY + 58 - size / 2), (float)0.0f);
        GL11.glScalef((float)modifier, (float)modifier, (float)1.0f);
        ItemStack item = this.itemStack.func_77946_l();
        item.field_77994_a = 1;
        renderItem.func_77015_a(this.field_73886_k, this.field_73882_e.func_110434_K(), item, 0, 0);
        renderItem.func_77021_b(this.field_73886_k, this.field_73882_e.func_110434_K(), item, 0, 0);
        GL11.glPopMatrix();
        GL11.glDisable((int)2896);
        this.radioButton.draw(par1, par2);
        super.func_73863_a(par1, par2, par3);
        if (par1 >= this.posX + 14 && par1 <= this.posX + 14 + 65 && par2 >= this.posY + 13 && par2 <= this.posY + 13 + 91) {
            NationsGUIClientHooks.drawItemStackTooltip(item, par1, par2);
        }
        GL11.glDisable((int)2896);
    }

    public void func_73876_c() {
        int d = this.taxe;
        if (this.radioButton.getState()) {
            d += this.pubPrice;
        }
        this.sellButton.field_73742_g = this.taxe != -1 && (double)d <= ShopGUI.CURRENT_MONEY && this.quantity.getNumber() > 0 && this.startPrice.getNumber() > 0 && this.time.getNumber() > 0;
    }

    public void setTaxe(int taxe) {
        this.taxe = taxe;
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73741_f == 0) {
            PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AuctionSellPacket(this.slotID, this.startPrice.getNumber(), this.quantity.getNumber(), this.time.getNumber() * 3600 * 1000, this.radioButton.getState())));
            this.field_73882_e.func_71373_a(null);
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public void setPubPrice(int pubPrice) {
        this.pubPrice = pubPrice;
    }
}

