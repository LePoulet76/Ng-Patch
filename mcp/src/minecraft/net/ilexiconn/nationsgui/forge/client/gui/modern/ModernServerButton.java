package net.ilexiconn.nationsgui.forge.client.gui.modern;

import java.math.BigInteger;
import net.halalaboos.cfont.CFontRenderer;
import net.ilexiconn.nationsgui.forge.client.data.ServersData$Server;
import net.ilexiconn.nationsgui.forge.client.util.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ModernServerButton extends ModernColorIconButton
{
    private String ip;
    private int port;
    public ServersData$Server server;
    public static CFontRenderer slotNumberRenderer;
    private String serverName;
    private String serverType;

    public ModernServerButton(int id, int posX, int posY, int width, int height, ServersData$Server server, String serverName, String serverType)
    {
        super(id, posX, posY, width, height, "\u00a70" + I18n.getString("nationsgui.server." + serverName), -1, new ModernResourceLocation(server.getIconTexture()), (new BigInteger(server.getBackgroundIconColor() + "FF", 16)).intValue());
        String[] strings = server.getIp().split(":");
        this.ip = strings[0];
        this.port = Integer.parseInt(strings[1]);
        this.server = server;
        this.serverName = serverName;
        this.serverType = serverType;

        if (slotNumberRenderer == null)
        {
            slotNumberRenderer = FontManager.createFont("nationsgui", "VisbyCF-Bold.otf");
            slotNumberRenderer.setFontSize(11.0F);
        }
    }

    public String getServerName()
    {
        return this.serverName;
    }

    public String getServerType()
    {
        return this.serverType;
    }

    public String getIp()
    {
        return this.ip;
    }

    public int getPort()
    {
        return this.port;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
    {
        super.drawButton(minecraft, mouseX, mouseY);
    }

    protected void drawIconInSquare(Minecraft minecraft, float w, float h)
    {
        if (this.field_82253_i && this.enabled)
        {
            slotNumberRenderer.drawCenteredString(Integer.toString(this.server.getSlots()), (float)this.xPosition + 3.0F + 4.5F, (float)this.yPosition + (float)this.height / 2.0F - (float)slotNumberRenderer.getStringHeight("") / 2.0F, this.server.isInvertTextColor() ? 0 : 16777215);
        }
        else
        {
            super.drawIconInSquare(minecraft, w, h);
        }
    }
}
