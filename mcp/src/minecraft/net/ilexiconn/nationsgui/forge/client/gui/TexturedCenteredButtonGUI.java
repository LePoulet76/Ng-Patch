package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class TexturedCenteredButtonGUI extends GuiButton
{
    private final String texture;
    private final int u;
    private final int v;

    public TexturedCenteredButtonGUI(int id, int x, int y, int width, int height, String texture, int u, int v, String text)
    {
        super(id, x, y, width, height, text);
        this.texture = texture;
        this.u = u;
        this.v = v;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.drawButton)
        {
            this.field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int hoverState = this.getHoverState(this.field_82253_i);
            ClientEventHandler.STYLE.bindTexture(this.texture);
            this.drawContinuousTexturedBox(this.xPosition, this.yPosition, this.u, this.v + hoverState * this.height, this.width, this.height, this.width, this.height, 2, 3, 2, 2, this.zLevel);
            this.drawString(mc.fontRenderer, this.displayString, this.xPosition + this.width / 2 - mc.fontRenderer.getStringWidth(this.displayString) / 2, this.yPosition + (this.height - 8) / 2, 14737632);
        }
    }

    public void drawContinuousTexturedBox(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight, int topBorder, int bottomBorder, int leftBorder, int rightBorder, float zLevel)
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
        this.drawTexturedModalRect(x, y, u, v, leftBorder, topBorder);
        this.drawTexturedModalRect(x + leftBorder + canvasWidth, y, u + leftBorder + fillerWidth, v, rightBorder, topBorder);
        this.drawTexturedModalRect(x, y + topBorder + canvasHeight, u, v + topBorder + fillerHeight, leftBorder, bottomBorder);
        this.drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + canvasHeight, u + leftBorder + fillerWidth, v + topBorder + fillerHeight, rightBorder, bottomBorder);
        int j;

        for (j = 0; j < xPasses + (remainderWidth > 0 ? 1 : 0); ++j)
        {
            this.drawTexturedModalRect(x + leftBorder + j * fillerWidth, y, u + leftBorder, v, j == xPasses ? remainderWidth : fillerWidth, topBorder);
            this.drawTexturedModalRect(x + leftBorder + j * fillerWidth, y + topBorder + canvasHeight, u + leftBorder, v + topBorder + fillerHeight, j == xPasses ? remainderWidth : fillerWidth, bottomBorder);

            for (int j1 = 0; j1 < yPasses + (remainderHeight > 0 ? 1 : 0); ++j1)
            {
                this.drawTexturedModalRect(x + leftBorder + j * fillerWidth, y + topBorder + j1 * fillerHeight, u + leftBorder, v + topBorder, j == xPasses ? remainderWidth : fillerWidth, j1 == yPasses ? remainderHeight : fillerHeight);
            }
        }

        for (j = 0; j < yPasses + (remainderHeight > 0 ? 1 : 0); ++j)
        {
            this.drawTexturedModalRect(x, y + topBorder + j * fillerHeight, u, v + topBorder, leftBorder, j == yPasses ? remainderHeight : fillerHeight);
            this.drawTexturedModalRect(x + leftBorder + canvasWidth, y + topBorder + j * fillerHeight, u + leftBorder + fillerWidth, v + topBorder, rightBorder, j == yPasses ? remainderHeight : fillerHeight);
        }
    }
}
