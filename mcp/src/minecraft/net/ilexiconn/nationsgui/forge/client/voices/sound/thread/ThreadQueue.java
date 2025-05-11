/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 */
package net.ilexiconn.nationsgui.forge.client.voices.sound.thread;

import net.ilexiconn.nationsgui.forge.client.voices.sound.Datalet;
import net.ilexiconn.nationsgui.forge.client.voices.sound.SoundManager;
import net.minecraft.client.Minecraft;

public class ThreadQueue
implements Runnable {
    public static String name;
    private SoundManager sndManager;
    private Object notifier = new Object();
    Minecraft mc = Minecraft.func_71410_x();

    public ThreadQueue(SoundManager sndManager) {
        this.sndManager = sndManager;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void notifyQueue() {
        Object var1 = this.notifier;
        Object object = this.notifier;
        synchronized (object) {
            this.notifier.notify();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (true) {
            boolean end;
            Object data = this.notifier;
            Object object = this.notifier;
            synchronized (object) {
                if (this.sndManager.queue.isEmpty()) {
                    try {
                        this.notifier.wait();
                    }
                    catch (InterruptedException interruptedException) {
                        // empty catch block
                    }
                    continue;
                }
            }
            Datalet data1 = this.sndManager.queue.poll();
            boolean bl = end = data1.data == null;
            if (this.sndManager.newDatalet(data1) && !end) {
                this.sndManager.createStream(data1);
                continue;
            }
            if (end) {
                this.sndManager.giveEnd(data1.id);
                continue;
            }
            this.sndManager.giveStream(data1);
        }
    }
}

