package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.util.ArrayList;
import java.util.HashMap;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionSaveSettingsDataPacket implements IPacket, IClientPacket
{
    public String factionName;
    private final String motd;
    private final String entryMsg;
    private final String discord;
    private final ArrayList<String> tags;
    private final boolean recruitmentOpen;
    public boolean isOpen;
    private final LinkedTreeMap<String, String> rolesName;
    public String description;
    private HashMap<String, Object> perms;
    private boolean kickWarRecruit;

    public FactionSaveSettingsDataPacket(String factionName, String description, String motd, String entryMsg, String discord, ArrayList<String> tags, boolean isOpen, boolean recruitmentOpen, LinkedTreeMap<String, String> rolesName, HashMap<String, Object> perms, boolean kickWarRecruit)
    {
        this.factionName = factionName;
        this.description = description;
        this.motd = motd;
        this.entryMsg = entryMsg;
        this.discord = discord;
        this.tags = tags;
        this.isOpen = isOpen;
        this.recruitmentOpen = recruitmentOpen;
        this.rolesName = rolesName;
        this.perms = perms;
        this.kickWarRecruit = kickWarRecruit;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.factionName);
        data.writeUTF(this.description);
        data.writeUTF(this.motd);
        data.writeUTF(this.entryMsg);
        data.writeUTF(this.discord);
        data.writeUTF((new Gson()).toJson(this.tags));
        data.writeBoolean(this.isOpen);
        data.writeBoolean(this.recruitmentOpen);
        data.writeUTF((new Gson()).toJson(this.rolesName));
        data.writeUTF((new Gson()).toJson(this.perms));
        data.writeBoolean(this.kickWarRecruit);
    }

    public void handleClientPacket(EntityPlayer player) {}
}
