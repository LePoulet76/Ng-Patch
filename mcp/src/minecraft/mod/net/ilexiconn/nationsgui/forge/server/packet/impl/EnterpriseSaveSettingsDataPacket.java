package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseSaveSettingsDataPacket implements IPacket, IClientPacket
{
    public String enterpriseName;
    public String newDescription;
    public boolean isOpen;
    public String services;

    public EnterpriseSaveSettingsDataPacket(String factionName, String newDescription, boolean isOpen, String services)
    {
        this.enterpriseName = factionName;
        this.newDescription = newDescription;
        this.isOpen = isOpen;
        this.services = services;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
        data.writeUTF(this.newDescription);
        data.writeBoolean(this.isOpen);
        data.writeUTF(this.services);
    }

    public void handleClientPacket(EntityPlayer player) {}
}
