package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import acs.tabbychat.TabbyChat;
import cpw.mods.fml.common.network.PacketDispatcher;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.ilexiconn.nationsgui.forge.server.packet.impl.PlayerExecCmdPacket;

public class ImageTag extends AbstractActionTag
{
    private String url;
    private URL actionUrl = null;
    private String cmd = null;

    public ImageTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.url = (String)parameters.get("src");

        if (this.url == null)
        {
            throw new Exception("[ChatEngin] No URL defined to image");
        }
        else
        {
            if (parameters.get("action") != null)
            {
                if (!((String)parameters.get("action")).matches("https://glor\\.cc/.*") && !((String)parameters.get("action")).matches("https://nationsglory.fr/.*"))
                {
                    if (((String)parameters.get("action")).equals("cmd"))
                    {
                        this.cmd = (String)parameters.get("value");
                    }
                }
                else
                {
                    try
                    {
                        this.actionUrl = new URL((String)parameters.get("action"));
                    }
                    catch (MalformedURLException var3)
                    {
                        ;
                    }
                }
            }
        }
    }

    public void render(int mouseX, int mouseY)
    {
        if (this.url.matches("https://static\\.nationsglory\\.fr/.*\\.png.*") || this.url.matches("http://localhost/.*\\.png") || this.url.matches("https://apiv2.nationsglory.fr/.*"))
        {
            int offsetX = 320 - TabbyChat.gnc.chatWidth;
            offsetX /= 2;
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(0 - offsetX, 0, 0, 0, 1280, 128, 320, 32, 1280.0F, 128.0F, false, this.url);
        }
    }

    public void onClick(int mouseX, int mouseY)
    {
        int width = TabbyChat.gnc.chatWidth;

        if (mouseX >= 0 && mouseX <= width && mouseY >= 0 && mouseY <= this.getLineHeight() * 8)
        {
            if (this.actionUrl != null)
            {
                try
                {
                    Class e = Class.forName("java.awt.Desktop");
                    Object object = e.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    e.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {this.actionUrl.toURI()});
                }
                catch (URISyntaxException var6)
                {
                    var6.printStackTrace();
                }
            }
            else if (this.cmd != null)
            {
                PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new PlayerExecCmdPacket(this.cmd, 0)));
            }
            else
            {
                this.doAction();
            }
        }
    }

    public int getLineHeight()
    {
        return 4;
    }

    public int getWidth()
    {
        return 0;
    }
}
