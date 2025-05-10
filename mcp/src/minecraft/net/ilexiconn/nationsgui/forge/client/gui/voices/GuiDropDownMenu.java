package net.ilexiconn.nationsgui.forge.client.gui.voices;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiDropDownMenu extends GuiButton
{
    String[] array;
    boolean[] mouseOn;
    private int prevHeight;
    private int amountOfItems = 1;
    public boolean dropDownMenu = false;
    public int selectedInteger;

    public GuiDropDownMenu(int par1, int par2, int par3, String par4Str, String[] array)
    {
        super(par1, par2, par3, par4Str);
        this.prevHeight = this.height;
        this.array = array;
        this.amountOfItems = array.length;
        this.mouseOn = new boolean[this.amountOfItems];
    }

    public GuiDropDownMenu(int par1, int par2, int par3, int par4, int par5, String par6Str, String[] array)
    {
        super(par1, par2, par3, par4, par5, par6Str);
        this.prevHeight = this.height;
        this.array = array;
        this.amountOfItems = array.length;
        this.mouseOn = new boolean[this.amountOfItems];
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            if (this.dropDownMenu && this.array.length != 0)
            {
                this.height = this.prevHeight * (this.amountOfItems + 1);
            }
            else
            {
                this.height = this.prevHeight;
            }

            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -6250336);
            drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, -16777216);
            drawRect(this.xPosition, this.yPosition + this.prevHeight, this.xPosition + this.width, this.yPosition + this.prevHeight, -6250336);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean var10;

            if (this.dropDownMenu && this.array.length != 0)
            {
                var10 = true;
            }
            else
            {
                var10 = true;
            }

            if (!this.enabled)
            {
                l = -6250336;
            }

            this.drawCenteredString(fontrenderer, this.displayString.substring(0, Math.min(this.displayString.length(), 25)), this.xPosition + this.width / 2, this.yPosition + (this.prevHeight - 8) / 2, l);
            GL11.glPushMatrix();

            if (this.dropDownMenu && this.array.length != 0)
            {
                for (int i = 0; i < this.amountOfItems; ++i)
                {
                    this.mouseOn[i] = this.inBounds(par2, par3, this.xPosition, this.yPosition + this.prevHeight * (i + 1), this.width, this.prevHeight);
                    String s = this.array[i].substring(0, Math.min(this.array[i].length(), 26)) + "..";
                    this.drawCenteredString(fontrenderer, s, this.xPosition + this.width / 2, this.yPosition + this.prevHeight * (i + 1) + 7, this.mouseOn[i] ? 16777120 : 14737632);
                }
            }

            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public boolean inBounds(int x, int y, int posX, int posY, int width, int height)
    {
        return this.drawButton && this.enabled && x >= posX && y >= posY && x < posX + width && y < posY + height;
    }

    public void setDisplayString(String s)
    {
        this.displayString = s;
    }

    public void setArray(String[] array)
    {
        this.array = array;
        this.amountOfItems = array.length;
        this.mouseOn = new boolean[this.amountOfItems];
    }

    public int getMouseOverInteger()
    {
        for (int i = 0; i < this.mouseOn.length; ++i)
        {
            if (this.mouseOn[i])
            {
                return i;
            }
        }

        return -1;
    }
}
