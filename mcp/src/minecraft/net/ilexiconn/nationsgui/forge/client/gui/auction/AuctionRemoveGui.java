/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.data.Auction;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionRemovePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.packet.Packet;

public class AuctionRemoveGui
extends GuiScreen {
    private int posX;
    private int posY;
    private GuiScreen previous;
    private Auction auction;
    private List<String> textList = new ArrayList<String>();

    public AuctionRemoveGui(GuiScreen previous, Auction auction) {
        String[] words;
        this.previous = previous;
        this.auction = auction;
        this.textList.clear();
        StringBuilder sub = new StringBuilder();
        for (String word : words = I18n.func_135053_a((String)"hdv.remove.confirm.text").split(" ")) {
            String temp = (!Objects.equals(words[0], word) ? " " : "") + word;
            if (Minecraft.func_71410_x().field_71466_p.func_78256_a(sub.toString()) + Minecraft.func_71410_x().field_71466_p.func_78256_a(temp) <= 210) {
                sub.append(temp);
                continue;
            }
            this.textList.add(sub.toString());
            sub = new StringBuilder(word);
        }
        if (!sub.toString().equals("")) {
            this.textList.add(sub.toString());
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.posX = this.field_73880_f / 2 - 137;
        this.posY = this.field_73881_g / 2 - 58;
        this.field_73887_h.clear();
        this.field_73887_h.add(new GuiButton(0, this.posX + 50, this.posY + 75, 75, 20, I18n.func_135053_a((String)"hdv.remove.confirm.yes")));
        this.field_73887_h.add(new GuiButton(1, this.posX + 260 - 75 - 50, this.posY + 75, 75, 20, I18n.func_135053_a((String)"hdv.remove.confirm.no")));
    }

    public void func_73863_a(int par1, int par2, float par3) {
        ClientEventHandler.STYLE.bindTexture("auction_house");
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX, this.posY, 0, 0, 275, 171, 275.0f, 256.0f, false);
        this.field_73886_k.func_78276_b(I18n.func_135053_a((String)"hdv.remove.confirm.title"), this.posX + 14 + 123 - this.field_73886_k.func_78256_a(I18n.func_135053_a((String)"hdv.remove.confirm.title")) / 2, this.posY + 14, 0);
        int i = 0;
        for (String line : this.textList) {
            this.field_73886_k.func_78276_b(line, this.posX + 14 + 123 - this.field_73886_k.func_78256_a(line) / 2, this.posY + 30 + 10 * i, 0);
            ++i;
        }
        super.func_73863_a(par1, par2, par3);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        switch (par1GuiButton.field_73741_f) {
            case 0: {
                PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AuctionRemovePacket(this.auction.getUuid())));
                if (this.previous instanceof AuctionGui) {
                    AuctionGui auctionGui = (AuctionGui)this.previous;
                    auctionGui.removeAuction(this.auction);
                }
                this.field_73882_e.func_71373_a(this.previous);
                break;
            }
            case 1: {
                this.field_73882_e.func_71373_a(this.previous);
            }
        }
    }
}

