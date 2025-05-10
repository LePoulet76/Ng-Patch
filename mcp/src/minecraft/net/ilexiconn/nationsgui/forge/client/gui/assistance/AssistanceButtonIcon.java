package net.ilexiconn.nationsgui.forge.client.gui.assistance;

import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class AssistanceButtonIcon extends GuiButton
{
    private final int iconU;
    private final int iconV;

    public AssistanceButtonIcon(int id, int posX, int posY, int iconU, int iconV)
    {
        this(id, posX, posY, iconU, iconV, "");
    }

    public AssistanceButtonIcon(int id, int posX, int posY, int iconU, int iconV, String text)
    {
        this(id, posX, posY, 200, 20, iconU, iconV, text);
    }

    public AssistanceButtonIcon(int id, int posX, int posY, int width, int height, int iconU, int iconV)
    {
        this(id, posX, posY, width, height, iconU, iconV, "");
    }

    public AssistanceButtonIcon(int id, int posX, int posY, int width, int height, int iconU, int iconV, String text)
    {
        super(id, posX, posY, width, height, text);
        this.iconU = iconU;
        this.iconV = iconV;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.drawButton)
        {
            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            par1Minecraft.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int k = this.getHoverState(this.field_82253_i);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            if (!this.enabled)
            {
                l = -6250336;
            }
            else if (this.field_82253_i)
            {
                l = 16777120;
            }

            par1Minecraft.getTextureManager().bindTexture(AbstractAssistanceGUI.GUI_TEXTURE);
            ModernGui.drawModalRectWithCustomSizedTexture((float)(this.xPosition + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2 - 8), (float)(this.yPosition + (this.height - 16) / 2), this.iconU, this.iconV, 16, 16, 512.0F, 512.0F, false);
            this.drawString(fontrenderer, this.displayString, this.xPosition + this.width / 2 - fontrenderer.getStringWidth(this.displayString) / 2 + 9, this.yPosition + (this.height - 8) / 2, l);
        }
    }
}
