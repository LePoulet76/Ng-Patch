package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import java.util.Iterator;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IServerPacket;
import net.ilexiconn.nationsgui.forge.server.packet.PacketRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class StartAnimationPacket implements IPacket, IServerPacket
{
    private String emote;
    private boolean playerOnly = false;

    public StartAnimationPacket(String emote, boolean playerOnly)
    {
        this.emote = emote;
        this.playerOnly = playerOnly;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.emote = data.readUTF();
        this.playerOnly = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.emote);
        data.writeBoolean(this.playerOnly);
    }

    public void handleServerPacket(EntityPlayer player)
    {
        if (this.emote != null && !this.emote.isEmpty())
        {
            if (this.playerOnly)
            {
                PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(this.emote, player.username)), (Player)player);
            }
            else
            {
                Iterator var2 = player.worldObj.getEntitiesWithinAABB(player.getClass(), AxisAlignedBB.getAABBPool().getAABB(player.posX - 50.0D, player.posY - 50.0D, player.posZ - 50.0D, player.posX + 50.0D, player.posY + 50.0D, player.posZ + 50.0D)).iterator();

                while (var2.hasNext())
                {
                    Object e = var2.next();

                    if (e instanceof Player)
                    {
                        PacketDispatcher.sendPacketToPlayer(PacketRegistry.INSTANCE.generatePacket(new PacketEmote(this.emote, player.username)), (Player)e);
                    }
                }
            }
        }
    }
}
