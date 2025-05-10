package net.ilexiconn.nationsgui.forge.client.gui.override;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;
import sun.misc.BASE64Decoder;

public class LobbyOverride extends Gui implements ElementOverride
{
    public ElementType getType()
    {
        return ElementType.HOTBAR;
    }

    public ElementType[] getSubTypes()
    {
        return new ElementType[0];
    }

    public void renderOverride(Minecraft client, ScaledResolution resolution, float partialTicks)
    {
        if (ClientProxy.serverType.equals("lobby"))
        {
            Double doubleY = Double.valueOf((double)resolution.getScaledHeight() * 0.4D);
            int y = doubleY.intValue();
            int x = resolution.getScaledWidth() - 120;
            drawRect(x, y, x + 120, y + 16, -1157627904);
            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.title"), x + 60 - client.fontRenderer.getStringWidth(I18n.getString("lobby.title")) / 2, y + 4, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawRect(x, y + 16, x + 120, y + 16 + 97, 1996488704);
            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.step_1"), x + 15, y + 23, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");

            if (client.thePlayer.posX > 60.0D)
            {
                this.drawTexturedModalRect(x + 3, y + 22, 182, 0, 10, 10);
            }
            else
            {
                this.drawTexturedModalRect(x + 3, y + 22, 192, 0, 10, 10);
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.step_2"), x + 15, y + 38, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");

            if (client.thePlayer.posX > 80.0D)
            {
                this.drawTexturedModalRect(x + 3, y + 37, 182, 0, 10, 10);
            }
            else
            {
                this.drawTexturedModalRect(x + 3, y + 37, 192, 0, 10, 10);
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.step_3"), x + 15, y + 53, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");

            if (client.thePlayer.posX > 100.0D)
            {
                this.drawTexturedModalRect(x + 3, y + 52, 182, 0, 10, 10);
            }
            else
            {
                this.drawTexturedModalRect(x + 3, y + 52, 192, 0, 10, 10);
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.step_4"), x + 15, y + 68, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");

            if (client.thePlayer.posX > 100.0D)
            {
                this.drawTexturedModalRect(x + 3, y + 67, 182, 0, 10, 10);
            }
            else
            {
                this.drawTexturedModalRect(x + 3, y + 67, 192, 0, 10, 10);
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.step_5"), x + 15, y + 83, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");

            if (client.thePlayer.posX > 122.0D)
            {
                this.drawTexturedModalRect(x + 3, y + 82, 182, 0, 10, 10);
            }
            else
            {
                this.drawTexturedModalRect(x + 3, y + 82, 192, 0, 10, 10);
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("lobby.step_6"), x + 15, y + 98, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");

            if (client.thePlayer.posX > 130.0D)
            {
                this.drawTexturedModalRect(x + 3, y + 97, 182, 0, 10, 10);
            }
            else
            {
                this.drawTexturedModalRect(x + 3, y + 97, 192, 0, 10, 10);
            }
        }
    }

    private void drawSmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.95F, 0.95F, 0.95F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }

    private void drawVerySmallString(FontRenderer fontRenderer, String string, int posX, int posY, int color)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)posX, (float)posY, 0.0F);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        this.drawString(fontRenderer, string, 0, 0, 16777215);
        GL11.glPopMatrix();
    }

    public void drawScaledString(FontRenderer fontRenderer, String text, int x, int y, int color, float scale, boolean centered, boolean shadow)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        float newX = (float)x;

        if (centered)
        {
            newX = (float)x - (float)fontRenderer.getStringWidth(text) * scale / 2.0F;
        }

        if (shadow)
        {
            fontRenderer.drawString(text, (int)(newX / scale), (int)((float)(y + 1) / scale), (color & 16579836) >> 2 | color & -16777216, false);
        }

        fontRenderer.drawString(text, (int)(newX / scale), (int)((float)y / scale), color, false);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static BufferedImage decodeToImage(String imageString)
    {
        BufferedImage image = null;

        try
        {
            BASE64Decoder e = new BASE64Decoder();
            byte[] imageByte = e.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        }
        catch (Exception var5)
        {
            var5.printStackTrace();
        }

        return image;
    }
}
