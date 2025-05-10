package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.SuffixedNumberField;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class NumberSelector extends Gui
{
    private SuffixedNumberField textField;
    private Minecraft mc;
    private int posX;
    private int posY;
    private int width;
    private int max;

    public NumberSelector(Minecraft mc, int x, int y, int width)
    {
        this(mc, x, y, width, "");
    }

    public NumberSelector(Minecraft mc, int x, int y, int width, String suffix)
    {
        this(mc, x, y, width, 18, suffix);
    }

    public NumberSelector(Minecraft mc, int x, int y, int width, int height, String suffix)
    {
        this.max = -1;
        this.mc = mc;
        this.posX = x;
        this.posY = y;
        this.width = width;
        this.textField = new SuffixedNumberField(mc.fontRenderer, x, y + 1, width - 24, height, suffix);
    }

    public void setText(String text)
    {
        this.textField.setText(text);
    }

    public String getText()
    {
        return this.textField.getText();
    }

    public void draw(int mouseX, int mouseY)
    {
        this.textField.drawTextBox();
        ClientEventHandler.STYLE.bindTexture("auction_sell");
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.drawButton(this.posX + this.width - 23, this.posY, false, this.upActivated(), mouseX, mouseY);
        this.drawButton(this.posX + this.width - 23, this.posY + 10, true, this.downActivated(), mouseX, mouseY);
    }

    public void update()
    {
        this.textField.updateCursorCounter();
    }

    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        this.clickButton(this.posX + this.width - 23, this.posY, mouseX, mouseY, 1);
        this.clickButton(this.posX + this.width - 23, this.posY + 10, mouseX, mouseY, -1);
        this.textField.mouseClicked(mouseX, mouseY, button);
    }

    public void keyTyped(char character, int key)
    {
        this.textField.textboxKeyTyped(character, key);
    }

    private boolean upActivated()
    {
        try
        {
            return this.max != -1 ? Integer.parseInt(this.textField.getText()) < this.max : true;
        }
        catch (NumberFormatException var2)
        {
            return false;
        }
    }

    private boolean downActivated()
    {
        try
        {
            return Integer.parseInt(this.textField.getText()) > 0;
        }
        catch (NumberFormatException var2)
        {
            return false;
        }
    }

    private void clickButton(int x, int y, int mouseX, int mouseY, int inc)
    {
        try
        {
            if (mouseX >= x && mouseX <= x + 22 && mouseY >= y && mouseY <= y + 10)
            {
                if (Keyboard.isKeyDown(42))
                {
                    if (inc > 0 && this.max != -1)
                    {
                        this.textField.setText(Integer.toString(this.max));
                    }
                    else if (inc < 0)
                    {
                        this.textField.setText("0");
                    }

                    return;
                }

                int e = Integer.parseInt(this.textField.getText()) + inc;

                if (e < 0)
                {
                    e = 0;
                }
                else if (this.max != -1 && e > this.max)
                {
                    e = this.max;
                }

                this.textField.setText(e + "");
            }
        }
        catch (NumberFormatException var7)
        {
            this.textField.setText("0");
        }
    }

    private void drawButton(int x, int y, boolean flipped, boolean activated, int mouseX, int mouseY)
    {
        int pos = activated ? 128 : 118;

        if (activated && mouseX >= x && mouseX <= x + 22 && mouseY >= y && mouseY <= y + 10)
        {
            pos = 138;
        }

        ModernGui.drawModalRectWithCustomSizedTexture((float)x, (float)y, flipped ? 24 : 3, pos, 21, 10, 275.0F, 256.0F, false);
    }

    public void setMax(int max)
    {
        this.max = max;
        this.textField.setMax(max);
    }

    public int getMax()
    {
        return this.max;
    }

    public int getNumber()
    {
        try
        {
            return Integer.parseInt(this.textField.getText());
        }
        catch (NumberFormatException var2)
        {
            return 0;
        }
    }
}
