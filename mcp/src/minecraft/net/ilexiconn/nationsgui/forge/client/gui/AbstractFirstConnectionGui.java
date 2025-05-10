package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.AbstractFirstConnectionGui$QuitButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public abstract class AbstractFirstConnectionGui extends GuiScreen
{
    public static final ResourceLocation BACKGROUND = new ResourceLocation("nationsgui", "textures/gui/first_connection.png");
    public static final ResourceLocation SHOP_ASSET = new ResourceLocation("nationsgui", "textures/gui/shop.png");

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.add(new AbstractFirstConnectionGui$QuitButton(this, this.width / 2 + 82, this.height / 2 - 58));
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == -1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawRectangleBox(this.width / 2 - 100, this.height / 2 - 64, 200, 128);
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        this.drawRectangleWelcome(this.width / 2 - 90, this.height / 2 - 64, 110);
        super.drawScreen(par1, par2, par3);
    }

    protected void drawRectangleBox(int x, int y, int width, int height)
    {
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, 0.0D);
        this.drawTexturedModalRect(0, 0, 0, 0, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated(3.0D, 0.0D, 0.0D);
        GL11.glScalef((float)(width - 6), 1.0F, 1.0F);
        this.drawTexturedModalRect(0, 0, 3, 0, 1, 3);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(width - 3, 0, 24, 0, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated(0.0D, 3.0D, 0.0D);
        GL11.glScalef(1.0F, (float)(height - 6), 1.0F);
        this.drawTexturedModalRect(0, 0, 0, 3, 3, 1);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(0, height - 3, 0, 7, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated(3.0D, 0.0D, 0.0D);
        GL11.glScalef((float)(width - 6), 1.0F, 1.0F);
        this.drawTexturedModalRect(0, height - 3, 3, 7, 1, 3);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(width - 3, height - 3, 24, 7, 3, 3);
        GL11.glPushMatrix();
        GL11.glTranslated((double)((float)width - 3.0F), 3.0D, 0.0D);
        GL11.glScalef(1.0F, (float)(height - 6), 1.0F);
        this.drawTexturedModalRect(0, 0, 24, 3, 3, 1);
        GL11.glPopMatrix();
        drawRect(3, 3, width - 3, height - 3, -15000805);
        GL11.glPopMatrix();
    }

    protected void drawRectangleWelcome(int x, int y, int width)
    {
        this.drawTexturedModalRect(x, y, 28, 0, 3, 20);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(x + 3), (float)y, 0.0F);
        GL11.glScalef((float)(width - 6), 1.0F, 1.0F);
        this.drawTexturedModalRect(0, 0, 31, 0, 1, 20);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(x + width - 3, y, 49, 0, 3, 20);
        GL11.glPushMatrix();
        String text = "Bienvenue";
        GL11.glTranslatef((float)((double)(x + width / 2) - (double)this.mc.fontRenderer.getStringWidth(text) * 1.5D / 2.0D), (float)((double)(y + 10) - 6.0D), 0.0F);
        GL11.glScalef(1.5F, 1.5F, 1.0F);
        this.mc.fontRenderer.drawStringWithShadow(text, 0, 0, -1);
        GL11.glPopMatrix();
    }

    protected void drawGreyRectangle(int xPosition, int yPosition, int width, int height)
    {
        this.mc.getTextureManager().bindTexture(BACKGROUND);
        this.drawTexturedModalRect(xPosition, yPosition, 25, 33, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(xPosition + 2), (float)yPosition, 0.0F);
        GL11.glScalef((float)(width - 4), 1.0F, 1.0F);
        this.drawTexturedModalRect(0, 0, 27, 33, 1, 2);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(xPosition + width - 2, yPosition, 42, 33, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)xPosition, (float)(yPosition + 2), 0.0F);
        GL11.glScalef(1.0F, (float)(height - 4), 1.0F);
        this.drawTexturedModalRect(0, 0, 25, 35, 2, 1);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(xPosition, yPosition + height - 2, 25, 40, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(xPosition + 2), (float)(yPosition + height - 2), 0.0F);
        GL11.glScalef((float)(width - 4), 1.0F, 1.0F);
        this.drawTexturedModalRect(0, 0, 27, 40, 1, 2);
        GL11.glPopMatrix();
        this.drawTexturedModalRect(xPosition + width - 2, yPosition + height - 2, 42, 40, 2, 2);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(xPosition + width - 2), (float)(yPosition + 2), 0.0F);
        GL11.glScalef(1.0F, (float)(height - 4), 1.0F);
        this.drawTexturedModalRect(0, 0, 42, 35, 2, 1);
        GL11.glPopMatrix();
        drawRect(xPosition + 2, yPosition + 2, xPosition + width - 2, yPosition + height - 2, -11645362);
    }

    static Minecraft access$000(AbstractFirstConnectionGui x0)
    {
        return x0.mc;
    }
}
