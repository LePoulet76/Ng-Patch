package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.gui.main.MainGUI;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;

public class ServerSwitchExpressGui extends GuiScreen
{
    private String address;
    private int port;
    private String serverName;
    private int update = 0;

    public ServerSwitchExpressGui(String address, int port, String serverName)
    {
        this.address = address;
        this.port = port;
        this.serverName = serverName;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.update < 2)
        {
            if (this.update == 1)
            {
                Minecraft mc = Minecraft.getMinecraft();

                if (mc.getNetHandler() != null)
                {
                    mc.getNetHandler().disconnect();
                    mc.loadWorld((WorldClient)null);
                }

                Minecraft.getMinecraft().displayGuiScreen(new CustomConnectingGui(new MainGUI(), this.address, this.port, this.serverName));
            }

            ++this.update;
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        ModernGui.bindRemoteTexture("https://apiv2.nationsglory.fr/proxy_images/screen_join_all");
        ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, this.width, this.height, 3840.0F, 2160.0F, false);
    }
}
