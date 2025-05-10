package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.gui.faction.FactionGUI;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class FactionGalleryDeleteImagePacket implements IPacket, IClientPacket
{
    public String target;
    public Integer imageIndex;
    public Integer imageId;

    public FactionGalleryDeleteImagePacket(String targetName, Integer imageIndex, Integer imageId)
    {
        this.target = targetName;
        this.imageIndex = imageIndex;
        this.imageId = imageId;
    }

    public void fromBytes(ByteArrayDataInput data) {}

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.target);
        data.writeInt(this.imageIndex.intValue());
        data.writeInt(this.imageId.intValue());
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new FactionGalleryDataPacket((String)FactionGUI.factionInfos.get("name"))));
    }
}
