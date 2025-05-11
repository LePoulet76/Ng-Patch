/*
 * Decompiled with CFR 0.152.
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking;

import net.ilexiconn.nationsgui.forge.server.voices.networking.DataManager;
import net.ilexiconn.nationsgui.forge.server.voices.networking.ServerDatalet;

public class ThreadDataQueue
implements Runnable {
    private DataManager manager;

    public ThreadDataQueue(DataManager manager) {
        this.manager = manager;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (true) {
            if (!this.manager.dataQueue.isEmpty()) {
                ServerDatalet e = (ServerDatalet)this.manager.dataQueue.poll();
                if (this.manager.newDatalet(e)) {
                    this.manager.createStream(e);
                    continue;
                }
                this.manager.giveStream(e);
                continue;
            }
            try {
                ThreadDataQueue threadDataQueue = this;
                synchronized (threadDataQueue) {
                    this.wait(20L);
                    continue;
                }
            }
            catch (InterruptedException interruptedException) {
                continue;
            }
            break;
        }
    }
}

