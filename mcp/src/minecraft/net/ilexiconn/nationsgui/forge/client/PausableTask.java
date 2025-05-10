package net.ilexiconn.nationsgui.forge.client;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public abstract class PausableTask implements Runnable
{
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future<?> publisher;
    protected volatile int counter;

    private void someJob()
    {
        System.out.println("Job Done :- " + this.counter);
    }

    abstract void task();

    public void run()
    {
        while (true)
        {
            Thread.currentThread();

            if (Thread.interrupted())
            {
                return;
            }

            this.task();
        }
    }

    public void start()
    {
        this.publisher = this.executor.submit(this);
    }

    public void pause()
    {
        this.publisher.cancel(true);
    }

    public void resume()
    {
        this.start();
    }

    public void stop()
    {
        this.executor.shutdownNow();
    }

    public boolean isStoped()
    {
        return this.executor.isShutdown();
    }
}
