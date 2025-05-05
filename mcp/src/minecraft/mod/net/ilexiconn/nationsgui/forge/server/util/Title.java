package net.ilexiconn.nationsgui.forge.server.util;

import com.google.common.io.ByteArrayDataOutput;

public class Title
{
    private String title;
    private String subtitle;
    private float timer;

    public Title(String title, String subtitle, float timer)
    {
        this.title = title;
        this.subtitle = subtitle;
        this.timer = timer;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSubtitle()
    {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle)
    {
        this.subtitle = subtitle;
    }

    public float getTimer()
    {
        return this.timer;
    }

    public void writePacket(ByteArrayDataOutput data)
    {
        data.writeUTF(this.title);
        data.writeUTF(this.subtitle);
        data.writeFloat(this.timer);
    }

    public static Title fromPacket(String title, String subtitle, float timer)
    {
        return new Title(title, subtitle, timer);
    }
}
