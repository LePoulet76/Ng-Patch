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

public class CosmeticBuyPacket implements IPacket, IClientPacket
{
    private String playerTarget;
    private String skinName;
    private String categoryId;

    public CosmeticBuyPacket(String playerTarget, String skinName, String categoryId)
    {
        this.playerTarget = playerTarget;
        this.skinName = skinName;
        this.categoryId = categoryId;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.playerTarget = data.readUTF();
        this.skinName = data.readUTF();
        this.categoryId = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.playerTarget);
        data.writeUTF(this.skinName);
        data.writeUTF(this.categoryId);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientProxy.SKIN_MANAGER.clearPlayerCachedSkins(this.playerTarget, (SkinType)null);
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticDataPacket(this.playerTarget)));

        if (!this.categoryId.isEmpty())
        {
            PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new CosmeticCategoryDataPacket(this.categoryId, this.playerTarget)));
        }
    }
}
