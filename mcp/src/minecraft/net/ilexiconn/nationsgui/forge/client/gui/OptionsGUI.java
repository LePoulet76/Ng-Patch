/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiOptions
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.CustomizationGUI;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScreenshotOptions;
import net.ilexiconn.nationsgui.forge.client.gui.SoundGUI;
import net.ilexiconn.nationsgui.forge.client.gui.voices.options.GuiVoiceChatOptions;
import net.ilexiconn.nationsgui.forge.server.config.NBTConfig;
import net.ilexiconn.nationsgui.forge.server.voices.VoiceChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

@SideOnly(value=Side.CLIENT)
public class OptionsGUI
extends GuiOptions {
    public OptionsGUI(GuiScreen parent) {
        super(parent, Minecraft.func_71410_x().field_71474_y);
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.add(new GuiButton(1044, this.field_73880_f / 2 + 5, this.field_73881_g / 6 - 12 + 72, 150, 20, I18n.func_135053_a((String)"options.sounds")));
        Iterator iterator = this.field_73887_h.iterator();
        List<Integer> idsToRemove = Arrays.asList(104, 1, 0, 27, 200);
        while (iterator.hasNext()) {
            GuiButton guiButton = (GuiButton)iterator.next();
            if (!idsToRemove.contains(guiButton.field_73741_f)) continue;
            iterator.remove();
        }
        this.field_73887_h.add(new GuiButton(1042, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 144 - 6, 150, 20, I18n.func_135053_a((String)"gui.customization.title")));
        this.field_73887_h.add(new GuiButton(146, this.field_73880_f / 2 - 155, this.field_73881_g / 6 + 60, 150, 20, I18n.func_135053_a((String)"options.voices")));
        this.field_73887_h.add(new GuiButton(156, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 162, 150, 20, I18n.func_135053_a((String)"options.screenshots")));
        this.field_73887_h.add(new GuiButton(200, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 194, I18n.func_135053_a((String)"gui.done")));
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        switch (par1GuiButton.field_73741_f) {
            case 1042: {
                this.field_73882_e.func_71373_a((GuiScreen)new CustomizationGUI((GuiScreen)this));
                break;
            }
            case 1044: {
                this.field_73882_e.func_71373_a((GuiScreen)new SoundGUI((GuiScreen)this));
                break;
            }
            case 146: {
                this.field_73882_e.func_71373_a((GuiScreen)new GuiVoiceChatOptions(VoiceChat.getProxyInstance(), (GuiScreen)this));
                break;
            }
            case 156: {
                this.field_73882_e.func_71373_a((GuiScreen)new GuiScreenshotOptions((GuiScreen)this));
            }
        }
    }

    public void func_73874_b() {
        NBTConfig.CONFIG.save();
    }
}

