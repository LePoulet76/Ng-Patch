package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import fr.nationsglory.ngcontent.client.render.block.IslandsPlateEntityRenderer;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.minecraft.entity.player.EntityPlayer;

public class IslandsTogglePlatesPacket implements IPacket, IClientPacket
{
    public boolean can;

    public IslandsTogglePlatesPacket(boolean can)
    {
        this.can = can;
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.can = data.readBoolean();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeBoolean(this.can);
    }

    public void handleClientPacket(EntityPlayer player)
    {
        IslandsPlateEntityRenderer.renderHolo = this.can;
    }
}
