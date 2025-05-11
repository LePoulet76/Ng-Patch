/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiTextMultiLines
extends Gui
implements GuiScrollerElement {
    private int lineMargin = 1;
    private final List<String> lines = new ArrayList<String>();
    private int posX;
    private int posY;
    private int color = 0xFFFFFF;
    private boolean centered = false;
    private float textSize = 1.0f;
    private int width = 0;

    public GuiTextMultiLines(String text, int width, boolean centered, float textSize) {
        String[] words;
        this.centered = centered;
        this.textSize = textSize;
        this.width = width;
        StringBuilder sub = new StringBuilder();
        for (String word : words = text.split(" ")) {
            String temp = (!Objects.equals(words[0], word) ? " " : "") + word;
            if (Minecraft.func_71410_x().field_71466_p.func_78256_a(sub.toString()) + Minecraft.func_71410_x().field_71466_p.func_78256_a(temp) <= width) {
                sub.append(temp);
                continue;
            }
            this.lines.add(sub.toString());
            sub = new StringBuilder(word);
        }
        if (!sub.toString().equals("")) {
            this.lines.add(sub.toString());
        }
    }

    public void setPosition(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void init(GuiScroller scroller) {
    }

    @Override
    public int getHeight() {
        return this.lines.size() * this.getLineHeight();
    }

    @Override
    public void init(ComponentContainer container) {
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        for (int i = 0; i < this.lines.size(); ++i) {
            String line = this.lines.get(i);
            ModernGui.drawScaledString(line, this.centered ? this.posX + this.width / 2 : this.posX, this.posY + this.getLineHeight() * i, this.color, this.textSize, this.centered, false);
        }
    }

    @Override
    public void onClick(int mouseX, int mouseY, int clickType) {
    }

    @Override
    public void update() {
    }

    @Override
    public void keyTyped(char c, int key) {
    }

    @Override
    public boolean isPriorityClick() {
        return false;
    }

    private int getLineHeight() {
        return 10 + this.lineMargin;
    }

    public void setLineMargin(int lineMargin) {
        this.lineMargin = lineMargin;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

