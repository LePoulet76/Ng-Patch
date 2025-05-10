package net.ilexiconn.nationsgui.forge.client.gui.shop.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class HorizontalArrowButtonGUI extends GuiButton
{
    private boolean left;

    public HorizontalArrowButtonGUI(int id, int x, int y, boolean left)
    {
        super(id, x, y, 13, 13, "");
        this.left = left;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        this.field_82253_i = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        int state = this.getHoverState(this.field_82253_i);
        mc.getTextureManager().bindTexture(ShopGUI.TEXTURE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, this.left ? 213 : 226, state * 13, this.width, this.height);
    }
}
