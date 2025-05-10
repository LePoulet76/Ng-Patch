package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public abstract class AdvancedGui extends GuiScreen implements ComponentContainer
{
    private final List<GuiComponent> components = new ArrayList();

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        super.initGui();
        this.components.clear();
    }

    protected void addComponent(GuiComponent guiComponent)
    {
        if (!this.components.contains(guiComponent))
        {
            this.components.add(guiComponent);
        }

        guiComponent.init(this);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        Iterator var4 = this.components.iterator();

        while (var4.hasNext())
        {
            GuiComponent component = (GuiComponent)var4.next();
            component.draw(par1, par2, par3);
        }

        super.drawScreen(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        Iterator var3 = this.components.iterator();

        while (var3.hasNext())
        {
            GuiComponent component = (GuiComponent)var3.next();
            component.keyTyped(par1, par2);
        }

        super.keyTyped(par1, par2);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        boolean clickSkipped = false;
        Iterator var5 = this.components.iterator();
        GuiComponent component;

        while (var5.hasNext())
        {
            component = (GuiComponent)var5.next();

            if (component.isPriorityClick())
            {
                component.onClick(par1, par2, par3);
                clickSkipped = true;
            }
        }

        if (!clickSkipped)
        {
            var5 = this.components.iterator();

            while (var5.hasNext())
            {
                component = (GuiComponent)var5.next();
                component.onClick(par1, par2, par3);
            }
        }

        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        Iterator var1 = this.components.iterator();

        while (var1.hasNext())
        {
            GuiComponent component = (GuiComponent)var1.next();
            component.update();
        }

        super.updateScreen();
    }
}
