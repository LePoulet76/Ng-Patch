package net.ilexiconn.nationsgui.forge.client;

import java.io.IOException;

class ClientSocket$1 implements Runnable
{
    final ClientSocket this$0;

    ClientSocket$1(ClientSocket this$0)
    {
        this.this$0 = this$0;
    }

    public void run()
    {
        ClientSocket.access$000().run();

        while (ClientSocket.tryToReconnect)
        {
            try
            {
                if (System.currentTimeMillis() - ClientSocket.lastSocketPing > 30000L)
                {
                    System.out.println("[Socket] No ping received from server, trying to reconnect...");
                    throw new Exception("No ping received from server");
                }

                ClientSocket.socket.getOutputStream().write(666);
                Thread.sleep(5000L);
            }
            catch (Exception var5)
            {
                System.out.println("[Socket] Erreur d\u00e9tect\u00e9e, tentative de reconnexion...");

                try
                {
                    if (ClientSocket.socket != null && !ClientSocket.socket.isClosed())
                    {
                        ClientSocket.socket.close();
                        System.out.println("[Socket] Socket ferm\u00e9 proprement.");
                    }
                }
                catch (IOException var4)
                {
                    System.out.println("[Socket] Erreur lors de la fermeture du socket : " + var4.getMessage());
                }

                try
                {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException var3)
                {
                    System.out.println("[Socket] Thread interrompu : " + var3.getMessage());
                }

                ClientSocket.access$000().run();
            }
        }
    }
}
