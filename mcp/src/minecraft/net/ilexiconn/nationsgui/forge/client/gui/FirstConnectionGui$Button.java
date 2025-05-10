package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class FirstConnectionGui$Button extends GuiButton
{
    final FirstConnectionGui this$0;

    public FirstConnectionGui$Button(FirstConnectionGui var1, int x, int y)
    {
        super(0, x, y, 17, 40, ">");
        this.this$0 = var1;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        GL11.glPushMatrix();
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;

        if (this.field_82253_i)
        {
            float color = 0.7F;
            GL11.glColor3f(color, color, color);
        }

        GL11.glTranslatef((float)this.xPosition, (float)this.yPosition, 0.0F);
        GL11.glScalef(0.75F, 0.75F, 0.75F);
        FirstConnectionGui.access$200(this.this$0).getTextureManager().bindTexture(AbstractFirstConnectionGui.BACKGROUND);
        this.drawTexturedModalRect(0, 0, 0, 12, 22, 53);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(11 - FirstConnectionGui.access$300(this.this$0).getStringWidth(this.displayString) + 1), 19.0F, 0.0F);
        GL11.glScalef(2.0F, 2.0F, 0.0F);
        FirstConnectionGui.access$400(this.this$0).drawString(this.displayString, 0, 0, -1);
        GL11.glPopMatrix();
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }
}
