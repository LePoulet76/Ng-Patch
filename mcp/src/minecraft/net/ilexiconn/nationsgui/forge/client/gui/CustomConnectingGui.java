package net.ilexiconn.nationsgui.forge.client.gui;

import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.asm.NationsGUIClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.resources.I18n;

public class CustomConnectingGui extends GuiConnecting
{
    private GuiScreen prevGuiScreen;

    public CustomConnectingGui(GuiScreen prevGuiScreen, String ip, int port, String serverName)
    {
        super(prevGuiScreen, Minecraft.getMinecraft(), ip, port);
        ClientData.lastCustomConnectionIP = ip;
        ClientData.lastCustomConnectionPort = port;
        ClientData.lastCustomConnectionServerName = serverName;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        String backgroudURL = "https://apiv2.nationsglory.fr/proxy_images/screen_join_all";

        if (ClientData.lastCustomConnectionServerName != null && ClientData.lastCustomConnectionServerName.equalsIgnoreCase("hub"))
        {
            backgroudURL = "https://apiv2.nationsglory.fr/proxy_images/screen_join_hub";
        }
        else if (ClientData.lastCustomConnectionServerName != null && ClientData.lastCustomConnectionServerName.toLowerCase().contains("event"))
        {
            backgroudURL = "https://apiv2.nationsglory.fr/proxy_images/screen_join_event";
        }

        ModernGui.bindRemoteTexture(backgroudURL);
        ModernGui.drawScaledCustomSizeModalRect(0.0F, 0.0F, 0.0F, 0.0F, 3840, 2160, this.width, this.height, 3840.0F, 2160.0F, false);
        Minecraft.getMinecraft().getTextureManager().bindTexture(NationsGUIClientHooks.MINECRAFT_SCREEN_TEXTURE);
        ModernGui.drawScaledCustomSizeModalRect(12.0F, (float)(this.height - 38), (float)(1074 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(8 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), 85 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 96 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE, 28, 32, (float)(1792 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), (float)(276 * NationsGUIClientHooks.LOADING_SCREEN_GUI_SCALE), false);

        if (ClientData.lastCustomConnectionServerName != null)
        {
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.connecting.move_to"), 52.0F, (float)(this.height - 28), 8882056, 0.5F, "left", false, "georamaSemiBold", 24);
            ModernGui.drawScaledStringCustomFont(I18n.getString("gui.connecting.server") + " " + ClientData.lastCustomConnectionServerName + " ...", 52.0F, (float)(this.height - 22), 16777215, 0.5F, "left", false, "minecraftDungeons", 27);
        }
    }

    /**
     * Draws either a gradient over the background screen (when it exists) or a flat gradient over background.png
     */
    public void drawDefaultBackground() {}
}
