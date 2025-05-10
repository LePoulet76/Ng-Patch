package net.ilexiconn.nationsgui.forge.client.gui.hdv;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ChangePageButton extends GuiButton
{
    private static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/market.png");
    private boolean direction;

    public ChangePageButton(int id, int posX, int posY, boolean direction)
    {
        super(id, posX, posY, 12, 12, "");
        this.direction = direction;
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
            int textureOffset = this.enabled ? (this.field_82253_i ? 24 : 12) : 0;
            ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition, (float)this.yPosition, 348 + (this.direction ? 12 : 0), textureOffset, 12, 12, 372.0F, 400.0F, false);
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }
}
