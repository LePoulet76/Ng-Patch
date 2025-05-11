/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.I18n
 */
package net.ilexiconn.nationsgui.forge.client.gui.modern;

import java.math.BigInteger;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.data.ServersData;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernColorIconButton;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernResourceLocation;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ModernServerButton
extends ModernColorIconButton {
    private String ip;
    private int port;
    public ServersData.Server server;
    public static CFontRenderer slotNumberRenderer;
    private String serverName;
    private String serverType;

    public ModernServerButton(int id, int posX, int posY, int width, int height, ServersData.Server server, String serverName, String serverType) {
        super(id, posX, posY, width, height, "\u00a70" + I18n.func_135053_a((String)("nationsgui.server." + serverName)), -1, new ModernResourceLocation(server.getIconTexture()), new BigInteger(server.getBackgroundIconColor() + "FF", 16).intValue());
        String[] strings = server.getIp().split(":");
        this.ip = strings[0];
        this.port = Integer.parseInt(strings[1]);
        this.server = server;
        this.serverName = serverName;
        this.serverType = serverType;
        if (slotNumberRenderer == null) {
            slotNumberRenderer = FontManager.createFont("nationsgui", "VisbyCF-Bold.otf");
            slotNumberRenderer.setFontSize(11.0f);
        }
    }

    public String getServerName() {
        return this.serverName;
    }

    public String getServerType() {
        return this.serverType;
    }

    public String getIp() {
        return this.ip;
    }

    public int getPort() {
        return this.port;
    }

    @Override
    public void func_73737_a(Minecraft minecraft, int mouseX, int mouseY) {
        super.func_73737_a(minecraft, mouseX, mouseY);
    }

    @Override
    protected void drawIconInSquare(Minecraft minecraft, float w, float h) {
        if (this.field_82253_i && this.field_73742_g) {
            slotNumberRenderer.drawCenteredString(Integer.toString(this.server.getSlots()), (float)this.field_73746_c + 3.0f + 4.5f, (float)this.field_73743_d + (float)this.field_73745_b / 2.0f - (float)slotNumberRenderer.getStringHeight("") / 2.0f, this.server.isInvertTextColor() ? 0 : 0xFFFFFF);
        } else {
            super.drawIconInSquare(minecraft, w, h);
        }
    }
}

