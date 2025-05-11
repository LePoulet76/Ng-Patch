/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.NotificationOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class NoelMegaGiftOverride
extends Gui
implements ElementOverride {
    private HashMap<String, DynamicTexture> flagTextures = new HashMap();

    @Override
    public RenderGameOverlayEvent.ElementType getType() {
        return RenderGameOverlayEvent.ElementType.HOTBAR;
    }

    @Override
    public RenderGameOverlayEvent.ElementType[] getSubTypes() {
        return new RenderGameOverlayEvent.ElementType[0];
    }

    @Override
    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks) {
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB < 50L) {
            return;
        }
        if (GenericOverride.displaysCountryEntry()) {
            return;
        }
        if (NotificationOverride.displaysNotification()) {
            return;
        }
        if (ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty() && System.currentTimeMillis() - ClientData.noelMegaGiftTimeSpawn < 70000L) {
            int remainingTimeSec = Math.max(0, 60 - (int)((System.currentTimeMillis() - ClientData.noelMegaGiftTimeSpawn) / 1000L));
            ClientEventHandler.STYLE.bindTexture("overlay_hud");
            ModernGui.drawScaledCustomSizeModalRect((float)(resolution.func_78326_a() / 2) - 148.0f, 0.0f, 0.0f, 739.0f, 740, 153, 296, 61, 1920.0f, 1033.0f, true);
            ModernGui.drawScaledStringCustomFont(I18n.func_135053_a((String)"gui.overlay.mega_gift.title"), resolution.func_78326_a() / 2, 15.0f, 14984509, 0.5f, "center", true, "minecraftDungeons", 18);
            ModernGui.drawScaledStringCustomFont(remainingTimeSec > 0 ? remainingTimeSec + "" : I18n.func_135053_a((String)"gui.overlay.mega_gift.opening"), resolution.func_78326_a() / 2, 22.0f, 0xFFFFFF, 0.5f, "center", true, "minecraftDungeons", 25);
        }
    }

    public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow) {
        GL11.glPushMatrix();
        GL11.glScalef((float)scale, (float)scale, (float)scale);
        float newX = x;
        if (centered) {
            newX = (float)x - (float)fontRenderer.func_78256_a(text) * scale / 2.0f;
        }
        if (shadow) {
            fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 0xFCFCFC) >> 2 | color & 0xFF000000, false);
        }
        fontRenderer.func_85187_a(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

