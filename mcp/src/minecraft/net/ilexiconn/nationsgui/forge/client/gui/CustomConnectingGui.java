/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiScreen
 *  net.minecraft.client.multiplayer.GuiConnecting
 *  net.minecraft.client.resources.I18n
 */
package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;

public class CustomConnectingGui
extends GuiConnecting {
    private GuiScreen prevGuiScreen;

    public CustomConnectingGui(GuiScreen prevGuiScreen, String ip, int port, String serverName) {
        super(prevGuiScreen, Minecraft.func_71410_x(), ip, port);
        ClientData.lastCustomConnectionIP = ip;
        ClientData.lastCustomConnectionPort = port;
        ClientData.lastCustomConnectionServerName = serverName;
    }

    public void func_73863_a(int par1, int par2, float par3) {
        String backgroudURL = "https://apiv2.nationsglory.fr/proxy_images/screen_join_all";
        if (ClientData.lastCustomConnectionServerName != null && ClientData.lastCustomConnectionServerName.equalsIgnoreCase("hub")) {
            backgroudURL = "https://apiv2.nationsglory.fr/proxy_images/screen_join_hub";
        } else if (ClientData.lastCustomConnectionServerName != null && ClientData.lastCustomConnectionServerName.toLowerCase().contains("event")) {
            backgroudURL = "https://apiv2.nationsglory.fr/proxy_images/screen_join_event";
        }
        ModernGui.bindRemoteTexture(backgroudURL);
        ModernGui.drawScaledCustomSizeModalRect(0.0f, 0.0f, 0.0f, 0.0f, 3840, 2160, this.field_73880_f, this.field_73881_g, 3840.0f, 2160.0f, false);
        Minecraft.func_71410_x().func_110434_K().func_110577_a(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
        ModernGui.drawScaledCustomSizeModalRect(12.0f, this.field_73881_g - 38, 1074 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 8 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 85 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 96 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 28, 32, 1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, false);
        if (ClientData.lastCustomConnectionServerName != null) {
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.connecting.move_to"), 52.0f, this.field_73881_g - 28, 0x878788, 0.5f, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.connecting.server") + " " + ClientData.lastCustomConnectionServerName + " ...", 52.0f, this.field_73881_g - 22, 0xFFFFFF, 0.5f, "left", false, "minecraftDungeons", 27);
        }
    }

    public void func_73873_v_() {
    }
}

