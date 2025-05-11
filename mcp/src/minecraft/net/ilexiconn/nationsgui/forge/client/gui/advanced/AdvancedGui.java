/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiScreen
 */
package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.minecraft.client.gui.GuiScreen;

public abstract class AdvancedGui
extends GuiScreen
implements ComponentContainer {
    private final List<GuiComponent> components = new ArrayList<GuiComponent>();

    public void func_73866_w_() {
        super.func_73866_w_();
        this.components.clear();
    }

    protected void addComponent(GuiComponent guiComponent) {
        if (!this.components.contains(guiComponent)) {
            this.components.add(guiComponent);
        }
        guiComponent.init(this);
    }

    public void func_73863_a(int par1, int par2, float par3) {
        for (GuiComponent component : this.components) {
            component.draw(par1, par2, par3);
        }
        super.func_73863_a(par1, par2, par3);
    }

    protected void func_73869_a(char par1, int par2) {
        for (GuiComponent component : this.components) {
            component.keyTyped(par1, par2);
        }
        super.func_73869_a(par1, par2);
    }

    protected void func_73864_a(int par1, int par2, int par3) {
        boolean clickSkipped = false;
        for (GuiComponent component : this.components) {
            if (!component.isPriorityClick()) continue;
            component.onClick(par1, par2, par3);
            clickSkipped = true;
        }
        if (!clickSkipped) {
            for (GuiComponent component : this.components) {
                component.onClick(par1, par2, par3);
            }
        }
        super.func_73864_a(par1, par2, par3);
    }

    public void func_73876_c() {
        for (GuiComponent component : this.components) {
            component.update();
        }
        super.func_73876_c();
    }
}

