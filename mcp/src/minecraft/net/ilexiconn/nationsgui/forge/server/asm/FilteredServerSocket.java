package net.ilexiconn.nationsgui.forge.server.asm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import net.ilexiconn.nationsgui.forge.NationsGUI;

public class FilteredServerSocket extends ServerSocket
{
    public FilteredServerSocket(int port) throws IOException
    {
        super(port);
        System.out.println("FilteredServerSocket created on port: " + port);
    }

    public Socket accept() throws IOException
    {
        Socket socket = super.accept();
        String remoteAddress = socket.getInetAddress().getHostAddress();

        if (NationsGUI.getServerType().equalsIgnoreCase(""))
        {
            return socket;
        }
        else if (!NationsGUIHooks.allowedIPConnection.contains(remoteAddress) && !remoteAddress.equalsIgnoreCase("127.0.0.1") && !remoteAddress.equalsIgnoreCase("51.38.177.21"))
        {
            socket.close();
            throw new IOException("DEBUG FilteredServerSocket - REFUSE CONNECTION : " + remoteAddress);
        }
        else
        {
            return socket;
        }
    }
}
