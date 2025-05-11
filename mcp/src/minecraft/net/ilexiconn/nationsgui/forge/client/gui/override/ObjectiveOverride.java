/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.util.ArrayList;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.data.Objective;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.GenericOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class ObjectiveOverride
extends Gui
implements ElementOverride {
    private boolean active;

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
        if (!ClientData.versusOverlayData.isEmpty() && !((ArrayList)ClientData.versusOverlayData.get("playersInMatch")).contains(Minecraft.func_71410_x().field_71439_g.field_71092_bJ)) {
            return;
        }
        if (System.currentTimeMillis() - ClientEventHandler.lastPlayerDispayTAB > 50L && ClientData.currentAssault.isEmpty() && ClientData.currentWarzone.isEmpty() && ClientProxy.serverType.equals("ng") && ClientProxy.clientConfig.displayObjectives && !Minecraft.func_71410_x().field_71474_y.field_74330_P && this.active) {
            int offsetY = 30;
            if (!ClientData.objectives.isEmpty()) {
                for (Objective objective : ClientData.objectives) {
                    ModernGui.bindTextureOverlayMain();
                    ModernGui.drawScaledCustomSizeModalRect(4.0f, offsetY, 326 * GenericOverride.GUI_SCALE, 580 * GenericOverride.GUI_SCALE, 224 * GenericOverride.GUI_SCALE, 58 * GenericOverride.GUI_SCALE, 112, 29, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                    ModernGui.drawScaledStringCustomFont(objective.getTitle().length() < 21 ? objective.getTitle().toUpperCase() : objective.getTitle().toUpperCase().substring(0, 20) + "..", 35.0f, offsetY + 6, 0xFFFFFF, 0.5f, "left", false, "minecraftDungeons", 21);
                    ModernGui.drawScaledStringCustomFont(objective.getRewardName().length() > 22 ? objective.getRewardName().substring(0, 22) + ".." : objective.getRewardName(), 35.0f, offsetY + 15, 0x6E76EE, 0.5f, "left", false, "georamaSemiBold", 30);
                    ClientEventHandler.STYLE.bindTexture("overlay_icons");
                    ModernGui.drawScaledCustomSizeModalRect(5.7f, (float)offsetY + 3.5f, (objective.getRewardName().isEmpty() ? 0 : 1260) * GenericOverride.GUI_SCALE, 504 * GenericOverride.GUI_SCALE, 126 * GenericOverride.GUI_SCALE, 123 * GenericOverride.GUI_SCALE, 23, 22, 1920 * GenericOverride.GUI_SCALE, 1033 * GenericOverride.GUI_SCALE, true);
                    offsetY += 34;
                }
            }
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }
}

