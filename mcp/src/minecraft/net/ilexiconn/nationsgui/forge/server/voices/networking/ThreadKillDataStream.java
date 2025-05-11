/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayerMP
 */
package net.ilexiconn.nationsgui.forge.server.voices.networking;

import java.util.Map;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataManager;
import net.ilexiconn.nationsgui.forge.server.voices.networking.DataStream;
import net.minecraft.entity.player.EntityPlayerMP;

public class ThreadKillDataStream
implements Runnable {
    DataManager dataManager;

    public ThreadKillDataStream(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        while (true) {
            if (!this.dataManager.streaming.isEmpty()) {
                for (Map.Entry entry : this.dataManager.streaming.entrySet()) {
                    DataStream stream = (DataStream)entry.getValue();
                    if (System.currentTimeMillis() - stream.lastUpdated <= 250L || System.currentTimeMillis() - stream.lastUpdated <= (long)(((EntityPlayerMP)stream.player).field_71138_i * 2)) continue;
                    this.dataManager.killStream(stream.id);
                }
            }
            try {
                ThreadKillDataStream e = this;
                synchronized (e) {
                    this.wait(500L);
                    continue;
                }
            }
            catch (InterruptedException var6) {
                var6.printStackTrace();
                continue;
            }
            break;
        }
    }
}

