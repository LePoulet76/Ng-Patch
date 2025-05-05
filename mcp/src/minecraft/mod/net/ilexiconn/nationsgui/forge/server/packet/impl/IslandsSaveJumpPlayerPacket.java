package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import fr.nationsglory.ngcontent.server.block.entity.IslandsPlateBlockEntity;
import net.ilexiconn.nationsgui.forge.client.ClientEventHandler;
import net.ilexiconn.nationsgui.forge.client.gui.override.BuildOverride;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class IslandsSaveJumpPlayerPacket implements IPacket, IServerPacket, IClientPacket
{
    private String position;
    private String positionType;
    private long time;

    public IslandsSaveJumpPlayerPacket(String position, String positionType, long time)
    {
        this.position = position;
        this.positionType = positionType;
        this.time = time;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.position = data.readUTF();
        this.positionType = data.readUTF();
        this.time = data.readLong();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.position);
        data.writeUTF(this.positionType);
        data.writeLong(this.time);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        if (this.time != -1L)
        {
            TileEntity tileEntity = player.worldObj.getBlockTileEntity(Integer.parseInt(this.position.split("#")[0]), Integer.parseInt(this.position.split("#")[1]), Integer.parseInt(this.position.split("#")[2]));

            if (tileEntity != null && tileEntity instanceof IslandsPlateBlockEntity)
            {
                ((IslandsPlateBlockEntity)tileEntity).updateRecords(player.username, Long.valueOf(this.time));
                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(this), (Player)player);
            }
        }
    }

    public void handleClientPacket(EntityPlayer player)
    {
        ClientEventHandler.getInstance().getTitleOverlay().displayTitle("\u00a7a\u00a7lJump termin\u00e9", "\u00a76Votre temps: \u00a7e" + BuildOverride.chronoTimeToStr(Long.valueOf(this.time), true), 60, 0, 0);
    }
}
