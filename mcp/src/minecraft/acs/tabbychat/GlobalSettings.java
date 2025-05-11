/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package acs.tabbychat;

import acs.tabbychat.TimeStampEnum;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import net.minecraft.client.Minecraft;

public class GlobalSettings {
    private File settingsFile;
    protected static File tabbyChatDir = new File(Minecraft.func_71410_x().field_71412_D, "config" + File.separatorChar + "tabbychat");
    public boolean autoSearchEnabled = true;
    public int maxChannelNameLength = 10;
    public boolean saveLocalLogEnabled = false;
    public boolean TCenabled = true;
    public boolean timestampsEnabled = false;
    public TimeStampEnum timestampStyle = TimeStampEnum.MILITARYWITHCOLON;
    public int retainedChats = 100;

    public GlobalSettings() {
        this.settingsFile = new File(tabbyChatDir, "global.cfg");
    }

    protected void loadSettings() {
        File source = this.settingsFile;
        if (!this.settingsFile.exists()) {
            return;
        }
        try {
            FileInputStream settingsStream = new FileInputStream(source);
            ObjectInputStream gsObjStream = new ObjectInputStream(settingsStream);
            this.TCenabled = gsObjStream.readBoolean();
            this.autoSearchEnabled = gsObjStream.readBoolean();
            this.retainedChats = gsObjStream.readInt();
            this.maxChannelNameLength = gsObjStream.readInt();
            this.timestampsEnabled = gsObjStream.readBoolean();
            this.saveLocalLogEnabled = gsObjStream.readBoolean();
            this.timestampStyle = (TimeStampEnum)((Object)gsObjStream.readObject());
            gsObjStream.close();
            settingsStream.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    protected void saveSettings() {
        try {
            FileOutputStream settingsStream = new FileOutputStream(this.settingsFile);
            ObjectOutputStream gsObjStream = new ObjectOutputStream(settingsStream);
            gsObjStream.writeBoolean(this.TCenabled);
            gsObjStream.writeBoolean(this.autoSearchEnabled);
            gsObjStream.writeInt(this.retainedChats);
            gsObjStream.writeInt(this.maxChannelNameLength);
            gsObjStream.writeBoolean(this.timestampsEnabled);
            gsObjStream.writeBoolean(this.saveLocalLogEnabled);
            gsObjStream.writeObject((Object)this.timestampStyle);
            gsObjStream.close();
            settingsStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }
}

