package net.ilexiconn.nationsgui.forge.client.gui.advanced;

import net.ilexiconn.nationsgui.forge.client.gui.assistance.AbstractAssistanceComponent;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class CheckboxComponent extends AbstractAssistanceComponent
{
    private final int posX;
    private final int posY;
    private final String string;
    private final int textColor;
    private boolean checked;

    public CheckboxComponent(int posX, int posY, String string)
    {
        this(posX, posY, string, 16777215);
    }

    public CheckboxComponent(int posX, int posY, String string, int textColor)
    {
        this.checked = false;
        this.posX = posX;
        this.posY = posY;
        this.string = string;
        this.textColor = textColor;
    }

    public void draw(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.posX, this.posY, this.checked ? 0 : 10, 237, 10, 10);
        int var10003 = this.posX + 12;
        this.drawString(Minecraft.getMinecraft().fontRenderer, this.string, var10003, this.posY, this.textColor);
    }

    public void onClick(int mouseX, int mouseY, int clickType)
    {
        if (clickType == 0 && mouseX >= this.posX && mouseX <= this.posX + 12 + Minecraft.getMinecraft().fontRenderer.getStringWidth(this.string) && mouseY >= this.posY && mouseY <= this.posY + 10)
        {
            this.checked = !this.checked;
            this.container.actionPerformed(this);
        }
    }

    public void update() {}

    public void keyTyped(char c, int key) {}

    public boolean isChecked()
    {
        return this.checked;
    }

    public void setChecked(boolean checked)
    {
        this.checked = checked;
    }
}
