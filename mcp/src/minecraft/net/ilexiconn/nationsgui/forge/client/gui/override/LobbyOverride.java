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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class LobbyOverride
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
        if (ClientProxy.serverType.equals("lobby")) {
            Double doubleY = (double)resolution.func_78328_b() * 0.4;
            int y = doubleY.intValue();
            int x = resolution.func_78326_a() - 120;
            LobbyOverride.func_73734_a((int)x, (int)y, (int)(x + 120), (int)(y + 16), (int)-1157627904);
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.title"), x + 60 - client.field_71466_p.func_78256_a(I18n.func_135053_a((String)"lobby.title")) / 2, y + 4, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            LobbyOverride.func_73734_a((int)x, (int)(y + 16), (int)(x + 120), (int)(y + 16 + 97), (int)0x77000000);
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.step_1"), x + 15, y + 23, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            if (client.field_71439_g.field_70165_t > 60.0) {
                this.func_73729_b(x + 3, y + 22, 182, 0, 10, 10);
            } else {
                this.func_73729_b(x + 3, y + 22, 192, 0, 10, 10);
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.step_2"), x + 15, y + 38, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            if (client.field_71439_g.field_70165_t > 80.0) {
                this.func_73729_b(x + 3, y + 37, 182, 0, 10, 10);
            } else {
                this.func_73729_b(x + 3, y + 37, 192, 0, 10, 10);
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.step_3"), x + 15, y + 53, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            if (client.field_71439_g.field_70165_t > 100.0) {
                this.func_73729_b(x + 3, y + 52, 182, 0, 10, 10);
            } else {
                this.func_73729_b(x + 3, y + 52, 192, 0, 10, 10);
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.step_4"), x + 15, y + 68, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            if (client.field_71439_g.field_70165_t > 100.0) {
                this.func_73729_b(x + 3, y + 67, 182, 0, 10, 10);
            } else {
                this.func_73729_b(x + 3, y + 67, 192, 0, 10, 10);
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.step_5"), x + 15, y + 83, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            if (client.field_71439_g.field_70165_t > 122.0) {
                this.func_73729_b(x + 3, y + 82, 182, 0, 10, 10);
            } else {
                this.func_73729_b(x + 3, y + 82, 192, 0, 10, 10);
            }
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"lobby.step_6"), x + 15, y + 98, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            if (client.field_71439_g.field_70165_t > 130.0) {
                this.func_73729_b(x + 3, y + 97, 182, 0, 10, 10);
            } else {
                this.func_73729_b(x + 3, y + 97, 192, 0, 10, 10);
            }
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.95f, (float)0.95f, (float)0.95f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
    }

    private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, (float)0.0f);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        this.func_73731_b(fontRenderer, string, 0, 0, 0xFFFFFF);
        GL11.glPopMatrix();
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

    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
}

