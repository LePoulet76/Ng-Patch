package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class AssistanceSimpleButton extends GuiButton
{
    private final int u;
    private final int v;

    public AssistanceSimpleButton(int id, int posX, int posY, int u, int v)
    {
        super(id, posX, posY, 20, 16, "");
        this.u = u;
        this.v = v;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            Minecraft.getMinecraft().getTextureManager().bindTexture(AbstractAssistanceGUI.GUI_TEXTURE);
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, this.u + (this.field_82253_i ? 20 : 0), this.v, 20, 16, 512.0F, 512.0F, false);
        }
    }
}
