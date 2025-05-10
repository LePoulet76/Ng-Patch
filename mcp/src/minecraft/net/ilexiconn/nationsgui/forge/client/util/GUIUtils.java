package net.ilexiconn.nationsgui.forge.client.util;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIUtils
{
    public static void drawContinuousTexturedBox(ResourceLocation res, float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(res);
        drawContinuousTexturedBox(x, y, u, v, width, height, textureWidth, textureHeight, topBorder, bottomBorder, leftBorder, rightBorder, zLevel);
    }

    public static void drawGradientRect(int par1, int par2, int par3, int par4, int par5, int par6)
    {
        float f = (float)(par5 >> 24 & 255) / 255.0F;
        float f1 = (float)(par5 >> 16 & 255) / 255.0F;
        float f2 = (float)(par5 >> 8 & 255) / 255.0F;
        float f3 = (float)(par5 & 255) / 255.0F;
        float f4 = (float)(par6 >> 24 & 255) / 255.0F;
        float f5 = (float)(par6 >> 16 & 255) / 255.0F;
        float f6 = (float)(par6 >> 8 & 255) / 255.0F;
        float f7 = (float)(par6 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)par3, (double)par2, 0.0D);
        tessellator.addVertex((double)par1, (double)par2, 0.0D);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)par1, (double)par4, 0.0D);
        tessellator.addVertex((double)par3, (double)par4, 0.0D);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void drawContinuousTexturedBox(float x, float y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        int fillerWidth = textureWidth - leftBorder - rightBorder;
        int fillerHeight = textureHeight - topBorder - bottomBorder;
        int canvasWidth = width - leftBorder - rightBorder;
        int canvasHeight = height - topBorder - bottomBorder;
        int xPasses = canvasWidth / fillerWidth;
        int remainderWidth = canvasWidth % fillerWidth;
        int yPasses = canvasHeight / fillerHeight;
        int remainderHeight = canvasHeight % fillerHeight;
        drawTexturedModalRect(x, y, u, v, leftBorder, topBorder, zLevel);
        drawTexturedModalRect(x + (float)leftBorder + (float)canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder, zLevel);
        drawTexturedModalRect(x, y + (float)topBorder + (float)canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder, zLevel);
        drawTexturedModalRect(x + (float)leftBorder + (float)canvasWidth, y + (float)topBorder + (float)canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder, zLevel);
        int j;

        for (j = 0; j < xPasses + (remainderWidth > 0 ? 1 : 0); ++j)
        {
            drawTexturedModalRect(x + (float)leftBorder + (float)(j * fillerWidth), y, u + leftBorder, v, j == xPasses ? remainderWidth : fillerWidth, topBorder, zLevel);
            drawTexturedModalRect(x + (float)leftBorder + (float)(j * fillerWidth), y + (float)topBorder + (float)canvasHeight, u + leftBorder, v + topBorder + fillerHeight, j == xPasses ? remainderWidth : fillerWidth, bottomBorder, zLevel);

            for (int j1 = 0; j1 < yPasses + (remainderHeight > 0 ? 1 : 0); ++j1)
            {
                drawTexturedModalRect(x + (float)leftBorder + (float)(j * fillerWidth), y + (float)topBorder + (float)(j1 * fillerHeight), u + leftBorder, v + topBorder, j == xPasses ? remainderWidth : fillerWidth, j1 == yPasses ? remainderHeight : fillerHeight, zLevel);
            }
        }

        for (j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j)
        {
            drawTexturedModalRect(x, y + (float)topBorder + (float)(j * fillerHeight), u, v + topBorder, leftBorder, j == yPasses ? remainderHeight : fillerHeight, zLevel);
            drawTexturedModalRect(x + (float)leftBorder + (float)canvasWidth, y + (float)topBorder + (float)(j * fillerHeight), u + leftBorder + fillerWidth, v + topBorder, rightBorder, j == yPasses ? remainderHeight : fillerHeight, zLevel);
        }
    }

    public static void drawTexturedModalRect(float x, float y, int u, int v, int width, int height, float zLevel)
    {
        float uScale = 0.00390625F;
        float vScale = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + (float)height), (double)zLevel, (double)((float)u * uScale), (double)((float)(v + height) * vScale));
        tessellator.addVertexWithUV((double)(x + (float)width), (double)(y + (float)height), (double)zLevel, (double)((float)(u + width) * uScale), (double)((float)(v + height) * vScale));
        tessellator.addVertexWithUV((double)(x + (float)width), (double)y, (double)zLevel, (double)((float)(u + width) * uScale), (double)((float)v * vScale));
        tessellator.addVertexWithUV((double)x, (double)y, (double)zLevel, (double)((float)u * uScale), (double)((float)v * vScale));
        tessellator.draw();
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
    {
        float f = 1.0F / tileWidth;
        float f1 = 1.0F / tileHeight;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * f), (double)((v + (float)vHeight) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1));
        tessellator.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * f), (double)(v * f1));
        tessellator.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * f), (double)(v * f1));
        tessellator.draw();
    }

    public static float interpolate(float prev, float current, float partialTicks)
    {
        return prev + partialTicks * (current - prev);
    }

    public static void startGLScissor(int x, int y, int width, int height)
    {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        double scaledWidth = (double)Minecraft.getMinecraft().displayWidth / resolution.getScaledWidth_double();
        double scaledHeight = (double)Minecraft.getMinecraft().displayHeight / resolution.getScaledHeight_double();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor((int)Math.floor((double)x * scaledWidth), (int)Math.floor((double)Minecraft.getMinecraft().displayHeight - (double)(y + height) * scaledHeight), (int)Math.floor((double)(x + width) * scaledWidth) - (int)Math.floor((double)x * scaledWidth), (int)Math.floor((double)Minecraft.getMinecraft().displayHeight - (double)y * scaledHeight) - (int)Math.floor((double)Minecraft.getMinecraft().displayHeight - (double)(y + height) * scaledHeight));
    }

    public static void endGLScissor()
    {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }
}
