package net.ilexiconn.nationsgui.forge.client.gui.main.component;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler;
import net.ilexiconn.nationsgui.forge.client.gui.main.ButtonPositionHandler$State;
import net.ilexiconn.nationsgui.forge.client.gui.main.component.MenuButtonGUI$1;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class MenuButtonGUI extends GuiButton
{
    public static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("nationsgui", "textures/gui/menu.png");
    public float positionX;
    public float positionY;
    public float positionXOrig;
    public float positionYOrog;
    private int textureX;
    private int textureY;
    private int color;
    private boolean continuous;
    private ButtonPositionHandler positionHandler;

    public MenuButtonGUI(int id, int x, int y, int textureX, int textureY)
    {
        this(id, x, y, 200, 20, textureX, textureY);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY)
    {
        this(id, x, y, width, height, textureX, textureY, "");
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text)
    {
        this(id, x, y, width, height, textureX, textureY, text, -1);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text, int color)
    {
        this(id, x, y, width, height, textureX, textureY, text, color, false);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text, int color, boolean continuous)
    {
        this(id, x, y, width, height, textureX, textureY, text, color, continuous, ButtonPositionHandler.NONE);
    }

    public MenuButtonGUI(int id, int x, int y, int width, int height, int textureX, int textureY, String text, int color, boolean continuous, ButtonPositionHandler positionHandler)
    {
        super(id, 0, 0, width, height, text);
        this.positionX = this.positionXOrig = (float)x;
        this.positionY = this.positionYOrog = (float)y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.color = color;
        this.continuous = continuous;
        this.positionHandler = positionHandler;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.drawButton)
        {
            mc.getTextureManager().bindTexture(BUTTON_TEXTURE);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hovered = (float)mouseX >= this.positionX && (float)mouseY >= this.positionY && (float)mouseX < this.positionX + (float)this.width && (float)mouseY < this.positionY + (float)this.height;

            if (hovered)
            {
                this.positionHandler.handleTransform(this, ButtonPositionHandler$State.HOVER);
            }
            else
            {
                this.positionHandler.handleTransform(this, ButtonPositionHandler$State.NORMAL);
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            if (!this.continuous)
            {
                ModernGui.drawModalRectWithCustomSizedTexture(this.positionX, this.positionY, this.textureX, this.textureY + (hovered ? this.height : 0), this.width, this.height, 256.0F, 256.0F, false);
            }
            else
            {
                GUIUtils.drawContinuousTexturedBox(BUTTON_TEXTURE, this.positionX, this.positionY, this.textureX, this.textureY + (hovered ? this.height : 0), this.width, this.height, 200, 20, 2, 3, 2, 2, this.zLevel);
            }

            GL11.glTranslatef(this.positionX, this.positionY, 0.0F);

            switch (MenuButtonGUI$1.$SwitchMap$net$ilexiconn$nationsgui$forge$client$gui$main$ButtonPositionHandler[this.positionHandler.ordinal()])
            {
                case 1:
                    this.drawString(mc.fontRenderer, this.displayString, this.width - mc.fontRenderer.getStringWidth(this.displayString) - 6, (this.height - 8) / 2, this.color);
                    break;

                case 2:
                    this.drawString(mc.fontRenderer, this.displayString, 6, (this.height - 8) / 2, this.color);
                    break;

                case 3:
                    this.drawCenteredString(mc.fontRenderer, this.displayString, this.width / 2, (this.height - 8) / 2, this.color);
            }

            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.drawButton && (float)mouseX >= this.positionX && (float)mouseY >= this.positionY && (float)mouseX < this.positionX + (float)this.width && (float)mouseY < this.positionY + (float)this.height;
    }
}
