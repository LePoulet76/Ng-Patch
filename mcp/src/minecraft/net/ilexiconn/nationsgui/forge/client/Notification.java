/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraft.nbt.NBTTagCompound
 */
package net.ilexiconn.nationsgui.forge.client;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.util.InterpolationUtil;
import net.ilexiconn.nationsgui.forge.server.notifications.NotificationManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;

public class Notification
extends Gui {
    private NBTTagCompound compound;
    private long spawnTime = -1L;
    private boolean actionDone = false;
    private float posY = 0.0f;
    private int prevY = 0;

    public Notification(NBTTagCompound compound) {
        this.compound = compound;
    }

    public NBTTagCompound getCompound() {
        return this.compound;
    }

    public String getTitle() {
        return this.compound.func_74779_i("title");
    }

    public void setActionDone(boolean actionDone) {
        this.actionDone = actionDone;
    }

    public String getContent() {
        return this.compound.func_74779_i("content");
    }

    public NotificationManager.NColor getColor() {
        return NotificationManager.NColor.valueOf(this.compound.func_74779_i("color"));
    }

    public NotificationManager.NIcon getIcon() {
        return NotificationManager.NIcon.valueOf(this.compound.func_74779_i("icon"));
    }

    public NBTTagCompound getActions() {
        return this.compound.func_74764_b("actions") ? this.compound.func_74775_l("actions") : null;
    }

    private int getPosY(float partialTick) {
        this.prevY = (int)InterpolationUtil.interpolate(this.prevY, this.posY, partialTick);
        return this.prevY;
    }

    public void render(float partialTick, ScaledResolution resolution) {
        if (this.spawnTime == -1L) {
            this.spawnTime = System.currentTimeMillis();
        }
        int offsetXMiddle = resolution.func_78326_a() / 2;
        float offsetXNotif = (float)offsetXMiddle - 82.2f;
        this.posY = 10.0f - Math.max(0.0f, 1.0f - (float)(System.currentTimeMillis() - this.spawnTime) / 250.0f) * 55.0f;
        ModernGui.bindTextureOverlayMain();
        ModernGui.drawScaledCustomSizeModalRect(offsetXNotif, this.posY, 221 * GenericOverride.GUI_SCALE, 430 * GenericOverride.GUI_SCALE, 411 * GenericOverride.GUI_SCALE, 113 * GenericOverride.GUI_SCALE, 164, 45, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 0.3f, this.posY + 3.1f, 126 * this.getColor().ordinal() * GenericOverride.GUI_SCALE, 13 * GenericOverride.GUI_SCALE, 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 38, 37, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        ClientEventHandler.STYLE.bindTexture("overlay_icons");
        ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 0.5f, this.posY + 3.0f, 126 * (this.getIcon().ordinal() % 15) * GenericOverride.GUI_SCALE, (381 + this.getIcon().ordinal() / 15 * 123) * GenericOverride.GUI_SCALE, 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 38, 37, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
        ModernGui.drawScaledStringCustomFont(this.getTitle().toUpperCase(), offsetXNotif + 43.0f, this.posY + 9.0f, this.getColor().getColorCode(), 0.6f, "left", true, "minecraftDungeons", 22);
        ModernGui.drawSectionStringCustomFont(this.getContent(), offsetXNotif + 43.0f, this.posY + 20.0f, 15463162, 0.5f, "left", false, "georamaSemiBold", 23, 5, 240);
        if (this.getActions() != null) {
            ModernGui.bindTextureOverlayMain();
            ModernGui.drawScaledCustomSizeModalRect(offsetXNotif + 164.4f - 25.6f, this.posY + 39.2f, 645 * GenericOverride.GUI_SCALE, 444 * GenericOverride.GUI_SCALE, 64 * GenericOverride.GUI_SCALE, 15 * GenericOverride.GUI_SCALE, 25, 6, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"global.open").toUpperCase(), offsetXNotif + 164.4f - 25.6f + 10.0f, this.posY + 39.2f + 1.0f, -1314054, 0.3f, "center", false, "minecraftDungeons", 20);
        }
    }

    public boolean isExpired() {
        return this.actionDone || this.spawnTime != -1L && System.currentTimeMillis() - this.spawnTime > this.compound.func_74763_f("lifetime");
    }
}

