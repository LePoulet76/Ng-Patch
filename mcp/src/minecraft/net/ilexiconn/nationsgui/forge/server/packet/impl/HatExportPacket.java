package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.itemskin.SkinType;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class HatExportPacket implements IPacket, IClientPacket
{
    private String hatIdentifier;

    public HatExportPacket(String hatIdentifier)
    {
        this.hatIdentifier = hatIdentifier;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.hatIdentifier = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.hatIdentifier);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.SKIN_MANAGER.clearPlayerCachedSkins(player.username, (SkinType)null);
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket("hats", player.username)));
    }
}
