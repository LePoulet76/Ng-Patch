/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiButton
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.client.settings.EnumOptions
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import java.io.IOException;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.GuiButtonOptionBoolean;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.EnumOptions;

public class CustomizationGUI
extends GuiScreen {
    GuiScreen previous;
    private boolean displayRestartMessage = false;

    public CustomizationGUI(GuiScreen previous) {
        this.previous = previous;
    }

    public void func_73863_a(int par1, int par2, float par3) {
        this.func_73873_v_();
        this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"gui.customization.title"), this.field_73880_f / 2, 15, 0xFFFFFF);
        super.func_73863_a(par1, par2, par3);
        if (this.displayRestartMessage) {
            this.func_73732_a(this.field_73886_k, I18n.func_135053_a((String)"gui.option.restartRequired"), this.field_73880_f / 2, this.field_73881_g / 6 + 153, 0xFFFFFF);
        }
    }

    public void func_73866_w_() {
        super.func_73866_w_();
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 - 152, this.field_73881_g / 6, 150, 20, I18n.func_135053_a((String)"options.displayobjectives")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.displayObjectives = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.displayObjectives;
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 125, 150, 20, I18n.func_135053_a((String)"options.azimutenable")){

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.enableAzimut;
            }

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.enableAzimut = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6, 150, 20, I18n.func_135053_a((String)"options.azimutposition")){

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.azimutBottom;
            }

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.azimutBottom = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String getEnabledStateText() {
                return "Bas";
            }

            @Override
            protected String getDisabledStateText() {
                return "Haut";
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 - 25, 150, 20, I18n.func_135053_a((String)"options.armorInfosRight")){

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.armorInfosRight;
            }

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.armorInfosRight = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String getEnabledStateText() {
                return "Droite";
            }

            @Override
            protected String getDisabledStateText() {
                return "Gauche";
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 25, 150, 20, I18n.func_135053_a((String)"options.damageIndicator")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.damageIndicator = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.damageIndicator;
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 25, 150, 20, I18n.func_135053_a((String)"options.displayArmorInInfo")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.displayArmorInInfo = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.displayArmorInInfo;
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 50, 150, 20, I18n.func_135053_a((String)"options.render3DSkins")){

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.render3DSkins;
            }

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.render3DSkins = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 50, 150, 20, I18n.func_135053_a((String)"options.displayNotifications")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.displayNotifications = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.displayNotifications;
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 75, 150, 20, I18n.func_135053_a((String)"options.displayFurnitures")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.renderFurnitures = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                CustomizationGUI.this.displayRestartMessage = true;
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.renderFurnitures;
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 75, 150, 20, I18n.func_135053_a((String)"options.customArmor")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.renderCustomArmors = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.renderCustomArmors;
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 100, 150, 20, I18n.func_135053_a((String)"options.pictureframe")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.displayPictureFrame = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.displayPictureFrame;
            }
        });
        final EnumOptions options = EnumOptions.SHOW_CAPE;
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 + 2, this.field_73881_g / 6 + 100, 150, 20, I18n.func_135053_a((String)options.func_74378_d())){

            @Override
            public void setData(Boolean data) {
                ((CustomizationGUI)CustomizationGUI.this).field_73882_e.field_71474_y.func_74306_a(options, 1);
            }

            @Override
            public Boolean getData() {
                return ((CustomizationGUI)CustomizationGUI.this).field_73882_e.field_71474_y.func_74308_b(options);
            }
        });
        this.field_73887_h.add(new GuiButtonOptionBoolean(1, this.field_73880_f / 2 - 152, this.field_73881_g / 6 + 125, 150, 20, I18n.func_135053_a((String)"options.displayEmotes")){

            @Override
            public void setData(Boolean data) {
                ClientProxy.clientConfig.renderEmotes = data;
                try {
                    ClientProxy.saveConfig();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public Boolean getData() {
                return ClientProxy.clientConfig.renderEmotes;
            }
        });
        this.field_73887_h.add(new GuiButton(0, this.field_73880_f / 2 - 100, this.field_73881_g / 6 + 165, I18n.func_135053_a((String)"gui.done")));
    }

    protected void func_73875_a(GuiButton par1GuiButton) {
        if (par1GuiButton.field_73741_f == 0) {
            this.field_73882_e.func_71373_a(this.previous);
        }
    }
}

