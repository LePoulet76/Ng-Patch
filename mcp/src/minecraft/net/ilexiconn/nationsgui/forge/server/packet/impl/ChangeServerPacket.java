package net.ilexiconn.nationsgui.forge.server.packet.impl;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Timer;
import net.ilexiconn.nationsgui.forge.client.ClientData;
import net.ilexiconn.nationsgui.forge.client.ClientProxy;
import net.ilexiconn.nationsgui.forge.client.ClientSocket;
import net.ilexiconn.nationsgui.forge.client.gui.ServerSwitchExpressGui;
import net.ilexiconn.nationsgui.forge.server.packet.IClientPacket;
import net.ilexiconn.nationsgui.forge.server.packet.IPacket;
import net.ilexiconn.nationsgui.forge.server.packet.impl.ChangeServerPacket$1;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class ChangeServerPacket implements IPacket, IClientPacket
{
    private String address;
    private int port;

    public ChangeServerPacket(String address, int port)
    {
        this.address = address;
        this.port = port;
    }

    public ChangeServerPacket() {}

    @SideOnly(Side.CLIENT)
    public void handleClientPacket(EntityPlayer player)
    {
        String serverName = ClientProxy.getServerNameByIpAndPort(this.address + ":" + this.port);

        if (serverName != null)
        {
            ClientSocket.out.println("MESSAGE socket ADD_WAITINGLIST " + serverName);
            ClientData.waitingJoinTime = Long.valueOf(System.currentTimeMillis());
            (new Timer()).schedule(new ChangeServerPacket$1(this, serverName), 1500L);
        }
        else
        {
            Minecraft.getMinecraft().displayGuiScreen(new ServerSwitchExpressGui(this.address, this.port, (String)null));
        }
    }

    public void fromBytes(ByteArrayDataInput data)
    {
        this.address = data.readUTF();
        this.port = data.readInt();
    }

    public void toBytes(ByteArrayDataOutput data)
    {
        data.writeUTF(this.address);
        data.writeInt(this.port);
    }

    static String access$000(ChangeServerPacket x0)
    {
        return x0.address;
    }

    static int access$100(ChangeServerPacket x0)
    {
        return x0.port;
    }
}
