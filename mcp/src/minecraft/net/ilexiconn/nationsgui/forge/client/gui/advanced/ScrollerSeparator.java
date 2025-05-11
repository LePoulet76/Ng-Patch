/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScrollerElement;
import net.minecraft.client.gui.Gui;

public class ScrollerSeparator
extends Gui
implements GuiScrollerElement {
    private int width;

    @Override
    public void init(ComponentContainer container) {
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        ScrollerSeparator.func_73734_a((int)0, (int)4, (int)this.width, (int)5, (int)-16777216);
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

    @Override
    public void init(GuiScroller scroller) {
        this.width = scroller.getWorkWidth();
    }

    @Override
    public int getHeight() {
        return 9;
    }
}

