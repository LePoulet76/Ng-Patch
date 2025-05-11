/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.network.packet.Packet
 */
package net.ilexiconn.nationsgui.forge.client.gui.auction;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.auction.AuctionGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.AuctionDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.packet.Packet;

public class AuctionFilterSelectorGui<T>
extends Gui {
    private int posX;
    private int posY;
    private int width;
    private Map<String, T> stringComparatorMap;
    private Map.Entry<String, T> currentSelection;
    private boolean open;

    public AuctionFilterSelectorGui(int posX, int posY, int width, Map<String, T> stringComparatorMap) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.stringComparatorMap = stringComparatorMap;
        Iterator<Map.Entry<String, T>> iterator = stringComparatorMap.entrySet().iterator();
        this.currentSelection = iterator.next();
    }

    public void draw(int mouseX, int mouseY) {
        FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
        String text = this.currentSelection.getKey();
        fontRenderer.func_78276_b(text, this.posX + (this.width - fontRenderer.func_78256_a(text)) / 2, this.posY + 4, 0xFFFFFF);
        if (this.open) {
            int i = 1;
            AuctionFilterSelectorGui.func_73734_a((int)(this.posX - 1), (int)(this.posY + 15), (int)(this.posX + this.width + 16), (int)(this.posY + (this.stringComparatorMap.size() + 1) * 15 + 1), (int)-13487566);
            for (Map.Entry<String, T> entry : this.stringComparatorMap.entrySet()) {
                int y = this.posY + 15 * i;
                int color = -14737633;
                if (mouseX >= this.posX && mouseX <= this.posX + this.width + 15 && mouseY >= y && mouseY <= y + 15) {
                    color = -11119018;
                }
                AuctionFilterSelectorGui.func_73734_a((int)this.posX, (int)y, (int)(this.posX + this.width + 15), (int)(y + 15), (int)color);
                fontRenderer.func_78276_b(entry.getKey(), this.posX + (this.width - fontRenderer.func_78256_a(entry.getKey()) + 15) / 2, y + 2, 0xFFFFFF);
                ++i;
            }
        }
    }

    public T getSelectedObject() {
        return this.currentSelection.getValue();
    }

    public String getSelectedFilterString() {
        return this.currentSelection.getKey();
    }

    public boolean mousePressed(int mouseX, int mouseY) {
        if (mouseX > this.posX && mouseX < this.posX + this.width && mouseY > this.posY && mouseY < this.posY + 13) {
            this.open = !this.open;
        } else if (this.open) {
            int i = 1;
            for (Map.Entry<String, T> entry : this.stringComparatorMap.entrySet()) {
                int y = this.posY + 15 * i;
                if (mouseX >= this.posX && mouseX <= this.posX + this.width + 15 && mouseY >= y && mouseY <= y + 14) {
                    this.currentSelection = entry;
                    AuctionGui.currentPage = 1;
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new AuctionDataPacket(AuctionGui.search.func_73781_b(), entry.getKey(), AuctionGui.currentPage - 1, AuctionGui.myAuctionsOnly)));
                    this.open = false;
                    return true;
                }
                ++i;
            }
        }
        return false;
    }

    public Map.Entry<String, T> saveSelection() {
        return this.currentSelection;
    }

    public void restoreSelection(Map.Entry<String, T> s) {
        this.currentSelection = s;
    }
}

