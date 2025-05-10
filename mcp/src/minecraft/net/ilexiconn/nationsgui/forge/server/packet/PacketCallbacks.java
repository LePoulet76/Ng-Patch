package net.ilexiconn.nationsgui.forge.server.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.ilexiconn.nationsgui.forge.server.packet.PacketCallbacks$1;
import net.minecraft.entity.player.EntityPlayer;

public enum PacketCallbacks
{
    PERMISSION,
    MONEY,
    TICKETS,
    TELEPORT,
    REMOVE_TICKET,
    TICKET_RESOLVED,
    NEW_TICKET,
    REQUEST_SONG;

    public abstract void handleCallback(EntityPlayer var1, ByteArrayDataInput var2);

    public void send(String ... extra)
    {
        ByteArrayDataOutput data = ByteStreams.newDataOutput();
        data.writeInt(this.ordinal() - 50);
        data.writeInt(extra.length);
        String[] var3 = extra;
        int var4 = extra.length;

        for (int var5 = 0; var5 < var4; ++var5)
        {
            String s = var3[var5];
            data.writeUTF(s);
        }

        PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket("nationsgui", data.toByteArray()));
    }

    PacketCallbacks(PacketCallbacks$1 x2)
    {
        this(x0, x1);
    }
}
