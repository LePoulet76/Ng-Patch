package net.ilexiconn.nationsgui.forge.client.gui.modern;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModernColorIconButton extends ModernButton
{
    private int backgroundColor;

    public ModernColorIconButton(int id, int posX, int posY, int width, int height, String text, int color, ResourceLocation icon, int backgroundColor)
    {
        super(id, posX, posY, width, height, text, color, icon);
        this.backgroundColor = backgroundColor;
    }

    public ModernColorIconButton(int id, int posX, int posY, String text, int color, ResourceLocation icon, int backgroundColor)
    {
        super(id, posX, posY, text, color, icon);
        this.backgroundColor = backgroundColor;
    }

    public ModernColorIconButton(int id, int posX, int posY, int color, ResourceLocation location, int backgroundColor)
    {
        super(id, posX, posY, color, location);
        this.backgroundColor = backgroundColor;
    }

    protected void drawText(Minecraft minecraft, float w, float h)
    {
        cFontRenderer.drawString(this.displayString, (float)this.xPosition + 4.0F + 7.0F + 3.0F, (float)this.yPosition + (float)this.height / 2.0F - h / 2.0F, 16777215);
    }

    protected void drawIcon(Minecraft minecraft, float w, float h)
    {
        float r = (float)(this.backgroundColor >> 24 & 255) / 255.0F;
        float g = (float)(this.backgroundColor >> 16 & 255) / 255.0F;
        float b = (float)(this.backgroundColor >> 8 & 255) / 255.0F;
        GL11.glColor3f(r, g, b);
        ModernGui.drawRoundedRectangle((float)this.xPosition + 3.0F, (float)this.yPosition + (float)this.height / 2.0F - 4.5F, this.zLevel, 9.0F, 9.0F);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.drawIconInSquare(minecraft, w, g);
    }

    protected void drawIconInSquare(Minecraft minecraft, float w, float h)
    {
        minecraft.getTextureManager().bindTexture(this.resourceIcon);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition + 4.0F, (float)this.yPosition + (float)this.height / 2.0F - 3.5F, 0, 0, 7, 7, 7.0F, 7.0F, false);
    }
}
