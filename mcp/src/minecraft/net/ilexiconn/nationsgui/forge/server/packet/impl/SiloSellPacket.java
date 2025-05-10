package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;

public class SiloSellPacket implements IPacket, IClientPacket
{
    private String cereal;
    private int amount;
    private int price;
    private int blockX;
    private int blockY;
    private int blockZ;
    private boolean valid;

    public SiloSellPacket(String cereal, int amount, int price, int blockX, int blockY, int blockZ, boolean valid)
    {
        this.cereal = cereal;
        this.amount = amount;
        this.price = price;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
        this.valid = valid;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.cereal = data.readUTF();
        this.amount = data.readInt();
        this.price = data.readInt();
        this.blockX = data.readInt();
        this.blockY = data.readInt();
        this.blockZ = data.readInt();
        this.valid = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.cereal);
        data.writeInt(this.amount);
        data.writeInt(this.price);
        data.writeInt(this.blockX);
        data.writeInt(this.blockY);
        data.writeInt(this.blockZ);
        data.writeBoolean(this.valid);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        PacketDispatcher.sendPacketToServer(PacketRegistry.INSTANCE.generatePacket(new SiloInfosMachinePacket(this.blockX, this.blockY, this.blockZ)));
    }
}
