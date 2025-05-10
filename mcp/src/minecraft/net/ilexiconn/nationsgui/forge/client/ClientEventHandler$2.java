package net.ilexiconn.nationsgui.forge.client;

class ClientEventHandler$2 extends Thread
{
    final ClientEventHandler this$0;

    ClientEventHandler$2(ClientEventHandler this$0)
    {
        this.this$0 = this$0;
    }

    public void run()
    {
        while (this.isAlive())
        {
            ClientEventHandler.lastClicks = ClientEventHandler.currentClicks;
            ClientEventHandler.currentClicks = 0;

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException var2)
            {
                ;
            }
        }
    }
}
