package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionGalleryAddImagePacket implements IPacket, IClientPacket
{
    public String imageLink;

    public FactionGalleryAddImagePacket(String imageLink)
    {
        this.imageLink = imageLink;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.imageLink);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGalleryDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }
}
