package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.advanced.ComponentContainer;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;

public abstract class AbstractAssistanceComponent extends Gui implements GuiComponent
{
    protected ComponentContainer container;

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawTexturedModalRect(int posX, int posY, int u, int v, int width, int height)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractAssistanceGUI.GUI_TEXTURE);
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

    public boolean isPriorityClick()
    {
        return false;
    }

    public void init(ComponentContainer container)
    {
        this.container = container;
    }
}
