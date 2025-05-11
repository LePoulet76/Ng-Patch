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

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.override.BuildOverride;
import net.ilexiconn.nationsgui.forge.client.gui.override.ElementOverride;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class FootOverride
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
        if (!ClientData.currentFoot.isEmpty()) {
            Double doubleY = (double)resolution.func_78328_b() * 0.4;
            int y = doubleY.intValue();
            int x = resolution.func_78326_a() - 140;
            FootOverride.func_73734_a((int)x, (int)y, (int)(x + 140), (int)(y + 16), (int)-1157627904);
            this.drawSmallString(client.field_71466_p, "Football", x + 75 - client.field_71466_p.func_78256_a("Football") / 2, y + 4, 0xFFFFFF);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.func_73729_b(x + 75 - client.field_71466_p.func_78256_a("Football") / 2 - 20, y + 2, 183, 35, 12, 12);
            this.func_73729_b(x + 75 + client.field_71466_p.func_78256_a("Football") / 2 + 4, y + 2, 183, 35, 12, 12);
            FootOverride.func_73734_a((int)x, (int)(y + 16), (int)(x + 140), (int)(y + 16 + 45), (int)0x77000000);
            GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            this.drawSmallString(client.field_71466_p, ClientData.currentFoot.get("team1"), x + 3, y + 22, 0xFFFFFF);
            this.drawSmallString(client.field_71466_p, "\u00a7cVS", x + 75 - client.field_71466_p.func_78256_a("VS") / 2, y + 35, 0xFFFFFF);
            this.drawSmallString(client.field_71466_p, ClientData.currentFoot.get("team2"), x + 3, y + 47, 0xFFFFFF);
            FootOverride.func_73734_a((int)x, (int)(y + 61), (int)(x + 140), (int)(y + 61 + 14), (int)-1157627904);
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"assault.time"), x + 3, y + 65, 0xFFFFFF);
            this.drawSmallString(client.field_71466_p, "\u00a77" + BuildOverride.chronoTimeToStr(Long.parseLong(ClientData.currentFoot.get("remainingTime")) * 1000L, false), x + 115 - 3, y + 65, 0xFFFFFF);
            int scoreY = 79;
            FootOverride.func_73734_a((int)x, (int)(y + scoreY), (int)(x + 140), (int)(y + scoreY + 16), (int)-1157627904);
            this.drawSmallString(client.field_71466_p, I18n.func_135053_a((String)"assault.score.title"), x + 75 - client.field_71466_p.func_78256_a(I18n.func_135053_a((String)"assault.score.title")) / 2, y + scoreY + 4, 0xFFFFFF);
            FootOverride.func_73734_a((int)x, (int)(y + scoreY + 16), (int)(x + 140), (int)(y + scoreY + 16 + 35), (int)0x77000000);
            this.drawSmallString(client.field_71466_p, ClientData.currentFoot.get("team1"), x + 3, y + scoreY + 22, 0xFFFFFF);
            this.drawSmallString(client.field_71466_p, "\u00a77" + ClientData.currentFoot.get("score1"), x + 140 - client.field_71466_p.func_78256_a(ClientData.currentFoot.get("score1")) - 3, y + scoreY + 22, 0xFFFFFF);
            this.drawSmallString(client.field_71466_p, ClientData.currentFoot.get("team2"), x + 3, y + scoreY + 37, 0xFFFFFF);
            this.drawSmallString(client.field_71466_p, "\u00a77" + ClientData.currentFoot.get("score2"), x + 140 - client.field_71466_p.func_78256_a(ClientData.currentFoot.get("score2")) - 3, y + scoreY + 37, 0xFFFFFF);
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

