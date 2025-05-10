package net.ilexiconn.nationsgui.forge.server.voices.networking;

public class ThreadDataQueue implements Runnable
{
    private DataManager manager;

    public ThreadDataQueue(DataManager manager)
    {
        this.manager = manager;
    }

    public void run()
    {
        while (true)
        {
            if (!this.manager.dataQueue.isEmpty())
            {
                ServerDatalet e = (ServerDatalet)this.manager.dataQueue.poll();

                if (this.manager.newDatalet(e))
                {
                    this.manager.createStream(e);
                }
                else
                {
                    this.manager.giveStream(e);
                }
            }
            else
            {
                try
                {
                    synchronized (this)
                    {
                        this.wait(20L);
                    }
                }
                catch (InterruptedException var4)
                {
                    ;
                }
            }
        }
    }
}
