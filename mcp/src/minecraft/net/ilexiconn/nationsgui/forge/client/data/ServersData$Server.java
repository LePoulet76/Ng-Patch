package net.ilexiconn.nationsgui.forge.client.data;

import java.util.List;
import net.minecraft.client.Minecraft;

public class ServersData$Server
{
    private boolean isOnline;
    private String ip;
    private String backgroundIconColor;
    private String iconTexture;
    private List<String> zone;
    private int slots;
    private boolean invertTextColor;

    final ServersData this$0;

    public ServersData$Server(ServersData this$0)
    {
        this.this$0 = this$0;
        this.isOnline = true;
        this.ip = "";
        this.backgroundIconColor = "";
        this.iconTexture = "";
        this.zone = null;
        this.slots = -1;
        this.invertTextColor = false;
    }

    public boolean isOnline()
    {
        return this.isOnline;
    }

    public String getIp()
    {
        return this.ip;
    }

    public String getBackgroundIconColor()
    {
        return this.backgroundIconColor;
    }

    public List<String> getZones()
    {
        return this.zone;
    }

    public String getIconTexture()
    {
        return this.iconTexture;
    }

    public boolean isVisible(Minecraft mc)
    {
        return mc.getSession().getSessionID() != null && !mc.getSession().getSessionID().equals("took") || !this.isOnline();
    }

    public int getSlots()
    {
        return this.slots;
    }

    public void setSlots(int slots)
    {
        this.slots = slots;
    }

    public boolean isInvertTextColor()
    {
        return this.invertTextColor;
    }
}
