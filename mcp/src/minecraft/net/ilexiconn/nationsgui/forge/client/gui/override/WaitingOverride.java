/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.FontRenderer
 *  net.minecraft.client.gui.Gui
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.resources.I18n
 *  net.minecraftforge.client.event.RenderGameOverlayEvent$ElementType
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.gui.override;

import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;

public class WaitingOverride
extends Gui
implements ElementOverride {
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
        if (ClientData.waitingServerName != null && !ClientData.waitingServerName.isEmpty() && !ClientData.waitingServerName.equalsIgnoreCase("null")) {
            int y = 150;
            ClientEventHandler.STYLE.bindTexture("hud2");
            WaitingOverride.func_73734_a((int)0, (int)y, (int)130, (int)(y + 15), (int)-1157627904);
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"waiting.title"), 25, y + 4, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            this.func_73729_b(5, y + 2, 183, 13, 12, 11);
            WaitingOverride.func_73734_a((int)0, (int)(y += 15), (int)130, (int)(y + 15), (int)0x77000000);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(5, y + 3, 199, 13, 9, 10);
            if (ClientData.waitingServerName != null) {
                this.drawSmallString(client.field_71466_p, ClientData.waitingServerName.toUpperCase(), 19, y + 4, 0xFFFFFF);
            }
            String dd = ClientData.waitingPosition + " " + I18n.func_135053_a((String)"waiting.of") + " " + ClientData.waitingTotal;
            this.drawSmallString(client.field_71466_p, "\u00a7c" + dd, 130 - client.field_71466_p.func_78256_a(dd) - 3, y + 4, 0xFFFFFF);
            WaitingOverride.func_73734_a((int)0, (int)(y += 20), (int)130, (int)(y + 15), (int)-1157627904);
            String waitingTime = I18n.func_135053_a((String)"waiting.period.1");
            if (ClientData.waitingPosition >= 25 && ClientData.waitingPosition < 50) {
                waitingTime = I18n.func_135053_a((String)"waiting.period.2");
            } else if (ClientData.waitingPosition >= 50 && ClientData.waitingPosition < 75) {
                waitingTime = I18n.func_135053_a((String)"waiting.period.3");
            } else if (ClientData.waitingPosition >= 75) {
                waitingTime = I18n.func_135053_a((String)"waiting.period.4");
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"waiting.estimation") + " \u00a77" + waitingTime, 5, y + 4, 0xFFFFFF);
            WaitingOverride.func_73734_a((int)0, (int)(y += 15), (int)130, (int)(y + 15), (int)-1157627904);
            long diffTime = System.currentTimeMillis() - ClientData.waitingJoinTime;
            diffTime = diffTime / 1000L / 60L;
            String timeToDisplay = diffTime + "min";
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"waiting.pastTime") + " \u00a77" + timeToDisplay, 5, y + 4, 0xFFFFFF);
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }
}

