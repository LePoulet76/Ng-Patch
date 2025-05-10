package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.TextAreaGUI;
import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;

public class TextAreaComponent extends AbstractAssistanceComponent
{
    private final TextAreaGUI textAreaGUI;
    private final int posX;
    private final int posY;
    private final int width;
    private final int height;

    public TextAreaComponent(int x, int y, int width, int lines)
    {
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.height = 10 * lines + 2;
        this.textAreaGUI = new TextAreaGUI(x + 2, y + 2, width - 10);
        this.textAreaGUI.setMaxStringLength((width - 4) / 6 * lines);
    }

    public void draw(int mouseX, int mouseY, float partialTicks)
    {
        ModernGui.drawNGBlackSquare(this.posX, this.posY, this.width, this.height);
        this.textAreaGUI.drawTextBox();
    }

    public String getText()
    {
        return this.textAreaGUI.getText();
    }

    public void onClick(int mouseX, int mouseY, int clickType)
    {
        this.textAreaGUI.mouseClicked(mouseX, mouseY, clickType);
    }

    public void update()
    {
        this.textAreaGUI.updateCursorCounter();
    }

    public void keyTyped(char c, int key)
    {
        this.textAreaGUI.textboxKeyTyped(c, key);
        this.container.actionPerformed(this);
    }
}
