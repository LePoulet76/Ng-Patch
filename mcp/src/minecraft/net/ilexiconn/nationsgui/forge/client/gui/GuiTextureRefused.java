/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect
 *  net.minecraft.client.resources.I18n
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.GuiScreenTemporaryResourcePackSelect;
import net.minecraft.client.resources.I18n;

public class GuiTextureRefused
extends GuiScreen {
    private GuiScreen previous;

    public GuiTextureRefused(GuiScreen previous) {
        this.previous = previous;
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.func_73871_c(0);
        this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"textureRefused.title"), this.field_73880_f / 2, 25, 0xFFFFFF);
        this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"textureRefused.text"), this.field_73880_f / 2, 75, 0xFFFFFF);
        super.func_73863_a(par1, par2, par3);
    }

    public void func_73866_w_() {
        this.field_73887_h.clear();
        this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 - 100, this.field_73881_g - 30, I18n.func_135053_a((String)"textureRefused.button")));
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73741_f == 0) {
            this.field_73882_e.func_71373_a((GuiScreen)new GuiScreenTemporaryResourcePackSelect(this.previous, this.field_73882_e.field_71474_y));
        }
    }
}

