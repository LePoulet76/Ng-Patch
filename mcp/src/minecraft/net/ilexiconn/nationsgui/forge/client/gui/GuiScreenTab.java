/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.GuiScreen;

public interface GuiScreenTab {
    public Class<? extends GuiScreen> getClassReferent();

    public void call();
}

