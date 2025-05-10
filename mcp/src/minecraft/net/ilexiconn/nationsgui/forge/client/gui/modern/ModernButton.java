package net.ilexiconn.nationsgui.forge.client.gui.modern;

import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ModernButton extends GuiButton
{
    protected int color;
    protected static CFontRenderer cFontRenderer = null;
    protected ResourceLocation resourceIcon;

    public ModernButton(int id, int posX, int posY, int width, int height, String text, int color)
    {
        this(id, posX, posY, width, height, text, color, (ResourceLocation)null);
    }

    public ModernButton(int id, int posX, int posY, int width, int height, String text, int color, ResourceLocation icon)
    {
        super(id, posX, posY, width, height, text);
        this.color = color;

        if (cFontRenderer == null)
        {
            cFontRenderer = FontManager.createFont("nationsgui", "VisbyCF-Bold.otf");
            cFontRenderer.setFontSize(14.0F);
        }

        this.resourceIcon = icon;
    }

    public ModernButton(int id, int posX, int posY, String text, int color, ResourceLocation icon)
    {
        this(id, posX, posY, 0, 0, text, color, icon);

        if (!text.equals(""))
        {
            this.width = (int)((float)this.width + cFontRenderer.getStringWidth(text) + 8.0F);
        }

        if (icon != null)
        {
            this.width += 12 + (!text.equals("") ? 0 : 2);
        }

        this.height += cFontRenderer.getStringHeight(text) + 12;
    }

    public ModernButton(int id, int posX, int posY, String text, int color)
    {
        this(id, posX, posY, text, color, (ResourceLocation)null);
    }

    public ModernButton(int id, int posX, int posY, int color, ResourceLocation location)
    {
        this(id, posX, posY, "", color, location);
    }

    public void setIcon(ResourceLocation icon)
    {
        this.resourceIcon = icon;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getPosX()
    {
        return this.xPosition;
    }

    public int getPosY()
    {
        return this.yPosition;
    }

    public void setPos(int x, int y)
    {
        this.xPosition = x;
        this.yPosition = y;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int i, int i1)
    {
        float r = (float)(this.color >> 24 & 255) / 255.0F;
        float g = (float)(this.color >> 16 & 255) / 255.0F;
        float b = (float)(this.color >> 8 & 255) / 255.0F;
        float a = (float)(this.color & 255) / 255.0F;
        this.field_82253_i = i >= this.xPosition && i1 >= this.yPosition && i < this.xPosition + this.width && i1 < this.yPosition + this.height;
        float modifier = this.field_82253_i && this.enabled ? 0.1F : 0.0F;
        GL11.glColor4f(r - modifier, g - modifier, b - modifier, a);
        ModernGui.drawRoundedRectangle((float)this.xPosition, (float)this.yPosition, this.zLevel, (float)this.width, (float)this.height);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        float w = cFontRenderer.getStringWidth(this.displayString);
        float h = (float)cFontRenderer.getStringHeight(this.displayString);

        if (this.resourceIcon != null)
        {
            this.drawIcon(minecraft, w + (float)(!this.displayString.equals("") ? 5 : 2), h);
        }

        this.drawText(minecraft, w, h);
    }

    protected void drawText(Minecraft minecraft, float w, float h)
    {
        cFontRenderer.drawString(this.displayString, (float)this.xPosition + (float)this.width / 2.0F - (w - (this.resourceIcon != null ? 10.0F : 0.0F)) / 2.0F, (float)this.yPosition + (float)this.height / 2.0F - h / 2.0F, 16777215);
    }

    protected void drawIcon(Minecraft minecraft, float w, float h)
    {
        minecraft.getTextureManager().bindTexture(this.resourceIcon);
        ModernGui.drawModalRectWithCustomSizedTexture((float)this.xPosition + (float)this.width / 2.0F - (w + 10.0F) / 2.0F, (float)this.yPosition + (float)this.height / 2.0F - 6.0F, 0, 0, 12, 12, 12.0F, 12.0F, false);
    }
}
