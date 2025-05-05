package acs.tabbychat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class ServerSettings
{
    private ServerData server;
    private static File settingsFile;
    protected ChannelDelimEnum chanDelims;
    protected ChatColorEnum chanDelimColor;
    protected ChatColorEnum chanDelimFormat;
    protected ArrayList<CustomChatFilter> customFilters;
    protected String[] defaultChans;
    protected String[] ignoredChans;
    public String ip;
    public String name;

    public ServerSettings()
    {
        this.chanDelims = ChannelDelimEnum.BRACKETS;
        this.chanDelimColor = ChatColorEnum.DEFAULTCOLOR;
        this.chanDelimFormat = ChatColorEnum.DEFAULTFORMAT;
        this.customFilters = new ArrayList();
        this.defaultChans = new String[0];
        this.ignoredChans = new String[0];
        this.updateForServer();
    }

    protected void loadSettings()
    {
        if (settingsFile == null || settingsFile.exists())
        {
            File source = settingsFile;

            try
            {
                FileInputStream settingsStream = new FileInputStream(source);
                ObjectInputStream ssObjStream = new ObjectInputStream(settingsStream);
                this.chanDelims = (ChannelDelimEnum)ssObjStream.readObject();
                this.defaultChans = (String[])((String[])ssObjStream.readObject());
                this.ignoredChans = (String[])((String[])ssObjStream.readObject());
                int numFilters = ssObjStream.readInt();
                this.customFilters.clear();

                for (int i = 0; i < numFilters; ++i)
                {
                    this.customFilters.add((CustomChatFilter)ssObjStream.readObject());

                    if (((CustomChatFilter)this.customFilters.get(i)).highlightColor == null)
                    {
                        ((CustomChatFilter)this.customFilters.get(i)).highlightColor = ChatColorEnum.RED;
                    }

                    if (((CustomChatFilter)this.customFilters.get(i)).highlightFormat == null)
                    {
                        ((CustomChatFilter)this.customFilters.get(i)).highlightFormat = ChatColorEnum.BOLD;
                    }
                }

                this.chanDelimColor = (ChatColorEnum)ssObjStream.readObject();
                this.chanDelimFormat = (ChatColorEnum)ssObjStream.readObject();
                ssObjStream.close();
                settingsStream.close();
            }
            catch (Exception var6)
            {
                ;
            }
        }
    }

    protected void saveSettings()
    {
        try
        {
            FileOutputStream ssOutStream = new FileOutputStream(settingsFile);
            ObjectOutputStream ssObjStream = new ObjectOutputStream(ssOutStream);
            ssObjStream.writeObject(this.chanDelims);
            ssObjStream.writeObject(this.defaultChans);
            ssObjStream.writeObject(this.ignoredChans);
            ssObjStream.writeInt(this.customFilters.size());

            for (int i = 0; i < this.customFilters.size(); ++i)
            {
                ssObjStream.writeObject(this.customFilters.get(i));
            }

            ssObjStream.writeObject(this.chanDelimColor);
            ssObjStream.writeObject(this.chanDelimFormat);
            ssObjStream.close();
            ssOutStream.close();
        }
        catch (IOException var4)
        {
            ;
        }
    }

    public void updateForServer()
    {
        boolean clear = false;

        if (!Minecraft.getMinecraft().isSingleplayer() && getServerData() != null)
        {
            this.server = getServerData();
            this.name = this.server.serverName;
            this.ip = this.server.serverIP;
            File settingsDir = new File(GlobalSettings.tabbyChatDir, "servers");
            settingsFile = new File(settingsDir, this.ip + ".cfg");
        }
        else
        {
            this.server = null;
            settingsFile = null;
            this.name = "";
            this.ip = "";
        }
    }

    public static ServerData getServerData()
    {
        Minecraft mc = Minecraft.getMinecraft();
        ServerData serverData = null;
        Field[] var2 = Minecraft.class.getDeclaredFields();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4)
        {
            Field field = var2[var4];

            if (field.getType() == ServerData.class)
            {
                field.setAccessible(true);

                try
                {
                    serverData = (ServerData)field.get(mc);
                }
                catch (Exception var7)
                {
                    System.out.println("[TukMC] Unable to find server information (" + var7.getCause().toString() + ")");
                }
            }
        }

        return serverData;
    }
}
