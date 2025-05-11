/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.MapColor
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.client.renderer.texture.TextureManager
 *  net.minecraft.client.settings.GameSettings
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client;

import java.util.Collections;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MinimapRenderer {
    private static final ResourceLocation field_111277_a = new ResourceLocation("textures/map/map_icons.png");
    private final DynamicTexture bufferedImage;
    private int[] intArray;
    private GameSettings gameSettings;
    private TextureManager textureManager;
    private final ResourceLocation field_111276_e;
    private int chunkWidth;
    private int chunkHeight;
    private String overredFaction = null;

    public MinimapRenderer(int chunkWidth, int chunkHeight) {
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
        this.gameSettings = Minecraft.func_71410_x().field_71474_y;
        this.bufferedImage = new DynamicTexture(chunkWidth * 16, chunkHeight * 16);
        this.textureManager = Minecraft.func_71410_x().func_110434_K();
        this.field_111276_e = this.textureManager.func_110578_a("map", this.bufferedImage);
        this.intArray = this.bufferedImage.func_110565_c();
        for (int i = 0; i < this.intArray.length; ++i) {
            this.intArray[i] = 0;
        }
    }

    public void renderMap(int posX, int posY, int mouseX, int mouseY, boolean claims) {
        if (ClientData.minimapColors != null && ClientData.minimapColors.length == this.chunkWidth * 16 * this.chunkHeight * 16) {
            for (int i = 0; i < this.chunkWidth * 16 * this.chunkHeight * 16; ++i) {
                byte b0 = ClientData.minimapColors[i];
                if (b0 / 4 == 0) {
                    this.intArray[i] = (i + i / 128 & 1) * 8 + 16 << 24;
                    continue;
                }
                int j = MapColor.field_76281_a[b0 / 4].field_76291_p;
                int k = b0 & 3;
                int short1 = 220;
                if (k == 2) {
                    short1 = 255;
                }
                if (k == 0) {
                    short1 = 180;
                }
                int l = (j >> 16 & 0xFF) * short1 / 255;
                int i1 = (j >> 8 & 0xFF) * short1 / 255;
                int j1 = (j & 0xFF) * short1 / 255;
                this.intArray[i] = 0xFF000000 | l << 16 | i1 << 8 | j1;
            }
            int w = this.chunkWidth * 16;
            int h = this.chunkHeight * 16;
            this.bufferedImage.func_110564_a();
            int b1 = posX;
            int b2 = posY;
            Tessellator tessellator = Tessellator.field_78398_a;
            float f = 0.0f;
            this.textureManager.func_110577_a(this.field_111276_e);
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)1, (int)771);
            GL11.glDisable((int)3008);
            GL11.glDisable((int)2896);
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            tessellator.func_78382_b();
            tessellator.func_78374_a((double)((float)b1 + f), (double)((float)(b2 + h) - f), (double)-0.01f, 0.0, 1.0);
            tessellator.func_78374_a((double)((float)(b1 + w) - f), (double)((float)(b2 + h) - f), (double)-0.01f, 1.0, 1.0);
            tessellator.func_78374_a((double)((float)(b1 + w) - f), (double)((float)b2 + f), (double)-0.01f, 1.0, 0.0);
            tessellator.func_78374_a((double)((float)b1 + f), (double)((float)b2 + f), (double)-0.01f, 0.0, 0.0);
            tessellator.func_78381_a();
            GL11.glColor3f((float)1.0f, (float)1.0f, (float)1.0f);
            if (claims && ClientData.mapFactions != null && ClientData.mapFactions.length == this.chunkWidth * this.chunkHeight) {
                this.displayFactions(posX, posY, mouseX, mouseY);
            }
            GL11.glEnable((int)3008);
            GL11.glDisable((int)3042);
            this.textureManager.func_110577_a(field_111277_a);
            GL11.glPushMatrix();
            GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-0.04f);
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glPopMatrix();
            if (this.overredFaction != null) {
                Minecraft mc = Minecraft.func_71410_x();
                NationsGUIClientHooks.drawHoveringText(mc.field_71462_r, Collections.singletonList(this.overredFaction), mouseX, mouseY, mc.field_71466_p, 0x505000FF, -267386864);
            }
        }
    }

    private void displayFactions(int posX, int posY, int mouseX, int mouseY) {
        String previousFaction = this.overredFaction;
        this.overredFaction = null;
        Tessellator tessellator = Tessellator.field_78398_a;
        for (int z = 0; z < this.chunkHeight; ++z) {
            for (int x = 0; x < this.chunkWidth; ++x) {
                int pX = posX + x * 16;
                int pY = posY + z * 16;
                String faction = this.getFactionAt(x, z);
                if (!faction.equals("")) {
                    tessellator.func_78382_b();
                    if (previousFaction == null || !previousFaction.equals(faction)) {
                        tessellator.func_78369_a(0.25f, 0.25f, 0.25f, 0.5f);
                    } else {
                        tessellator.func_78369_a(0.5f, 0.5f, 0.5f, 0.5f);
                    }
                    tessellator.func_78377_a((double)pX, (double)(pY + 16), 0.0);
                    tessellator.func_78377_a((double)(pX + 16), (double)(pY + 16), 0.0);
                    tessellator.func_78377_a((double)(pX + 16), (double)pY, 0.0);
                    tessellator.func_78377_a((double)pX, (double)pY, 0.0);
                    tessellator.func_78381_a();
                }
                GL11.glLineWidth((float)1.0f);
                if (!this.getFactionAt(x, z - 1).equals(faction)) {
                    this.drawLine(pX, pY, pX + 16, pY);
                }
                if (!this.getFactionAt(x, z + 1).equals(faction)) {
                    this.drawLine(pX, pY + 16, pX + 16, pY + 16);
                }
                if (!this.getFactionAt(x - 1, z).equals(faction)) {
                    this.drawLine(pX, pY, pX, pY + 16);
                }
                if (!this.getFactionAt(x + 1, z).equals(faction)) {
                    this.drawLine(pX + 16, pY, pX + 16, pY + 16);
                }
                if (faction.equals("") || mouseX < pX || mouseX > pX + 16 || mouseY < pY || mouseY > pY + 16) continue;
                this.overredFaction = faction;
            }
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2) {
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(1);
        tessellator.func_78386_a(0.0f, 0.0f, 0.0f);
        tessellator.func_78377_a((double)x1, (double)y1, 0.0);
        tessellator.func_78377_a((double)x2, (double)y2, 0.0);
        tessellator.func_78381_a();
    }

    private String getFactionAt(int x, int z) {
        if (x >= 0 && x < this.chunkWidth && z >= 0 && z < this.chunkHeight) {
            return ClientData.mapFactions[z * this.chunkWidth + x];
        }
        return "";
    }
}

