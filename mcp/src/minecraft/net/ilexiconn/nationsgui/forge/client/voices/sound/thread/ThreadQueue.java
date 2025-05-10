package net.ilexiconn.nationsgui.forge.client.voices.sound.thread;

import net.ilexiconn.nationsgui.forge.client.voices.sound.Datalet;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.minecraft.client.Minecraft;

public class ThreadQueue implements Runnable
{
    public static String name;
    private SoundManager sndManager;
    private Object notifier = new Object();
    Minecraft mc = Minecraft.getMinecraft();

    public ThreadQueue(SoundManager sndManager)
    {
        this.sndManager = sndManager;
    }

    public void notifyQueue()
    {
        Object var1 = this.notifier;
        Object var2 = this.notifier;

        synchronized (this.notifier)
        {
            this.notifier.notify();
        }
    }

    public void run()
    {
        while (true)
        {
            Object data = this.notifier;
            Object data1 = this.notifier;

            synchronized (this.notifier)
            {
                if (this.sndManager.queue.isEmpty())
                {
                    try
                    {
                        this.notifier.wait();
                    }
                    catch (InterruptedException var5)
                    {
                        ;
                    }

                    continue;
                }
            }

            Datalet data11 = (Datalet)this.sndManager.queue.poll();
            boolean end = data11.data == null;

            if (this.sndManager.newDatalet(data11) && !end)
            {
                this.sndManager.createStream(data11);
            }
            else if (end)
            {
                this.sndManager.giveEnd(data11.id);
            }
            else
            {
                this.sndManager.giveStream(data11);
            }
        }
    }
}
