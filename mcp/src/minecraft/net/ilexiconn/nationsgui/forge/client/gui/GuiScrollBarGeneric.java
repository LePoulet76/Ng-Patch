package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class GuiScrollBarGeneric extends Gui
{
    protected float x;
    protected float y;
    private final ResourceLocation resourceLocation;
    private final int cursorHeight;
    private final int cursorWidth;
    protected int width;
    protected int height;
    private boolean dragging;
    protected float sliderValue = 0.0F;
    private boolean selected;
    private boolean isPressed = false;
    private float increment = 0.05F;

    public GuiScrollBarGeneric(float x, float y, int height, ResourceLocation resourceLocation, int cursorWidth, int cursorHeight)
    {
        this.x = x;
        this.y = y;
        this.width = cursorWidth;
        this.height = height;
        this.resourceLocation = resourceLocation;
        this.cursorWidth = cursorWidth;
        this.cursorHeight = cursorHeight;
    }

    public void setScrollIncrement(float i)
    {
        this.increment = i;
    }

    protected void drawScroller()
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        ModernGui.drawModalRectWithCustomSizedTexture((float)((int)this.x), (float)((int)(this.y + (float)(this.height - this.cursorHeight) * this.sliderValue)), 0, 0, this.cursorWidth, this.cursorHeight, (float)this.cursorWidth, (float)this.cursorHeight, true);
    }

    public void draw(int mouseX, int mouseY)
    {
        this.drawScroller();

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

                    this.sliderValue += (float)l2 * this.increment;

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

    public void reset()
    {
        this.sliderValue = 0.0F;
    }

    public void setSliderValue(float sliderValue)
    {
        this.sliderValue = sliderValue;
    }
}
