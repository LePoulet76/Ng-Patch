package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.ilexiconn.nationsgui.forge.client.gui.GuiScrollBar;
import net.ilexiconn.nationsgui.forge.client.gui.advanced.GuiScroller$Scrollbar;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

public class GuiScroller extends Gui implements GuiComponent, ComponentContainer
{
    private int posX;
    private int posY;
    private final int width;
    private final int height;
    private GuiScrollBar scrollBar = null;
    private final List<GuiScrollerElement> elementList = new ArrayList();
    private ComponentContainer container;

    public GuiScroller(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public void init(int posX, int posY)
    {
        this.posX = posX;
        this.posY = posY;
        this.scrollBar = new GuiScroller$Scrollbar((float)(posX + this.width - 5), (float)(posY + 3), this.height - 6);
        Iterator var3 = this.elementList.iterator();

        while (var3.hasNext())
        {
            GuiScrollerElement element = (GuiScrollerElement)var3.next();
            element.init(this);
        }
    }

    public void clearElement()
    {
        this.elementList.clear();
    }

    public void addElement(GuiScrollerElement element)
    {
        this.elementList.add(element);
    }

    private int getTotalHeight()
    {
        int height = 0;
        GuiScrollerElement element;

        for (Iterator var2 = this.elementList.iterator(); var2.hasNext(); height += element.getHeight())
        {
            element = (GuiScrollerElement)var2.next();
        }

        return height;
    }

    private int getRelativeMouseY(int mouseY)
    {
        return this.getRelativeMouseY(mouseY, this.getTotalHeight());
    }

    private int getRelativeMouseY(int mouseY, int totalHeight)
    {
        return (int)((float)(mouseY - this.posY) + this.scrollBar.getSliderValue() * (float)Math.max(totalHeight - this.height + 3, 0));
    }

    private int getRelativeMouseX(int mouseX)
    {
        return mouseX - this.posX;
    }

    protected void drawBackground(int mouseX, int mouseY, float partialTick)
    {
        ModernGui.drawNGBlackSquare(this.posX, this.posY, this.width, this.height);
        drawRect(this.posX + this.width - 5, this.posY + 3, this.posX + this.width - 3, this.posY + this.height - 3, -16777216);
    }

    public void init(ComponentContainer container)
    {
        this.container = container;
        Iterator var2 = this.elementList.iterator();

        while (var2.hasNext())
        {
            GuiScrollerElement element = (GuiScrollerElement)var2.next();
            element.init(container);
        }
    }

    public void draw(int mouseX, int mouseY, float partialTick)
    {
        int totalHeight = this.getTotalHeight();
        int relativeX = this.getRelativeMouseX(mouseX);
        int relativeY = this.getRelativeMouseY(mouseY, totalHeight);
        this.drawBackground(mouseX, mouseY, partialTick);
        GUIUtils.startGLScissor(this.posX, this.posY, this.width, this.height);
        int offset = (int)(-(this.scrollBar.getSliderValue() * (float)Math.max(totalHeight - this.height + 6, 0)));
        Iterator hovered = this.elementList.iterator();

        while (hovered.hasNext())
        {
            GuiScrollerElement element = (GuiScrollerElement)hovered.next();
            GL11.glPushMatrix();
            GL11.glTranslatef((float)(this.posX + 4), (float)(this.posY + offset + 3), 0.0F);
            element.draw(relativeX, relativeY, partialTick);
            offset += element.getHeight();
            GL11.glPopMatrix();
        }

        GUIUtils.endGLScissor();
        boolean hovered1 = mouseX >= this.posX && mouseX <= this.posX + this.width && mouseY >= this.posY && mouseY <= this.posY + this.height;
        this.scrollBar.draw(mouseX, mouseY, hovered1);
    }

    public void onClick(int mouseX, int mouseY, int clickType)
    {
        int relativeX = this.getRelativeMouseX(mouseX);
        int relativeY = this.getRelativeMouseY(mouseY);
        int totalHeight = 0;
        GuiScrollerElement element;

        for (Iterator var7 = this.elementList.iterator(); var7.hasNext(); totalHeight += element.getHeight())
        {
            element = (GuiScrollerElement)var7.next();
            element.onClick(relativeX, relativeY - totalHeight, clickType);
        }
    }

    public void update()
    {
        Iterator var1 = this.elementList.iterator();

        while (var1.hasNext())
        {
            GuiScrollerElement element = (GuiScrollerElement)var1.next();
            element.update();
        }
    }

    public void keyTyped(char c, int key)
    {
        Iterator var3 = this.elementList.iterator();

        while (var3.hasNext())
        {
            GuiScrollerElement element = (GuiScrollerElement)var3.next();
            element.keyTyped(c, key);
        }
    }

    public boolean isPriorityClick()
    {
        return false;
    }

    public int getWorkWidth()
    {
        return this.width - 12;
    }

    public void actionPerformed(GuiComponent guiComponent)
    {
        this.container.actionPerformed(guiComponent);
    }

    public float getValue()
    {
        return this.scrollBar.sliderValue;
    }

    public float setValue(float value)
    {
        return this.scrollBar.sliderValue = Math.max(0.0F, Math.min(value, 1.0F));
    }
}
