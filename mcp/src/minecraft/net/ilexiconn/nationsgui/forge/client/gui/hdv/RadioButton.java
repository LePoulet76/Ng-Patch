package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RadioButton extends Gui
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/hdv_sell.png");
    private static final int WIDTH = 10;
    private static final int HEIGHT = 8;
    private int posX;
    private int posY;
    private boolean state = false;

    public RadioButton(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
    }

    public void draw(int mouseX, int mouseY)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(BACKGROUND);

        if (this.isHovered(mouseX, mouseY))
        {
            GL11.glColor3f(0.8F, 0.8F, 0.8F);
        }
        else
        {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
        }

        ModernGui.drawModalRectWithCustomSizedTexture((float)this.posX, (float)this.posY, 46, this.state ? 127 : 118, 10, 8, 275.0F, 256.0F, false);
    }

    public void mousePressed(int mouseX, int mouseY, int button)
    {
        if (this.isHovered(mouseX, mouseY) && button == 0)
        {
            this.state = !this.state;
        }
    }

    public boolean getState()
    {
        return this.state;
    }

    private boolean isHovered(int mouseX, int mouseY)
    {
        return mouseX >= this.posX && mouseX <= this.posX + 10 && mouseY >= this.posY && mouseY <= this.posY + 8;
    }
}
