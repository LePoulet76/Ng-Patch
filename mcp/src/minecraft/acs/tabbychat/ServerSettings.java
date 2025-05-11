/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ServerData
 */
package acs.tabbychat;

import acs.tabbychat.ChannelDelimEnum;
import acs.tabbychat.ChatColorEnum;
import acs.tabbychat.CustomChatFilter;
import acs.tabbychat.GlobalSettings;
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

public class ServerSettings {
    private ServerData server;
    private static File settingsFile;
    protected ChannelDelimEnum chanDelims = ChannelDelimEnum.BRACKETS;
    protected ChatColorEnum chanDelimColor = ChatColorEnum.DEFAULTCOLOR;
    protected ChatColorEnum chanDelimFormat = ChatColorEnum.DEFAULTFORMAT;
    protected ArrayList<CustomChatFilter> customFilters = new ArrayList();
    protected String[] defaultChans = new String[0];
    protected String[] ignoredChans = new String[0];
    public String ip;
    public String name;

    public ServerSettings() {
        this.updateForServer();
    }

    protected void loadSettings() {
        if (settingsFile != null) {
            if (!settingsFile.exists()) {
                return;
            }
        }
        File source = settingsFile;
        try {
            FileInputStream settingsStream = new FileInputStream(source);
            ObjectInputStream ssObjStream = new ObjectInputStream(settingsStream);
            this.chanDelims = (ChannelDelimEnum)((Object)ssObjStream.readObject());
            this.defaultChans = (String[])ssObjStream.readObject();
            this.ignoredChans = (String[])ssObjStream.readObject();
            int numFilters = ssObjStream.readInt();
            this.customFilters.clear();
            for (int i = 0; i < numFilters; ++i) {
                this.customFilters.add((CustomChatFilter)ssObjStream.readObject());
                if (this.customFilters.get((int)i).highlightColor == null) {
                    this.customFilters.get((int)i).highlightColor = ChatColorEnum.RED;
                }
                if (this.customFilters.get((int)i).highlightFormat != null) continue;
                this.customFilters.get((int)i).highlightFormat = ChatColorEnum.BOLD;
            }
            this.chanDelimColor = (ChatColorEnum)((Object)ssObjStream.readObject());
            this.chanDelimFormat = (ChatColorEnum)((Object)ssObjStream.readObject());
            ssObjStream.close();
            settingsStream.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void saveSettings() {
        try {
            FileOutputStream ssOutStream = new FileOutputStream(settingsFile);
            ObjectOutputStream ssObjStream = new ObjectOutputStream(ssOutStream);
            ssObjStream.writeObject((Object)this.chanDelims);
            ssObjStream.writeObject(this.defaultChans);
            ssObjStream.writeObject(this.ignoredChans);
            ssObjStream.writeInt(this.customFilters.size());
            for (int i = 0; i < this.customFilters.size(); ++i) {
                ssObjStream.writeObject(this.customFilters.get(i));
            }
            ssObjStream.writeObject((Object)this.chanDelimColor);
            ssObjStream.writeObject((Object)this.chanDelimFormat);
            ssObjStream.close();
            ssOutStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void updateForServer() {
        boolean clear = false;
        if (Minecraft.func_71410_x().func_71356_B() || ServerSettings.getServerData() == null) {
            this.server = null;
            settingsFile = null;
            this.name = "";
            this.ip = "";
        } else {
            this.server = ServerSettings.getServerData();
            this.name = this.server.field_78847_a;
            this.ip = this.server.field_78845_b;
            File settingsDir = new File(GlobalSettings.tabbyChatDir, "servers");
            settingsFile = new File(settingsDir, this.ip + ".cfg");
        }
    }

    public static ServerData getServerData() {
        Minecraft mc = Minecraft.func_71410_x();
        ServerData serverData = null;
        for (Field field : Minecraft.class.getDeclaredFields()) {
            if (field.getType() != ServerData.class) continue;
            field.setAccessible(true);
            try {
                serverData = (ServerData)field.get(mc);
            }
            catch (Exception e) {
                System.out.println("[TukMC] Unable to find server information (" + e.getCause().toString() + ")");
            }
        }
        return serverData;
    }
}

