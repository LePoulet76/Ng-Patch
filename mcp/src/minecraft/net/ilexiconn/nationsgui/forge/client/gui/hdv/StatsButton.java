package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class StatsButton extends GuiButton
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");

    public StatsButton(int id, int posX, int posY)
    {
        super(id, posX, posY, 71, 14, I18n.getString("hdv.stats"));
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            par1Minecraft.getTextureManager().bindTexture(BACKGROUND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;

            if (this.field_82253_i)
            {
                GL11.glColor3f(0.75F, 0.75F, 0.75F);
            }
            else
            {
                GL11.glColor3f(1.0F, 1.0F, 1.0F);
            }

            ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, 36, 373, 71, 14, 372.0F, 400.0F, false);
            ModernGui.drawScaledString(this.displayString, this.xPosition + 18, this.yPosition + 4, 16777215, 1.0F, false, false);
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
}
