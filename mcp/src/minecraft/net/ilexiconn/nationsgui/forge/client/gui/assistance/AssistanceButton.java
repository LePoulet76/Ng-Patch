package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class AssistanceButton extends GuiButton
{
    private int uMin = 0;
    private int uMax = 157;
    private int v = 256;

    public AssistanceButton(int par1, int par2, int par3, String par4Str)
    {
        super(par1, par2, par3, par4Str);
    }

    public AssistanceButton(int par1, int par2, int par3, int par4, int par5, String par6Str)
    {
        super(par1, par2, par3, par4, par5, par6Str);
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean par1)
    {
        byte b0 = 0;

        if (!this.enabled)
        {
            b0 = 2;
        }
        else if (par1)
        {
            b0 = 1;
        }

        return b0;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            par1Minecraft.getTextureManager().bindTexture(AbstractAssistanceGUI.GUI_TEXTURE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.uMin, this.v + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, this.uMax - this.width / 2, this.v + k * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
        }
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int posX, int posY, int u, int v, int width, int height)
    {
        float f = 0.001953125F;
        float f1 = 0.001953125F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)posX, (double)(posY + height), (double)this.zLevel, (double)((float)u * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(posX + width), (double)(posY + height), (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)(v + height) * f1));
        tessellator.addVertexWithUV((double)(posX + width), (double)posY, (double)this.zLevel, (double)((float)(u + width) * f), (double)((float)v * f1));
        tessellator.addVertexWithUV((double)posX, (double)posY, (double)this.zLevel, (double)((float)u * f), (double)((float)v * f1));
        tessellator.draw();
    }

    public void setUVMap(int uMin, int uMax, int v)
    {
        this.uMin = uMin;
        this.uMax = uMax;
        this.v = v;
    }
}
