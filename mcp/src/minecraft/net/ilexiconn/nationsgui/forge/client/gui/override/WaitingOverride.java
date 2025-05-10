package net.ilexiconn.nationsgui.forge.client.gui.override;

import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import org.lwjgl.opengl.GL11;

public class WaitingOverride extends Gui implements ElementOverride
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
        if (ClientData.waitingServerName != null && !ClientData.waitingServerName.isEmpty() && !ClientData.waitingServerName.equalsIgnoreCase("null"))
        {
            short y = 150;
            ClientEventHandler.STYLE.bindTexture("hud2");
            drawRect(0, y, 130, y + 15, -1157627904);
            this.drawSmallString(client.fontRenderer, I18n.getString("waiting.title"), 25, y + 4, 16777215);
            ClientEventHandler.STYLE.bindTexture("hud2");
            this.drawTexturedModalRect(5, y + 2, 183, 13, 12, 11);
            int y1 = y + 15;
            drawRect(0, y1, 130, y1 + 15, 1996488704);
            ClientEventHandler.STYLE.bindTexture("hud2");
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(5, y1 + 3, 199, 13, 9, 10);

            if (ClientData.waitingServerName != null)
            {
                this.drawSmallString(client.fontRenderer, ClientData.waitingServerName.toUpperCase(), 19, y1 + 4, 16777215);
            }

            String dd = ClientData.waitingPosition + " " + I18n.getString("waiting.of") + " " + ClientData.waitingTotal;
            this.drawSmallString(client.fontRenderer, "\u00a7c" + dd, 130 - client.fontRenderer.getStringWidth(dd) - 3, y1 + 4, 16777215);
            y1 += 20;
            drawRect(0, y1, 130, y1 + 15, -1157627904);
            String waitingTime = I18n.getString("waiting.period.1");

            if (ClientData.waitingPosition >= 25 && ClientData.waitingPosition < 50)
            {
                waitingTime = I18n.getString("waiting.period.2");
            }
            else if (ClientData.waitingPosition >= 50 && ClientData.waitingPosition < 75)
            {
                waitingTime = I18n.getString("waiting.period.3");
            }
            else if (ClientData.waitingPosition >= 75)
            {
                waitingTime = I18n.getString("waiting.period.4");
            }

            this.drawSmallString(client.fontRenderer, I18n.getString("waiting.estimation") + " \u00a77" + waitingTime, 5, y1 + 4, 16777215);
            y1 += 15;
            drawRect(0, y1, 130, y1 + 15, -1157627904);
            long diffTime = System.currentTimeMillis() - ClientData.waitingJoinTime.longValue();
            diffTime = diffTime / 1000L / 60L;
            String timeToDisplay = diffTime + "min";
            this.drawSmallString(client.fontRenderer, I18n.getString("waiting.pastTime") + " \u00a77" + timeToDisplay, 5, y1 + 4, 16777215);
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
}
