package net.ilexiconn.nationsgui.forge.server.voices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class ServerConfiguration
{
    private Properties properties = new Properties();
    private final File file;
    private ServerSettings settings;

    public ServerConfiguration(ServerSettings settings, File file)
    {
        this.settings = settings;
        this.file = file;
    }

    private boolean load()
    {
        FileInputStream is = null;

        try
        {
            is = new FileInputStream(this.file);
            this.properties.load(is);
            this.settings.setVoiceEnable(Boolean.parseBoolean("" + this.properties.get("isVoiceEnable")));
            this.settings.setSoundDistance(Integer.parseInt("" + this.properties.get("sound_dist")));
            this.settings.setUDP(Integer.parseInt("" + this.properties.get("udp")));
            this.settings.setAdvancedNetworking(Boolean.parseBoolean("" + this.properties.get("adv_net")));
            return true;
        }
        catch (Exception var3)
        {
            return false;
        }
    }

    public boolean save()
    {
        try
        {
            this.properties.setProperty("isVoiceEnable", Boolean.toString(this.settings.isVoiceEnable()));
            this.properties.setProperty("sound_dist", Integer.toString(this.settings.getSoundDistance()));
            this.properties.setProperty("udp", Integer.toString(this.settings.getUDPort()));
            this.properties.setProperty("adv_net", Boolean.toString(this.settings.isAdvancedNetworkAllowed()));
            FileOutputStream var2 = new FileOutputStream(this.file);
            this.properties.store(var2, "Properties for Voice Chat Mod Server for Forge, VERSION: " + VoiceChat.getServerInstance().getVersion());
            return true;
        }
        catch (Exception var21)
        {
            var21.printStackTrace();
            return false;
        }
    }

    public void init()
    {
        if (!this.load())
        {
            VoiceChat.getLogger().info("No Configuration file found on server, will create one with default settings.");

            if (this.save())
            {
                VoiceChat.getLogger().info("Created Configuration file with default settings on server.");
            }
        }
    }
}
