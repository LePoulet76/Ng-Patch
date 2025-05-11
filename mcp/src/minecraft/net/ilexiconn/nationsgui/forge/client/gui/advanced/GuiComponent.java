/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;

public interface GuiComponent {
    public void init(ComponentContainer var1);

    public void draw(int var1, int var2, float var3);

    public void onClick(int var1, int var2, int var3);

    public void update();

    public void keyTyped(char var1, int var2);

    public boolean isPriorityClick();
}

