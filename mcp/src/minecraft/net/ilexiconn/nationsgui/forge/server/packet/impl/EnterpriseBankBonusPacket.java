package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class EnterpriseBankBonusPacket implements IPacket, IClientPacket
{
    public String enterpriseName;
    public String memberName;
    public Integer amount;

    public EnterpriseBankBonusPacket(String enterpriseName, String memberName, Integer amount)
    {
        this.enterpriseName = enterpriseName;
        this.memberName = memberName;
        this.amount = amount;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.enterpriseName = data.readUTF();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.enterpriseName);
        data.writeUTF(this.memberName);
        data.writeInt(this.amount.intValue());
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new EnterpriseBankDataPacket(this.enterpriseName)));
    }
}
