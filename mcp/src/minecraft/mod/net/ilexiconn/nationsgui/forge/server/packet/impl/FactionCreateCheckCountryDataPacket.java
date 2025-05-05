package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import net.ilexiconn.nationsgui.forge.client.gui.faction.CreateGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class FactionCreateCheckCountryDataPacket implements IPacket, IClientPacket
{
    public boolean hasCountry;

    public void fromBytes(ByteArrayDataInput data)
    {
        this.hasCountry = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data) {}

    public void handleClientPacket(EntityPlayer player)
    {
        CreateGui.playerHasCountry = this.hasCountry;
    }
}
