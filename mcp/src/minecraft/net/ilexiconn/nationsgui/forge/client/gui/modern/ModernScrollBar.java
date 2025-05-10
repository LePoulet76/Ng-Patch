package net.ilexiconn.nationsgui.forge.client.gui.modern;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModernScrollBar extends ModernGui
{
    private float x;
    private float y;
    private int width;
    private int height;
    private int steps;
    private boolean dragging;
    private float sliderValue = 0.0F;
    private boolean selected;
    private boolean isPressed = false;

    public ModernScrollBar(float x, float y, int width, int height, int steps)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.steps = steps;
    }

    public void draw(int mouseX, int mouseY)
    {
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.25F);
        ModernGui.drawExtendedCircle(this.x, this.y, (float)this.width, (float)this.height);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.5F);
        ModernGui.drawExtendedCircle(this.x, this.y + this.sliderValue * (float)(this.height - this.height / this.steps), (float)this.width, (float)(this.height / this.steps));

        if (!Mouse.isButtonDown(0))
        {
            if (this.isPressed)
            {
                this.mouseReleased(mouseX, mouseY);
                this.isPressed = false;
            }

            while (!Minecraft.getMinecraft().gameSettings.touchscreen && Mouse.next())
            {
                int l2 = Mouse.getEventDWheel();

                if (l2 != 0)
                {
                    if (l2 > 0)
                    {
                        l2 = -1;
                    }
                    else if (l2 < 0)
                    {
                        l2 = 1;
                    }

                    this.sliderValue += (float)l2 * 0.05F;

                    if (this.sliderValue < 0.0F)
                    {
                        this.sliderValue = 0.0F;
                    }

                    if (this.sliderValue > 1.0F)
                    {
                        this.sliderValue = 1.0F;
                    }
                }
            }
        }
        else if (!this.isPressed)
        {
            this.isPressed = true;
            this.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY);
        }

        this.mouseDragged(Minecraft.getMinecraft(), mouseX, mouseY);
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.dragging)
        {
            this.sliderValue = ((float)mouseY - this.y) / (float)this.height;

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if ((float)mouseX >= this.x - 2.0F && (float)mouseX <= this.x + (float)this.width + 2.0F && (float)mouseY >= this.y && (float)mouseY <= this.y + (float)this.height)
        {
            this.selected = true;
            this.sliderValue = ((float)mouseY - this.y) / (float)this.height;

            if (this.sliderValue < 0.0F)
            {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F)
            {
                this.sliderValue = 1.0F;
            }

            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }

    public void mouseReleased(int mouseX, int mouseY)
    {
        if (this.selected)
        {
            this.dragging = this.selected = false;
        }
    }

    public float getSliderValue()
    {
        return this.sliderValue;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }
}
