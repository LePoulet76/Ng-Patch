package net.ilexiconn.nationsgui.forge.client;

import java.io.IOException;

class ClientSocket$2 implements Runnable
{
    final ClientSocket this$0;

    ClientSocket$2(ClientSocket this$0)
    {
        this.this$0 = this$0;
    }

    public void run()
    {
        while (true)
        {
            System.out.println("[Socket] Last ping : " + (System.currentTimeMillis() - ClientSocket.lastSocketPing) + "ms");

            if (System.currentTimeMillis() - ClientSocket.lastSocketPing > 30000L)
            {
                try
                {
                    if (ClientSocket.socket != null && !ClientSocket.socket.isClosed())
                    {
                        ClientSocket.socket.close();
                        System.out.println("[Socket] Socket ferm\u00e9 proprement car aucune activit\u00e9 durant les 30 derni\u00e8res secondes");
                    }
                }
                catch (IOException var3)
                {
                    throw new RuntimeException(var3);
                }
            }

            try
            {
                Thread.sleep(5000L);
            }
            catch (InterruptedException var2)
            {
                throw new RuntimeException(var2);
            }
        }
    }
}
