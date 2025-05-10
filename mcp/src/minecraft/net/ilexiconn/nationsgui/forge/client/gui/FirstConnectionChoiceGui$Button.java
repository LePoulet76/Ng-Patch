package net.ilexiconn.nationsgui.forge.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

class FirstConnectionChoiceGui$Button extends GuiButton
{
    final FirstConnectionChoiceGui this$0;

    public FirstConnectionChoiceGui$Button(FirstConnectionChoiceGui var1, int id, int x, int y, String text)
    {
        super(id, x, y, 150, 15, text);
        this.this$0 = var1;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int mouseX, int mouseY)
    {
        this.field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        if (this.field_82253_i)
        {
            float finale = 0.5F;
            GL11.glColor3f(finale, finale, finale);
        }

        this.this$0.drawGreyRectangle(this.xPosition, this.yPosition, this.width, this.height);
        String finale1 = this.displayString + (this.enabled ? "" : " (Indisponible)");
        FirstConnectionChoiceGui.access$300(this.this$0).fontRenderer.drawString(finale1, this.xPosition + this.width / 2 - FirstConnectionChoiceGui.access$200(this.this$0).fontRenderer.getStringWidth(finale1) / 2, this.yPosition + this.height / 2 - 4, -1);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
    }
}
