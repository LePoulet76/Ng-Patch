/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  fr.nationsglory.ngbrowser.NGBrowser
 *  fr.nationsglory.ngbrowser.client.ClientProxy
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.GuiSlider
 *  net.minecraft.client.gui.GuiSmallButton
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.EnumOptions
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import fr.nationsglory.ngbrowser.NGBrowser;
import fr.nationsglory.ngbrowser.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.SliderGUI;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;

public class SoundGUI
extends GuiScreen {
    private GuiScreen previous;
    private static final EnumOptions[] relevantOptions = new EnumOptions[]{EnumOptions.MUSIC, EnumOptions.SOUND};

    public SoundGUI(GuiScreen previous) {
        this.previous = previous;
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.func_73873_v_();
        this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"options.sounds"), this.field_73880_f / 2, 15, 0xFFFFFF);
        super.func_73863_a(par1, par2, par3);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73741_f == 100) {
            this.field_73882_e.func_71373_a(this.previous);
        }
    }

    public void func_73866_w_() {
        this.field_73887_h.clear();
        this.field_73887_h.add(new SliderGUI(-1, this.field_73880_f / 2 - 155, this.field_73881_g / 6 + 12, "Radio", new SliderGUI.ISliderCallback(){

            @Override
            public void call(float value) {
                NBTConfig.CONFIG.getCompound().func_74776_a("RadioVolume", value);
            }

            @Override
            public float getMaxValue() {
                return 100.0f;
            }

            @Override
            public float getCurrentValue() {
                return NBTConfig.CONFIG.getCompound().func_74760_g("RadioVolume");
            }
        }));
        this.field_73887_h.add(new GuiButton(100, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 50, I18n.func_135053_a((String)"gui.done")));
        EnumOptions[] aenumoptions = relevantOptions;
        int j = aenumoptions.length;
        int i = 0;
        for (int k = 0; k < j; ++k) {
            EnumOptions enumoptions = aenumoptions[k];
            if (enumoptions.func_74380_a()) {
                this.field_73887_h.add(new GuiSlider(enumoptions.func_74381_c(), this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 - 12 + 24 * (i >> 1), enumoptions, this.field_73882_e.field_71474_y.func_74297_c(enumoptions), this.field_73882_e.field_71474_y.func_74296_a(enumoptions)));
            } else {
                GuiSmallButton guismallbutton = new GuiSmallButton(enumoptions.func_74381_c(), this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 - 12 + 24 * (i >> 1), enumoptions, this.field_73882_e.field_71474_y.func_74297_c(enumoptions));
                if (enumoptions == EnumOptions.DIFFICULTY && this.field_73882_e.field_71441_e != null && this.field_73882_e.field_71441_e.func_72912_H().func_76093_s()) {
                    guismallbutton.field_73742_g = false;
                    guismallbutton.field_73744_e = I18n.func_135053_a((String)"options.difficulty") + ": " + I18n.func_135053_a((String)"options.difficulty.hardcore");
                }
                this.field_73887_h.add(guismallbutton);
            }
            ++i;
        }
        this.field_73887_h.add(new SliderGUI(-1, this.field_73880_f / 2 + 5, this.field_73881_g / 6 - 12 + 24, "Browser Volume", new SliderGUI.ISliderCallback(){

            @Override
            public void call(float value) {
                NBTConfig.CONFIG.getCompound().func_74776_a("BrowserVolume", value);
                ClientProxy clientProxy = (ClientProxy)NGBrowser.proxy;
                clientProxy.setBrowserVolume(value / 100.0f);
            }

            @Override
            public float getMaxValue() {
                return 100.0f;
            }

            @Override
            public float getCurrentValue() {
                return NBTConfig.CONFIG.getCompound().func_74760_g("BrowserVolume");
            }
        }));
    }
}

