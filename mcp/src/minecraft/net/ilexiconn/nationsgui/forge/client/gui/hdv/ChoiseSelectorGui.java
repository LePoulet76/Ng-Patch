/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.common.network.PacketDispatcher
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.network.packet.Packet
 *  net.minecraft.util.ResourceLocation
 */
package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import cpw.mods.fml.common.network.PacketDispatcher;
import java.util.Iterator;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.hdv.MarketGui;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.MarketPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.ResourceLocation;

public class ChoiseSelectorGui<T>
extends Gui {
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");
    private int posX;
    private int posY;
    private int width;
    private Map<String, T> stringComparatorMap;
    private Map.Entry<String, T> currentSelection;
    private boolean open;

    public ChoiseSelectorGui(int posX, int posY, int width, Map<String, T> stringComparatorMap) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.stringComparatorMap = stringComparatorMap;
        Iterator<Map.Entry<String, T>> iterator = stringComparatorMap.entrySet().iterator();
        this.currentSelection = iterator.next();
    }

    public void draw(int mouseX, int mouseY) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(BACKGROUND);
        ModernGui.drawModalRectWithCustomSizedTexture(this.posX, this.posY, 0, 373, 14, 16, 372.0f, 400.0f, false);
        ChoiseSelectorGui.func_73734_a((int)(this.posX + 14), (int)this.posY, (int)(this.posX + this.width - 1), (int)(this.posY + 16), (int)-13487566);
        ChoiseSelectorGui.func_73734_a((int)(this.posX + this.width - 1), (int)(this.posY + 1), (int)(this.posX + this.width), (int)(this.posY + 15), (int)-13487566);
        FontRenderer fontRenderer = Minecraft.func_71410_x().field_71466_p;
        String text = this.currentSelection.getKey();
        fontRenderer.func_78276_b(text, this.posX + 14 + (this.width - 14) / 2 - fontRenderer.func_78256_a(text) / 2, this.posY + 5, 0xFFFFFF);
        if (this.open) {
            int i = 1;
            for (Map.Entry<String, T> entry : this.stringComparatorMap.entrySet()) {
                int y = this.posY - 14 * i;
                int color = -14737633;
                if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= y && mouseY <= y + 14) {
                    color = -11119018;
                }
                ChoiseSelectorGui.func_73734_a((int)this.posX, (int)y, (int)(this.posX + this.width), (int)(y + 14), (int)color);
                fontRenderer.func_78276_b(entry.getKey(), this.posX + this.width / 2 - fontRenderer.func_78256_a(entry.getKey()) / 2, y + 2, 0xFFFFFF);
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
                int y = this.posY - 14 * i;
                if (mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= y && mouseY <= y + 14) {
                    this.currentSelection = entry;
                    MarketGui.currentPage = 1;
                    PacketDispatcher.sendPacketToServer((Packet)PacketRegistry.INSTANCE.generatePacket(new MarketPacket(MarketGui.search.func_73781_b(), entry.getKey(), MarketGui.currentPage - 1, MarketGui.mySalesOnly)));
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

