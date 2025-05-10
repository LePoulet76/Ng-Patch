package net.ilexiconn.nationsgui.forge.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.shop.ShopGUI;
import net.ilexiconn.nationsgui.forge.client.util.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class SnackbarGUI extends Gui
{
    private String message;
    private int maxAge;
    private int age;
    private float yOffset;
    private boolean clicked;

    public SnackbarGUI(String message)
    {
        this(message, true);
    }

    public SnackbarGUI(String message, boolean autoclose)
    {
        this.yOffset = 20.0F;
        this.message = message;

        if (autoclose)
        {
            this.maxAge = (int)((double)Minecraft.getMinecraft().fontRenderer.getStringWidth(message) * 0.75D);
        }
        else
        {
            this.maxAge = 2147383647;
        }
    }

    public void updateSnackbar(ScaledResolution resolution, int mouseX, int mouseY)
    {
        ++this.age;

        if (this.age > this.maxAge)
        {
            ClientEventHandler.getInstance().snackbarGUI = null;
        }

        if (!this.clicked && Mouse.isButtonDown(0) && mouseX >= resolution.getScaledWidth() - 15 && mouseX <= resolution.getScaledWidth() - 15 + 9 && mouseY >= 5 && mouseY <= 15)
        {
            this.age = this.maxAge - 15;
            Minecraft.getMinecraft().sndManager.playSoundFX("random.click", 1.0F, 1.0F);
            this.clicked = true;
        }
    }

    public void drawSnackbar()
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, 0.0F, 500.0F);
        GL11.glTranslatef(0.0F, -this.yOffset, 0.0F);
        GL11.glEnable(GL11.GL_BLEND);
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        Gui.drawRect(0, 20, resolution.getScaledWidth(), 0, -1342177280);
        Minecraft.getMinecraft().fontRenderer.drawString(this.message, 10, 6, -1);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ShopGUI.TEXTURE);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.drawTexturedModalRect(resolution.getScaledWidth() - 15, 5, 204, 18, 9, 10);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        this.yOffset = GUIUtils.interpolate(this.yOffset, this.age < this.maxAge - 15 ? 0.0F : 20.0F, 0.05F);
    }
}
