/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.gui.ScreenChatOptions
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.GameSettings
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class MultiplayerConfigGUI
extends ScreenChatOptions {
    public MultiplayerConfigGUI(GuiScreen p_i1023_1_, GameSettings p_i1023_2_) {
        super(p_i1023_1_, p_i1023_2_);
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        Iterator iterator = this.field_73887_h.iterator();
        List<Integer> idsToRemove = Arrays.asList(18, 19, 12);
        while (iterator.hasNext()) {
            GuiButton guiButton = (GuiButton)iterator.next();
            if (!idsToRemove.contains(guiButton.field_73741_f)) continue;
            iterator.remove();
        }
        int i = 1;
        GuiButton guiButtonTimestamp = new GuiButton(4244, this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 + 24 * (i >> 1), 150, 20, "Enable Timestamp");
        guiButtonTimestamp.field_73744_e = I18n.func_135053_a((String)"options.chat_timestamp") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableTimestamp ? "options.on" : "options.off"));
        this.field_73887_h.add(guiButtonTimestamp);
        i = 2;
        GuiButton guiButtonTag = new GuiButton(4245, this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 + 24 * (i >> 1), 150, 20, "Enable Tag");
        guiButtonTag.field_73744_e = I18n.func_135053_a((String)"options.chat_tag") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableTag ? "options.on" : "options.off"));
        this.field_73887_h.add(guiButtonTag);
        i = 9;
        GuiButton guiButtonUnicode = new GuiButton(4243, this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 + 24 * (i >> 1), 150, 20, "Enable Unicode");
        guiButtonUnicode.field_73744_e = I18n.func_135053_a((String)"options.chat_unicode") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableUnicode ? "options.on" : "options.off"));
        this.field_73887_h.add(guiButtonUnicode);
        i = 12;
        GuiButton guiButtonAnimation = new GuiButton(4242, this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 + 24 * (i >> 1), 150, 20, I18n.func_135053_a((String)"options.chat_animation"));
        guiButtonAnimation.field_73744_e = I18n.func_135053_a((String)"options.chat_animation") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableChatAnimation ? "options.on" : "options.off"));
        this.field_73887_h.add(guiButtonAnimation);
        i = 13;
        GuiButton guiButtonBackground = new GuiButton(4241, this.field_73880_f / 2 - 155 + i % 2 * 160, this.field_73881_g / 6 + 24 * (i >> 1), 150, 20, I18n.func_135053_a((String)"options.chat_background"));
        guiButtonBackground.field_73744_e = I18n.func_135053_a((String)"options.chat_background") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableChatBackground ? "options.on" : "options.off"));
        this.field_73887_h.add(guiButtonBackground);
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        super.func_73875_a(par1GuiButton);
        if (par1GuiButton.field_73741_f == 4242) {
            ClientProxy.clientConfig.enableChatAnimation = !ClientProxy.clientConfig.enableChatAnimation;
            try {
                ClientProxy.saveConfig();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            par1GuiButton.field_73744_e = I18n.func_135053_a((String)"options.chat_animation") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableChatAnimation ? "options.on" : "options.off"));
        } else if (par1GuiButton.field_73741_f == 4241) {
            ClientProxy.clientConfig.enableChatBackground = !ClientProxy.clientConfig.enableChatBackground;
            try {
                ClientProxy.saveConfig();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            par1GuiButton.field_73744_e = I18n.func_135053_a((String)"options.chat_background") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableChatBackground ? "options.on" : "options.off"));
        } else if (par1GuiButton.field_73741_f == 4243) {
            ClientProxy.clientConfig.enableUnicode = !ClientProxy.clientConfig.enableUnicode;
            try {
                ClientProxy.saveConfig();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            par1GuiButton.field_73744_e = I18n.func_135053_a((String)"options.chat_unicode") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableUnicode ? "options.on" : "options.off"));
        } else if (par1GuiButton.field_73741_f == 4244) {
            ClientProxy.clientConfig.enableTimestamp = !ClientProxy.clientConfig.enableTimestamp;
            try {
                ClientProxy.saveConfig();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            par1GuiButton.field_73744_e = I18n.func_135053_a((String)"options.chat_timestamp") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableTimestamp ? "options.on" : "options.off"));
        } else if (par1GuiButton.field_73741_f == 4245) {
            ClientProxy.clientConfig.enableTag = !ClientProxy.clientConfig.enableTag;
            try {
                ClientProxy.saveConfig();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            par1GuiButton.field_73744_e = I18n.func_135053_a((String)"options.chat_tag") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.enableTag ? "options.on" : "options.off"));
        }
    }

    private void updateButtonText(GuiButton guiButton) {
        guiButton.field_73744_e = I18n.func_135053_a((String)"options.pictureframe") + ": " + I18n.func_135053_a((String)(ClientProxy.clientConfig.displayPictureFrame ? "options.on" : "options.off"));
    }
}

