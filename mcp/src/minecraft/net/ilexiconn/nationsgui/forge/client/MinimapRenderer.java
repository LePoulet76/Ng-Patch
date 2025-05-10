package net.ilexiconn.nationsgui.forge.client;

import java.util.Collections;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MinimapRenderer
{
    private static final ResourceLocation field_111277_a = new ResourceLocation("textures/map/map_icons.png");
    private final DynamicTexture bufferedImage;
    private int[] intArray;
    private GameSettings gameSettings;
    private TextureManager textureManager;
    private final ResourceLocation field_111276_e;
    private int chunkWidth;
    private int chunkHeight;
    private String overredFaction = null;

    public MinimapRenderer(int chunkWidth, int chunkHeight)
    {
        this.chunkWidth = chunkWidth;
        this.chunkHeight = chunkHeight;
        this.gameSettings = Minecraft.getMinecraft().gameSettings;
        this.bufferedImage = new DynamicTexture(chunkWidth * 16, chunkHeight * 16);
        this.textureManager = Minecraft.getMinecraft().getTextureManager();
        this.field_111276_e = this.textureManager.getDynamicTextureLocation("map", this.bufferedImage);
        this.intArray = this.bufferedImage.getTextureData();

        for (int i = 0; i < this.intArray.length; ++i)
        {
            this.intArray[i] = 0;
        }
    }

    public void renderMap(int posX, int posY, int mouseX, int mouseY, boolean claims)
    {
        if (ClientData.minimapColors != null && ClientData.minimapColors.length == this.chunkWidth * 16 * this.chunkHeight * 16)
        {
            int w;

            for (w = 0; w < this.chunkWidth * 16 * this.chunkHeight * 16; ++w)
            {
                byte h = ClientData.minimapColors[w];

                if (h / 4 == 0)
                {
                    this.intArray[w] = (w + w / 128 & 1) * 8 + 16 << 24;
                }
                else
                {
                    int b1 = MapColor.mapColorArray[h / 4].colorValue;
                    int b2 = h & 3;
                    short tessellator = 220;

                    if (b2 == 2)
                    {
                        tessellator = 255;
                    }

                    if (b2 == 0)
                    {
                        tessellator = 180;
                    }

                    int f = (b1 >> 16 & 255) * tessellator / 255;
                    int mc = (b1 >> 8 & 255) * tessellator / 255;
                    int j1 = (b1 & 255) * tessellator / 255;
                    this.intArray[w] = -16777216 | f << 16 | mc << 8 | j1;
                }
            }

            w = this.chunkWidth * 16;
            int var14 = this.chunkHeight * 16;
            this.bufferedImage.updateDynamicTexture();
            Tessellator var15 = Tessellator.instance;
            float var16 = 0.0F;
            this.textureManager.bindTexture(this.field_111276_e);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            var15.startDrawingQuads();
            var15.addVertexWithUV((double)((float)posX + var16), (double)((float)(posY + var14) - var16), -0.009999999776482582D, 0.0D, 1.0D);
            var15.addVertexWithUV((double)((float)(posX + w) - var16), (double)((float)(posY + var14) - var16), -0.009999999776482582D, 1.0D, 1.0D);
            var15.addVertexWithUV((double)((float)(posX + w) - var16), (double)((float)posY + var16), -0.009999999776482582D, 1.0D, 0.0D);
            var15.addVertexWithUV((double)((float)posX + var16), (double)((float)posY + var16), -0.009999999776482582D, 0.0D, 0.0D);
            var15.draw();
            GL11.glColor3f(1.0F, 1.0F, 1.0F);

            if (claims && ClientData.mapFactions != null && ClientData.mapFactions.length == this.chunkWidth * this.chunkHeight)
            {
                this.displayFactions(posX, posY, mouseX, mouseY);
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDisable(GL11.GL_BLEND);
            this.textureManager.bindTexture(field_111277_a);
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, -0.04F);
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();

            if (this.overredFaction != null)
            {
                Minecraft var17 = Minecraft.getMinecraft();
                NationsGUIClientHooks.drawHoveringText(var17.currentScreen, Collections.singletonList(this.overredFaction), mouseX, mouseY, var17.fontRenderer, 1347420415, -267386864);
            }
        }
    }

    private void displayFactions(int posX, int posY, int mouseX, int mouseY)
    {
        String previousFaction = this.overredFaction;
        this.overredFaction = null;
        Tessellator tessellator = Tessellator.instance;

        for (int z = 0; z < this.chunkHeight; ++z)
        {
            for (int x = 0; x < this.chunkWidth; ++x)
            {
                int pX = posX + x * 16;
                int pY = posY + z * 16;
                String faction = this.getFactionAt(x, z);

                if (!faction.equals(""))
                {
                    tessellator.startDrawingQuads();

                    if (previousFaction != null && previousFaction.equals(faction))
                    {
                        tessellator.setColorRGBA_F(0.5F, 0.5F, 0.5F, 0.5F);
                    }
                    else
                    {
                        tessellator.setColorRGBA_F(0.25F, 0.25F, 0.25F, 0.5F);
                    }

                    tessellator.addVertex((double)pX, (double)(pY + 16), 0.0D);
                    tessellator.addVertex((double)(pX + 16), (double)(pY + 16), 0.0D);
                    tessellator.addVertex((double)(pX + 16), (double)pY, 0.0D);
                    tessellator.addVertex((double)pX, (double)pY, 0.0D);
                    tessellator.draw();
                }

                GL11.glLineWidth(1.0F);

                if (!this.getFactionAt(x, z - 1).equals(faction))
                {
                    this.drawLine(pX, pY, pX + 16, pY);
                }

                if (!this.getFactionAt(x, z + 1).equals(faction))
                {
                    this.drawLine(pX, pY + 16, pX + 16, pY + 16);
                }

                if (!this.getFactionAt(x - 1, z).equals(faction))
                {
                    this.drawLine(pX, pY, pX, pY + 16);
                }

                if (!this.getFactionAt(x + 1, z).equals(faction))
                {
                    this.drawLine(pX + 16, pY, pX + 16, pY + 16);
                }

                if (!faction.equals("") && mouseX >= pX && mouseX <= pX + 16 && mouseY >= pY && mouseY <= pY + 16)
                {
                    this.overredFaction = faction;
                }
            }
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(1);
        tessellator.setColorOpaque_F(0.0F, 0.0F, 0.0F);
        tessellator.addVertex((double)x1, (double)y1, 0.0D);
        tessellator.addVertex((double)x2, (double)y2, 0.0D);
        tessellator.draw();
    }

    private String getFactionAt(int x, int z)
    {
        return x >= 0 && x < this.chunkWidth && z >= 0 && z < this.chunkHeight ? ClientData.mapFactions[z * this.chunkWidth + x] : "";
    }
}
