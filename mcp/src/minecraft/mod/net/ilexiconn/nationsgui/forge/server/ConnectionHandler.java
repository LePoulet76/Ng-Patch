package net.ilexiconn.nationsgui.forge.server;

import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.ReflectionHelper;
import java.util.Arrays;
import net.ilexiconn.nationsgui.forge.NationsGUI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.TcpConnection;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

public class ConnectionHandler implements IConnectionHandler
{
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {}

    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager)
    {
        return null;
    }

    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {}

    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {}

    public void connectionClosed(INetworkManager manager)
    {
        if (manager instanceof TcpConnection)
        {
            TcpConnection tcpConnection = (TcpConnection)manager;
            String reason = (String)ReflectionHelper.getPrivateValue(TcpConnection.class, tcpConnection, new String[] {"terminationReason", "terminationReason", "w"});

            if (reason.equals("disconnect.overflow"))
            {
                NetHandler netHandler = (NetHandler)ReflectionHelper.getPrivateValue(TcpConnection.class, tcpConnection, new String[] {"theNetHandler", "theNetHandler", "s"});
                EntityPlayer entityPlayer = netHandler.getPlayer();

                if (Arrays.asList(new String[] {"world", "dim-1", "dim-1"}).contains(entityPlayer.getEntityWorld().getWorldInfo().getWorldName().toLowerCase()))
                {
                    NationsGUI.spawnOffPlayer(entityPlayer.username);
                }
            }
        }
    }

    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {}
}
