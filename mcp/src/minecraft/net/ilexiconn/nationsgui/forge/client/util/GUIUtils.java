/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  cpw.mods.fml.relauncher.Side
 *  cpw.mods.fml.relauncher.SideOnly
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.util.ResourceLocation
 *  org.lwjgl.opengl.GL11
 */
package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(value=Side.CLIENT)
public class GUIUtils {
    public static void drawContinuousTexturedBox(ResourceLocation res, float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
        Minecraft.func_71410_x().func_110434_K().func_110577_a(res);
        GUIUtils.drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel);
    }

    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float f = (float)(par5 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(par5 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(par5 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(par5 & 0xFF) / 255.0f;
        float f4 = (float)(par6 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(par6 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(par6 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(par6 & 0xFF) / 255.0f;
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78369_a(f1, f2, f3, f);
        tessellator.func_78377_a((double)par3, (double)par2, 0.0);
        tessellator.func_78377_a((double)par1, (double)par2, 0.0);
        tessellator.func_78369_a(f5, f6, f7, f4);
        tessellator.func_78377_a((double)par1, (double)par4, 0.0);
        tessellator.func_78377_a((double)par3, (double)par4, 0.0);
        tessellator.func_78381_a();
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    public static void drawContinuousTexturedBox(float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel) {
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;
        GUIUtils.drawTexturedModalRect(x, y, u, v, leftBorder, topBorder, zLevel);
        GUIUtils.drawTexturedModalRect(x + (float)leftBorder + (float)canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        GUIUtils.drawTexturedModalRect(x, y + (float)topBorder + (float)canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        GUIUtils.drawTexturedModalRect(x + (float)leftBorder + (float)canvasWidth, y + (float)topBorder + (float)canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);
        for (int i = 0; i < xPasses + (remainderWidth > 0 ? 1 : 0); ++i) {
            GUIUtils.drawTexturedModalRect(x + (float)leftBorder + (float)(i * fillerWidth), y, u + leftBorder, v, i == xPasses ? remainderWidth : fillerWidth, topBorder, zLevel);
            GUIUtils.drawTexturedModalRect(x + (float)leftBorder + (float)(i * fillerWidth), y + (float)topBorder + (float)canvasHeight, u + leftBorder, v + topBorder + fillerHeight, i == xPasses ? remainderWidth : fillerWidth, bottomBorder, zLevel);
            for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j) {
                GUIUtils.drawTexturedModalRect(x + (float)leftBorder + (float)(i * fillerWidth), y + (float)topBorder + (float)(j * fillerHeight), u + leftBorder, v + topBorder, i == xPasses ? remainderWidth : fillerWidth, j == yPasses ? remainderHeight : fillerHeight, zLevel);
            }
        }
        for (int j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j) {
            GUIUtils.drawTexturedModalRect(x, y + (float)topBorder + (float)(j * fillerHeight), u, v + topBorder, leftBorder, j == yPasses ? remainderHeight : fillerHeight, zLevel);
            GUIUtils.drawTexturedModalRect(x + (float)leftBorder + (float)canvasWidth, y + (float)topBorder + (float)(j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, j == yPasses ? remainderHeight : fillerHeight, zLevel);
        }
    }

    public static void drawTexturedModalRect(float x, float y, int u, int v, int width, int height, float zLevel) {
        float uScale = 0.00390625f;
        float vScale = 0.00390625f;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)x, (double)(y + (float)height), (double)zLevel, (double)((float)u * uScale), (double)((float)(v + height) * vScale));
        tessellator.func_78374_a((double)(x + (float)width), (double)(y + (float)height), (double)zLevel, (double)((float)(u + width) * uScale), (double)((float)(v + height) * vScale));
        tessellator.func_78374_a((double)(x + (float)width), (double)y, (double)zLevel, (double)((float)(u + width) * uScale), (double)((float)v * vScale));
        tessellator.func_78374_a((double)x, (double)y, (double)zLevel, (double)((float)u * uScale), (double)((float)v * vScale));
        tessellator.func_78381_a();
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78382_b();
        tessellator.func_78374_a((double)x, (double)(y + height), 0.0, (double)(u * f), (double)((v + (float)vHeight) * f1));
        tessellator.func_78374_a((double)(x + width), (double)(y + height), 0.0, (double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1));
        tessellator.func_78374_a((double)(x + width), (double)y, 0.0, (double)((u + (float)uWidth) * f), (double)(v * f1));
        tessellator.func_78374_a((double)x, (double)y, 0.0, (double)(u * f), (double)(v * f1));
        tessellator.func_78381_a();
    }

    public static float interpolate(float prev, float current, float partialTicks) {
        return prev + partialTicks * (current - prev);
    }

    public static void startGLScissor(int x, int y, int width, int height) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.func_71410_x().field_71474_y, Minecraft.func_71410_x().field_71443_c, Minecraft.func_71410_x().field_71440_d);
        double scaledWidth = (double)Minecraft.func_71410_x().field_71443_c / resolution.func_78327_c();
        double scaledHeight = (double)Minecraft.func_71410_x().field_71440_d / resolution.func_78324_d();
        GL11.glEnable((int)3089);
        GL11.glScissor((int)((int)Math.floor((double)x * scaledWidth)), (int)((int)Math.floor((double)Minecraft.func_71410_x().field_71440_d - (double)(y + height) * scaledHeight)), (int)((int)Math.floor((double)(x + width) * scaledWidth) - (int)Math.floor((double)x * scaledWidth)), (int)((int)Math.floor((double)Minecraft.func_71410_x().field_71440_d - (double)y * scaledHeight) - (int)Math.floor((double)Minecraft.func_71410_x().field_71440_d - (double)(y + height) * scaledHeight)));
    }

    public static void endGLScissor() {
        GL11.glDisable((int)3089);
    }
}

