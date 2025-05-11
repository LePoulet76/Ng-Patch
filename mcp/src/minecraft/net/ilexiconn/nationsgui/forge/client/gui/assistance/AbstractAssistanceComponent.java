/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.renderer.Tessellator
 */
package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;

public abstract class AbstractAssistanceComponent
extends Gui
implements GuiComponent {
    protected ComponentContainer container;

    public void func_73729_b(int posX, int posY, int u, int v, int width, int height) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(AbstractAssistanceGUI.GUI_TEXTURE);
        float f = 0.001953125f;
        float f1 = 0.001953125f;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)posX, (double)(posY + height), (double)this.field_73735_i, (double)((float)u * f), (double)((float)(v + height) * f1));
        tessellator.func_78374_a((double)(posX + width), (double)(posY + height), (double)this.field_73735_i, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.func_78374_a((double)(posX + width), (double)posY, (double)this.field_73735_i, (double)((float)(u + width) * f), (double)((float)v * f1));
        tessellator.func_78374_a((double)posX, (double)posY, (double)this.field_73735_i, (double)((float)u * f), (double)((float)v * f1));
        tessellator.func_78381_a();
    }

    @Override
    public boolean isPriorityClick() {
        return false;
    }

    @Override
    public void init(ComponentContainer container) {
        this.container = container;
    }
}

