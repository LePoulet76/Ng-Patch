package net.ilexiconn.nationsgui.forge.client.gui.chat.tag;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import net.ilexiconn.nationsgui.forge.client.gui.modern.ModernGui;

public class HologramTag extends AbstractActionTag
{
    private String url;
    private URL actionUrl = null;

    public HologramTag(Map<String, String> parameters) throws Exception
    {
        super(parameters);
        this.url = (String)parameters.get("src");

        if (this.url == null)
        {
            throw new Exception("[ChatEngin] No URL defined to image");
        }
        else
        {
            if (parameters.get("action") != null && (((String)parameters.get("action")).matches("https://glor\\.cc/.*") || ((String)parameters.get("action")).matches("https://nationsglory.fr/.*")))
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

    public void render(int mouseX, int mouseY)
    {
        if (this.url.matches("https://static\\.nationsglory\\.fr/.*\\.png.*") || this.url.matches("http://localhost/.*\\.png") || this.url.matches("https://apiv2.nationsglory.fr/.*"))
        {
            short width = 320;
            ModernGui.drawScaledModalRectWithCustomSizedRemoteTexture(-width / 4 + 8, 0, 0, 0, width, 32, width, 32, (float)width, 32.0F, false, this.url);
        }
    }

    public void onClick(int mouseX, int mouseY) {}

    public int getLineHeight()
    {
        return 4;
    }

    public int getWidth()
    {
        return 0;
    }
}
